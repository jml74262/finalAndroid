package com.example.tdpa_proyectofinal
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url


interface ApiService {
    @GET("api/breeds/image/random")
    suspend fun getImage(): Response<Imagen>

}