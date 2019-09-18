package com.uncala.databasecontroller

import java.io.File
import java.nio.file.Path

class SearchDatabase {
    private val list = mutableListOf<String>("選択")

    fun searchSqlite(path : String) : MutableList<String>{
        val dir = File(path)
        val listFiles = dir.listFiles()
        if(listFiles != null){
            for(i in listFiles){
                if(i.isFile) {
                    if (isSqlite(i.absolutePath))
                        list.add(i.name.substringBefore("."))
                }else if(i.isDirectory){
                    searchSqlite(i.path.toString())
                }
            }
        }
        return list
    }
}

fun isSqlite(path : String) : Boolean{
    if(path.contains("test", true)){
        System.out.println("あるじゃねえか")
    }
    return if(path.length > 0 && path.endsWith(".sqlite3")) true else false
}