package com.example.tataprototype

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.tataprototype.Network.DataSource
import com.example.tataprototype.databinding.FragmentHubBinding

private lateinit var binding: FragmentHubBinding

class HubFragment : Fragment()
{
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        binding = FragmentHubBinding.inflate(inflater, container, false)

        binding.categoriesHolder.setOnClickListener{ onClickCategoriesHolder() }
        binding.documentCategoryHolder.setOnClickListener{ onClickDocumentCategory() }
        binding.urgentCategoryHolder.setOnClickListener{ onClickUrgentCategory() }

        showDocumentPercentage()
        showUrgentDocs()
        showUnsignedDocs()

        return binding.root
    }

    private fun onClickDocumentCategory()
    {
        findNavController().navigate(HubFragmentDirections.actionHubFragmentToDocumentViewFragment(2))
    }

    private fun onClickUrgentCategory()
    {
        findNavController().navigate(HubFragmentDirections.actionHubFragmentToDocumentViewFragment(1))
    }

    private fun onClickCategoriesHolder()
    {
        findNavController().navigate(HubFragmentDirections.actionHubFragmentToDocumentViewFragment(0))
    }


    @SuppressLint("SetTextI18n")
    private fun showDocumentPercentage()
    {
        var unsignedDocs = 0

        for (document in DataSource.documents)
        {
            if (!document.signed)
                unsignedDocs++
        }

        binding.percentage.text = "${((unsignedDocs / DataSource.documents.size) * 100)}%"
    }

    private fun showUrgentDocs()
    {
        var urgentDocs = 0

        for(document in DataSource.documents)
        {
            if(document.urgent)
                urgentDocs++
        }

        binding.urgentCategoryNotifText.text = getString(R.string.urgent_category_notification_text, urgentDocs)
    }

    private fun showUnsignedDocs()
    {
        var unsignedDocuments = 0

        for(document in DataSource.documents)
        {
            if(!document.signed)
                unsignedDocuments++
        }

        binding.documentCategoryNotifText.text = getString(R.string.document_category_notification_text, unsignedDocuments)
    }
}