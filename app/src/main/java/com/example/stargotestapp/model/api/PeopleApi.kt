package com.example.stargotestapp.model.api

import com.example.stargotestapp.model.entities.People
import com.example.stargotestapp.Utils.Constants
import com.example.stargotestapp.model.entities.PersonApiResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface PeopleApi {
    @Headers(
        "Authorization: ${Constants.token}",
        "Content-Type: application/json")
    @GET("list")
    fun getPeople(): Single<People>

    @Headers(
        "Authorization: ${Constants.token}",
        "Content-Type: application/json")
    @GET("get/{id}")
    fun getPerson(
        @Path("id") personId: String
    ): Single<PersonApiResponse>
}