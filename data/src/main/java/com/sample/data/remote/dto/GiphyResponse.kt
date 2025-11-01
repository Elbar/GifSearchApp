package com.sample.data.remote.dto

import com.squareup.moshi.Json

data class GiphyResponse(
    val data: List<GifDto>,
    val pagination: PaginationDto,
    val meta: MetaDto
)

data class GiphySingleResponse(
    val data: GifDto,
    val meta: MetaDto
)

data class GifDto(
    val id: String,
    val title: String,
    val url: String,
    val username: String,
    val rating: String,
    @Json(name = "import_datetime") val importDateTime: String,
    @Json(name = "trending_datetime") val trendingDateTime: String,
    val images: GifImagesDto
)

data class GifImagesDto(
    val original: GifImageDataDto,
    val downsized: GifImageDataDto,
    @Json(name = "downsized_medium")
    val downsizedMedium: GifImageDataDto,
    @Json(name = "preview_gif")
    val preview: GifImageDataDto,
    @Json(name = "fixed_height")
    val fixedHeight: GifImageDataDto,
    @Json(name = "fixed_width")
    val fixedWidth: GifImageDataDto
)

data class GifImageDataDto(
    val url: String,
    val width: String,
    val height: String,
    val size: String,
    val mp4: String? = null,
    @Json(name = "mp4_size")
    val mp4Size: String? = null,
    val webp: String? = null,
    @Json(name = "webp_size")

    val webpSize: String? = null
)

data class PaginationDto(
    @Json(name = "total_count")
    val totalCount: Int,
    val count: Int,
    val offset: Int
)

data class MetaDto(
    val status: Int,
    val msg: String,
    @Json(name = "response_id")
    val responseId: String
)