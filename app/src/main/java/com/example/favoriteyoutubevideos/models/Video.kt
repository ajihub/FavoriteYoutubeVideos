package com.example.favoriteyoutubevideos.models

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class Video(var id: String? = "", var rank: String? = "", var title: String? = "", var link: String? = "", var reason: String? = "") {
    override fun toString(): String {
        return  "Rank: $rank \n" +
                "Title: $title \n" +
                "Link: $link \n" +
                "Reason: $reason"
    }
}