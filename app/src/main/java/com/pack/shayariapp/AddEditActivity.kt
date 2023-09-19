package com.pack.shayariapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.pack.shayariapp.databinding.ActivityAddEditBinding


class AddEditActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddEditBinding
    private lateinit var myViewModel: MyViewModel


    private var flag = false
    private var dialogTitle :String = "Add Title"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


//        Binding the view with the ViewBinding
        binding = ActivityAddEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(findViewById(R.id.toolbar))

        binding.tlTitle.title =dialogTitle

        myViewModel = ViewModelProvider(this).get(MyViewModel::class.java)

        val shayaritype = intent.getStringExtra("shayariType")
        if(shayaritype.equals("Edit")){
            Log.i("testing","it is working")
            flag =true
            binding.include5.etShayari.setText(intent.getStringExtra("shayari"))
            binding.tlTitle.title = intent.getStringExtra("title")
        }else{
            flag=false
        }

        binding.include5.btnSave.setOnClickListener{
            val shyariDesc:String = binding.include5.etShayari.text.toString()
            val title = binding.tlTitle.title.toString()
            if(flag){
                var id= intent.getIntExtra("id",-1)
                var dateLong= intent.getIntExtra("date",-1)
                var date = DateConverter().toDate(dateLong.toLong())
                //we have to create the DataEntity instance and assign to the propirty
                var dataEntityCurrent:NewDataEntity = NewDataEntity(id=id, date=date, shayari = shyariDesc, title = title)
                myViewModel.updateShayari(dataEntityCurrent)

            }else {
                myViewModel.addShayari(NewDataEntity(shayari = shyariDesc, title = title))
            }
            startActivity(Intent(applicationContext, MainActivity::class.java))
            this.finish()        }

        binding.tlTitle.setOnClickListener {
            binding.tlTitle.setOnClickListener {
                showEditTitleDialog()
            }
        }
        dialogTitle = binding.tlTitle.title.toString();
    }

    private fun showEditTitleDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.edit_title_dialog, null)
        val editTitle = dialogView.findViewById<EditText>(R.id.etTitle)
        editTitle.setText(dialogTitle)

        val dialog = AlertDialog.Builder(this)
            .setTitle("Enter the Title")
            .setView(dialogView)
            .setPositiveButton("Save") { _, _ ->
                val newTitle = editTitle.text.toString()
                if (newTitle.isNotEmpty()) {
                    dialogTitle = newTitle
                    binding.tlTitle.title = dialogTitle
                } else {
                    Toast.makeText(this, "Title cannot be empty", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .create()

        dialog.show()
    }


}