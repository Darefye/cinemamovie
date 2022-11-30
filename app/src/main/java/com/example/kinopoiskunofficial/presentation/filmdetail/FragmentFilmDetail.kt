package com.example.kinopoiskunofficial.presentation.filmdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kinopoiskunofficial.CinemaViewModel
import com.example.kinopoiskunofficial.R
import com.example.kinopoiskunofficial.data.CategoriesFilms
import com.example.kinopoiskunofficial.data.TOP_TYPES
import com.example.kinopoiskunofficial.data.filmbyid.ResponseCurrentFilm
import com.example.kinopoiskunofficial.data.staffbyfilmid.ResponseStaffByFilmId
import com.example.kinopoiskunofficial.databinding.FragmentDetailFilmBinding
import com.example.kinopoiskunofficial.loadImage
import com.example.kinopoiskunofficial.presentation.StateLoading
import com.example.kinopoiskunofficial.presentation.filmdetail.galleryadapter.GalleryAdapter
import com.example.kinopoiskunofficial.presentation.filmdetail.staffadapter.StaffAdapter
import com.example.kinopoiskunofficial.presentation.home.filmrecycler.FilmAdapter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class FragmentFilmDetail : Fragment() {
    private var _binding: FragmentDetailFilmBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CinemaViewModel by activityViewModels()
    private lateinit var actorAdapter: StaffAdapter
    private lateinit var makersAdapter: StaffAdapter
    private lateinit var galleryAdapter: GalleryAdapter
    private lateinit var similarAdapter: FilmAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailFilmBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        stateLoadingListener()              // Установка слушателя состояния загрузки

        setFilmDetails()                    // Установка постера, инфорации на нём и описания фильма
        setFilmActors()                     // Установка списка актёров
        setFilmMakers()                     // Установка списка съёмочной группы
        setFilmGallery()                    // Установка галереи фотографий
        setSimilarFilms()                   // Установка списка похожих фильмов
        setIconClick()                     // Установка слушателей кнопок

        binding.btnBack.setOnClickListener { requireActivity().onBackPressed() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setIconClick() {
        binding.apply {
            var isSelect = false
            ivLike.setOnClickListener {
                it.isSelected = !isSelect
                isSelect = !isSelect
            }

            var isSelectOne = false
            ivFavorite.setOnClickListener {
                it.isSelected = !isSelectOne
                isSelectOne = !isSelectOne
            }

            var isSelectTwo = false
            ivLook.setOnClickListener {
                it.isSelected = !isSelectTwo
                isSelectTwo = !isSelectTwo
            }

            var isSelectThird = false
            ivShare.setOnClickListener {
                it.isSelected = !isSelectThird
                isSelectThird = !isSelectThird
            }

            var isSelectFour = false
            ivShare.setOnClickListener {
                it.isSelected = !isSelectFour
                isSelectFour = !isSelectFour
            }

            var isSelectFive = false
            ivShowMore.setOnClickListener {
                it.isSelected = !isSelectFive
                isSelectFive = !isSelectFive
            }
        }
    }


    private fun stateLoadingListener() {
        viewModel.loadCurrentFilmState.onEach { state ->
            when (state) {
                is StateLoading.Loading -> {
                    binding.apply {
                        loader.isVisible = true
                        loaderProgressBar.isVisible = true
                        loaderRefresh.isVisible = false
                        scroll.isVisible = false
                    }
                }
                is StateLoading.Success -> {
                    binding.apply {
                        loader.isVisible = false
                        scroll.isVisible = true
                    }
                }
                else -> {
                    binding.apply {
                        loader.isVisible = true
                        loaderProgressBar.isVisible = false
                        loaderRefresh.isVisible = true
                        scroll.isVisible = false
                    }
                }
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun setFilmDetails() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.currentFilm.collect { film ->
                if (film != null) {
                    if (film.type == TOP_TYPES.getValue(CategoriesFilms.TV_SERIES)) {
                        binding.seasons.isVisible = true
                        viewModel.getSeasons(film.kinopoiskId)
                        getSeriesSeasons(getName(film))
                    } else {
                        binding.seasons.isVisible = false
                    }

                    binding.apply {
                        ivPrevFilm.loadImage(film.posterUrl)
                        tvFilmDescriptionShort.text = film.shortDescription ?: ""
                        tvFilmDescriptionFull.text = film.description ?: ""

                        tvName.text = getName(film)
                        tvRating.text = getRating(film)
                        tvYearGenres.text = getYearGenres(film)
                        tvCountryLengthAge.text = getStrCountriesLengthAge(film)
                    }
                }
            }
        }
    }

    // Информация о сезонах и сериях сериала
    private fun getSeriesSeasons(seriesName: String) {
        binding.seriesSeasonsBtn.setOnClickListener { showAllSeasons(seriesName) }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.seasons.collect {
                val seasonsCount = it.size
                var seriesCount = 0
                it.forEach { season ->
                    seriesCount += season.episodes.size
                }
                val seasonStr =
                    resources.getQuantityString(
                        R.plurals.film_details_series_count,
                        seasonsCount,
                        seasonsCount
                    )
                val episodeStr =
                    resources.getQuantityString(
                        R.plurals.film_details_episode_count,
                        seriesCount,
                        seriesCount
                    )
                binding.seriesSeasonsCount.text = resources.getString(
                    R.string.seasons_episodes_count,
                    seasonStr,
                    episodeStr
                )
            }
        }
    }

    private fun showAllSeasons(seriesName: String) {
        val action =
            FragmentFilmDetailDirections.actionFragmentFilmDetailToFragmentSeasons(seriesName)
        findNavController().navigate(action)
    }


    private fun onStaffClick(staff: ResponseStaffByFilmId) {
        val action =
            FragmentFilmDetailDirections.actionFragmentFilmDetailToFragmentStaffDetail(staff.staffId)
        findNavController().navigate(action)
    }

    private fun showAllStaffs(professionalKey: String) {
        val action = FragmentFilmDetailDirections.actionFragmentFilmDetailToFragmentAllStaffsByFilm(
            professionalKey
        )
        findNavController().navigate(action)
    }

    private fun setFilmActors() {

        actorAdapter = StaffAdapter { onStaffClick(it) }

        binding.rvFilmActorsList.adapter = actorAdapter
        binding.rvFilmActorsList.layoutManager =
            GridLayoutManager(
                requireContext(), MAX_ACTORS_ROWS, GridLayoutManager.HORIZONTAL, false
            )

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.currentFilmActors.collect { actorList ->
                binding.tvActorsCount.text = actorList.size.toString()
                if (actorList.size < MAX_ACTORS_COLUMN * MAX_ACTORS_ROWS) {
                    actorAdapter.submitList(actorList)
                } else {
                    val actorsListTemp = mutableListOf<ResponseStaffByFilmId>()
                    repeat(MAX_ACTORS_COLUMN * MAX_ACTORS_ROWS) { actorsListTemp.add(actorList[it]) }
                    actorAdapter.submitList(actorsListTemp)
                }
            }
        }
        binding.ibShowAllActors.setOnClickListener { showAllStaffs("ACTOR") }
    }

    private fun setFilmMakers() {
        makersAdapter = StaffAdapter { onActorClick(it) }
        binding.rvMakersList.layoutManager = GridLayoutManager(
            requireContext(), MAX_MAKERS_ROWS, GridLayoutManager.HORIZONTAL, false
        )
        binding.rvMakersList.adapter = makersAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.currentFilmMakers.collect { makersList ->
                binding.tvMakersCount.text = makersList.size.toString()
                if (makersList.size < MAX_MAKERS_COLUMN * MAX_MAKERS_ROWS) {
                    binding.apply {
                        tvMakersCount.isVisible = false
                        ibShowAllMakers.isVisible = false
                    }
                    makersAdapter.submitList(makersList)

                } else {
                    binding.apply {
                        tvMakersCount.isVisible = true
                        ibShowAllMakers.isVisible = true
                    }
                    binding.tvMakersCount.text = makersList.size.toString()
                    val makersListTemp = mutableListOf<ResponseStaffByFilmId>()
                    repeat(MAX_MAKERS_COLUMN * MAX_MAKERS_ROWS) { makersListTemp.add(makersList[it]) }
                    makersAdapter.submitList(makersListTemp)
                }
            }
        }
        binding.ibShowAllMakers.setOnClickListener { showAllStaffs("") }
    }

    // Галерея фильма
    private fun setFilmGallery() {


        binding.imShowFilmGallery.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentFilmDetail_to_fragmentGalleryFull)
        }

        galleryAdapter = GalleryAdapter()
        binding.rvFilmGalleryList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvFilmGalleryList.adapter = galleryAdapter

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.currentFilmGallery.collect { responseGallery ->
                galleryAdapter.submitList(responseGallery)
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.galleryCount.collect {
                binding.tvFilmGalleryCount.text = it.toString()
            }
        }
    }


    private fun onSimilarFilmClick(filmId: Int) {
        viewModel.getFilmById(filmId)
    }

    private fun showAllSimilarFilms() {
        findNavController().navigate(R.id.action_fragmentFilmDetail_to_fragmentSimilarFilms)
    }

    private fun setSimilarFilms() {
        similarAdapter = FilmAdapter(20, { showAllSimilarFilms() }, { onSimilarFilmClick(it) })
        binding.rvFilmSimilarList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvFilmSimilarList.adapter = similarAdapter
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.currentFilmSimilar.collect {
                binding.ivFilmSimilarCount.text = it.size.toString()
                similarAdapter.submitList(it)
            }
        }
        binding.ibFilmSimilarShowAll.setOnClickListener { showAllSimilarFilms() }
    }

    private fun onActorClick(staff: ResponseStaffByFilmId) {
        val action =
            FragmentFilmDetailDirections.actionFragmentFilmDetailToFragmentStaffDetail(staff.staffId)
        findNavController().navigate(action)
    }


    companion object {
        private const val MAX_ACTORS_COLUMN = 5
        private const val MAX_ACTORS_ROWS = 4
        private const val MAX_MAKERS_COLUMN = 3
        private const val MAX_MAKERS_ROWS = 2

        private fun getRating(film: ResponseCurrentFilm): String {
            val result = mutableListOf<String>()
            val rating = when {
                film.ratingKinopoisk != null -> film.ratingKinopoisk.toString()
                film.ratingImdb != null -> film.ratingImdb.toString()
                film.ratingMpaa != null -> film.ratingMpaa.toString()
                else -> null
            }
            if (rating != null) result.add(rating)
            return result.joinToString(", ")
        }

        private fun getName(film: ResponseCurrentFilm): String {
            return when {
                film.nameRu != null -> film.nameRu
                film.nameEn != null -> film.nameEn
                film.nameOriginal != null -> film.nameOriginal
                else -> ""
            }
        }

        private fun getYearGenres(film: ResponseCurrentFilm): String {
            val result = mutableListOf<String>()
            if (film.year != null) result.add(film.year.toString())
            if (film.genres.size > 1) {
                val genreNameList = mutableListOf<String>()
                repeat(2) {
                    genreNameList.add(film.genres[it].genre)
                }
                result.add(genreNameList.joinToString(", "))
            } else {
                result.add(film.genres[0].genre)
            }
            return result.joinToString(", ")
        }

        private fun getStrCountriesLengthAge(film: ResponseCurrentFilm): String {
            val result = mutableListOf<String?>()
            result.add(film.getCountries())
            if (film.getLength() != null) result.add(film.getLength())
            if (film.getAgeLimit() != null) result.add("${film.getAgeLimit()}+")
            return result.joinToString(", ")
        }

        private fun ResponseCurrentFilm.getCountries(): String {
            return if (this.countries.size > 1) {
                val list = mutableListOf<String>()
                repeat(this.countries.size - 1) {
                    list.add(this.countries[it].country)
                }
                list.joinToString(", ")
            } else if (this.countries.size == 1) {
                this.countries[0].country
            } else {
                ""
            }
        }

        private fun ResponseCurrentFilm.getLength(): String? {
            return if (this.filmLength != null) {
                val hours = this.filmLength.div(60)
                val minutes = this.filmLength.rem(60)
                "$hours ч $minutes мин"
            } else null
        }

        private fun ResponseCurrentFilm.getAgeLimit(): String? {
            return if (this.ratingAgeLimits != null) this.ratingAgeLimits.removePrefix("age")
            else null
        }
    }
}