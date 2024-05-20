package com.aashay.spoonshare

import Event
import ParcelableGeoPoint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.aashay.spoonshare.addfragment.AddFragment
import com.aashay.spoonshare.homefragment.HomeFragment
import com.aashay.spoonshare.mypostsfragment.MyPostsFragment
import com.aashay.spoonshare.myprofile.MyProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNav: BottomNavigationView
    private val events = mutableListOf<Event>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadFragment(HomeFragment.newInstance(events))
        bottomNav = findViewById(R.id.bottomNav)
        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    loadFragment(HomeFragment.newInstance(events))
                    true
                }
                R.id.myposts -> {
                    loadFragment(MyPostsFragment())
                    true
                }
                R.id.add -> {
                    loadFragment(AddFragment())
                    true
                }
                R.id.profile -> {
                    loadFragment(MyProfileFragment())
                    true
                }
                else -> false
            }
        }

        loadData()
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.commit()
    }

    private fun loadData() {
        val user = FirebaseAuth.getInstance().currentUser
        if (user == null) {
            Log.d("Load Data", "User is not authenticated")
            return
        }

        val db = FirebaseFirestore.getInstance()
        val docRef = db.collection("UsersData").document("RecentPosts")

        docRef.get().addOnSuccessListener { document ->
            if (document.exists()) {
                val posts = document.get("Posts") as? List<Map<String, Any>>
                if (posts != null) {
                    for (post in posts) {
                        val eventDescription = post["EventDe" +
                                "scription"] as? String ?: ""
                        val eventGeopoint = post["EventGeopoint"] as? GeoPoint ?: GeoPoint(0.0, 0.0)
                        val eventHost = post["EventHost"] as? String ?: ""
                        val eventImage = post["EventImage"] as? String ?: ""
                        val eventName = post["EventName"] as? String ?: ""
                        val eventTime = post["EventTime"] as? Timestamp ?: Timestamp.now()
                        val eventVolunteers = post["EventVolunteers"] as? Long ?: 0L

                        val event = Event(eventDescription, ParcelableGeoPoint(eventGeopoint.latitude, eventGeopoint.longitude), eventHost, eventImage, eventName, eventTime, eventVolunteers)
                        events.add(event)
                    }
                    loadFragment(HomeFragment.newInstance(events))
                } else {
                    Log.d("Load Data", "Posts is null or not a list")
                }
            } else {
                Log.d("Load Data", "Document does not exist")
            }
        }.addOnFailureListener { exception ->
            Log.d("Load Data", "Error getting document: $exception")
        }
    }
}
