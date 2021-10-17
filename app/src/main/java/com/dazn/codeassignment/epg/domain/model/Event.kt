package com.dazn.codeassignment.epg.domain.model


data class Event(
    var date: String,
    val id: String,
    val imageUrl: String,
    val subtitle: String,
    val title: String,
    val videoUrl: String
)