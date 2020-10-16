package com.test.notesapp.DB

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.test.notesapp.DB.DatabaseContract.USER_EMAIL
import com.test.notesapp.Entity.Note
import com.test.notesapp.Entity.USER

class DatabaseHelper(context: Context?) : SQLiteOpenHelper(
    context,
    DATABASE_NAME,
    null,
    DATABASE_VERSION
) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE_NOTE)
        db.execSQL(SQL_CREATE_TABLE_USER)

    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int
    ) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TABLE_NOTE)
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TABLE_USER)
        onCreate(db)
    }

    companion object {
        var DATABASE_NAME = "dbnoteapp"
        private const val DATABASE_VERSION = 1
        private val SQL_CREATE_TABLE_NOTE = String.format(
            "CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            DatabaseContract.TABLE_NOTE,
            DatabaseContract._ID,
             DatabaseContract.USER_ID,
            DatabaseContract.TITLE,
            DatabaseContract.DESCRIPTION,
            DatabaseContract.DATE
        )

        private val SQL_CREATE_TABLE_USER = String.format(
            "CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            DatabaseContract.TABLE_USER,
            DatabaseContract._ID,
            DatabaseContract.USER_NAME,
            DatabaseContract.USER_IMAGE,USER_EMAIL
        )
    }



    fun checkIfEmailExists(title: String): Boolean {
        val db: SQLiteDatabase = this.writableDatabase

        val Query = "Select * from ${DatabaseContract.TABLE_USER} where $USER_EMAIL = '$title'"

        val cursor: Cursor = db?.rawQuery(Query, null)!!
        //returns only 0 and 1
        if (cursor.count <= 0) {
            cursor.close()
            return false
        }
        cursor.close()
        return true
    }


    fun insertUser(note: USER): Long?{
        val database: SQLiteDatabase = this.writableDatabase
        val initialValues = ContentValues()
        initialValues.put(DatabaseContract.USER_NAME, note.name)
        initialValues.put(DatabaseContract.USER_IMAGE, note.image)
        initialValues.put(USER_EMAIL, note.email)
        return database!!.insert(DatabaseContract.TABLE_USER, null, initialValues)
    }


    fun getUserEmailData(email: String): USER? {
        val db: SQLiteDatabase = this.writableDatabase

        val selectQuery =
            ("SELECT * FROM " + DatabaseContract.TABLE_USER + " WHERE "
                    + USER_EMAIL + " = '" + email+"'")

        val c = db.rawQuery(selectQuery, null)
        c?.moveToFirst()
        val mealunit = USER(c!!.getInt(c.getColumnIndex(DatabaseContract._ID)),c.getString(c.getColumnIndex(
            DatabaseContract.USER_NAME
        )),
            c.getString(c.getColumnIndex(DatabaseContract.USER_IMAGE)),c.getString(c.getColumnIndex(USER_EMAIL)))

        return mealunit
    }


    fun insert_note(note: Note): Long {
        val db: SQLiteDatabase = this.writableDatabase

        val initialValues = ContentValues()
        initialValues.put(DatabaseContract.TITLE, note.title)
        initialValues.put(DatabaseContract.USER_ID, note.userid)
        initialValues.put(DatabaseContract.DESCRIPTION, note.description)
        initialValues.put(DatabaseContract.DATE, note.date)
        return db!!.insert(DatabaseContract.TABLE_NOTE, null, initialValues)
    }

    fun getalldata(userid:Int): Cursor? {

        val db = this.writableDatabase
        return db.rawQuery(
            " select * from " + DatabaseContract.TABLE_NOTE +" where ${DatabaseContract.USER_ID} = '$userid'"+ " ORDER BY " + DatabaseContract._ID + " asc",
            null
        )
    }

    fun update_note(note: Note){
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(DatabaseContract.TITLE,note.title)
        cv.put(DatabaseContract.DESCRIPTION,note.description)

        val result: Int = db.update(DatabaseContract.TABLE_NOTE, cv, DatabaseContract._ID + "=?", arrayOf<String>(note.id.toString()))
        if(result>0)
        //Successfully Updated
            Log.d( "update_note: ", "Successfully Updated")
        else
        //Updation failed
            Log.d( "update_note: ", "Successfully Updated")

    }

    fun delete_note(note: Note){
        val db = this.writableDatabase
        val result: Int =  db.delete(DatabaseContract.TABLE_NOTE, DatabaseContract._ID + "=?", arrayOf<String>(note.id.toString()));

        if(result>0)
        //Successfully Updated
            Log.d( "update_note: ", "Successfully deleted")
        else
        //Updation failed
            Log.d( "update_note: ", "Successfully deleted")
    }

}