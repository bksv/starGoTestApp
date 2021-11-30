package com.example.stargotestapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.stargotestapp.model.entities.People
import com.example.stargotestapp.model.repositories.PeopleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val repository: PeopleRepository
): ViewModel() {

    internal val _people: MutableLiveData<People> = MutableLiveData()
    val people: LiveData<People> = _people

    internal val _error: MutableLiveData<Throwable> = MutableLiveData()
    val error: LiveData<Throwable> = _error

    fun getPeople(){
        val disposable = repository.getPeople()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                _people.value = it
            }, {
                _error.value = it
            })
    }
}