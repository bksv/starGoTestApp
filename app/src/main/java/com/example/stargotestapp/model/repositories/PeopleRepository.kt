package com.example.stargotestapp.model.repositories

import com.example.stargotestapp.model.api.PeopleApi
import com.example.stargotestapp.model.entities.People
import com.example.stargotestapp.model.entities.PersonApiResponse
import io.reactivex.Single
import javax.inject.Inject

class PeopleRepository @Inject constructor(
    private val api: PeopleApi
) {
    fun getPeople(): Single<People> = api.getPeople()

    fun getPerson(personId: String): Single<PersonApiResponse> = api.getPerson(personId)
}