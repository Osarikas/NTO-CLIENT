package ru.myitschool.work.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.myitschool.work.data.UserDataStoreManager
import ru.myitschool.work.utils.UserState
import java.text.SimpleDateFormat
import java.util.Locale

class MainViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val _userState = MutableStateFlow<UserState>(UserState.Loading)
    val userState: StateFlow<UserState> get() = _userState

    private val dataStoreManager = UserDataStoreManager(application)




    fun formatDate(date: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val outputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        return try {
            val formattedDate = inputFormat.parse(date)
            if (formattedDate != null) {
                outputFormat.format(formattedDate)
            } else{}
        } catch (e: Exception) {
            "Invalid Date"
        }.toString()
    }

    fun getUserData() {}
    fun logout() {

    }

    fun clearUsername() {
        viewModelScope.launch{
            withContext(Dispatchers.IO) {
                dataStoreManager.clearUsername()
            }
        }

    }
}
