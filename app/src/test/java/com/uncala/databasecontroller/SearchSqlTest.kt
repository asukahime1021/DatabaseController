package com.uncala.databasecontroller

import android.os.Environment
import android.util.Log
import org.junit.Test

class SearchSqlTest{

    @Test
    fun test1(){
        val search = SearchDatabase()
        val list = search.searchSqlite(Environment.getExternalStorageDirectory().path)
        assert(list.size == 0)
        for(i in list)
            assert(i == null)
    }
}
