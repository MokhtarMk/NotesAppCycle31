package com.route.notesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.route.notesapp.DataBase.MyDataBase
import kotlinx.android.synthetic.main.activity_update.*

class UpdateActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        val intent = intent
        val noteId = intent.getIntExtra("note",-1)

        val note = MyDataBase.getInstance(applicationContext)
            ?.notesDao()
            ?.searchNoteById(noteId)

        titleTv.setText(note?.title)
        descriptionTv.setText(note?.description)
        dateTv.setText(note?.time)


        update_note_button.setOnClickListener{
            note?.title = titleTv.text.toString()
            note?.description = descriptionTv.text.toString()
            note?.time = dateTv.text.toString()

            if (note != null) {
                MyDataBase.getInstance(applicationContext)
                    ?.notesDao()
                    ?.updateNote(note)
            }

            showSuccessMessage()
        }

    }

    fun showSuccessMessage() {
        val builder = AlertDialog.Builder(this);
        builder.setMessage("Note updated successfully")
        builder.setPositiveButton("Ok", { dialogInterface, i ->
            dialogInterface.dismiss()
            finish()
        })
        builder.setCancelable(false)
        builder.show()
    }
}
