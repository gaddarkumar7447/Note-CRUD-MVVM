package com.example.notecrud

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.notecrud.databinding.ActivityAddNoteBinding
import com.example.notecrud.database.Note
import java.text.SimpleDateFormat
import java.util.*

class AddNote : AppCompatActivity() {
    private lateinit var dataBinding : ActivityAddNoteBinding
    private lateinit var note1 : Note
    private lateinit var old_note : Note
    var isUpdate = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_note)

        try {
            old_note = intent.getSerializableExtra("current_note") as Note
            dataBinding.edTitle.setText(old_note.title)
            dataBinding.edNote.setText(old_note.note)
            isUpdate = true
        }catch (e : Exception){
            e.printStackTrace().toString()
        }

        dataBinding.UpdateNote.setOnClickListener(View.OnClickListener {
            val title = dataBinding.edTitle.toString().trim()
            var note = dataBinding.edNote.toString().trim()

            if (title.isNotEmpty() || note.isNotEmpty()){
                val formatter = SimpleDateFormat("EEE, d MMM yyy HH:mm a")
                if (isUpdate){
                    note1 = Note(old_note.id, title, note, formatter.format(Date()))
                }else{
                    note1 = Note(null, title, note, formatter.format(Date()))
                }
                val intent = Intent()
                intent.putExtra("note", note1)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
            else{
                Toast.makeText(this, "Please enter some data", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
        })
    }
}