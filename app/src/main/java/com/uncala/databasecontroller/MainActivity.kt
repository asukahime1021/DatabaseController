package com.uncala.databasecontroller

import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.support.v4.app.ActivityCompat
import android.util.Log
import android.view.View
import android.widget.*
import java.io.File
import java.nio.file.Paths
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {
    lateinit var helper : SQLiteOpenHelper
    lateinit var sdbWrite : SQLiteDatabase
    lateinit var sdbRead : SQLiteDatabase
    lateinit var dbList : MutableList<String>
    var errorMessage = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbList = mutableListOf()

        if(Build.VERSION.SDK_INT >= 23) {
            System.out.println("PERMISSION")
            checkPermission()
        }else {
            System.out.println("MAIN ELSEELSEELSE")
        }

        helper = SubSqlOpenHelper(applicationContext, "db_controller")
        sdbWrite = helper.writableDatabase
        sdbRead = helper.readableDatabase

        getDBList()

        val selected = getSelectedDB()

        val button = findViewById<Button>(R.id.newDB)
        button.setOnClickListener {
            val newtext = findViewById<EditText>(R.id.dbName).text.toString()
            try {
                createSchema(newtext)
                val intent2 = Intent(applicationContext, Main2Activity::class.java)
                intent2.putExtra("db_name", newtext)
                startActivity(intent2)
            } catch (e : SQLException) {
                e.printStackTrace()
                errorMessage = "データベース作成に失敗しました。"
            }
        }
    }

    fun getDBList(){
        dbList.add("選択")

        try{
            val getDBquery = "SELECT descr FROM databases WHERE delete_flg = '0'"
            val c = sdbRead.rawQuery(getDBquery, null)

            c.use {
                while(c.moveToNext()){
                    dbList.add(c.getString(0))
                }
            }
        }catch(e : SQLException){
            e.printStackTrace()
            errorMessage = "データベースの取得に失敗しました。開発者に問い合わせてください。"
        }
    }

    private fun getSelectedDB() : String{
        var selected = ""
        val spinner = findViewById<Spinner>(R.id.spinner1)
        val spinAdapter = ArrayAdapter<String>(applicationContext, android.R.layout.simple_spinner_item, dbList)
        spinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = spinAdapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selected = parent?.selectedItem as String
                if(selected != "選択"){
                    intent.putExtra("db_name", selected)
                    startActivity(intent)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
        return selected
    }

    fun createSchema(dbName : String){
        val value = ContentValues()
        value.put("descr", dbName)
        value.put("delete_flg", "0")
        sdbWrite.insertOrThrow("databases", null, value)
    }

    fun getDatabaseFiles() : MutableList<String> {
        val path = arrayOf(Environment.getExternalStorageDirectory().path, "/data")
        System.out.println(path)
        val search = SearchDatabase()

        Log.d("PATH : ",path[1])
        return search.searchSqlite(path[1])
    }

    fun checkPermission(){

        if(ActivityCompat.checkSelfPermission(applicationContext, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            getDBList()
        }else{
            requestLocationPermission()
        }
    }

    fun requestLocationPermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)){
            ActivityCompat.requestPermissions(this,arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 1)
        }else{
            ActivityCompat.requestPermissions(this,arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 1)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if(requestCode == 1){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getDBList()
            }
        }
    }
}
