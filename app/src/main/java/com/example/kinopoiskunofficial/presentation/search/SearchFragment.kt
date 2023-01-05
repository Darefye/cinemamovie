package com.example.kinopoiskunofficial.presentation.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.example.kinopoiskunofficial.R
import com.example.kinopoiskunofficial.SearchViewModel
import com.example.kinopoiskunofficial.databinding.FragmentSearchBinding
import com.example.kinopoiskunofficial.presentation.home.allfilmsbycategory.allfilmadapter.AllFilmAdapter
import com.example.kinopoiskunofficial.presentation.search.adapters.search.SearchAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FragmentSearch : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SearchViewModel by activityViewModels()

    private val adapter by lazy(LazyThreadSafetyMode.NONE) {
        SearchAdapter { onFilmClick(it) }
    }

    private var isEditFocused = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAdapter()                // Установка адаптера
        setSearchString()           // Установка строки поиска
        getFilmList()               // Получение списка фильмов

        binding.searchFilterBtn.setOnClickListener {
            findNavController().navigate(R.id.action_search_fragment_to_fragmentSearchSettings)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isFilterChanged.collect {
                    if (it) adapter.refresh()
                }
            }
        }
    }

    private fun onFilmClick(filmId: Int) {
        val action =
            FragmentSearchDirections.actionSearchFragmentToFragmentFilmDetail(filmId)
        findNavController().navigate(action)
    }

    private fun setAdapter() {

        adapter.addLoadStateListener { state ->
            val currentState = state.refresh
            binding.searchFilmList.isVisible = currentState != LoadState.Loading
            binding.loadingProgress.isVisible = currentState == LoadState.Loading
            binding.searchProgressText.isVisible = currentState != LoadState.Loading
            binding.searchProgressImage.isVisible = currentState == LoadState.Loading
            when (currentState) {
                is LoadState.Loading -> {
                    binding.searchFilmList.isVisible = false
                    binding.searchProgressGroup.isVisible = true
                    binding.searchProgressText.isVisible = false
                    binding.searchProgressImage.isVisible = true
                }
                is LoadState.NotLoading -> {
                    binding.searchFilmList.isVisible = true
                    binding.loadingProgress.isVisible = false
                    binding.searchProgressText.isVisible = false
                    binding.searchProgressImage.isVisible = false

                }
                else -> {
                    binding.searchFilmList.isVisible = false
                    binding.loadingProgress.isVisible = false
                    binding.searchProgressText.isVisible = true
                    binding.searchProgressImage.isVisible = true
                }
            }
        }


        binding.searchFilmList.layoutManager =
            GridLayoutManager(requireContext(), 1, GridLayoutManager.VERTICAL, false)
        binding.searchFilmList.adapter = adapter
    }

    private fun setSearchString() {
        binding.searchMyField.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int){}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                    delay(500)
                    viewModel.updateFilters(
                        filterFilm = viewModel.getFilters().copy(keyword = s.toString())
                    )
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun getFilmList() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.films.collectLatest(adapter::submitData)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}