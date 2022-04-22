package com.wojciechkula.goodtime.domain.model

data class ProductModel(
    val id: String = "",
    val name: String = "",
    val number: Int = 0,
    val type: String = "",
    val ingredients: String = "",
    val price: ArrayList<Double> = arrayListOf(),
    val picture: String = ""
)