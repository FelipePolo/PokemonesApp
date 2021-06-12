package com.felipepolo.pokedexchallengue.Views

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.felipepolo.pokedexchallengue.Adapters.RecycleViewSimpleAdapter
import com.felipepolo.pokedexchallengue.Interfaces.PokemonDetailInterface
import com.felipepolo.pokedexchallengue.Presenters.PokemonDetailPresenter
import com.felipepolo.pokedexchallengue.R
import com.google.android.flexbox.FlexboxLayout
import com.skydoves.progressview.ProgressView
import com.squareup.picasso.Picasso

class PokemonDetailView : AppCompatActivity(), PokemonDetailInterface.View {

    var presenter:PokemonDetailPresenter? = null

    var maintoolbar: Toolbar? = null;

    var pokemonimagen: ImageView? = null
    var pokemon_name:TextView? = null
    var progressBar:ProgressView? = null
    var rvtypes:RecyclerView? = null
    var movescontainer :FlexboxLayout? = null
    var pokedex:TextView? = null
    var wandh:TextView? = null
    var ivlike:LottieAnimationView? = null

    var typesAdapter: RecycleViewSimpleAdapter? = null

    var types = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_detail)

        maintoolbar = findViewById(R.id.MainToolbar)
        setSupportActionBar(maintoolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        pokemonimagen = findViewById(R.id.pokemon_imagen)
        pokemon_name = findViewById(R.id.pokemon_name)
        progressBar = findViewById(R.id.exp_bar)
        rvtypes = findViewById(R.id.types_recycler)
        movescontainer = findViewById(R.id.moves)
        pokedex = findViewById(R.id.pokedex_number)
        wandh = findViewById(R.id.wandh)
        ivlike = findViewById(R.id.iv_like)

        ivlike!!.setAnimation(R.raw.dislike_animation)
        ivlike!!.playAnimation()

        typesAdapter = RecycleViewSimpleAdapter(this,types)
        rvtypes!!.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        rvtypes!!.adapter = typesAdapter

        presenter = PokemonDetailPresenter(this)

        val pokemonname = intent.getStringExtra("pokemon")

        presenter!!.getPokemonByName(pokemonname!!)
        ivlike!!.setOnClickListener { view -> presenter!!.setLikeAction(view as LottieAnimationView)}

    }

    override fun showTitle(title: String) {
        supportActionBar!!.title = title
    }

    override fun showImagePokemon(url: String) {
        Picasso.get().load(url).into(pokemonimagen!!)
    }

    override fun showNamePokemon(name: String) {
        pokemon_name!!.text = name
    }

    override fun showExperiencePokemon(xp: Int) {
        progressBar!!.labelText = "Base XP $xp"
        progressBar!!.progress = xp.toFloat()
    }

    override fun showTypesPokemon(types: ArrayList<String>) {
        for(type in types){
            typesAdapter!!.list.add(type)
        }
        typesAdapter!!.notifyDataSetChanged()
    }

    @SuppressLint("SetTextI18n")
    override fun showPokedexNumberPokemon(number: Int) {
        pokedex!!.text = "Pokedex Number: $number"
    }

    @SuppressLint("SetTextI18n")
    override fun showWeightAndHeightPokemon(w: Int, h:Int) {
        wandh!!.text = "Weight: $w      Height: $h"
    }

    override fun showMoves(moves: ArrayList<String>) {
        for(move in moves){
            val view = LayoutInflater.from(this).inflate(R.layout.template_simple_moves,movescontainer,false)
            view.findViewById<TextView>(R.id.tvname).text = move
            movescontainer!!.addView(view)
        }
    }

    override fun showDefaultLikeAnimation(animation: Int) {
        ivlike!!.setAnimation(animation)
        ivlike!!.playAnimation()
    }

    override fun showToast(msj: String) {
        Toast.makeText(this,msj,Toast.LENGTH_LONG).show()
    }
}