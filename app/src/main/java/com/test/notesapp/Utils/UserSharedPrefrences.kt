package com.test.notesapp.Utils
import android.content.Context
import android.content.SharedPreferences
import android.util.Log

class UserSharedPrefrences {


    lateinit var context: Context

    //    var preferences: UserSharedPrefrences? = null
    lateinit  var userPreferences: SharedPreferences
    lateinit var playeruserPreferences:SharedPreferences
    lateinit   var edit: SharedPreferences.Editor
    lateinit   var playeredit:SharedPreferences.Editor

    companion object {
        var preferences: UserSharedPrefrences? = null
        fun getInstance(context: Context): UserSharedPrefrences {

            if (preferences == null) {
                preferences = UserSharedPrefrences(context.applicationContext)
            }
            return preferences as UserSharedPrefrences
        }


    }


    constructor(context: Context){
        userPreferences = context.getSharedPreferences("UserSharedPreferences", Context.MODE_PRIVATE)
        edit = userPreferences.edit()
    }


    constructor(context: Context,s:String){
        playeruserPreferences = context.getSharedPreferences("UserSharedPreferencesplayer", Context.MODE_PRIVATE)
        playeredit = playeruserPreferences.edit()
    }

    fun Clear() {
        edit.clear()
        edit.commit()
    }

    fun setlogin(login: Boolean?) {
        edit.putBoolean("login", login!!)
        edit.commit()
    }

    fun getlogin(): Boolean? {
        return userPreferences.getBoolean("login", false)
    }

    fun setuserid(name: Int) {

        edit.putInt("userid", name)
        edit.commit()
    }

    fun getuserid(): Int {

        return userPreferences.getInt("userid", 0)!!
    }

    fun setname(name: String) {

        edit.putString("name", name)
        edit.commit()
    }

    fun getname(): String {

        return userPreferences.getString("name", "")!!
    }

    fun setemail(email: String) {
        Log.e("email", email)
        edit.putString("email", email)
        edit.commit()
    }

    fun getemail(): String {
        return userPreferences.getString("email", "")!!
    }

    fun setimage(image: String) {
        edit.putString("image", image)
        edit.commit()
    }

    fun getimage(): String {
        return userPreferences.getString("image", "")!!
    }


}