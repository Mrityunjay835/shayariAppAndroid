package com.pack.shayariapp

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.ArrayList
import java.util.Collections


class MyViewModel(application: Application) : AndroidViewModel(application) {
    var allShayari: LiveData<List<NewDataEntity>>
    var sortedData= MutableLiveData<List<NewDataEntity>?>()
    private val appReposititory: AppReposititory

    init {
        val dao = ShayariDatabase.getDatabase(application).getShayariDao()
        appReposititory = AppReposititory(dao)
        allShayari = appReposititory.getAllShayari()
    }

    fun addShayari(dataEntity: NewDataEntity) = viewModelScope.launch(Dispatchers.IO) {
        appReposititory.insert(dataEntity)
    }

    fun updateShayari(dataEntity: NewDataEntity) = viewModelScope.launch(Dispatchers.IO) {
        appReposititory.update(dataEntity)
    }
    fun deleteShayari(dataEntity: NewDataEntity) = viewModelScope.launch(Dispatchers.IO) {
        appReposititory.delete(dataEntity)
    }

    fun getSortDataByName(){
        val unsortedList = allShayari.value ?: return
        val sortedList = unsortedList.sortedWith(NameSortingComparator())
//.............................................................
        sortedData.postValue(sortedList)
        Log.i("allShayari","${sortedData?.value}")
        Log.i("allShayari","${sortedList}")
//...........................................................
        allShayari = sortedData as LiveData<List<NewDataEntity>>
        Log.i("allShayari","${allShayari.value}")

    }

    class NameSortingComparator: Comparator<NewDataEntity>{
        override fun compare(p0: NewDataEntity?, p1: NewDataEntity?): Int {
            if(p0 == null || p1 == null){
                return 0
            }
            return p0.title.compareTo(p1.title)
        }

    }

    fun updateThedataList(latestList:ArrayList<NewDataEntity>)
    {
        sortedData.postValue(latestList)

    }
}