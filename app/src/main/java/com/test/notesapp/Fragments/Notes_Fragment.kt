package com.test.notesapp.Fragments

import android.database.Cursor
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.notesapp.Adapter.Note_Adapter
import com.test.notesapp.Dialog.NoteCreate_BottomSheetDialog
import com.test.notesapp.R
import com.test.notesapp.Interface.Click_Interface
import com.test.notesapp.Utils.UserSharedPrefrences
import com.test.notesapp.DB.DatabaseContract
import com.test.notesapp.DB.DatabaseHelper
import com.test.notesapp.Entity.Note
import kotlinx.android.synthetic.main.fragment_notes_page.view.*

class Notes_Fragment : Fragment() ,View.OnClickListener ,
    Click_Interface {


    lateinit var pref:UserSharedPrefrences
    lateinit var database: DatabaseHelper
    lateinit var list: ArrayList<Note>
    lateinit var mview: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_notes_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mview = view
        pref= UserSharedPrefrences.getInstance(context!!)

        database = DatabaseHelper(activity!!)
        list = ArrayList()

        view.tvname.text = pref.getname()
        view.fab_add.setOnClickListener(this)
        view.iv_logout.setOnClickListener(this)

        view.rv_notes.layoutManager = LinearLayoutManager(activity!!)
        view.rv_notes.adapter = Note_Adapter(activity!!,list,this)


        call_notedata()

    }

    override fun onClick(p0: View?) {
        when(p0!!.id){
            R.id.iv_logout->{
                pref.Clear()
                fragmentManager!!.beginTransaction().replace(R.id.MainFrame,Login_Fragment()).commit()
            }
            R.id.fab_add->{
                val myRoundedBottomSheet = NoteCreate_BottomSheetDialog(this)
                myRoundedBottomSheet.show(fragmentManager!!, myRoundedBottomSheet.tag)
            }

        }


    }




    fun call_notedata(){

        val c: Cursor = database.getalldata(pref.getuserid())!!

        if (c != null && c.moveToFirst()) {
            do{
                list.add(
                    Note(c.getInt(c.getColumnIndex(DatabaseContract._ID)),
                    c.getInt(c.getColumnIndex(DatabaseContract.USER_ID)),
                    c.getString(c.getColumnIndex(DatabaseContract.TITLE)),
                    c.getString(c.getColumnIndex(DatabaseContract.DESCRIPTION)),
                    c.getString(c.getColumnIndex(DatabaseContract.DATE)))
                )
            } while(c.moveToNext());
        }
        mview.rv_notes.adapter!!.notifyDataSetChanged()
    }

    override fun on_Insert(not: Note) {
        list.add(0,not)
        mview.rv_notes.adapter!!.notifyDataSetChanged()
    }



    override fun on_itemClick(not: Note, position: Int) {
        var bundle = Bundle()
        bundle.putSerializable("note",not)
        bundle.putInt("pos",position)
        val myRoundedBottomSheet = NoteCreate_BottomSheetDialog(this)
        myRoundedBottomSheet.arguments = bundle
        myRoundedBottomSheet.show(fragmentManager!!, myRoundedBottomSheet.tag)
    }

    override fun on_itemDelete(not: Note,position:Int) {
        database.delete_note(not)

        list.removeAt(position)
        mview.rv_notes.adapter!!.notifyItemChanged(position)
    }

    override fun on_Update(not: Note, position: Int) {
        list.set(position,not)
        mview.rv_notes.adapter!!.notifyItemChanged(position)
    }

}