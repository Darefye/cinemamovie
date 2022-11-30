package com.example.kinopoiskunofficial.presentation.allfilmsbycategory.allfilmadapter

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.kinopoiskunofficial.data.CategoriesFilms
import com.example.kinopoiskunofficial.data.FilmFilterParams
import com.example.kinopoiskunofficial.data.TOP_TYPES
import com.example.kinopoiskunofficial.domain.GetFilmListUseCase
import com.example.kinopoiskunofficial.domain.GetPremierFilmUseCase
import com.example.kinopoiskunofficial.domain.GetTopFilmsUseCase
import com.example.kinopoiskunofficial.entity.HomeItem

class AllFilmPagingSource(
    private val filterParams: FilmFilterParams,
    private val categoriesFilms: CategoriesFilms,
    private val year: Int,
    private val month: String,
    private val getPremierFilmUseCase: GetPremierFilmUseCase,
    private val getTopFilmsUseCase: GetTopFilmsUseCase,
    private val getFilmListUseCase: GetFilmListUseCase
) : PagingSource<Int, HomeItem>() {
    override fun getRefreshKey(state: PagingState<Int, HomeItem>): Int = FIRST_PAGE

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, HomeItem> {
        val page = params.key ?: FIRST_PAGE
        return kotlin.runCatching {
            when (categoriesFilms) {
                CategoriesFilms.PREMIERS -> {
                    getPremierFilmUseCase.executePremieres(year, month)
                }
                CategoriesFilms.TV_SERIES -> {
                    getFilmListUseCase
                        .executeFilmsByFilter(
                            filters = filterParams,
                            page = page
                        )
                }
                else -> {
                    getTopFilmsUseCase.executeTopFilms(
                        topType = TOP_TYPES.getValue(categoriesFilms),
                        page = page
                    )
                }
            }
        }.fold(
            onSuccess = {
                LoadResult.Page(
                    data = it,
                    prevKey = null,
                    nextKey = if (it.isEmpty()) null else page + 1
                )
            },
            onFailure = {
                LoadResult.Error(it)
            }
        )
    }

    private companion object {
        private const val FIRST_PAGE = 1
    }
}