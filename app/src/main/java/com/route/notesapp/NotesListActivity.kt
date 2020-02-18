package com.route.notesapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.route.notesapp.DataBase.Model.Note
import com.route.notesapp.DataBase.MyDataBase
import kotlinx.android.synthetic.main.activity_notes_list.*
import kotlinx.android.synthetic.main.content_notes_list.*

class NotesListActivity : AppCompatActivity() {

    var notesAdapter=NotesAdapter(null)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes_list)
        setSupportActionBar(toolbar)
        recycler_view.adapter=notesAdapter


        val swipeHandler =
            object : SwipeToDelete(applicationContext) {
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    notesAdapter.removeAt(viewHolder.adapterPosition)
                    showUndoSnackbar()

                }
            }

        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recycler_view)

        fab.setOnClickListener { view ->
            val intent = Intent(this@NotesListActivity,
                AddNoteActivity::class.java)
            startActivity(intent)

        }

    }

    override fun onStart() {
        super.onStart()
        val data = MyDataBase.getInstance(applicationContext)
            ?.notesDao()
            ?.getAllNotes();
        notesAdapter.changeData(data)

    }

    fun showUndoSnackbar(){

        val view = home_layout

        Snackbar.make(view, "Note Deleted Successfully", Snackbar.LENGTH_LONG)
            .setAction("Undo", {
                notesAdapter.undoDelete()
            })
            .addCallback(
                object : Snackbar.Callback(){
                    override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {

                        val note: Note? = notesAdapter.getRecentlyDeletedItem()
                        if (note != null){
                            MyDataBase.getInstance(applicationContext)
                                ?.notesDao()
                                ?.deleteNote(note)
                        }
                        super.onDismissed(transientBottomBar, event)
                    }
                }
            )
            .show()




        /* val snackbar = Snackbar.make(
             view, R.string.snack_bar_text,
             Snackbar.LENGTH_LONG
         )
         snackbar.setAction(R.string.snack_bar_undo, { v -> undoDelete() })
         snackbar.show()

         */
    }

}
