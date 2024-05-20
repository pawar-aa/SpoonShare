package com.aashay.spoonshare.addfragment

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.ContentValues.TAG
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.aashay.spoonshare.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Calendar
import java.util.Date

class AddFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private val db = FirebaseFirestore.getInstance()
    // declare all ui fields and buttons
    private lateinit var eventNameEditText: EditText
    private lateinit var eventDescriptionEditText: EditText
    private lateinit var eventGeopointEditText: EditText
    private lateinit var eventHostEditText: EditText
    private lateinit var eventImageUrlEditText: EditText
    private lateinit var eventDateEditText: EditText
    private lateinit var eventTimeEditText: EditText
    private lateinit var eventVolunteersEditText: EditText
    private lateinit var submitButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add, container, false)

        // initialize all ui fields and buttons
        eventNameEditText = view.findViewById(R.id.et_event_name)
        eventDescriptionEditText = view.findViewById(R.id.et_event_description)
        eventGeopointEditText = view.findViewById(R.id.et_event_geopoint)
        eventHostEditText = view.findViewById(R.id.et_event_host)
        eventImageUrlEditText = view.findViewById(R.id.et_event_image_url)
        eventDateEditText = view.findViewById(R.id.et_event_date)
        eventTimeEditText = view.findViewById(R.id.et_event_time)
        eventVolunteersEditText = view.findViewById(R.id.et_event_volunteers)
        submitButton = view.findViewById(R.id.btn_submit)

        val user = FirebaseAuth.getInstance().currentUser

        if (user != null) {
            val email = user.email
        }
        else {
        }

        eventDateEditText.setOnClickListener { showDatePickerDialog(eventDateEditText) }
        eventTimeEditText.setOnClickListener { showTimePickerDialog(eventTimeEditText) }

        submitButton.setOnClickListener { onSubmitClicked() }

        return view
    }

    private fun showDatePickerDialog(dateEditText: EditText) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
            dateEditText.setText("$selectedYear-${selectedMonth + 1}-$selectedDay")
        }, year, month, day)

        datePickerDialog.show()
    }

    private fun showTimePickerDialog(timeEditText: EditText) {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(requireContext(), { _, selectedHour, selectedMinute ->
            timeEditText.setText(String.format("%02d:%02d", selectedHour, selectedMinute))
        }, hour, minute, true)

        timePickerDialog.show()
    }

    private fun onSubmitClicked() {
        // Construct the new post data
        val mUser = auth.currentUser?.email.toString()
        val newPostData = hashMapOf(
            "EventDescription" to "Northeastern Donation",
            "EventGeopoint" to mapOf("latitude" to 0.0, "longitude" to 0.0),
            "EventHost" to mUser,
            "EventImage" to "https://firebasestorage.googleapis.com/v0/b/spoonshare-77e16.appspot.com/o/Picture%201.jpg?alt=media&token=f0ed0fdd-1a25-44c3-9657-0a18cc6c4556",
            "EventName" to "Northeastern Donation Camp",
            "EventTime" to Date(), // Replace with actual timestamp
            "EventVolunteers" to 10
        )

        // Get a reference to the document containing the "Posts" array
        val postsRef = db.collection("UsersData")
            .document("RecentPosts")

        // Atomically add a new post to the "Posts" array
        postsRef.update("Posts", FieldValue.arrayUnion(newPostData))
            .addOnSuccessListener {
                Log.d(TAG, "DocumentSnapshot successfully updated with new post")
                // Clear input fields or perform other actions upon success
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error updating document with new post", e)
                // Handle failure case
            }
    }

}
