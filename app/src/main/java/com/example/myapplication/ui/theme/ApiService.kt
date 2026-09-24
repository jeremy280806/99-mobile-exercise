package com.example.myapplication.ui.theme

import com.example.myapplication.ListingResponse
import retrofit2.http.GET

interface ApiService {
    @GET("Listing.json")
    suspend fun getListings(): List<ListingResponse>
}