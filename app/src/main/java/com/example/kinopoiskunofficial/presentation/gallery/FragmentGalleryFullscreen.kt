package com.example.kinopoiskunofficial.presentation.gallery

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.kinopoiskunofficial.CinemaViewModel
import com.example.kinopoiskunofficial.databinding.FragmentGalleryFullscreenBinding
import com.example.kinopoiskunofficial.presentation.gallery.pageradapter.GalleryFullscreenAdapter

class FragmentGalleryFullscreen : Fragment() {
    private var _binding: FragmentGalleryFullscreenBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CinemaViewModel by activityViewModels()
    private lateinit var adapter: GalleryFullscreenAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGalleryFullscreenBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setGalleryFullscreen()
        setButtonsListeners()
    }


    private fun setButtonsListeners() {
        binding.btnBackAllFilms.setOnClickListener { requireActivity().onBackPressed() }
    }

    private fun setGalleryFullscreen() {
        val args: FragmentGalleryFullscreenArgs by navArgs()

        adapter = GalleryFullscreenAdapter()
        PagerSnapHelper().attachToRecyclerView(binding.galleryImageFullscreenContainer)
        binding.galleryImageFullscreenContainer.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.galleryImageFullscreenContainer.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.galleryByType.collect {
                binding.galleryImageFullscreenContainer.scrollToPosition(args.position)
                adapter.submitData(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}