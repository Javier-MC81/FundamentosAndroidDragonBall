package com.jmoreno.dragonballandroid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.jmoreno.dragonballandroid.databinding.FightingHeroeBinding
import com.squareup.picasso.Picasso


import kotlinx.coroutines.launch

class HeroeFragment() : Fragment() {

    private lateinit var binding: FightingHeroeBinding

    private val activityViewModel: SecondActivityViewModel by activityViewModels()
    private var personaje = Personaje(false,"","","","",100,100)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FightingHeroeBinding.inflate(inflater)
        return binding.root
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            returnToList()
        }
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

            viewLifecycleOwner.lifecycleScope.launch {

                activityViewModel.uiListState.collect {
                    when (it) {
                        is SecondActivityViewModel.UiListState.OnHeroReceived -> {
                            personaje = it.personaje
                            showHero(it.personaje)

                            binding.bHerir.setOnClickListener {
                                reducirVida()
                            }
                            binding.bCurar.setOnClickListener {
                                curarVida()
                            }
                         }
                        else -> Unit
                    }
                }
            }
        }

    private fun showHero(personaje: Personaje) {
        binding.tvName.text = personaje.name
        binding.tvVidaActual.text = "Vida actual:"
        binding.tvNumeroVida.text = personaje.vidaActual.toString()

        if (personaje.photo.isNotEmpty())
            Picasso.get().load(personaje.photo)
                .into(binding.ivPersonaje)
        }
    private fun updateBar() {
        binding.tvNumeroVida.text = (personaje.vidaActual).toString()
        binding.progressBar.progress = personaje.vidaActual
    }
    private fun returnToList() {
        activityViewModel.showList()
        parentFragmentManager.beginTransaction()
            .replace(R.id.fFragmentList, ListFragment()).commit()
        }
    private fun reducirVida() {
        if (personaje.vidaActual in 0..25) {
            personaje.vidaActual = 0
            updateBar()
            returnToList()
            Toast.makeText(
                binding.root.context,
                "¡${personaje.name} ha perdido la batalla!",
                Toast.LENGTH_LONG
            ).show()
        } else {
            personaje.vidaActual = personaje.vidaActual - 25
            updateBar()
        }
    }
    private fun curarVida(){
        if (personaje.vidaActual in 80..99) {
            personaje.vidaActual = 100
            updateBar()

        } else if (personaje.vidaActual<80){
            personaje.vidaActual = personaje.vidaActual + 20
            updateBar()

        }else{
            Toast.makeText(
                binding.root.context,
                "¡${personaje.name} tiene la vida máxima!",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}
