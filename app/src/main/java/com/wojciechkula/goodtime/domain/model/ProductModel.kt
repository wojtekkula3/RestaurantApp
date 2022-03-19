package com.wojciechkula.goodtime.domain.model

data class ProductModel(
    val id: String = "",
    val name: String = "",
    val number: Int = 0,
    val type: String = "",
    val ingredients: String = "",
    val price: Double = 0.0,
    val picture: String = ""
)