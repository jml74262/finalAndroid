package com.example.tdpa_proyectofinal

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.tdpa_proyectofinal.databinding.ActivityMainBinding
import com.example.tdpa_proyectofinal.databinding.ActivityResultadosBinding

class resultados : AppCompatActivity() {
    private lateinit var binding: ActivityResultadosBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityResultadosBinding.inflate(layoutInflater)
        setContentView(binding.root)
        cargarImagen()
        logica()

    }
    fun cargarImagen(){
        val urlImagenPrincipal = "https://picsum.photos/g/1920/1080?random"
        val imagenRandom: Uri = Uri.parse(urlImagenPrincipal)
        Glide.with(applicationContext).load(imagenRandom).into(binding.imgSecunddaria)
    }
    fun logica(){
        val bundle = intent.extras
        val nombre = bundle?.getString("nombre")
        val nombreMateria = bundle?.getString("nombreMateria")
        val primeraCal = bundle?.getString("primerCal")?.toDouble()
        val segundaCal = bundle?.getString("segundaCal")?.toDouble()

        val sumaCalis = (primeraCal!!*.2)+(segundaCal!!*.2)

        val paraSeis = 6-sumaCalis

        binding.txtSeis.setText(paraSeis.toString())

        if (primeraCal.toString().toDouble() == 10.0 && segundaCal.toString().toDouble() == 10.0){
            binding.txtDiez.setText("10")
            binding.txtPosibilidad.setText("Tú puedes bb")
            val urlImgHasbulla = "https://media.giphy.com/media/efH7XxDUwCuIRcaLyD/giphy.gif"
            val imagenHasbulla: Uri = Uri.parse(urlImgHasbulla)
            Glide.with(applicationContext).load(imagenHasbulla).into(binding.imgHasbulla)
        }
        else{
            val urlImgHasbullaSad = "https://media.giphy.com/media/XFADpTmFIgNpVDCBV1/giphy.gif"
            val imagenHasbullaSad: Uri = Uri.parse(urlImgHasbullaSad)
            Glide.with(applicationContext).load(imagenHasbullaSad).into(binding.imgHasbulla)
            binding.txtDiez.setText("Un milagro")
        }
    }
}