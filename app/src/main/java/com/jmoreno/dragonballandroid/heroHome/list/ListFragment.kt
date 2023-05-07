package com.jmoreno.dragonballandroid.heroHome.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.jmoreno.dragonballandroid.heroHome.heroBattle.HeroeFragment
import com.jmoreno.dragonballandroid.ListHeroesAdapter
import com.jmoreno.dragonballandroid.OnClicked
import com.jmoreno.dragonballandroid.models.Personaje
import com.jmoreno.dragonballandroid.R
import com.jmoreno.dragonballandroid.databinding.FragmentListBinding
import com.jmoreno.dragonballandroid.heroHome.SecondActivity
import com.jmoreno.dragonballandroid.heroHome.SecondActivityViewModel
import kotlinx.coroutines.launch

class ListFragment: Fragment() , OnClicked {

    private lateinit var binding: FragmentListBinding
    private val activityViewModel: SecondActivityViewModel by activityViewModels()
    private val callback = SecondActivity()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            activityViewModel.uiListState.collect{
                when (it) {
                    //SecondActivityViewModel.UiListState.Empty -> {}
                    is SecondActivityViewModel.UiListState.Error -> {} // Mostrar un mensaje de error
                    is SecondActivityViewModel.UiListState.Idle -> {} // Mostrar el loading (si tienes)
                    is SecondActivityViewModel.UiListState.OnListReceived -> {
                        val listaPersonajes = it.heroeList
                        val adapter = ListHeroesAdapter(listaPersonajes,callback,this@ListFragment)
                        binding.rvListaHeroes.layoutManager = LinearLayoutManager(binding.root.context)
                        binding.rvListaHeroes.adapter = adapter
                    }
                    is SecondActivityViewModel.UiListState.OnHeroReceived -> {
                        showHero(it.personaje)
                    }

                    is SecondActivityViewModel.UiListState.OnHeroDead -> {}
                }
        }
    }
    }
     override  fun showHero(hero: Personaje) {
        //val hero = Personaje(false, "Ejemplo", "1234", "", "",100, 100)
        activityViewModel.changeDetail(hero)
        parentFragmentManager.beginTransaction().replace(R.id.fFragmentList, HeroeFragment()).commit()
    }

}

