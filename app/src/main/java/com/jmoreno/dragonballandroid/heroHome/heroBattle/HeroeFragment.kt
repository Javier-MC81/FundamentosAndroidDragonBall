package com.jmoreno.dragonballandroid.heroHome.heroBattle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.jmoreno.dragonballandroid.models.Personaje
import com.jmoreno.dragonballandroid.R
import com.jmoreno.dragonballandroid.databinding.FightingHeroeBinding
import com.jmoreno.dragonballandroid.heroHome.SecondActivityViewModel
import com.jmoreno.dragonballandroid.heroHome.list.ListFragment
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
                //Escuchamos el cambio del StateFlow para que cuando se pueda jugar con los botones y actualizar la UI
                activityViewModel.uiListState.collect {
                    when (it) {
                        is SecondActivityViewModel.UiListState.OnHeroReceived -> {
                            personaje = it.personaje
                            showHero(it.personaje)
                            updateBar()
                            binding.bHerir.setOnClickListener {

                                activityViewModel.reducirVida(personaje)
                                updateBar()
                            }
                            binding.bCurar.setOnClickListener {

                                activityViewModel.curarVida(personaje)
                                updateBar()
                            }

                         }
                        //Si el cambio del StatefLow avisa de que el heroe está muerto, lanza la lista
                        is SecondActivityViewModel.UiListState.OnHeroDead -> {
                            parentFragmentManager.beginTransaction().replace(R.id.fFragmentList, ListFragment()).commit()
                        }
                        is SecondActivityViewModel.UiListState.OnListReceived -> {
                            parentFragmentManager.beginTransaction().replace(R.id.fFragmentList, ListFragment()).commit()
                        }
                        else -> Unit
                    }

                }
            }
        }
    //Funciones para pintar el fragment del héroe seleccionado
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
    //Función para volver con el botón de back hacia la lista.
    private fun returnToList() {
        activityViewModel.changeDetail(personaje)
        activityViewModel.showList()
        parentFragmentManager.beginTransaction()
            .replace(R.id.fFragmentList, ListFragment()).commit()
        }

}
