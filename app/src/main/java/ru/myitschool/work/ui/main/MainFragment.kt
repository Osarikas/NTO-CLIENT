package ru.myitschool.work.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import ru.myitschool.work.R
import ru.myitschool.work.data.dto.UserDTO
import ru.myitschool.work.databinding.FragmentMainBinding
import ru.myitschool.work.ui.qr.scan.QrScanDestination
import ru.myitschool.work.utils.UserState
import ru.myitschool.work.utils.collectWhenStarted

class MainFragment : Fragment(R.layout.fragment_main) {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMainBinding.bind(view)


        binding.refresh.setOnClickListener { viewModel.getUserData() }
        binding.logout.setOnClickListener { logout() }
        binding.scan.setOnClickListener { onScanClick() }

        setFragmentResultListener(QrScanDestination.REQUEST_KEY) { _, bundle ->
            val qrData = QrScanDestination.getDataIfExist(bundle)
            qrData?.let { navigateToResult(it) }
        }
    }

    private fun logout() {
        lifecycleScope.launch{
            viewModel.clearUsername()
            findNavController().navigate(R.id.loginFragment)
        }

    }

    private fun observeViewModel() {
        viewModel.userState.collectWhenStarted(viewLifecycleOwner as Fragment) { userState ->
            when (userState) {
                is UserState.Loading -> {
                    binding.loading.visibility = View.VISIBLE
                    //hideViews()
                }
                is UserState.Success -> {
                    binding.loading.visibility = View.GONE
                    showUserData(userState.userDTO)
                }
                is UserState.Error -> {
                    binding.loading.visibility = View.GONE
                    showError()
                }
            }
        }
    }

    private fun showUserData(userDTO: UserDTO) {
        binding.apply {
            fullname.text = userDTO.name
            position.text = userDTO.position
            lastEntry.text = viewModel.formatDate(userDTO.lastVisit)
            Picasso.get().load(userDTO.photo).into(photo)

            error.visibility = View.GONE
            setViewsVisibility(View.VISIBLE)
        }
    }

    private fun showError() {
        binding.error.visibility = View.VISIBLE
        setViewsVisibility(View.GONE)
    }

    private fun setViewsVisibility(visibility: Int) {
        binding.fullname.visibility = visibility
        binding.position.visibility = visibility
        binding.lastEntry.visibility = visibility
        binding.photo.visibility = visibility
        binding.logout.visibility = visibility
        binding.scan.visibility = visibility
    }





    private fun onScanClick() {
        findNavController().navigate(R.id.action_mainFragment_to_qrScanFragment)
    }

    private fun navigateToResult(qrData: String) {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}