package com.felipepolo.pokedexchallengue.Views

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.felipepolo.pokedexchallengue.R

class MainActivity : AppCompatActivity() {

    var SOLICITUD_PERMISOS = 1;

    var PERMISO_INTERNET = android.Manifest.permission.INTERNET;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<LinearLayout>(R.id.b_begin).setOnClickListener{
            if(checkSelfPermission(PERMISO_INTERNET) == PackageManager.PERMISSION_GRANTED){
                startActivity(Intent(this, PokemonListView::class.java))
            }else{
                SolicitudPermisos();
            }
        }
    }


    private fun SolicitudPermisos(){
        var proveerInfo = ActivityCompat.shouldShowRequestPermissionRationale(this,PERMISO_INTERNET);
        if(proveerInfo){
            Toast.makeText(this,"Me deberias dar permisos de internet",Toast.LENGTH_SHORT).show()
            pedirPermisos()
        }else{
            pedirPermisos()
        }
    }

    private fun pedirPermisos(){
        requestPermissions(arrayOf(PERMISO_INTERNET),SOLICITUD_PERMISOS);
    }
}