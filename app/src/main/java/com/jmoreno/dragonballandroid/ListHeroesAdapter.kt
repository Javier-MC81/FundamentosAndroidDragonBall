package com.jmoreno.dragonballandroid

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.jmoreno.dragonballandroid.databinding.ItemPersonajeBinding
import com.squareup.picasso.Picasso

interface PersonajeClicked {
    fun personajeClicked(personaje: Personaje)
}


class ListHeroesAdapter(
    private val listaPersonajes: List<Personaje>,
    private val callback: PersonajeClicked
): RecyclerView.Adapter<ListHeroesAdapter.MainActivityViewHolder>() {


    class MainActivityViewHolder(private var item: ItemPersonajeBinding,private val callback: PersonajeClicked) : RecyclerView.ViewHolder(item.root) {

        fun showPersonaje(personaje: Personaje) {

            Picasso.get().load(personaje.photo)
                .into(item.ivPersonaje)
            item.tvName.text = personaje.name
            item.tvNumeroVida.text = personaje.vidaActual.toString()

            item.bLuchar.setOnClickListener {
                Toast.makeText(item.root.context, "Pulsado sobre ${personaje.name}", Toast.LENGTH_LONG).show()
                callback.personajeClicked(personaje)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainActivityViewHolder {
        val binding = ItemPersonajeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MainActivityViewHolder(binding, callback)
    }

    override fun getItemCount(): Int {
        return listaPersonajes.size
    }

    override fun onBindViewHolder(holder: MainActivityViewHolder, position: Int) {
        holder.showPersonaje(listaPersonajes[position])
    }



}