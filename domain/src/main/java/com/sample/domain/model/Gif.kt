package com.sample.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Gif(
    val id: String,
    val title: String,
    val url: String,
    val previewUrl: String,
    val username: String,
    val rating: String,
    val importDateTime: String,
    val trendingDateTime: String,
    val images: GifImages
) : Parcelable

@Parcelize
data class GifImages(
    val original: GifImageData,
    val downsized: GifImageData,
    val downsizedMedium: GifImageData,
    val preview: GifImageData,
    val fixedHeight: GifImageData,
    val fixedWidth: GifImageData
) : Parcelable

@Parcelize
data class GifImageData(
    val url: String,
    val width: String,
    val height: String,
    val size: String,
    val mp4: String? = null,
    val mp4Size: String? = null,
    val webp: String? = null,
    val webpSize: String? = null
) : Parcelable