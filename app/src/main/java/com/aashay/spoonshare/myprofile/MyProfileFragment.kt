package com.aashay.spoonshare.myprofile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.aashay.spoonshare.R
import com.google.firebase.auth.FirebaseAuth
import com.aashay.spoonshare.LoginActvity

class MyProfileFragment : Fragment() {

    private lateinit var mUserEmail: TextView
    private lateinit var mLogoutButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout
        val view = inflater.inflate(R.layout.fragment_my_profile, container, false)

        mUserEmail = view.findViewById(R.id.et_1)
        mLogoutButton = view.findViewById(R.id.btn_1)

        val user = FirebaseAuth.getInstance().currentUser

        if (user != null) {
            // Set the user's email to the TextView
            val email = user.email
            mUserEmail.text = email
        } else {
            // Handle the case where the user is not signed in
        }

        mLogoutButton.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(view.context, LoginActvity::class.java))
        }

        return view
    }
}
