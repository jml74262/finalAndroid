package com.example.tdpa_proyectofinal

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.tdpa_proyectofinal.databinding.ActivityMainBinding
import com.example.tdpa_proyectofinal.databinding.ActivityResultadosBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class resultados : AppCompatActivity() {
    private lateinit var binding: ActivityResultadosBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityResultadosBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getImage()
        logica()

    }
    fun cargarImagen(){
        val urlImagenPrincipal = "https://picsum.photos/g/1920/1080?random"
        val imagenRandom: Uri = Uri.parse(urlImagenPrincipal)
        Glide.with(applicationContext).load(imagenRandom).into(binding.imgSecunddaria)
    }
    private fun getImage(){
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val call = getRetrofit().create(ApiService::class.java).getImage()
                val data = call.body()
                runOnUiThread {
                    if (call.isSuccessful) {
                        try {
                            val imagen:String? = data?.imagen
                            val url: Uri = Uri.parse(imagen.toString())
                            Glide.with(applicationContext).load(url).into(binding.imgSecunddaria)
                        } catch (e: Exception) {
                            print(e)
                        }
                    } else {
                        Toast.makeText(this@resultados, "Se guardó correctamente", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        } catch (e: Exception) {
            print(e)
        }
    }
    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://dog.ceo/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
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