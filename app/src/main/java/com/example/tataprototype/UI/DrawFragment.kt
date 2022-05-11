package com.example.tataprototype.UI

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.tataprototype.databinding.FragmentDrawBinding
import android.graphics.Bitmap

import android.provider.MediaStore

import android.os.Environment

import android.content.ContentValues
import android.net.Uri
import android.provider.MediaStore.Images.Media.RELATIVE_PATH
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import androidx.navigation.fragment.findNavController
import com.example.tataprototype.R
import com.google.android.material.snackbar.Snackbar
import java.io.OutputStream
import java.lang.Exception

class Draw : Fragment()
{
    private lateinit var binding: FragmentDrawBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        binding = FragmentDrawBinding.inflate(layoutInflater)

        binding.floatingEraseButton.setOnClickListener{ binding.drawView.reset() }
        binding.floatingDoneButton.setOnClickListener{ save() }

        val vto: ViewTreeObserver = binding.drawView.viewTreeObserver
        vto.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
            override fun onGlobalLayout()
            {
                binding.drawView.viewTreeObserver.removeOnGlobalLayoutListener(this)
                val width: Int = binding.drawView.measuredWidth
                val height: Int = binding.drawView.measuredHeight
                binding.drawView.init(height, width)
            }
        })

        return binding.root
    }


    private fun save()
    {
        val bmp: Bitmap = binding.drawView.save()

        // opening a OutputStream to write into the file
        val imageOutStream: OutputStream?

        val cv = ContentValues()

        // name of the file
        cv.put(MediaStore.Images.Media.DISPLAY_NAME, "drawing.png")

        // type of the file
        cv.put(MediaStore.Images.Media.MIME_TYPE, "image/png")

        // location of the file to be saved
        cv.put(RELATIVE_PATH, Environment.DIRECTORY_PICTURES)

        // get the Uri of the file which is to be created in the storage
        val uri: Uri = requireContext().contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, cv)!!

        try
        {
            // open the output stream with the above uri
            imageOutStream = requireContext().contentResolver.openOutputStream(uri)

            // this method writes the files in storage
            bmp.compress(Bitmap.CompressFormat.PNG, 100, imageOutStream)

            // close the output stream after use
            imageOutStream!!.close()

            Snackbar.make(binding.root, R.string.snackbar_success, Snackbar.LENGTH_INDEFINITE).setAnchorView(binding.floatingDoneButton)
                .setAction(R.string.snackbar_action){
                    findNavController().navigate(R.id.action_drawFragment_to_hubFragment)
                }
                .show()
        }
        catch (e: Exception)
        {
            Snackbar.make(binding.root, R.string.snackbar_fail, Snackbar.LENGTH_LONG).show()
        }
    }
}