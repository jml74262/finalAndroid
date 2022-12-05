package com.example.tdpa_proyectofinal

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.CursorFactory
import android.database.sqlite.SQLiteOpenHelper
import java.util.prefs.PreferencesFactory

class AdminSQLiteOpenHelper(
    context: Context,
    name: String,
    factory: CursorFactory?,
    version: Int
):  SQLiteOpenHelper(context,name,factory,version)
{
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE estudiantes(_id integer primary key autoincrement, nombre text, nombreMateria text, primerCal text, segundaCal text)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

}