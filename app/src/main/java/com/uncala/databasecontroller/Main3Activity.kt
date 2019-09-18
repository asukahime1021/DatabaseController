package com.uncala.databasecontroller

import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.ArrayAdapter
import android.widget.GridView
import android.widget.ListView

import kotlinx.android.synthetic.main.activity_main3.*

class Main3Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        val intent = Intent()
        val dbTitle = intent.getStringExtra("dbTitle")
        val query = intent.getStringExtra("query")
        val helper = RunSqlOpenHelper(applicationContext, dbTitle)
        val rdb = helper.readableDatabase

        val c = rdb.rawQuery(query, null)
        c.use {
            val columnCount = c.columnCount
            val columns = arrayOfNulls<Int>(columnCount)

            var index = 0
            while(columnCount > index){
                columns[index] = c.getType(index)
                index++
            }

            val dataArray = mutableListOf<MutableList<Any?>>()
            while(c.moveToNext()){
                val dataRawArray = mutableListOf<Any?>()
                var indexColumn = 0
                for(column in columns){
                    val data : Any? = when(column){
                        Cursor.FIELD_TYPE_BLOB -> c.getBlob(indexColumn) as Any?
                        Cursor.FIELD_TYPE_FLOAT -> c.getFloat(indexColumn) as Any?
                        Cursor.FIELD_TYPE_INTEGER -> c.getInt(indexColumn) as Any?
                        Cursor.FIELD_TYPE_STRING -> c.getString(indexColumn) as Any?
                        else -> null
                    }
                    dataRawArray.add(data)
                }
                dataArray.add(dataRawArray)
            }

            val gridview = findViewById<GridView>(R.id.grid)
            gridview.numColumns = columnCount
//            val adapter = ArrayAdapter<Any?>(applicationContext, android.R.layout.simple_list_item_1, dataArray)

        }
    }
}
