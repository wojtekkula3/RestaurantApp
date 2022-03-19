package com.wojciechkula.goodtime.domain.usecase

import androidx.lifecycle.liveData
import com.wojciechkula.goodtime.domain.repository.ProductRepository
import javax.inject.Inject

class getProductsInteractor @Inject constructor(private val repository: ProductRepository) {

    suspend operator fun invoke() = repository.getProducts()
}