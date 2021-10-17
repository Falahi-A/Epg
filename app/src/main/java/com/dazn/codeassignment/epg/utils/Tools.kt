package com.dazn.codeassignment.epg.utils

import android.annotation.SuppressLint
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.dazn.codeassignment.epg.R
import com.dazn.codeassignment.epg.domain.model.Event
import com.dazn.codeassignment.epg.domain.model.Schedule
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.absoluteValue

fun loadImage(url: String, imageView: AppCompatImageView) {
    Glide.with(imageView.context).load(url)
        .placeholder(R.drawable.ic_launcher_foreground)
        .into(imageView)
}

/**
 * convert string's date to desired format
 */
@SuppressLint("SimpleDateFormat")
fun convertDate(stringDate: String): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd")
    val date =
        dateFormat.parse(stringDate)!!
    val diff = getDatesDifferenceInDay(getCurrentDate(), date)
    return when {
        diff == 1L -> {
            Constants.YESTERDAY + ", " + stringDate.substringAfter("T").substringBeforeLast(
                ":"
            )
        }
        diff == 0L -> {
            Constants.TODAY + ", " + stringDate.substringAfter("T").substringBeforeLast(
                ":"
            )
        }
        diff == -1L -> {
            Constants.TOMORROW + ", " + stringDate.substringAfter("T").substringBeforeLast(
                ":"
            )
        }
        diff < -1L -> {
            "In ${diff.absoluteValue} days"
        }
        else -> {
            stringDate.substringBefore("T")
        }
    }
}

/**
 * today's date
 */
private fun getCurrentDate(): Date {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.UK)
    return dateFormat.parse(dateFormat.format(Date()))!!
}

/**
 *  difference between two dates
 */
private fun getDatesDifferenceInDay(firstDate: Date, secondDate: Date): Long {
    val diff = firstDate.time - secondDate.time
    val seconds = diff / 1000
    val minutes = seconds / 60
    val hours = minutes / 60
    return hours / 24
}

fun getFakeEventsList(): List<Event> {
    val arrayList = ArrayList<Event>()
    arrayList.add(Event("2021-10-13T08:14:40.361Z", "8", "", "", "", ""))
    arrayList.add(Event("2021-10-17T12:02:40.361Z", "12", "", "", "", ""))
    arrayList.add(Event("2021-10-15T10:06:40.361Z", "10", "", "", "", ""))
    arrayList.add(Event("2021-10-12T07:13:40.361Z", "7", "", "", "", ""))
    arrayList.add(Event("2021-10-14T09:05:40.361Z", "9", "", "", "", ""))
    arrayList.add(Event("2021-10-16T11:01:40.361Z", "11", "", "", "", ""))
    arrayList.add(Event("2021-10-11T06:12:40.361Z", "6", "", "", "", ""))
    arrayList.add(Event("2021-10-10T05:11:40.361Z", "5", "", "", "", ""))
    arrayList.add(Event("2021-10-09T04:10:40.361Z", "4", "", "", "", ""))
    arrayList.add(Event("2021-10-08T03:09:40.361Z", "3", "", "", "", ""))
    arrayList.add(Event("2021-10-06T02:08:40.361Z", "2", "", "", "", ""))
    arrayList.add(Event("2021-10-06T01:07:40.361Z", "1", "", "", "", ""))

    return arrayList
}

fun getFakeSchedulesList(): List<Schedule> {
    val arrayList = ArrayList<Schedule>()
    arrayList.add(Schedule("2021-10-18T08:14:40.361Z", "8", "", "", ""))
    arrayList.add(Schedule("2021-10-17T12:02:40.361Z", "12", "", "", ""))
    arrayList.add(Schedule("2021-10-18T10:06:40.361Z", "10", "", "", ""))
    arrayList.add(Schedule("2021-10-12T07:13:40.361Z", "7", "", "", ""))
    arrayList.add(Schedule("2021-10-14T09:05:40.361Z", "9", "", "", ""))
    arrayList.add(Schedule("2021-10-16T11:01:40.361Z", "11", "", "", ""))
    arrayList.add(Schedule("2021-10-11T06:12:40.361Z", "6", "", "", ""))
    arrayList.add(Schedule("2021-10-19T05:11:40.361Z", "5", "", "", ""))
    arrayList.add(Schedule("2021-10-21T04:10:40.361Z", "4", "", "", ""))
    arrayList.add(Schedule("2021-10-26T03:09:40.361Z", "3", "", "", ""))
    arrayList.add(Schedule("2021-10-24T02:08:40.361Z", "2", "", "", ""))
    arrayList.add(Schedule("2021-10-20T01:07:40.361Z", "1", "", "", ""))

    return arrayList
}