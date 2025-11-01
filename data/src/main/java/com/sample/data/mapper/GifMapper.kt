package com.sample.data.mapper

import com.sample.data.remote.dto.GifDto
import com.sample.data.remote.dto.GifImageDataDto
import com.sample.data.remote.dto.GifImagesDto
import com.sample.domain.model.Gif
import com.sample.domain.model.GifImageData
import com.sample.domain.model.GifImages


fun GifDto.toDomainModel(): Gif {
    return Gif(
        id = id,
        title = title,
        url = url,
        previewUrl = images.preview.url,
        username = username,
        rating = rating,
        importDateTime = importDateTime,
        trendingDateTime = trendingDateTime,
        images = images.toDomainModel()
    )
}

fun GifImagesDto.toDomainModel(): GifImages {
    return GifImages(
        original = original.toDomainModel(),
        downsized = downsized.toDomainModel(),
        downsizedMedium = downsizedMedium.toDomainModel(),
        preview = preview.toDomainModel(),
        fixedHeight = fixedHeight.toDomainModel(),
        fixedWidth = fixedWidth.toDomainModel()
    )
}

fun GifImageDataDto.toDomainModel(): GifImageData {
    return GifImageData(
        url = url,
        width = width,
        height = height,
        size = size,
        mp4 = mp4,
        mp4Size = mp4Size,
        webp = webp,
        webpSize = webpSize
    )
}