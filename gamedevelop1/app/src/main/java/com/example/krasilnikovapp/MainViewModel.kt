package com.example.krasilnikovapp

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private val _homeText = mutableStateOf("Натисніть кнопку на головному екрані")
    val homeText: State<String> = _homeText

    private val _favoritesText = mutableStateOf("Ваші улюблені елементи")
    val favoritesText: State<String> = _favoritesText

    private val _profileText = mutableStateOf("Інформація про профіль")
    val profileText: State<String> = _profileText
    private val _categories = mutableStateOf(DataRepository.categories)
    val categories: State<List<Category>> = _categories

    private val _favoriteProducts = mutableStateOf(DataRepository.getFavoriteProducts())
    val favoriteProducts: State<List<Product>> = _favoriteProducts

    private val _selectedCategory = mutableStateOf<Category?>(null)
    val selectedCategory: State<Category?> = _selectedCategory

    fun updateHomeText() {
        _homeText.value = "Кнопку натиснуто о ${System.currentTimeMillis() % 100000}"
    }

    fun updateFavoritesText() {
        _favoritesText.value = "Оновлено: ${System.currentTimeMillis() % 100000}"
    }

    fun updateProfileText() {
        _profileText.value = "Профіль змінено: ${System.currentTimeMillis() % 100000}"
    }

    fun selectCategory(category: Category) {
        _selectedCategory.value = category
    }

    fun getProductsForCategory(categoryId: Int): List<Product> {
        return DataRepository.getProductsByCategory(categoryId)
    }

    fun toggleFavorite(product: Product) {
        val currentFavorites = _favoriteProducts.value.toMutableList()
        val index = currentFavorites.indexOfFirst { it.id == product.id }

        if (index != -1) {
            currentFavorites.removeAt(index)
        } else {
            currentFavorites.add(product.copy(isFavorite = true))
        }

        _favoriteProducts.value = currentFavorites
    }
}