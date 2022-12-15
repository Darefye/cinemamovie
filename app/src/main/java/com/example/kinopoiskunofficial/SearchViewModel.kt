package com.example.kinopoiskunofficial

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.kinopoiskunofficial.data.ParamsFilterFilm
import com.example.kinopoiskunofficial.data.filmbyfilter.FilterCountry
import com.example.kinopoiskunofficial.data.filmbyfilter.FilterGenre
import com.example.kinopoiskunofficial.data.staffbyid.StaffsFilms
import com.example.kinopoiskunofficial.domain.GetFilmListUseCase
import com.example.kinopoiskunofficial.domain.GetGenresCountriesUseCase
import com.example.kinopoiskunofficial.entity.HomeItem
import com.example.kinopoiskunofficial.presentation.home.filmdetail.filmsbyfilter.FilmsByFilterPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getFilmListUseCase: GetFilmListUseCase,
    private val getGenresCountriesUseCase: GetGenresCountriesUseCase
) : ViewModel() {

    private var filters = ParamsFilterFilm()
    private val _isFilterChanged = MutableStateFlow(false)
    val isFilterChanged = _isFilterChanged.asStateFlow()


    private val _countries = MutableStateFlow<List<FilterCountry>>(emptyList())
    val countries = _countries.asStateFlow()

    private val _genres = MutableStateFlow<List<FilterGenre>>(emptyList())
    val genres = _genres.asStateFlow()

    val films: Flow<PagingData<HomeItem>> = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = {
            FilmsByFilterPagingSource(
                filters = filters,
                getFilmListUseCase = getFilmListUseCase
            )
        }
    ).flow.cachedIn(viewModelScope)

    init {
        getCountriesOrGenres()
    }

    fun getFilters() = filters

    fun updateFilters(filterFilm: ParamsFilterFilm) {
        _isFilterChanged.value = false
        filters = filterFilm
        _isFilterChanged.value = true
    }

    private fun getCountriesOrGenres() {
        viewModelScope.launch(Dispatchers.IO) {
            _countries.value =
                getGenresCountriesUseCase.executeGenresCountries().countries.sortedBy { it.name }
            _genres.value =
                getGenresCountriesUseCase.executeGenresCountries().genres.sortedBy { it.name }
        }
    }
}