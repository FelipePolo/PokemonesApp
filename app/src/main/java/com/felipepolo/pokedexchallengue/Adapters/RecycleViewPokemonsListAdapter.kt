package com.felipepolo.pokedexchallengue.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.felipepolo.pokedexchallengue.PokeApi.PokeStruct
import com.felipepolo.pokedexchallengue.Views.PokemonDetailView
import com.felipepolo.pokedexchallengue.R
import com.skydoves.progressview.ProgressView
import com.squareup.picasso.Picasso

class RecycleViewPokemonsListAdapter(
    var context: Context,
    var pokemons: ArrayList<PokeStruct.Pokemon>
) : RecyclerView.Adapter<RecycleViewPokemonsListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var vista = LayoutInflater.from(context).inflate(R.layout.template_pokemon_list, parent, false)
        var ViewHolder = ViewHolder(vista)
        return ViewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var pokemon = pokemons[position]

        var urlImagePokemon = pokemon.sprites!!.front_default

        Picasso.get()
            .load(urlImagePokemon)
            .placeholder(R.drawable.loading)
            .into(holder.pokemonImage)

        holder.pokemonName?.setText(pokemons.get(position).name!!)

        holder.expbar?.progress = pokemon.base_experience!!.toFloat()
        holder.expbar?.labelText = "Base XP " + pokemon.base_experience!!

        holder.tvhp?.text = pokemon.stats?.get(0)?.base_stat.toString()
        holder.tvdefense?.text = pokemon.stats?.get(2)?.base_stat.toString()
        holder.tvattack?.text = pokemon.stats?.get(1)?.base_stat.toString()
        holder.tvspeed?.text = pokemon.stats?.get(5)?.base_stat.toString()
    }

    override fun getItemCount(): Int {
        return pokemons.count()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var pokemonitem: LinearLayout? = null
        var pokemonName: TextView? = null
        var pokemonImage: ImageView? = null
        var expbar: ProgressView? = null
        var tvhp: TextView? = null
        var tvdefense: TextView? = null
        var tvattack: TextView? = null
        var tvspeed: TextView? = null

        init {
            pokemonitem = itemView.findViewById(R.id.pokemonitem) as LinearLayout
            pokemonName = itemView.findViewById(R.id.pokemon_name) as TextView
            pokemonImage = itemView.findViewById(R.id.pokemon_imagen) as ImageView
            expbar = itemView.findViewById(R.id.exp_bar) as ProgressView
            tvhp = itemView.findViewById(R.id.tvhp) as TextView
            tvdefense = itemView.findViewById(R.id.tvdefense) as TextView
            tvattack = itemView.findViewById(R.id.tvattack) as TextView
            tvspeed = itemView.findViewById(R.id.tvspeed) as TextView

            pokemonitem?.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val context = v!!.context
            var intent = Intent(context, PokemonDetailView::class.java)
            intent.putExtra("pokemon", this.pokemonName!!.text)
            context.startActivity(intent)
        }
    }
}