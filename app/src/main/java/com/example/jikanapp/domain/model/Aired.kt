package com.example.jikanapp.domain.model

import androidx.room.Embedded

data class Aired(
    val from: String = "",
    val prop: Prop = Prop(),
    val string: String = "",
    val to: String = ""
)