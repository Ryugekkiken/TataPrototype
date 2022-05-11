package com.example.tataprototype.UI

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.tataprototype.R
import com.example.tataprototype.databinding.FragmentStartBinding

private lateinit var binding: FragmentStartBinding

private const val aboutUrl = "https://www.tcs.com/about-us"
private const val contactUrl = "https://www.tcs.com/about-us#type=overlay&page=/connect-with-tcs/contact-us/contact-us-overlay"

//About page: https://www.tcs.com/about-us
//Contact page: https://www.tcs.com/about-us#type=overlay&page=/connect-with-tcs/contact-us/contact-us-overlay

class StartFragment : Fragment()
{
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        binding = FragmentStartBinding.inflate(inflater, container, false)

        binding.signInButton.setOnClickListener{ onClickSignIn() }
        binding.aboutButton.setOnClickListener{ onClickAbout() }
        binding.contactButton.setOnClickListener{ onClickContact() }

        return binding.root
    }

    private fun onClickAbout()
    {
        val queryUrl: Uri = Uri.parse(aboutUrl)
        val intent = Intent(Intent.ACTION_VIEW, queryUrl)

        context?.startActivity(intent)
    }

    private fun onClickContact()
    {
        val queryUrl: Uri = Uri.parse(contactUrl)
        val intent = Intent(Intent.ACTION_VIEW, queryUrl)

        context?.startActivity(intent)
    }

    private fun onClickSignIn()
    {
        findNavController().navigate(R.id.action_start_to_loginFragment)
    }

}