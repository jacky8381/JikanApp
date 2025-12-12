package com.example.jikanapp.data.local.entity

import androidx.room.Embedded

data class PropEntity(

    @Embedded(prefix = "from_")
    val from: FromEntity?,

    @Embedded(prefix = "to_")
    val to: ToEntity?
)