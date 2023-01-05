package com.example.kinopoiskunofficial.presentation.home.allfilmsbycategory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.example.kinopoiskunofficial.CinemaViewModel
import com.example.kinopoiskunofficial.R
import com.example.kinopoiskunofficial.data.CategoriesFilms
import com.example.kinopoiskunofficial.databinding.FragmentAllFilmsBinding
import com.example.kinopoiskunofficial.presentation.home.HomeFragmentDirections
import com.example.kinopoiskunofficial.presentation.home.allfilmsbycategory.allfilmadapter.AllFilmAdapter

class FragmentAllFilms : Fragment() {
    private var _binding: FragmentAllFilmsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CinemaViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllFilmsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            binding.allFilmsCategoryTv.text = viewModel.getCurrentCategory().text
            binding.btnBackAllFilms.setOnClickListener { requireActivity().onBackPressed() }
            binding.loaderRefresh.setOnClickListener { viewModel.getAllFilmAdapter().retry() }
        }
        setAdapter()
        setFilmList()
    }

    private fun setAdapter() {
        viewModel.setAllFilmAdapter(AllFilmAdapter { onClickFilm(it) })

        binding.allFilmsList.layoutManager =
            GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        binding.allFilmsList.adapter = viewModel.getAllFilmAdapter()

        viewModel.getAllFilmAdapter().addLoadStateListener { state ->
            val currentState = state.refresh
            binding.allFilmsList.isVisible = currentState != LoadState.Loading
            binding.loader.isVisible = currentState == LoadState.Loading
            binding.loaderRefresh.isVisible = currentState != LoadState.Loading

            when (currentState) {
                is LoadState.Loading -> {
                    binding.allFilmsList.isVisible = false
                    binding.loader.isVisible = true
                    binding.loaderRefresh.isVisible = false
                }
                is LoadState.NotLoading -> {
                    binding.allFilmsList.isVisible = true
                    binding.loader.isVisible = false
                    binding.loaderRefresh.isVisible = true
                }
                else -> {
                    binding.allFilmsList.isVisible = false
                    binding.loaderProgressBar.isVisible = false
                    binding.loaderRefresh.isVisible = true
                }
            }
        }
    }

    private fun setFilmList() {
        if (viewModel.getCurrentCategory() == CategoriesFilms.TV_SERIES) {
            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                viewModel.allSeries.collect {
                    viewModel.getAllFilmAdapter().submitData(it)
                }
            }
        } else {
            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                viewModel.allFilmsByCategory.collect {
                    viewModel.getAllFilmAdapter().submitData(it)
                }
            }
        }
    }

    private fun onClickFilm(filmId: Int) {
        val action = FragmentAllFilmsDirections.actionFragmentAllFilmsToFragmentFilmDetail(filmId)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}