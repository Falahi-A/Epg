package com.dazn.codeassignment.epg.utils

import android.annotation.SuppressLint
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.dazn.codeassignment.epg.R
import com.dazn.codeassignment.epg.domain.model.Schedule
import java.text.SimpleDateFormat

fun loadImage(url: String, imageView: AppCompatImageView) {
    Glide.with(imageView.context).load(url)
        .placeholder(R.drawable.ic_launcher_foreground)
        .into(imageView)
}


@SuppressLint("SimpleDateFormat")
fun convertDate(stringDate: String) {
    val dateFormat = SimpleDateFormat("yyyy-MM-ddTHH:mm:ss")
    val date = dateFormat.parse(stringDate)
    date.year
    date.month
    date.day
    date.hours
    date.minutes
    date.seconds

}