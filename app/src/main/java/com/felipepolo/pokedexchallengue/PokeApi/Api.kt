package com.felipepolo.pokedexchallengue.PokeApi

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson

class Api(var context: Context) {

    var queue:RequestQueue? = null

    var GENERATION_URL = "https://pokeapi.co/api/v2/generation/"
    var POKEMON_URL = "https://pokeapi.co/api/v2/pokemon/"

    init {
        this.queue = Volley.newRequestQueue(context)
    }

    fun getGenerationPokemon(id:Int, listener: Response.Listener<String>){

        var generation = PokeStruct.Generation()

        val generationInfoRequest = StringRequest(Request.Method.GET,GENERATION_URL + id.toString() +"/",
            listener,
            Response.ErrorListener { error: VolleyError? ->
                Log.d("Response",error?.message!!);
            }
        )
        queue?.add(generationInfoRequest)
    }

    fun getPokemonsbyGeneration(id:Int, listener: Response.Listener<String>){
        getGenerationPokemon(id , Response.Listener {response ->
            var gson = Gson()
            var generation = gson.fromJson(response,PokeStruct.Generation::class.java)

            for (especie in generation.pokemon_species!!){
                val pokemonrequest = StringRequest(Request.Method.GET,POKEMON_URL + especie.name!! + "/",
                        listener
                        ,
                        Response.ErrorListener { error ->
                            Log.d("Response",error?.message!!);
                        }
                )
                queue?.add(pokemonrequest)
            }
        })
    }

    fun getPokemonByGeneration(name:String,listener: Response.Listener<String>){
        val pokemonrequest = StringRequest(Request.Method.GET, "$POKEMON_URL$name/",
            listener
            ,
            Response.ErrorListener { error ->

            }
        )
        queue?.add(pokemonrequest)
    }

}