package com.edon.billot.databases

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.edon.billot.models.User

class DatabaseHandler private constructor(context: Context):
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    companion object{
        //make=singleton
        private var instance: DatabaseHandler? = null

        fun getInstance(context: Context): DatabaseHandler{
            if(instance == null){
                instance = DatabaseHandler(context)
            }
            return instance!!
        }

        private const val DB_NAME = "users_bd"
        private const val DB_VERSION = 1

        private const val TABLE = "users"
        private const val COL_ID = "_id"
        private const val COL_USERNAME = "username"
        private const val COL_POINTS = "points"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE = "CREATE TABLE " + TABLE + "(" +
             COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
             COL_USERNAME + " TEXT," +
             COL_POINTS + " INTEGER" + ")"

        db?.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS " + TABLE)
        onCreate(db)
    }

    //view all items in table
    fun getAllNames(): ArrayList<User>{
        val allNames = ArrayList<User>()

        val selectStatement = "SELECT * FROM $TABLE"

        val db = this.readableDatabase
        val cursor:Cursor

        try{
            cursor = db.rawQuery(selectStatement, null)
        } catch (e: SQLiteException){
            db.execSQL(selectStatement)
            return ArrayList()
        }

        var id: Int; var username: String; var userPoints: Int

        if(cursor.moveToFirst()){
            do{
                id = cursor.getInt(cursor.getColumnIndex(COL_ID))
                username = cursor.getString(cursor.getColumnIndex(COL_USERNAME))
                userPoints = cursor.getInt(cursor.getColumnIndex(COL_POINTS))

                allNames.add(User(id, username, userPoints))
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return allNames
    }

    //create new user in the table
    fun addUsername(user: User): Long{
        val db = this.writableDatabase
        val values: ContentValues = ContentValues()
        values.put(COL_USERNAME, user.userName)
        values.put(COL_POINTS, user.points)

        val success = db.insert(TABLE, null, values)
        db.close()
        return success
    }

    //update
    fun updateUser(user: User): Int{
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_USERNAME, user.userName)
        values.put(COL_POINTS, user.points)

        val success = db.update(TABLE, values, COL_ID + "=" + user.id, null)

        db.close()
        return success
    }

    //delete user
    fun deleteUser(user: User): Int{
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_ID, user.id)

        val success = db.delete(TABLE, COL_ID + "=" + user.id, null)

        db.close()
        return success
    }
}