package com.sample.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sample.data.mapper.toDomainModel
import com.sample.data.remote.api.GiphyApiService
import com.sample.domain.model.Gif
import retrofit2.HttpException
import java.io.IOException

class SearchGifsPagingSource(
    private val api: GiphyApiService,
    private val apiKey: String,
    private val query: String
) : PagingSource<Int, Gif>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Gif> {
        return try {
            val page = params.key ?: 0
            val response = api.searchGifs(
                apiKey = apiKey,
                query = query,
                limit = params.loadSize,
                offset = page * params.loadSize
            )

            val gifs = response.data.map { it.toDomainModel() }
            val nextKey = if (gifs.isEmpty()) null else page + 1
            val prevKey = if (page == 0) null else page - 1

            LoadResult.Page(
                data = gifs,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Gif>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}