package com.example.stargotestapp.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.stargotestapp.RxImmediateSchedulerRule
import com.example.stargotestapp.model.entities.Person
import com.example.stargotestapp.model.entities.PersonApiResponse
import com.example.stargotestapp.model.repositories.PeopleRepository
import dagger.hilt.android.testing.HiltAndroidTest
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Assert.*

import junit.framework.TestCase
import org.junit.*
import org.junit.rules.TestRule

import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@HiltAndroidTest
@RunWith(MockitoJUnitRunner.Silent::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class ProfileViewModelTest{

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var peopleRepository: PeopleRepository

    private lateinit var personObserver: Observer<PersonApiResponse>
    private lateinit var errorObserver: Observer<Throwable>

    private lateinit var viewModel: ProfileViewModel

    @Before
    fun setUp(){
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }

        viewModel = ProfileViewModel(peopleRepository)

        personObserver = Mockito.mock(Observer::class.java) as Observer<PersonApiResponse>
        viewModel._person.observeForever(personObserver)

        errorObserver = Mockito.mock(Observer::class.java) as Observer<Throwable>
        viewModel._error.observeForever(errorObserver)
    }

    @Test
    fun `getPerson - WHEN got a response THEN should update the person live data`() {
        // GIVEN
        val fakeId = "24564tgdjm564656"

        val fakePersonApiResponse = PersonApiResponse(
            status = "success",
            person = Person(
                "536etgd5yrgf",
                "Name",
                "Surname",
                25,
                "Helicopter",
                "Ua"
            )
        )

        // WHEN
        Mockito.`when`(peopleRepository.getPerson(fakeId)).thenReturn(Single.just(fakePersonApiResponse))
        viewModel.getPerson(fakeId)

        // THEN
        Mockito.verify(personObserver).onChanged(fakePersonApiResponse)
    }

    @Test
    fun `getPerson - WHEN got an error THEN should update the error live data`() {
        // GIVEN
        val fakeId = "24564tgdjm564656"
        val fakeErrorResponse = Throwable("asd")

        // WHEN
        Mockito.`when`(peopleRepository.getPerson(fakeId)).thenReturn(Single.error(fakeErrorResponse))
        viewModel.getPerson(fakeId)

        // THEN
        Mockito.verify(errorObserver).onChanged(fakeErrorResponse)
    }

    companion object {
        @ClassRule @JvmField
        val schedulers = RxImmediateSchedulerRule()
    }
}