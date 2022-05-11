package com.example.tataprototype.UI

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.tataprototype.Adapter.DocAdapter
import com.example.tataprototype.Model.DocumentViewModel
import com.example.tataprototype.Network.DataSource
import com.example.tataprototype.databinding.FragmentDocumentViewBinding

class DocumentViewFragment : Fragment()
{
    private val viewModel = DocumentViewModel()
    private lateinit var binding: FragmentDocumentViewBinding
    private val args: DocumentViewFragmentArgs by navArgs()
    private lateinit var adapter: DocAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        binding = FragmentDocumentViewBinding.inflate(inflater, container, false)

        initRecycler(args.colorIndex)

        binding.searchBar.setOnQueryTextListener(object: SearchView.OnQueryTextListener{

            override fun onQueryTextSubmit(p0: String?): Boolean
            {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean
            {
                adapter.filter.filter(newText)
                return false
            }
        })

        viewModel.url.observe(viewLifecycleOwner){
            findNavController().navigate(DocumentViewFragmentDirections.actionDocumentViewFragmentToWebViewFragment(it))
        }

        return binding.root
    }



    private fun initRecycler(colorIndex: Int)
    {
        adapter = DocAdapter(DataSource.documents.toMutableList() , viewModel, this.requireContext(), colorIndex)
        binding.rvDocument.layoutManager = GridLayoutManager(this.requireContext(), 2)
        binding.rvDocument.adapter = adapter
    }


}