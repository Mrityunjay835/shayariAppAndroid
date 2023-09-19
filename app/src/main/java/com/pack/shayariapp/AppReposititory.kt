package com.pack.shayariapp

import androidx.lifecycle.LiveData

class AppReposititory(private val shayariDao: ShayariDao ){
    fun getAllShayari(): LiveData<List<NewDataEntity>>
    {
        return shayariDao.getAllShayari()
    }
    suspend fun insert(dataEntity: NewDataEntity){
        shayariDao.insertShayari(dataEntity)
    }

    suspend fun update(dataEntity: NewDataEntity){
        shayariDao.updateShayari((dataEntity))
    }

    suspend fun delete(dataEntity: NewDataEntity){
        shayariDao.deleteShayari(dataEntity)
    }


}