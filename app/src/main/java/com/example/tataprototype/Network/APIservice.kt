package com.example.tataprototype.Network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class APIservice
{
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
}