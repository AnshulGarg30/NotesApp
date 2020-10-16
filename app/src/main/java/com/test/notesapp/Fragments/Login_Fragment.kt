package com.test.notesapp.Fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.test.notesapp.R
import com.test.notesapp.Utils.UserSharedPrefrences
import com.test.notesapp.DB.DatabaseHelper
import com.test.notesapp.Entity.USER
import kotlinx.android.synthetic.main.fragment_login_page.*


class Login_Fragment : Fragment() {

    //google login
    lateinit var mGoogleSignInClient: GoogleSignInClient
    private val RC_SIGN_IN = 7
    lateinit var gso: GoogleSignInOptions

    lateinit var pref:UserSharedPrefrences
    lateinit var database: DatabaseHelper

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        database = DatabaseHelper(activity!!)
        pref= UserSharedPrefrences.getInstance(activity!!)
        return inflater.inflate(R.layout.fragment_login_page, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        rl_google.setOnClickListener {
            initialize_google()
        }
    }

    fun initialize_google() {
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(activity!!, gso);
        val signInIntent: Intent = mGoogleSignInClient.getSignInIntent()
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) { // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task =
                GoogleSignIn.getSignedInAccountFromIntent(data)
            handlegoogleSignInResult(task)
        }
    }


    private fun handlegoogleSignInResult(result: Task<GoogleSignInAccount>) {
        Log.d("googlesignin", result.toString() + "")

        if (result.isSuccessful) { // Signed in successfully, show authenticated UI.
            val acct = result.result
            Log.d("acct:", acct.toString() + "")
            Log.d("display name: ", "" + acct!!.displayName)
            val personName = acct!!.displayName
            var personPhotoUrl = ""
            if (acct.photoUrl != null) {
                personPhotoUrl = acct.photoUrl.toString()
            }
            val email = acct.email
            Log.d("Name: ", "" + personName + ", email: " + email + ", Image: " + personPhotoUrl + ", token: " + acct.idToken +
                    ", Account:" + acct.account )





            if(database.checkIfEmailExists(email!!))
            {
                var user = database.getUserEmailData(email)
                user?.id?.let { pref.setuserid(it) }
                user?.name?.let { pref.setname(it) }
                user?.email?.let { pref.setemail(it) }
                user?.image?.let { pref.setimage(it) }

            }
            else {
                var user = USER(0, personName!!, personPhotoUrl, email)
                database.insertUser(user)
                 user = database.getUserEmailData(email)!!

                user?.id?.let { pref.setuserid(it) }
                user?.name?.let { pref.setname(it) }
                user?.email?.let { pref.setemail(it) }
                user?.image?.let { pref.setimage(it) }
            }

            pref.setlogin(true)
            fragmentManager!!.beginTransaction().replace(R.id.MainFrame,Notes_Fragment()).commit()
            mGoogleSignInClient.signOut()



        } else { // Signed out, show unauthenticated UI.
            Log.d("resultfalse", result.exception.toString() + "")
            pref.setlogin(false)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}