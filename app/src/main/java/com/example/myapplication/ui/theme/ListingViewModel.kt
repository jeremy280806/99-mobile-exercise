package com.example.myapplication.ui.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.ListingRepository
import com.example.myapplication.ListingResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ListingViewModel(private val repository: ListingRepository) : ViewModel() {
    private val _listings = MutableStateFlow<List<ListingResponse>>(emptyList())
    val listings = _listings.asStateFlow()

    fun getListings() {
        viewModelScope.launch {
            try {
                val result = repository.fetchListings()
                _listings.value = result
            } catch (e: Exception) {
                // TODO: Handle error state
            }
        }
    }
}