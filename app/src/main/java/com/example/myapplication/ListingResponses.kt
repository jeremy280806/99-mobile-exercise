package com.example.myapplication

class ListingResponses {
    data class ListingResponse(
        val id: Int,
        val project_name: String,
        val category: String,
        val completed_at: String,
        val tenure: Int,
        val photo: String,
        val address: Address,
        val attributes: Attributes
    )

    data class Address(
        val district: String,
        val street_name: String
    )

    data class Attributes(
        val area_size: Int,
        val bathrooms: Int,
        val bedrooms: Int,
        val price: Int
    )
}