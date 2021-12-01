package com.example.stargotestapp.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.stargotestapp.R
import com.example.stargotestapp.databinding.FragmentProfileBinding
import com.example.stargotestapp.viewmodels.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    private val viewModel: ProfileViewModel by viewModels()

    private val args: ProfileFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getPerson(args.personIdArgument)

        viewModel.person.observe(viewLifecycleOwner) {
            binding.tvId.text = it.person.id
            binding.tvFirstName.text = it.person.firstName
            binding.tvLastName.text = it.person.lastName
            binding.tvAge.text = it.person.age.toString()
            binding.tvGender.text = it.person.gender
            binding.tvCountry.text = it.person.country
        }
        viewModel.error.observe(viewLifecycleOwner) {
            AlertDialog.Builder(requireContext()).setMessage(R.string.incorrect_id_or_data_absent)
                .setPositiveButton(R.string.positive) { _, _ ->
                    val action =
                        ProfileFragmentDirections.actionProfileFragmentToMainScreenFragment()
                    Navigation.findNavController(requireView()).navigate(action) }
                .setNegativeButton(R.string.negative){dialog,_ ->
                    dialog.dismiss() }
                .show()
        }
    }

}