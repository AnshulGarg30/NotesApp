package com.test.notesapp.Dialog

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.test.notesapp.R
import com.test.notesapp.Interface.Click_Interface
import com.test.notesapp.Utils.UserSharedPrefrences
import com.test.notesapp.DB.DatabaseHelper
import com.test.notesapp.Entity.Note
import com.test.notesapp.Utils.RoundedBottomSheetDialogFragment
import kotlinx.android.synthetic.main.dialog_note_create.view.*

class NoteCreate_BottomSheetDialog(val mclickInterface: Click_Interface) :
    RoundedBottomSheetDialogFragment(), View.OnClickListener {


    lateinit var database: DatabaseHelper
    lateinit var mview: View
    lateinit var pref: UserSharedPrefrences
    lateinit var clickInterface: Click_Interface
    lateinit var note: Note
    var val_position = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_note_create, container, false)
        initl(view)
        return view;
    }

    fun initl(view: View) {
        mview = view

        database = DatabaseHelper(activity!!)
        pref = UserSharedPrefrences.getInstance(activity!!)
        clickInterface = mclickInterface
        view.tv_close.setOnClickListener(this)
        view.tv_create.setOnClickListener(this)

        if (arguments != null) {
            view.tv_create.setText("Update")

            note = arguments!!.getSerializable("note") as Note
            mview.et_tital.setText(note.title)
            mview.et_description.setText(note.description)


            val_position = arguments!!.getInt("pos", 0)

        }
    }


    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.tv_close -> {
                dismiss()
            }

            R.id.tv_create -> {


                if ((mview.et_tital.text.toString().length > 0) && (mview.et_description.text.toString().length > 0)) {

                    if (view!!.tv_create.text.toString().equals("Create")) {

                        val value_id: Long = database.insert_note(
                            Note(
                                0,
                                pref.getuserid(),
                                mview.et_tital.text.toString(),
                                mview.et_description.text.toString(),
                                System.currentTimeMillis().toString()
                            )
                        )
                        Log.e("onClick: ", "$value_id")

                        clickInterface.on_Insert(
                            Note(
                                value_id.toInt(),
                                pref.getuserid(),
                                mview.et_tital.text.toString(),
                                mview.et_description.text.toString(),
                                System.currentTimeMillis().toString()
                            )
                        )

                    } else {
                        database.update_note(
                            Note(
                                note.id,
                                pref.getuserid(),
                                mview.et_tital.text.toString(),
                                mview.et_description.text.toString(),
                                note.date
                            )
                        )
                        clickInterface.on_Update(
                            Note(
                                note.id,
                                pref.getuserid(),
                                mview.et_tital.text.toString(),
                                mview.et_description.text.toString(),
                                note.date
                            ), val_position
                        )


                    }
                    dismiss()

                } else {
                    Toast.makeText(activity!!, "Fields can not be blank", Toast.LENGTH_SHORT).show()
                }
            }


        }
    }


}