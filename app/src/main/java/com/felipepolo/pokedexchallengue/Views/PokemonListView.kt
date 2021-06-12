package com.felipepolo.pokedexchallengue.Views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import com.felipepolo.pokedexchallengue.Interfaces.PokemonListInterface
import com.felipepolo.pokedexchallengue.Presenters.PokemonListPresenter
import com.felipepolo.pokedexchallengue.R
import com.google.android.material.tabs.TabLayout

class PokemonListView : AppCompatActivity(), PokemonListInterface.View {

    var presenter: PokemonListInterface.Presenter? = null

    var maintoolbar: Toolbar? = null;

    var fragmentManager: FragmentManager? = null
    var tablayout: TabLayout? = null
    var viewPager:ViewPager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemones)

        presenter = PokemonListPresenter(this)

        maintoolbar = findViewById(R.id.MainToolbar)
        setSupportActionBar(maintoolbar)

        tablayout = findViewById(R.id.tablayout)
        viewPager = findViewById(R.id.viewpager)

        fragmentManager = supportFragmentManager

        // presenter calls
        presenter?.setTablayout(viewPager!!,supportFragmentManager)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.mainmenu, menu)
        return super.onCreateOptionsMenu(menu)
    }


    override fun setTabViewpager(viewPager: ViewPager) {
        tablayout?.setupWithViewPager(viewPager)
    }

    override fun showToast(msj: String) {
        Toast.makeText(this,msj,Toast.LENGTH_SHORT).show()
    }
}