package com.example.favoriteyoutubevideos.handlers

import com.example.favoriteyoutubevideos.models.Video
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class VideoHandler {
    var database: FirebaseDatabase
    var videoRef: DatabaseReference
    init{
        database = FirebaseDatabase.getInstance()
        videoRef =  database.getReference("my top videos")
    }
    fun create(video: Video): Boolean{
        val id = videoRef.push().key
        video.id = id
        videoRef.child(id!!).setValue(video)
        return true
    }
    fun update(video: Video): Boolean{
        videoRef.child(video.id!!).setValue(video)
        return true
    }
    fun  delete(video: Video): Boolean{
        videoRef.child(video.id!!).removeValue()
        return true
    }
}