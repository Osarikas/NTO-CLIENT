package ru.myitschool.work.ui

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.createGraph
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.fragment
import dagger.hilt.android.AndroidEntryPoint
import ru.myitschool.work.R
import ru.myitschool.work.databinding.ActivityRootBinding
import ru.myitschool.work.ui.login.LoginFragment
import ru.myitschool.work.ui.qr.scan.QrScanDestination
import ru.myitschool.work.ui.qr.scan.QrScanFragment

// НЕ ИЗМЕНЯЙТЕ НАЗВАНИЕ КЛАССА!
@AndroidEntryPoint
class RootActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRootBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRootBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        val windowInsetsController = window.insetsController
        windowInsetsController?.hide(android.view.WindowInsets.Type.statusBars() or android.view.WindowInsets.Type.navigationBars())
        sharedPreferences = getSharedPreferences("LoginSave", MODE_PRIVATE)


    }
}