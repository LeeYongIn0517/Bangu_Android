package com.example.bangu.main.mybangu.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bangu.R
import com.example.bangu.databinding.FragmentReviewBinding

class ReviewFragment : Fragment() {
    private lateinit var binding: FragmentReviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentReviewBinding.inflate(inflater,container,false)
        val view = binding.root
        return view
    }
}