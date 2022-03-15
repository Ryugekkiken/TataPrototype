package com.example.tataprototype.UI

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.tataprototype.R
import com.example.tataprototype.databinding.FragmentLoginBinding


class LoginFragment : Fragment()
{
    private lateinit var _binding: FragmentLoginBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        _binding.loginButton.setOnClickListener{switchFragments()}
        return _binding.root
    }


    private fun switchFragments()
    {
        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
    }
}