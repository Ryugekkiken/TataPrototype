package com.example.tataprototype.Model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DocumentViewModel: ViewModel()
{
    val url = MutableLiveData<String>()
    val docName = MutableLiveData<String>()
}