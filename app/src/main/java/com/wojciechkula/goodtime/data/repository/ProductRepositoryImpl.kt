package com.wojciechkula.goodtime.data.repository

import com.wojciechkula.goodtime.data.datasource.ProductLocalDataSource
import com.wojciechkula.goodtime.data.datasource.ProductRemoteDataSource
import com.wojciechkula.goodtime.data.mapper.ProductMapper
import com.wojciechkula.goodtime.domain.model.ProductModel
import com.wojciechkula.goodtime.domain.repository.ProductRepository
import timber.log.Timber
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productRemoteDataSource: ProductRemoteDataSource,
    private val productLocalDataSource: ProductLocalDataSource,
    private val productMapper: ProductMapper
) : ProductRepository {

    override suspend fun getProducts(): List<ProductModel> {
        try {
            val products = productRemoteDataSource.fetchProducts()
            productLocalDataSource.updateProducts(products)
        } catch (e: Throwable) {
            Timber.d(e, "Error while fetching products from server - using local data")
        }
        return productLocalDataSource.getProducts().map { productEntity -> productMapper.mapToDomain(productEntity) }
    }

}