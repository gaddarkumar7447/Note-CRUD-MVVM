package com.example.notecrud

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.SearchView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.cardview.widget.CardView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notecrud.adapter.NoteAdapter
import com.example.notecrud.database.Note
import com.example.notecrud.database.NoteDatabase
import com.example.notecrud.databinding.ActivityMainBinding
import com.example.notecrud.model.NoteViewModel

class MainActivity : AppCompatActivity(), NoteAdapter.NoteClickListener, PopupMenu.OnMenuItemClickListener {
    private lateinit var dataBinding: ActivityMainBinding
    lateinit var database: NoteDatabase
    lateinit var viewModel: NoteViewModel
    lateinit var adapter: NoteAdapter
    lateinit var selectNote : Note
    var updateNote = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        if (result.resultCode == Activity.RESULT_OK){
            val note = result.data?.getSerializableExtra("note") as? Note
            if (note != null){
                viewModel.updateNote(note)
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application))[NoteViewModel::class.java]
        viewModel.allNode.observe(this, Observer {list ->
            list?.let {
                adapter.updateList(it)
            }
        })

        database = NoteDatabase.getDataBase(this)!!
        initUI()
    }

    private fun initUI() {
        dataBinding.recyclerView.hasFixedSize()
        dataBinding.recyclerView.layoutManager = StaggeredGridLayoutManager(2, LinearLayout.VERTICAL)
        adapter = NoteAdapter(this, this)
        dataBinding.recyclerView.adapter = adapter

        val getContent = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
            if (result.resultCode == Activity.RESULT_OK){
                var note = result.data?.getSerializableExtra("note") as? Note
                if (note != null){
                    viewModel.insertNote(note)
                }
            }
        }
        dataBinding.fbAddNote.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, AddNote::class.java))
        })

        dataBinding.searchNote.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                if (p0 != null){
                    adapter.filterSearch(p0)
                }
                return true
            }

        })

    }

    override fun onItemClicked(note: Note) {
        val intenet = Intent(this, AddNote::class.java)
        intenet.putExtra("current_note", note)
        updateNote.launch(intenet)
    }

    override fun onLongItemClicked(note: Note, cardView: CardView) {
        selectNote = note
        popUpdisplayed(cardView)
    }

    private fun popUpdisplayed(cardView: CardView) {
        val popupMenu = PopupMenu(this, cardView)
        popupMenu.setOnMenuItemClickListener(this@MainActivity)
        popupMenu.inflate(R.menu.menu)
        popupMenu.show()
    }

    override fun onMenuItemClick(p0: MenuItem?): Boolean {
        if (p0?.itemId == R.id.delete){
            viewModel.deleteNote(selectNote)
            return true
        }
        return false
    }
}