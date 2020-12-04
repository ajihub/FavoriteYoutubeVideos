package com.example.favoriteyoutubevideos.models

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class Video(var id: String? = "", var rank: Int? = 0, var title: String? = "", var link: String? = "", var reason: String? = "") {
    override fun toString(): String {
        return  "⚫RANK: $rank \n" +
                "------------------------------ \n" +
                "•Title: $title \n" +
                "------------------------------ \n" +
                "•Link: $link \n" +
                "------------------------------ \n" +
                "•Reason: $reason"
    }
}