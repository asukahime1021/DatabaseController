package com.uncala.databasecontroller

import android.content.ContentValues
import android.content.Intent
import android.database.SQLException
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView

class Main2Activity : AppCompatActivity() {
    lateinit var helper : RunSqlOpenHelper
    lateinit var dbTitle : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        dbTitle = intent.getStringExtra("db_name")
        findViewById<TextView>(R.id.dbTitle).text = dbTitle

        val queryText = findViewById<EditText>(R.id.query).text.toString()
        val queryList = queryText.split(";")

        for(query in queryList){
            val queryHead = query.substring(0,6)
            when(queryHead){
                "SELECT" -> openSelect(query)
                "DELETE" -> updateInsertDelete(query, 0)
                "UPDATE" -> updateInsertDelete(query, 1)
                "INSERT" -> updateInsertDelete(query, 2)
                "CREATE" -> createDropAlter(query)
                else -> if(queryHead.startsWith("ALTER", true) || queryHead.startsWith("DROP", true))
                            createDropAlter(query)
            }
        }
    }

    fun openSelect(query : String){
        val intent = Intent(applicationContext, Main3Activity::class.java)
        intent.putExtra("query", query)
        intent.putExtra("dbTitle", dbTitle)
        startActivity(intent)
    }

    fun updateInsertDelete(query : String, duiFlg : Int){
        helper = RunSqlOpenHelper(applicationContext, dbTitle)

        try {
            when (duiFlg) {

            }
        }catch(e : SQLException){
            e.printStackTrace()

        }
    }

    fun createDropAlter(query : String){

    }

    fun testSelect(){
        val wdb = helper.writableDatabase
        val value = ContentValues()
        value.put("id", 1)
        value.put("name", "testUser")
        System.out.println(wdb.path)
        wdb.insertOrThrow("user", null, value)
        System.out.println(wdb.path)

        val rdb = helper.readableDatabase
        val c = rdb.rawQuery("select * from user", null)
        c.use {
            while(c.moveToNext()){
                System.out.println(c.getString(1))
                val i = 0;
            }
        }
    }
}
