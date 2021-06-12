package com.felipepolo.pokedexchallengue.Models

import android.content.Context
import com.android.volley.Response
import com.felipepolo.pokedexchallengue.Interfaces.PokemonListInterface
import com.felipepolo.pokedexchallengue.PokeApi.Api
import com.felipepolo.pokedexchallengue.PokeApi.PokeStruct
import com.felipepolo.pokedexchallengue.Sqlite.PokemonCRD
import com.google.gson.Gson
import org.json.JSONException

class PokemonListModel(presenter: PokemonListInterface.Presenter) : PokemonListInterface.Model {
    var presenter:PokemonListInterface.Presenter? = null
    init {
        this.presenter = presenter
    }

    override fun getPokemonNameList(context: Context, generationId:Int) {
        val api = Api(context)

        api.getGenerationPokemon(generationId, Response.Listener { response ->
            try {
                val gson = Gson()
                val generation = gson.fromJson(response, PokeStruct.Generation::class.java)
                generation!!.pokemon_species?.let { this.presenter!!.requestPokemonNameListReady(it) }
            }catch (excepcion: JSONException){}
        })
    }

    override fun getPokemonsByArray(context: Context, nameArray: ArrayList<String>) {
        val api = Api(context)
        var i = 1
        val pokemons = ArrayList<PokeStruct.Pokemon>()
        for(name in nameArray){
            api.getPokemonByGeneration(name, Response.Listener { response ->
                val gson = Gson()
                val pokemon = gson.fromJson(response, PokeStruct.Pokemon::class.java)
                pokemons.add(pokemon)
                if(nameArray.count() == i){
                    this.presenter!!.getPokemonsReady(pokemons)
                }
                i++
            })
        }
    }

    override fun getAllLocalPokemons(context: Context): ArrayList<String>? {
        val pokemonCRD = PokemonCRD(context)
        val pokemons = pokemonCRD.getPokemons()
        if(pokemons.size != 0){
            val pokemonNames = ArrayList<String>()
            for (pokemon in pokemons){
                pokemonNames.add(pokemon.name)
            }
            return pokemonNames
        }
        return null
    }

}