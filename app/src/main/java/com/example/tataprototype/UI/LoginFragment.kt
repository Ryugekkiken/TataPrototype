package com.example.tataprototype.UI

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.tataprototype.R
import com.example.tataprototype.databinding.FragmentLoginBinding

private const val USER = "user"
private const val PASS = "password"

class LoginFragment : Fragment()
{
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        binding.loginButton.setOnClickListener{ validateLogin() }
        return binding.root
    }

    private fun validateLogin()
    {
        if(binding.emailField.editText?.text.toString() == USER && binding.passwordField.editText?.text.toString() == PASS)
        {
            switchFragments()
            setErrorTextField(false)
        }
        else
        {
            setErrorTextField(true)
        }
    }

    private fun setErrorTextField(error: Boolean)
    {
        if(error)
        {
            binding.emailField.isErrorEnabled = true
            binding.passwordField.isErrorEnabled = true
            binding.emailField.error = getString(R.string.error_login)
            binding.passwordField.error = getString(R.string.error_login)
        }
        else
        {
            binding.emailField.isErrorEnabled = false
            binding.passwordField.isErrorEnabled = false
            binding.emailField.error = null
            binding.passwordField.error = null
        }
    }

    private fun switchFragments()
    {
        findNavController().navigate(R.id.action_loginFragment_to_hubFragment)
    }
}