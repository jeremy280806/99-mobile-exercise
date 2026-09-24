package com.example.myapplication

import com.example.myapplication.ui.theme.ApiService

class ListingRepository (private val apiService: ApiService){
    suspend fun fetchListings(): List<ListingResponse>{
        return apiService.getListings()
    }
}