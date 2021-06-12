package com.felipepolo.pokedexchallengue.Models

import android.content.Context
import com.android.volley.Response
import com.felipepolo.pokedexchallengue.Interfaces.PokemonDetailInterface
import com.felipepolo.pokedexchallengue.PokeApi.Api
import com.felipepolo.pokedexchallengue.PokeApi.PokeStruct
import com.felipepolo.pokedexchallengue.Sqlite.Pokemon
import com.felipepolo.pokedexchallengue.Sqlite.PokemonCRD
import com.google.gson.Gson

class PokemonDetailModel(presenter: PokemonDetailInterface.Presenter): PokemonDetailInterface.Model {

    var presenter:PokemonDetailInterface.Presenter? = null
    init {
        this.presenter = presenter
    }

    override fun getPokemonByName(context: Context, name: String) {
        val api = Api(context)
        api.getPokemonByGeneration(name, Response.Listener { response ->
            val gson = Gson()
            val pokemon = gson.fromJson(response, PokeStruct.Pokemon::class.java)
            presenter!!.getPokemonByNameReady(pokemon)
        })
    }

    override fun newLocalPokemon(context: Context, name: String) {
        val pokemonCRD = PokemonCRD(context)
        pokemonCRD.newPokemon(Pokemon(name))
    }

    override fun existLocalPokemon(context: Context, name: String): Boolean {
        val pokemonCRD = PokemonCRD(context)

        if(pokemonCRD.getPokemon(name) != null){
            return true
        }
        return false
    }

    override fun removeLocalPokemon(context: Context, name: String) {
        val pokemonCRD = PokemonCRD(context)
        pokemonCRD.deletePokemon(name)
    }
}