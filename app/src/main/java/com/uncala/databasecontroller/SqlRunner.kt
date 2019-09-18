package com.uncala.databasecontroller

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.support.v4.content.ContextCompat

class SqlRunner(val context : Context, val DB_NAME : String) {
    val helper = SubSqlOpenHelper(context, DB_NAME)

    fun runQuery(query : String) : Boolean{
        var start = query.substring(0,6).toUpperCase()
        when(start){
            "INSERT" -> return insertString(query)
            "DELETE" -> return updateString(query)
            "UPDATE" -> return updateString(query)
        }
        return false
    }

    fun openSelect(query : String){
        val intent = Intent(context, SelectActivity::class.java)
        intent.putExtra("query", query)

    }


    fun insertString(query : String) : Boolean{
        try{
            val sdb = helper.writableDatabase
            sdb.execSQL(query)
        }catch (e : SQLException){
            return false
        }
        return true
    }

    fun updateString(query : String) : Boolean{
        try{
            val sdb = helper.writableDatabase
            sdb.execSQL(query)
        }catch(e : SQLException){
            return false
        }
        return true
    }
}