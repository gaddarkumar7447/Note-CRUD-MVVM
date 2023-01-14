package com.example.notecrud.adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.notecrud.R
import com.example.notecrud.database.Note
import kotlin.random.Random

class NoteAdapter(private val context: Context, private val listener: NoteClickListener) : RecyclerView.Adapter<NoteAdapter.ViewModel>() {

    private val noteList = ArrayList<Note>()
    private val fullList = ArrayList<Note>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewModel {
        return ViewModel(LayoutInflater.from(context).inflate(R.layout.item_list, parent, false))
    }


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: ViewModel, position: Int) {
        val currNote = noteList[position]
        holder.title.text = currNote.title
        holder.title.isSelected = true
        holder.note.text = currNote.note
        holder.date.text = currNote.date
        holder.date.isSelected = true

        holder.note_layout.setCardBackgroundColor(holder.itemView.resources.getColor(randomColor(), null))

        holder.note_layout.setOnClickListener(View.OnClickListener {
            listener.onItemClicked(noteList[holder.adapterPosition])
        })

        holder.note_layout.setOnClickListener(View.OnClickListener {
            listener.onLongItemClicked(noteList[holder.adapterPosition], holder.note_layout)
            true
        })
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    private fun randomColor():Int{
        val arraList = ArrayList<Int>()
        arraList.add(R.color.noteCol1)
        arraList.add(R.color.noteCol2)
        arraList.add(R.color.noteCol3)
        arraList.add(R.color.noteCol4)
        arraList.add(R.color.noteCol5)
        arraList.add(R.color.noteCol6)

        val seed = System.currentTimeMillis().toInt()
        val randomIndex = Random(seed).nextInt(arraList.size)
        return arraList[randomIndex]
    }

    fun updateList(newList : List<Note>){
        fullList.clear()
        fullList.addAll(newList)

        noteList.clear()
        noteList.addAll(fullList)
        notifyDataSetChanged()
    }

    fun filterSearch(search : String){
        noteList.clear()
        for (item in fullList){
            if (item.title?.contains(search.lowercase()) == true || item.note?.contains(search.lowercase()) == true){
                noteList.add(item)
            }
        }
        notifyDataSetChanged()
    }

    interface NoteClickListener{
        fun onItemClicked(note: Note)
        fun onLongItemClicked(note: Note, cardView: CardView)

    }

    class ViewModel(view : View) : RecyclerView.ViewHolder(view){
        val note_layout : CardView = view.findViewById(R.id.cardView)
        var title : TextView = view.findViewById(R.id.title1)
        val note : TextView = view.findViewById(R.id.notes1)
        val date : TextView = view.findViewById(R.id.dateTime1)
    }
}