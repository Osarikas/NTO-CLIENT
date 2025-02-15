package ru.myitschool.work.data.login

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.basicAuth
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import ru.myitschool.work.core.Constants
import ru.myitschool.work.utils.NetworkModule

class LoginNetworkDataSource {
    private val client = NetworkModule.httpClient
    suspend fun login(username: String):Result<Unit> = withContext(Dispatchers.IO){
        runCatching {
            val result = client.get("${Constants.SERVER_ADDRESS}/api/$username/auth")

            if (result.status != HttpStatusCode.OK) {
                error("Status ${result.status}")
            }
            println(result.bodyAsText())
            result.body()
        }
    }

}