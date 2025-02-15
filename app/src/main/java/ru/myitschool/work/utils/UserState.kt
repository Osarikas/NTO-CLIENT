package ru.myitschool.work.utils

import ru.myitschool.work.data.dto.UserDTO

sealed class UserState {
    object Loading : UserState()
    data class Success(val userDTO: UserDTO) : UserState()
    object Error : UserState()
}