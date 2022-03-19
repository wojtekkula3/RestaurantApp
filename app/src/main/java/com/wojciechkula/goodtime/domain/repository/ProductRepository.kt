package com.wojciechkula.goodtime.domain.repository

import androidx.lifecycle.LiveData
import com.wojciechkula.goodtime.domain.model.ProductModel

interface ProductRepository {

    suspend fun getProducts(): List<ProductModel>
}