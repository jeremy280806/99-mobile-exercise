package com.example.myapplication

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


data class ListingDetailResponse(
    val id: Int,
    val project_name: String,
    val photo: String,
    val description: String,
    val address: DetailAddress,
    val attributes: Attributes,
    val property_details: List<PropertyDetail>
)

data class DetailAddress(
    val title: String,
    val subtitle: String,
    val map_coordinates: MapCoordinates
)

data class MapCoordinates(
    val lat: Double,
    val lng: Double
)

data class PropertyDetail(
    val label: String,
    val text: String
)