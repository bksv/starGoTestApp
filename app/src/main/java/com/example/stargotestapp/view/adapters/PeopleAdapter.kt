package com.example.stargotestapp.view.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.stargotestapp.databinding.PersonBinding
import com.example.stargotestapp.model.entities.People
import com.example.stargotestapp.view.fragments.MainScreenFragmentDirections

class PeopleAdapter: RecyclerView.Adapter<PeopleAdapter.PeopleViewHolder>() {

    private var people: List<String> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeopleViewHolder {
        val binding: PersonBinding =
            PersonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PeopleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PeopleViewHolder, position: Int) {
        holder.binding.tvPerson.text = people[position]
        holder.itemView.setOnClickListener {
            val action =MainScreenFragmentDirections.actionMainScreenFragmentToProfileFragment(people[position])
            Navigation.findNavController(it).navigate(action)

        }
    }

    override fun getItemCount(): Int {
        return people.size
    }

    fun updateData(updatedPeople: People){
        people = updatedPeople.data
        notifyDataSetChanged()
    }

    inner class PeopleViewHolder(var binding: PersonBinding) : RecyclerView.ViewHolder(binding.root)
}