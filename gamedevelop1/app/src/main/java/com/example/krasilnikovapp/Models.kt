package com.example.krasilnikovapp

data class Category(
    val id: Int,
    val name: String,
    val icon: String,
    val itemCount: Int
)

data class Product(
    val id: Int,
    val name: String,
    val price: Double,
    val rating: Float,
    val categoryId: Int,
    val imageUrl: String,
    val description: String,
    val isFavorite: Boolean = false
)