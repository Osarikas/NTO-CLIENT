package ru.myitschool.work.data

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path
import ru.myitschool.work.RequestBodyModel
import ru.myitschool.work.data.dto.UserDTO

interface SAPI {
    @GET("/api/{login}/info")
    fun getInfo(@Path("login") login: String): Call<UserDTO>

    @PATCH("/api/{login}/open")
    fun openDoor(@Path("login") login: String, @Body body: RequestBodyModel): Call<Void>
}