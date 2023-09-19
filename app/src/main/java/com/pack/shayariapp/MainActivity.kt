package com.pack.shayariapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.pack.shayariapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), RvAdapter.OnItemClickListener, RvAdapter.OnDeleteItemClickListener {

    private lateinit var binding: ActivityMainBinding
    private  var shayariList = ArrayList<NewDataEntity>()
    private lateinit var myViewModel:MyViewModel

    // call adaptor when it will call then only create the instance
    private val rvAdapter by lazy {
        RvAdapter(this , shayariList,this,this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //create instance of viewmodel
        myViewModel = ViewModelProvider(this).get(MyViewModel::class.java)

//        sharayiList.addAll(SampleData.data)
        binding.rv.layoutManager = LinearLayoutManager(this)


        myViewModel.allShayari.observe(this, Observer { list ->
            list?.let {
                shayariList.clear()
                shayariList.addAll(it)
                rvAdapter.notifyDataSetChanged()
            }
        })


        binding.rv.adapter = rvAdapter
        rvAdapter.notifyDataSetChanged()

        binding.fab.setOnClickListener {
            startActivity(Intent(this,AddEditActivity::class.java))
        }

        binding.fabSortedByName.setOnClickListener{
            myViewModel.getSortDataByName()
            myViewModel.sortedData.observe(this, Observer { list ->
                list?.let {
                    shayariList.clear()
                    shayariList.addAll(it)
                    rvAdapter.notifyDataSetChanged()
                }
            })
        }
    }

    override fun onItemClick(dataEntity: NewDataEntity) {
        var intent = Intent(this, AddEditActivity::class.java)
        intent.putExtra("shayariType","Edit")
        intent.putExtra("id",dataEntity.id)
        intent.putExtra("shayari",dataEntity.shayari)
        intent.putExtra("date",dataEntity.date)
        //here we have to add the title
        intent.putExtra("title",dataEntity.title)
        startActivity(intent)
    }

    override fun deleteItem(dataEntity: NewDataEntity,postion:Int) {
        myViewModel.deleteShayari(dataEntity)
        shayariList.removeAt(postion)
        myViewModel.updateThedataList(shayariList)
    }


}