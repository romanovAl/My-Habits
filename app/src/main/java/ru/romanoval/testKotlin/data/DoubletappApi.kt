package ru.romanoval.testKotlin.data

import com.google.gson.GsonBuilder
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import ru.romanoval.testKotlin.data.model.Delete
import ru.romanoval.testKotlin.data.model.DeleteResponse
import ru.romanoval.testKotlin.data.model.HabitJson
import ru.romanoval.testKotlin.data.model.PutResponse

interface DoubletappApi {

    @GET("api/habit")
    suspend fun getHabitsList(
        @Header("accept") accept: String,
        @Header("Authorization") authorization: String
    ): Response<List<HabitJson>>

    @PUT("api/habit")
    suspend fun putHabit(
        @Header("accept") accept: String,
        @Header("Authorization") authorization: String,
        @Header("Content-Type") contentType: String,
        @Body habit: HabitJson
    ): Response<PutResponse>

    @HTTP(method = "DELETE", path = "api/habit", hasBody = true)
    suspend fun deleteHabit(
        @Header("accept") accept: String,
        @Header("Authorization") authorization: String,
        @Header("Content-Type") contentType: String,
        @Body delete: Delete
    )


    companion object Factory {
        fun create(): DoubletappApi {
            val gson = GsonBuilder()
                .registerTypeAdapter(
                    HabitJson::class.java,
                    HabitJson.HabitJsonSerializer()
                )
                .registerTypeAdapter(
                    HabitJson::class.java,
                    HabitJson.HabitJsonDeserializer()
                )
                .registerTypeAdapter(
                    Delete::class.java,
                    Delete.DeleteSerializer())
                .create()
            val retrofit = Retrofit.Builder()
                .baseUrl("https://droid-test-server.doubletapp.ru/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
            return retrofit.create(DoubletappApi::class.java)
        }
    }
}