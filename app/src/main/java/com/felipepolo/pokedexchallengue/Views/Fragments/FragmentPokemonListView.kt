package com.felipepolo.pokedexchallengue.Views.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.get
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.felipepolo.pokedexchallengue.Adapters.RecycleViewPokemonsListAdapter
import com.felipepolo.pokedexchallengue.Interfaces.PokemonListInterface
import com.felipepolo.pokedexchallengue.PokeApi.Api
import com.felipepolo.pokedexchallengue.PokeApi.PokeStruct
import com.felipepolo.pokedexchallengue.Presenters.PokemonListPresenter
import com.felipepolo.pokedexchallengue.R
import com.google.gson.Gson
import org.json.JSONException

class FragmentPokemonListView() : Fragment(), PokemonListInterface.View.FragmentListPokemon {

    var nestedscrollview: NestedScrollView? = null
    var pokemon_recyclerview: RecyclerView? = null
    var adaptadorPokemonList: RecycleViewPokemonsListAdapter? = null
    var progressBar: ProgressBar? = null

    var pokemons: ArrayList<PokeStruct.Pokemon>? = null

    var presenter: PokemonListPresenter? = null

    var generationId: Int = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = PokemonListPresenter(this, requireContext())
        generationId = requireArguments().getInt("generation")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val itemview = inflater.inflate(R.layout.fragment_pokemonlist, container, false)

        pokemons = ArrayList();

        nestedscrollview = itemview.findViewById(R.id.scroll_view)
        pokemon_recyclerview = itemview.findViewById(R.id.pokemon_recycler)
        progressBar = itemview.findViewById(R.id.progress_bar)

        adaptadorPokemonList = RecycleViewPokemonsListAdapter(requireContext(), pokemons!!)

        pokemon_recyclerview?.layoutManager = LinearLayoutManager(requireContext());
        pokemon_recyclerview?.adapter = adaptadorPokemonList

        nestedscrollview?.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (scrollY == v[0].measuredHeight - v.measuredHeight) {
                progressBar?.visibility = View.VISIBLE
                presenter!!.getPokemons(5)
            }
        })
        return itemview
    }

    override fun onDestroy() {
        super.onDestroy()
        pokemon_recyclerview = null
    }

    override fun onResume() {
        super.onResume()
        presenter!!.generationId = null
        presenter!!.getNewPokemonsByGeneration(generationId)
    }

    override fun showPokemons(pokemons: ArrayList<PokeStruct.Pokemon>) {
        for(pokemon in pokemons){
            this.pokemons?.add(pokemon)
        }
        this.adaptadorPokemonList?.notifyItemRangeInserted(this.pokemons!!.size,pokemons.size)
        progressBar?.visibility = View.GONE
    }

    override fun limitpokemonListReached() {
        progressBar?.visibility = View.GONE
        Toast.makeText(requireContext(),"there are no more pochemons in this List",Toast.LENGTH_SHORT).show()
    }

    override fun resetList() {
        this.pokemons = null
        this.pokemons = ArrayList()
        this.adaptadorPokemonList?.notifyDataSetChanged()
    }

}