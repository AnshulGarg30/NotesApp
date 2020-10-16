package com.test.notesapp.Entity

import java.io.Serializable

data class Note(val id:Int, val userid:Int,  val title:String, val description:String,val date:String) : Serializable