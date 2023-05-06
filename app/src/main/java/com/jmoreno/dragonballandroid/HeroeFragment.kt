package com.jmoreno.dragonballandroid

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.jmoreno.dragonballandroid.databinding.FightingHeroeBinding
import com.squareup.picasso.Picasso


import kotlinx.coroutines.launch

class HeroeFragment() : Fragment() {

    private lateinit var binding: FightingHeroeBinding

    val activityViewModel: SecondActivityViewModel by activityViewModels()
    var personaje = Personaje(true,"","","","",100,100)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FightingHeroeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {

            activityViewModel.detailState.collect{
                when(it){

                        is SecondActivityViewModel.UiState.OnHeroReceived ->

                            personaje = it.personaje

                        //is ViewModelMainActivity.UiState.Error -> binding.tvToken?.text = it.error
                        else -> Unit


                }
                binding.tvName.text = personaje.name
                binding.tvVidaActual.text = personaje.vidaActual.toString()
                Picasso.get().load(personaje.photo)
                .into(binding.ivPersonaje)
            }
        }



    }

}