package com.example.kinopoiskunofficial.presentation.staffdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kinopoiskunofficial.CinemaViewModel
import com.example.kinopoiskunofficial.R
import com.example.kinopoiskunofficial.databinding.FragmentStaffDetailBinding
import com.example.kinopoiskunofficial.entity.HomeItem
import com.example.kinopoiskunofficial.loadImage
import com.example.kinopoiskunofficial.presentation.StateLoading
import com.example.kinopoiskunofficial.presentation.home.filmrecycler.FilmAdapter

class FragmentStaffDetail : Fragment() {
    private var _binding: FragmentStaffDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CinemaViewModel by activityViewModels()
    private lateinit var filmAdapter: FilmAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStaffDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args: FragmentStaffDetailArgs by navArgs()
        viewModel.getStaffDetail(args.staffId)

        setBestFilmsAdapter()
        setLoadingStateAndDetails()
        getStaffInfo()
        setButtonsListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setBestFilmsAdapter() {
        filmAdapter = FilmAdapter(20, {}, { onClickFilm(it) })
        binding.staffDetailBestList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.staffDetailBestList.adapter = filmAdapter
    }

    private fun setLoadingStateAndDetails() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.loadCurrentStaff.collect { state ->
                when (state) {
                    is StateLoading.Loading -> {
                        binding.apply {
                            loader.isVisible = true
                            loaderRefresh.isVisible = false
                            loaderProgressBar.isVisible = true
                            staffDetailItems.isVisible = false
                        }
                    }
                    is StateLoading.Success -> {
                        binding.apply {
                            loader.isVisible = false
                            loaderRefresh.isVisible = false
                            staffDetailItems.isVisible = true
                        }
                    }
                    else -> {
                        binding.apply {
                            loaderProgressBar.isVisible = false
                            loader.isVisible = true
                            loaderRefresh.isVisible = true
                            staffDetailItems.isVisible = false
                        }
                    }
                }
            }
        }
    }

    private fun getStaffInfo() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.currentStaff.collect { staff ->
                if (staff != null) {
                    binding.apply {
                        staffDetailPoster.loadImage(staff.posterUrl)
                        staffDetailName.text = staff.nameRu ?: staff.nameEn ?: "Unknown name"
                        if (staff.profession != null) staffDetailProfession.text = staff.profession
                        else staffDetailProfession.isVisible = false

                        if (staff.films != null) tvFilmographyCount.text =
                            resources.getQuantityString(
                                R.plurals.staff_details_film_count,
                                staff.films.size,
                                staff.films.size
                            )
                        if (staff.films != null) {
                            val list: MutableList<HomeItem> = staff.films.toMutableList()
                            list.removeAll { it.rating == null }
                            val sortedList = list.sortedBy { it.rating?.toDouble() }.reversed()
                            val result = mutableListOf<HomeItem>()

                            if (sortedList.size > 10) {
                                repeat(10) { result.add(sortedList[it]) }
                            } else result.addAll(sortedList)

                            result.sortedBy { it.rating }
                            filmAdapter.submitList(result)
                        }
                    }
                }
            }
        }
    }

    private fun onClickFilm(filmId: Int) {
        viewModel.getFilmById(filmId)
        findNavController().navigate(R.id.action_fragmentStaffDetail_to_fragmentFilmDetail)
    }

    private fun getAllFilmsByStaff() {
        findNavController().navigate(R.id.action_fragmentStaffDetail_to_fragmentFilmography)
    }

    private fun setButtonsListeners() {
        binding.apply {
            btnBackStaffDetail.setOnClickListener { requireActivity().onBackPressed() }
            ibShowAllFilmStaffDetail.setOnClickListener { getAllFilmsByStaff() }
            ibShowAllFilmography.setOnClickListener { getAllFilmsByStaff() }
        }
    }
}