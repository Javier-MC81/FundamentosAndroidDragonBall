package com.jmoreno.dragonballandroid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.jmoreno.dragonballandroid.databinding.FragmentListBinding
import kotlinx.coroutines.launch

class ListFragment: Fragment() , OnClicked{

    private lateinit var binding: FragmentListBinding
    private val activityViewModel: SecondActivityViewModel by activityViewModels()
    private val callback = SecondActivity()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(inflater)
        /*binding.tvTitle.setOnClickListener {
            showHero()
        }*/
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            activityViewModel.uiListState.collect{
                when (it) {
                    SecondActivityViewModel.UiListState.Empty -> {}
                    is SecondActivityViewModel.UiListState.Error -> {} // Mostrar un mensaje de error
                    SecondActivityViewModel.UiListState.Idle -> {} // Mostrar el loading (si tienes)
                    is SecondActivityViewModel.UiListState.OnHeroReceived -> {
                        showHero(it.personaje)
                    } // Abrir el otro fragment
                    is SecondActivityViewModel.UiListState.OnListReceived -> {
                        val listaPersonajes = it.heroeList
                        val adapter = ListHeroesAdapter(listaPersonajes,callback,this@ListFragment)
                        binding.rvListaHeroes.layoutManager = LinearLayoutManager(binding.root.context)
                        binding.rvListaHeroes.adapter = adapter
                    }
                }
            }
        }
    }

     override  fun showHero(hero:Personaje) {
        //val hero = Personaje(false, "Ejemplo", "1234", "", "",100, 100)
        activityViewModel.changeDetail(hero)
        parentFragmentManager.beginTransaction().replace(R.id.fFragmentList, HeroeFragment()).commit()
    }

}

