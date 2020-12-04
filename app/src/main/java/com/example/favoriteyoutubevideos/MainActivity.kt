package com.example.favoriteyoutubevideos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.*
import com.example.favoriteyoutubevideos.handlers.VideoHandler
import com.example.favoriteyoutubevideos.models.Video
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {
    lateinit var rankEditText: EditText
    lateinit var titleEditText: EditText
    lateinit var linkEditText: EditText
    lateinit var reasonEditText: EditText
    lateinit var addEditButton: Button
    lateinit var videoHandler: VideoHandler
    lateinit var videos: ArrayList<Video>
    lateinit var videoListView: ListView
    lateinit var videoGettingEdited: Video
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Texts and Buttons
        rankEditText = findViewById(R.id.rankText)
        titleEditText = findViewById(R.id.titleText)
        linkEditText = findViewById(R.id.linkText)
        reasonEditText = findViewById(R.id.reasonText)
        addEditButton = findViewById(R.id.addButton)
        //Handler
        videoHandler = VideoHandler()
        //Array and ListView
        videos = ArrayList()
        videoListView = findViewById(R.id.videoList)
        addEditButton.setOnClickListener{
            val rank = rankEditText.text.toString()
            val rankconv:Int = rank.toInt()
            val title = titleEditText.text.toString()
            val link = linkEditText.text.toString()
            val reason =  reasonEditText.text.toString()
            if(addEditButton.text.toString()== "Add"){
                val restaurant = Video(rank = rankconv, title = title, link = link, reason = reason)
                if(videoHandler.create(restaurant)){
                    Toast.makeText(applicationContext, "Video added", Toast.LENGTH_SHORT).show()
                    clearFields()
                }
            }
            else if(addEditButton.text.toString() == "Update"){
                val video = Video(id = videoGettingEdited.id, rank = rankconv, title = title,  link = link, reason = reason)
                if(videoHandler.update(video)){
                    Toast.makeText(applicationContext, "Video updated", Toast.LENGTH_SHORT).show()
                    clearFields()
                }
            }
        }
        registerForContextMenu(videoListView)
    }
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.video_options, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        return when(item.itemId){
            R.id.edit_video ->{
                videoGettingEdited = videos[info.position]
                val nullableInt = videoGettingEdited.rank
                val nonnullableInt = nullableInt!!
                rankEditText.setText(""+nonnullableInt)
                titleEditText.setText(videoGettingEdited.title)
                linkEditText.setText(videoGettingEdited.link)
                reasonEditText.setText(videoGettingEdited.reason)
                addEditButton.setText("Update")
                true
            }
            R.id.delete_video ->{
                if(videoHandler.delete(videos[info.position])){
                    Toast.makeText(applicationContext, "Video deleted from List", Toast.LENGTH_SHORT).show()
                }
                true
            }
            else ->return super.onContextItemSelected(item)
        }
    }

    override fun onStart() {
        super.onStart()
    videoHandler.videoRef.addValueEventListener(object: ValueEventListener{
        override fun onDataChange(snapshot: DataSnapshot) {
            videos.clear()

            snapshot.children.forEach{
                it -> val video = it.getValue(Video::class.java)
                videos.add(video!!)
            }
            videos.sortBy { it.rank }
            val adapter = ArrayAdapter<Video>(applicationContext, android.R.layout.simple_list_item_1, videos)
            videoListView.adapter = adapter
        }

        override fun onCancelled(error: DatabaseError) {
            //TODO("Not yet implemented")
        }

    })
    }
    fun clearFields(){
        rankEditText.text.clear()
        titleEditText.text.clear()
        linkEditText.text.clear()
        reasonEditText.text.clear()
        addEditButton.setText("Add")
    }
}