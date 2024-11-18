package com.aman.bottomnavplayground.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aman.bottomnavplayground.R
import com.aman.bottomnavplayground.databinding.FragmentRoseBinding
import com.aman.bottomnavplayground.databinding.FragmentTulipBinding


class TulipFragment : Fragment() {

     var _binding : FragmentTulipBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTulipBinding.inflate(layoutInflater,container,false)


        return _binding?.root
    }


}