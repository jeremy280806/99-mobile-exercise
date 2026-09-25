package com.example.myapplication

import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("listings.json")
    suspend fun getListings(): List<ListingResponse>

    @GET("details/{id}.json")
    suspend fun getListingDetail(@Path("id") id: String): ListingDetailResponse
}