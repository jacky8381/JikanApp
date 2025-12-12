package com.example.jikanapp.data.local.entity

import androidx.room.Embedded

data class ImagesEntity(
    @Embedded(prefix = "jpg_")
    var jpg: JpgEntity,

    @Embedded(prefix = "webp_")
    var webp: WebpEntity
)
