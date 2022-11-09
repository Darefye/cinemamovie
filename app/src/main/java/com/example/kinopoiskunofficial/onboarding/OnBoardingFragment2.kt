package com.example.kinopoiskunofficial.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kinopoiskunofficial.R
import com.example.kinopoiskunofficial.BaseFragment


class OnBoardingFragment2 : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_onboarding_second, container, false)
    }
}