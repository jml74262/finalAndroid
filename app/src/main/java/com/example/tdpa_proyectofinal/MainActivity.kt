package com.example.tdpa_proyectofinal

import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.tdpa_proyectofinal.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        cargarImagen()

        binding.btnAnalizar.setOnClickListener{
            enviarDatos()
        }

        binding.btnGuardar.setOnClickListener{
            insertar()

        }

        binding.btnBuscar.setOnClickListener{
                encontrarNombre()

        }

        binding.btnBorrar.setOnClickListener{
            borrar()
        }

        binding.btnModificar.setOnClickListener{
            modificar()
        }

    }
    fun cargarImagen(){
        val urlImagenPrincipal = "https://picsum.photos/g/1920/1080?random"
        val imagenRandom:Uri = Uri.parse(urlImagenPrincipal)
        Glide.with(applicationContext).load(imagenRandom).into(binding.imgPrincipal)
    }
    fun insertar(){
        if (validarTodosCampos()){
            val admin = AdminSQLiteOpenHelper(this,"administracion",null,1)
            val db = admin.writableDatabase
            val registro = ContentValues()

            registro.put("nombre", binding.txtNombre.text.toString())
            registro.put("nombreMateria", binding.txtNombreMateria.text.toString())
            registro.put("primerCal", binding.txtCal1.text.toString())
            registro.put("segundaCal", binding.txtCal2.text.toString())
            db.insert("estudiantes", null, registro)
            db.close()

            binding.txtNombre.setText("")
            binding.txtNombreMateria.setText("")
            binding.txtCal1.setText("")
            binding.txtCal2.setText("")
            Toast.makeText(this, "Se guardó correctamente", Toast.LENGTH_SHORT).show()
        }

    }
    fun encontrarNombre(){

        if (validarNombre()){
            try{
                val admin = AdminSQLiteOpenHelper(this,"administracion",null,1)
                val db = admin.writableDatabase
                var nom = binding.txtNombre.text
                var fila = db.rawQuery("SELECT nombreMateria, primerCal, segundaCal FROM estudiantes WHERE nombre='$nom'",null)
                if (fila.moveToFirst()){
                    binding.txtNombreMateria.setText(fila.getString(0))
                    binding.txtCal1.setText(fila.getString(1))
                    binding.txtCal2.setText(fila.getString(2))
                }
                else{
                    Toast.makeText(this, "No se encontró el estudiante", Toast.LENGTH_SHORT).show()
                }
            }catch (e:Exception){
                Log.d("findByNombre", e.toString())
            }

        }
    }
    fun borrar(){
        if (validarBorrar()){
            val admin = AdminSQLiteOpenHelper(this,"administracion",null,1)
            val db = admin.writableDatabase
            var nom = binding.txtNombre.text
            var cant = db.delete("estudiantes", "nombre='$nom'",null)
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
    fun modificar(){
        if (validarTodosCampos()){
            val admin = AdminSQLiteOpenHelper(this, "administracion", null, 1)
            val bd = admin.writableDatabase
            val registro = ContentValues()
            registro.put("nombre", binding.txtNombre.text.toString())
            registro.put("nombreMateria", binding.txtNombreMateria.text.toString())
            registro.put("primerCal", binding.txtCal1.text.toString())
            registro.put("segundaCal", binding.txtCal2.text.toString())
            val cant = bd.update("estudiantes", registro, "nombre='${binding.txtNombre.text}'", null)
            bd.close()
            if(cant == 1){
                Toast.makeText(this, "El estudiante se actualizó correctamente", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "No hubo coincidencia con ese nombre", Toast.LENGTH_SHORT).show()
            }
        }
    }
    fun enviarDatos(){
        if (validarTodosCampos()){
            val intento = Intent(this, resultados::class.java)
            intento.putExtra("nombre",binding.txtNombre.text.toString())
            intento.putExtra("nombreMateria",binding.txtNombreMateria.text.toString())
            intento.putExtra("primerCal",binding.txtCal1.text.toString())
            intento.putExtra("segundaCal",binding.txtCal2.text.toString())
            startActivity(intento)
        }

    }

    //VALIDACIONES//
    private fun validarTodosCampos(): Boolean{
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
    private fun validarNombre(): Boolean{
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
    private fun validarBorrar(): Boolean{
        var valido = true
        if(TextUtils.isEmpty(binding.txtNombre.text.toString())){
            binding.txtNombre.error = "Favor de poner un nombre"
            valido = false
        }
        return valido
    }

}