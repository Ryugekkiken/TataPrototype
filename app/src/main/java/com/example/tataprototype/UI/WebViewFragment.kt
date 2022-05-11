package com.example.tataprototype.UI

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.tataprototype.R
import com.example.tataprototype.databinding.FragmentWebViewBinding

private lateinit var binding: FragmentWebViewBinding

class WebViewFragment : Fragment()
{
    private val args: WebViewFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        binding = FragmentWebViewBinding.inflate(inflater, container, false)

        val webView: WebView = binding.webView
        val url = args.url

        webView.webViewClient = object : WebViewClient(){
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url!!)
                return true
            }
        }

        webView.loadUrl(url)

        binding.floatingEditButton.setOnClickListener{
            findNavController().navigate(R.id.action_webViewFragment_to_drawFragment)
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object: OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.action_webViewFragment_to_hubFragment)
            }
        })

        return binding.root
    }

    private fun setFullscreen()
    {
        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN)
    }

    private fun exitFullscreen()
    {
        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN)
        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }


    override fun onResume()
    {
        super.onResume()

        setFullscreen()
    }

    override fun onStop()
    {
        super.onStop()

        exitFullscreen()
    }
}