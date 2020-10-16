package com.test.notesapp.Interface

import com.test.notesapp.Entity.Note

interface Click_Interface {


    fun on_Insert(not:Note)

    fun on_itemClick(not:Note,position:Int)

      fun on_itemDelete(not:Note,position:Int)

   fun on_Update(not:Note,position:Int)


}