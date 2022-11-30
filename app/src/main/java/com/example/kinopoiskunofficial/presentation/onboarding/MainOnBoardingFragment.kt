package com.example.kinopoiskunofficial.presentation.onboarding

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.kinopoiskunofficial.R
import com.example.kinopoiskunofficial.databinding.FragmentOnboardingMainBinding
import com.example.kinopoiskunofficial.presentation.BaseFragment


class MainOnBoardingFragment : BaseFragment() {

    private lateinit var viewPager: ViewPager2
    private lateinit var viewPagerIndicator: LinearLayout
    private lateinit var skipButton: Button

    private var _binding: FragmentOnboardingMainBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentOnboardingMainBinding.inflate(inflater, container, false)
        binding.viewPager
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
        initListeners()
    }

    private fun initViews(view: View) {
        viewPager = view.findViewById(R.id.view_pager)
        viewPager.adapter = ViewPagerAdapter(activity as AppCompatActivity)
        viewPagerIndicator = view.findViewById(R.id.viewPagerIndicator)
        initIndicators()
        skipButton = binding.btnSkip
    }

    private fun initListeners() {
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                setSelectedIndicator(position)
                super.onPageSelected(position)
            }
        })


        skipButton.setOnClickListener {
            onBoardingFinished()
            findNavController().navigate(R.id.action_main_onboarding_fragment_to_home_fragment)
        }
    }

    private fun initIndicators() {
        val indicators = arrayOfNulls<View>(3)
        val layoutParamsFirst: LinearLayout.LayoutParams = LinearLayout.LayoutParams(48, 48)
        val layoutParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(30, 30)
        layoutParams.marginEnd = 16
        layoutParamsFirst.marginEnd = 16

        for (i in indicators.indices) {
            indicators[i] = View(activity)
            if (i == 0) {
                indicators[i]?.background =
                    ResourcesCompat.getDrawable(resources, R.drawable.indicator_active, null)
                indicators[i]?.layoutParams = layoutParamsFirst
            } else {
                indicators[i]?.background =
                    ResourcesCompat.getDrawable(resources, R.drawable.indicator_inactive, null)
                indicators[i]?.layoutParams = layoutParams
            }
            viewPagerIndicator.addView(indicators[i])
        }
    }

    private fun setSelectedIndicator(position: Int) {
        for (i in 0 until viewPagerIndicator.childCount) {
            val indicator: View = viewPagerIndicator.getChildAt(i)
            if (i == position) {
                val layoutParamsSelected: LinearLayout.LayoutParams =
                    LinearLayout.LayoutParams(32, 32)
                layoutParamsSelected.marginEnd = 16
                indicator.layoutParams = layoutParamsSelected
                indicator.background =
                    ResourcesCompat.getDrawable(resources, R.drawable.indicator_active, null)
            } else {
                val layoutParamsInactive: LinearLayout.LayoutParams =
                    LinearLayout.LayoutParams(30, 30)
                layoutParamsInactive.marginEnd = 16
                indicator.layoutParams = layoutParamsInactive
                indicator.background =
                    ResourcesCompat.getDrawable(resources, R.drawable.indicator_inactive, null)
            }
        }
    }

    private fun onBoardingFinished() {
        val sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("Finished", true)
        editor.apply()
    }
}