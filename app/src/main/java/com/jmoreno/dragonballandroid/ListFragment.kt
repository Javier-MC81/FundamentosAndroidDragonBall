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

class ListFragment: Fragment() {

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
            activityViewModel.uiState.collect{
                val listaPersonajes = activityViewModel.listaPersonajes.toMutableList()
                val adapter = ListHeroesAdapter(listaPersonajes,callback)
                binding.rvListaHeroes.layoutManager = LinearLayoutManager(binding.root.context)
                binding.rvListaHeroes.adapter = adapter
            }
        }
        }

}

