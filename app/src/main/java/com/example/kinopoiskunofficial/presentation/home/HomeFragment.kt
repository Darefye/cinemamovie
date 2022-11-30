package com.example.kinopoiskunofficial.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.kinopoiskunofficial.CinemaViewModel
import com.example.kinopoiskunofficial.R
import com.example.kinopoiskunofficial.data.CategoriesFilms
import com.example.kinopoiskunofficial.databinding.FragmentHomeBinding
import com.example.kinopoiskunofficial.presentation.StateLoading
import com.example.kinopoiskunofficial.presentation.home.categoryrecycler.CategoryAdapter

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CinemaViewModel by activityViewModels()
    private lateinit var categoryAdapter: CategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        stateLoadingListener()
        getCategories()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun getCategories() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.homePageList.collect {
                categoryAdapter =
                    CategoryAdapter(20, it, { onClickAllButton(it) }, { onClickFilm(it) })
                binding.categoryList.adapter = categoryAdapter
            }
        }
    }

    private fun onClickFilm(filmId: Int) {
        viewModel.getFilmById(filmId)
        findNavController().navigate(R.id.action_home_fragment_to_fragmentFilmDetail)
    }

    private fun onClickAllButton(category: CategoriesFilms) {
        viewModel.setCurrentCategory(category)
        findNavController().navigate(R.id.action_home_fragment_to_fragmentAllFilms)
    }

    private fun stateLoadingListener() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.loadCategoryState.collect { state ->
                when (state) {
                    is StateLoading.Loading -> {
                        binding.loader.isVisible = true
                        binding.loaderProgressBar.isVisible = true
                        binding.loaderRefresh.isVisible = false
                        binding.categoryList.isVisible = false
                    }
                    is StateLoading.Success -> {
                        binding.loader.isVisible = false
                        binding.categoryList.isVisible = true
                    }
                    else -> {
                        binding.loader.isVisible = true
                        binding.loaderProgressBar.isVisible = false
                        binding.loaderRefresh.isVisible = true
                        binding.categoryList.isVisible = false
                        binding.loaderRefresh.setOnClickListener { viewModel.getFilmsByCategories() }
                    }
                }
            }
        }
    }
}
