package com.jmoreno.dragonballandroid

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.jmoreno.dragonballandroid.databinding.ItemPersonajeBinding
import com.jmoreno.dragonballandroid.models.Personaje
import com.squareup.picasso.Picasso

interface PersonajeClicked {
    fun personajeClicked(personaje: Personaje)
}
interface OnClicked {
    fun showHero(personaje: Personaje)
}

class ListHeroesAdapter(
    private val listaPersonajes: List<Personaje>,
    private val callback: PersonajeClicked,
    private val fragParent: OnClicked
): RecyclerView.Adapter<ListHeroesAdapter.MainActivityViewHolder>() {


    class MainActivityViewHolder(private var item: ItemPersonajeBinding,private val callback: PersonajeClicked,private val fragParent: OnClicked) : RecyclerView.ViewHolder(item.root) {

        fun showPersonaje(personaje: Personaje) {

            Picasso.get().load(personaje.photo)
                .into(item.ivPersonaje)
            item.tvName.text = personaje.name
            item.tvNumeroVida.text = personaje.vidaActual.toString()
            if(personaje.vidaActual != 0){
                    item.bLuchar.isEnabled = true
            }else {
                item.bLuchar.isEnabled = false
                item.bLuchar.text = "Personaje Eliminado"
            }
            if (item.bLuchar.isEnabled){
                item.bLuchar.setOnClickListener {

                    fragParent.showHero(personaje)

                }
            }else {
                item.ipBackground.setOnClickListener {
                    Toast.makeText(item.root.context, "Lo sentimos. ${personaje.name} ha perdido todas sus batallas", Toast.LENGTH_LONG).show()
                }

            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainActivityViewHolder {
        val binding = ItemPersonajeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MainActivityViewHolder(binding, callback,fragParent)
    }

    override fun getItemCount(): Int {
        return listaPersonajes.size
    }

    override fun onBindViewHolder(holder: MainActivityViewHolder, position: Int) {
        holder.showPersonaje(listaPersonajes[position])
    }



}