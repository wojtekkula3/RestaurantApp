package com.wojciechkula.goodtime.data.datasource

import com.wojciechkula.goodtime.data.dao.ProductDao
import com.wojciechkula.goodtime.data.entity.ProductEntity
import javax.inject.Inject

class ProductLocalDataSource @Inject constructor(private val productDao: ProductDao) {

    suspend fun getProducts() = productDao.getProducts()

    suspend fun updateProducts(products: List<ProductEntity>) = productDao.insertProducts(products)
}