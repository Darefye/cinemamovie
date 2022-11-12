package com.example.kinopoiskunofficial.presentation.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kinopoiskunofficial.R
import com.example.kinopoiskunofficial.presentation.BaseFragment


class OnBoardingFragment1: BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_onboarding_first, container, false)
    }
}