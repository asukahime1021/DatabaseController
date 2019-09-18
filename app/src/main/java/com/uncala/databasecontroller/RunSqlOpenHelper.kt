package com.uncala.databasecontroller

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class RunSqlOpenHelper(val context : Context, val dbName : String, DB_RUN_VERSION : Int)
    : SQLiteOpenHelper(context, dbName, null, DB_RUN_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        val create = "create table user (id INTEGER, name TEXT)"
        db?.execSQL(create)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
}