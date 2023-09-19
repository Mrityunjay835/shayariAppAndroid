package com.pack.shayariapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "shayari_table")
data class DataEntity(

    @ColumnInfo(name = "date")
    var date:Date=Date(),

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id:Int=0,


    @ColumnInfo(name="title")
    var title:String,

    @ColumnInfo(name = "shayari")
    var shayari:String)

{


}