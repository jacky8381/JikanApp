package com.example.jikanapp.data.local.entity

import androidx.room.Embedded

data class AiredEntity(
    val from: String?,

    @Embedded(prefix = "prop_")
    val prop: PropEntity?,

    val string: String?,
    val to: String?
)