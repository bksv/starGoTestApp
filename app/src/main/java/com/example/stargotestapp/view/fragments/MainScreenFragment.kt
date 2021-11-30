package com.example.stargotestapp.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.stargotestapp.R
import com.example.stargotestapp.databinding.FragmentMainScreenBinding
import com.example.stargotestapp.view.adapters.PeopleAdapter
import com.example.stargotestapp.viewmodels.MainScreenViewModel
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.HttpException

@AndroidEntryPoint
class MainScreenFragment : Fragment() {

    private lateinit var binding: FragmentMainScreenBinding

    private val viewModel: MainScreenViewModel by viewModels()

    private var peopleAdapter: PeopleAdapter = PeopleAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =  DataBindingUtil.inflate(inflater, R.layout.fragment_main_screen, container, false)
        viewModel.getPeople()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvMainScreen.adapter = peopleAdapter

        viewModel.people.observe(viewLifecycleOwner){
            peopleAdapter.updateData(it)
        }
        viewModel.error.observe(viewLifecycleOwner){
            //(it as HttpException).code()
            //it.localizedMessage
        }
    }

}