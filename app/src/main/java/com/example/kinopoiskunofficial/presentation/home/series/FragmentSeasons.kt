package com.example.kinopoiskunofficial.presentation.home.series

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.size
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kinopoiskunofficial.CinemaViewModel
import com.example.kinopoiskunofficial.R
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.example.kinopoiskunofficial.data.seasons.Season
import com.example.kinopoiskunofficial.databinding.FragmentSeasonsBinding
import com.example.kinopoiskunofficial.presentation.home.series.adapter.SeasonsAdapter

class FragmentSeasons : Fragment() {

    private var _binding: FragmentSeasonsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CinemaViewModel by activityViewModels()
    private lateinit var adapter: SeasonsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSeasonsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args: FragmentSeasonsArgs by navArgs()

        setAdapter()
        setEpisodeList()

        binding.seriesNameTv.text = args.seriesName
        binding.seriesBack.setOnClickListener { requireActivity().onBackPressed() }
    }

    private fun setAdapter() {
        adapter = SeasonsAdapter()
        binding.seriesEpisodeList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.seriesEpisodeList.adapter = adapter
    }

    private fun setEpisodeList() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.seasons.collect { seasons ->
                adapter.submitList(seasons[0].episodes)
                setChipGroup(seasons)
            }
        }
    }

    private fun setChipGroup(seasons: List<Season>) {
        val chipBackColors = ColorStateList(
            arrayOf(
                intArrayOf(android.R.attr.state_checked, android.R.attr.state_enabled),
                intArrayOf()
            ),
            intArrayOf(Color.BLUE, Color.WHITE)
        )
        val chipTextColors = ColorStateList(
            arrayOf(
                intArrayOf(android.R.attr.state_checked, android.R.attr.state_enabled),
                intArrayOf()
            ),
            intArrayOf(Color.WHITE, Color.BLACK)
        )
        val chipStrokeColors = ColorStateList(
            arrayOf(
                intArrayOf(android.R.attr.state_checked, android.R.attr.state_enabled),
                intArrayOf()
            ),
            intArrayOf(Color.BLUE, Color.BLACK)
        )
        val chipGroup = ChipGroup(requireContext()).apply {
            isSingleSelection = true
            chipSpacingHorizontal = 8
        }
        for (i in seasons.indices) {
            val chip = Chip(requireContext()).apply {
                text = resources.getString(R.string.season_chip_name, i + 1)
                chipBackgroundColor = chipBackColors
                setTextColor(chipTextColors)
                chipStrokeColor = chipStrokeColors
                chipStartPadding = 24f
                chipEndPadding = 24f
                isCheckable = true
                checkedIcon = null
                chipStrokeWidth = 1f
                isSelected = false
                isChecked = chipGroup.size == 0
            }
            chip.setOnClickListener { adapter.submitList(seasons[i].episodes) }
            chipGroup.addView(chip)
        }
        binding.seriesChipsGroupContainer.addView(chipGroup)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}