package com.pack.shayariapp

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface ShayariDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertShayari(dataEntity: NewDataEntity)

    @Delete
    suspend fun deleteShayari(dataEntity: NewDataEntity)

//    @Update(onConflict = OnConflictStrategy.IGNORE)
    @Update
    suspend fun updateShayari(dataEntity: NewDataEntity)

    @Query("SELECT * FROM shayari_table ORDER BY date DESC")
    fun getAllShayari(): LiveData<List<NewDataEntity>>
}