package com.example.stargotestapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.stargotestapp.model.entities.PersonApiResponse
import com.example.stargotestapp.model.repositories.PeopleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: PeopleRepository
): ViewModel(){

    internal val _person: MutableLiveData<PersonApiResponse> = MutableLiveData()
    val person: LiveData<PersonApiResponse> = _person

    internal val _error: MutableLiveData<Throwable> = MutableLiveData()
    val error: LiveData<Throwable> = _error

    fun getPerson(personId: String){
        val disposable = repository.getPerson(personId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                _person.value = it
            }, {
                _error.value = it
            })
    }
}