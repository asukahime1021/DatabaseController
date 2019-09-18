package com.uncala.databasecontroller

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SubSqlOpenHelper(val context : Context, val DB_NAME : String)
    :SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION){

    companion object{
        const val DB_VERSION = 1
        const val create = "create table databases (id INTEGER PRIMARY KEY AUTOINCREMENT, descr TEXT, delete_flg INTEGER)"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(create)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
}