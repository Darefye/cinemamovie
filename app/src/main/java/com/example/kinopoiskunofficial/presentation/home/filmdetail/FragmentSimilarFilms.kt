package com.example.kinopoiskunofficial.presentation.home.filmdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.kinopoiskunofficial.CinemaViewModel
import com.example.kinopoiskunofficial.R
import com.example.kinopoiskunofficial.databinding.FragmentAllFilmsBinding
import com.example.kinopoiskunofficial.presentation.home.HomeFragmentDirections
import com.example.kinopoiskunofficial.presentation.home.filmrecycler.FilmAdapter

class FragmentSimilarFilms : Fragment() {
    private var _binding: FragmentAllFilmsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CinemaViewModel by activityViewModels()
    private lateinit var adapter: FilmAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllFilmsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.allFilmsCategoryTv.text = resources.getString(R.string.label_film_similar)
        binding.btnBackAllFilms.setOnClickListener { requireActivity().onBackPressed() }

        setAdapter()                // Установка адаптера
        setFilmList()               // Установка списка фильмов
    }

    private fun setAdapter() {
        adapter = FilmAdapter(20, {}) { onClickFilm(it) }
        binding.allFilmsList.layoutManager =
            GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        binding.allFilmsList.adapter = adapter
    }

    private fun setFilmList() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.currentFilmSimilar.collect {
                adapter.submitList(it)
            }
        }
    }

    private fun onClickFilm(filmId: Int) {
        val action = HomeFragmentDirections.actionHomeFragmentToFragmentFilmDetail(filmId)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}