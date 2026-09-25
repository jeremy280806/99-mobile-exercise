package com.example.myapplication

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchResultScreen(viewModel: ListingViewModel, onListingClick: (String) -> Unit = {}) {
    val listings by viewModel.listings.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getListings()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Search Results", fontWeight = FontWeight.Bold) }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(listings) { listing ->
                ListingCard(
                    listing = listing,
                    onClick = { onListingClick(listing.id.toString()) }
                )
            }
        }
    }
}

@Composable
fun ListingCard(listing: ListingResponse, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column {
            AsyncImage(
                model = listing.photo,
                contentDescription = "Foto ${listing.project_name}",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = listing.project_name,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "${listing.address.street_name} · ${listing.address.district}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "${listing.category} · ${listing.completed_at} · ${listing.tenure} yrs",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "${listing.attributes.bedrooms} Beds · ${listing.attributes.bathrooms} Baths · ${listing.attributes.area_size} sqft",
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "$${listing.attributes.price}/mo",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListingDetailScreen(listingId: String, viewModel: ListingViewModel, onBackClick: () -> Unit) {
    val detailState by viewModel.detailListing.collectAsState()

    // Ambil data detail dari internet berdasarkan ID saat halaman dibuka
    LaunchedEffect(listingId) {
        viewModel.getListingDetail(listingId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Listing detail") },
                navigationIcon = {
                    TextButton(onClick = onBackClick) {
                        Text(text = "<- Back", color = MaterialTheme.colorScheme.primary)
                    }
                }
            )
        }
    ) { paddingValues ->
        val detail = detailState
        if (detail == null) {
            // Tampilkan loading jika data belum selesai didownload
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            // Gambar UI detail jika data sudah ada
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                item {
                    AsyncImage(
                        model = detail.photo,
                        contentDescription = "Property Image",
                        modifier = Modifier.fillMaxWidth().height(250.dp),
                        contentScale = ContentScale.Crop
                    )

                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "$${detail.attributes.price}",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        Text(text = detail.address.title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        Text(text = detail.address.subtitle, style = MaterialTheme.typography.bodyMedium, color = Color.Gray)

                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "View on map", color = Color.Blue, style = MaterialTheme.typography.bodyMedium)

                        Spacer(modifier = Modifier.height(24.dp))
                        Text(text = "Property details", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }

                // Tabel detail (Price/sqft, Floor Level, dll)
                items(detail.property_details) { propDetail ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = propDetail.label, color = Color.Gray, modifier = Modifier.weight(1f))
                        Text(text = propDetail.text, modifier = Modifier.weight(1f))
                    }
                }

                item {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(text = "Description", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = detail.description, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }
    }
}