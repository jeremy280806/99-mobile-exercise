package com.example.myapplication

class ListingRepository (private val apiService: ApiService){
    suspend fun fetchListings(): List<ListingResponse>{
        return apiService.getListings()
    }

    suspend fun fetchListingDetail(id: String): ListingDetailResponse{
        return apiService.getListingDetail(id)
    }
}