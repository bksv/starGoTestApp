package com.example.stargotestapp.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.stargotestapp.RxImmediateSchedulerRule
import com.example.stargotestapp.model.entities.People
import com.example.stargotestapp.model.repositories.PeopleRepository
import dagger.hilt.android.testing.HiltAndroidTest
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.*
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@HiltAndroidTest
@RunWith(MockitoJUnitRunner.Silent::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class MainScreenViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var peopleRepository: PeopleRepository

    private lateinit var peopleObserver: Observer<People>
    private lateinit var errorObserver: Observer<Throwable>

    private lateinit var viewModel: MainScreenViewModel

    @Before
    fun setUp(){
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }

        viewModel = MainScreenViewModel(peopleRepository)

        peopleObserver = mock(Observer::class.java) as Observer<People>
        viewModel._people.observeForever(peopleObserver)

        errorObserver = mock(Observer::class.java) as Observer<Throwable>
        viewModel._error.observeForever(errorObserver)
    }

    @Test
    fun `getPeople - WHEN got a response THEN should update the people live data`() {
        // GIVEN
        val fakePeopleResponse = People(
            status = "success",
            data = listOf(
                "305950igkfnvjjcmvflkv",
                "ejlgkveoiglm34iomoimg"
            )
        )

        // WHEN
        `when`(peopleRepository.getPeople()).thenReturn(Single.just(fakePeopleResponse))
        viewModel.getPeople()

        // THEN
        verify(peopleObserver).onChanged(fakePeopleResponse)
    }

    @Test
    fun `getPeople - WHEN got an error THEN should update the error live data`() {
        // GIVEN
        val fakeErrorResponse = Throwable("asd")

        // WHEN
        `when`(peopleRepository.getPeople()).thenReturn(Single.error(fakeErrorResponse))
        viewModel.getPeople()

        // THEN
        verify(errorObserver).onChanged(fakeErrorResponse)
    }

    companion object {
        @ClassRule @JvmField
        val schedulers = RxImmediateSchedulerRule()
    }
}