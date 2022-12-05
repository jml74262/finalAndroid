package com.example.tdpa_proyectofinal

import android.content.ContentValues
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.tdpa_proyectofinal.databinding.ActivityMainBinding
import java.io.Console

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        cargarImagen()
        binding.btnGuardar.setOnClickListener{
            insertar()
        }
        binding.btnBuscar.setOnClickListener{
                encontrarNombre()

        }
        binding.btnBorrar.setOnClickListener{
            borrar()
        }

    }
    fun cargarImagen(){
        val urlImagenPrincipal = "https://picsum.photos/g/1920/1080?random"
        val imagenRandom:Uri = Uri.parse(urlImagenPrincipal)
        Glide.with(applicationContext).load(imagenRandom).into(binding.imgPrincipal)
    }
    fun insertar(){
        if (validarInsert()){
            val admin = AdminSQLiteOpenHelper(this,"administracion",null,1)
            val db = admin.writableDatabase
            val registro = ContentValues()

            registro.put("nombre", binding.txtNombre.text.toString())
            registro.put("nombre_materia", binding.txtNombreMateria.text.toString())
            registro.put("primerCal", binding.txtCal1.text.toString())
            registro.put("segundaCal", binding.txtCal2.toString())
            db.close()

            binding.txtNombre.setText("")
            binding.txtNombreMateria.setText("")
            binding.txtCal1.setText("")
            binding.txtCal2.setText("")
            Toast.makeText(this, "Se guardó correctamente", Toast.LENGTH_SHORT).show()
        }

    }
    fun encontrarNombre(){

        if (validarBuscar()){
            try{
                val admin = AdminSQLiteOpenHelper(this,"administracion",null,1)
                val db = admin.writableDatabase
                var nom = binding.txtNombre.text
                var fila = db.rawQuery("select * from "+
                        "estudiantes where nombre='"+nom+"'",null)
                if (fila.moveToFirst()){
                    binding.txtNombreMateria.setText(fila.getString(0))
                    binding.txtCal1.setText(fila.getString(1))
                    binding.txtCal2.setText(fila.getString(2))
                }
                //else{
                 //   Toast.makeText(this, "No se encontró el estudiante", Toast.LENGTH_SHORT).show()
                //}
            }catch (e:Exception){
                Log.d("mamadas", e.toString())
            }

        }
    }
    fun borrar(){
        if (validarBuscar()){
            val admin = AdminSQLiteOpenHelper(this,"administracion",null,1)
            val db = admin.writableDatabase
            var cant = db.delete("estudiantes", "nombre=${binding.txtNombre.text.toString()}",null)
            db.close()
            if (cant == 1){
                Toast.makeText(this, "Se borró adecuadamente", Toast.LENGTH_SHORT).show()
                binding.txtNombre.setText("")
                binding.txtNombreMateria.setText("")
                binding.txtCal1.setText("")
                binding.txtCal2.setText("")
            }
            else{
                Toast.makeText(this, "No se encontró el estudiante", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun validarInsert(): Boolean{
        var valido = true
        if(TextUtils.isEmpty(binding.txtNombre.text.toString())){
            binding.txtNombre.error = "Coloca un nombre"
            valido = false
        }
        if(TextUtils.isEmpty(binding.txtNombreMateria.text.toString())){
            binding.txtNombreMateria.error = "Coloca un nombre de materia"
            valido = false
        }
        if(TextUtils.isEmpty(binding.txtCal1.text.toString())){
            binding.txtCal1.error = "Coloca tu primer calificacion"
            valido = false
        }
        if(TextUtils.isEmpty(binding.txtCal2.text.toString())){
            binding.txtCal2.error = "Coloca tu segunda calificacion"
            valido = false
        }
        return valido
    }
    private fun validarBuscar(): Boolean{
        var valido = true
        if(TextUtils.isEmpty(binding.txtNombre.text.toString())){
            binding.txtNombre.error = "Favor de poner un nombre"
            valido = false
        }
        if(!TextUtils.isEmpty(binding.txtNombreMateria.text.toString())){
            binding.txtNombreMateria.error = "Favor de vaciar este campo"
            valido = false
        }
        if(!TextUtils.isEmpty(binding.txtCal1.text.toString())){
            binding.txtCal1.error = "Favor de vaciar este campo"
            valido = false
        }
        if(!TextUtils.isEmpty(binding.txtCal2.text.toString())){
            binding.txtCal2.error = "Favor de vaciar este campo"
            valido = false
        }
        return valido
    }
}