package com.example.kinopoiskunofficial.splash

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.kinopoiskunofficial.R
import com.example.kinopoiskunofficial.databinding.FragmentLouderBinding
import com.example.kinopoiskunofficial.BaseFragment

class SplashFragment : BaseFragment() {

    private lateinit var binding: FragmentLouderBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Handler().postDelayed({
            if (onBoardingFinished()) {
                findNavController().navigate(R.id.action_splashFragment_to_navigation_home)
            } else {
                findNavController().navigate(R.id.action_splashFragment_to_mainOnBoardingFragment)
            }
        }, 1000)

        binding = FragmentLouderBinding.inflate(layoutInflater, container, false)

        return binding.root

    }

    private fun onBoardingFinished(): Boolean {
        val sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        return sharedPref.getBoolean("Finished", false)
    }
}