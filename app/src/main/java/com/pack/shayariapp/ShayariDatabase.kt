package com.pack.shayariapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [NewDataEntity::class],
version = 3,
exportSchema = true)
@TypeConverters(DateConverter::class)
abstract class ShayariDatabase: RoomDatabase() {

    abstract fun getShayariDao():ShayariDao
    companion object{

        val magration_1_2  = object : Migration(1,2){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE shayari_table ADD COLUMN title VARCHAR NOT NULL DEFAULT('Add The Title')")
            }

        }

        val migration_2_3 = object : Migration(2,3){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE new_shayari_table (date INTEGER PRIMARY KEY NOT NULL,id INTEGER NOT NULL,  title TEXT NOT NULL, shayari TEXT NOT NULL )")

                database.execSQL("INSERT INTO new_shayari_table(id,date, title, shayari) SELECT id,date, title, shayari from shayari_table")

                database.execSQL("DROP TABLE shayari_table")

                database.execSQL("ALTER TABLE new_shayari_table RENAME TO shayari_table")


            }

        }

        private var INSTANCE:ShayariDatabase ?= null

        fun getDatabase(context: Context):ShayariDatabase{
            if(INSTANCE == null){
                synchronized(this){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                                                    ShayariDatabase::class.java,
                                                    "shayari_database").addMigrations(migration_2_3).build()
                }
            }
            return INSTANCE!!
        }
    }
}