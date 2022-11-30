package com.example.kinopoiskunofficial.presentation.splash

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.kinopoiskunofficial.R
import com.example.kinopoiskunofficial.databinding.FragmentSplashBinding
import com.example.kinopoiskunofficial.presentation.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : BaseFragment() {
    private lateinit var binding: FragmentSplashBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        Handler().postDelayed({
            if (onBoardingFinished()) {
                findNavController().navigate(R.id.action_splash_fragment_to_home_fragment)
            } else {
                findNavController().navigate(R.id.action_splash_fragment_to_main_onboarding_fragment)
            }
        }, 500)

        binding = FragmentSplashBinding.inflate(layoutInflater, container, false)
        return binding.root

    }

    private fun onBoardingFinished(): Boolean {
        val sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        return sharedPref.getBoolean("Finished", false)
    }
}