package com.wojciechkula.goodtime.data.mapper

import com.wojciechkula.goodtime.data.entity.ProductEntity
import com.wojciechkula.goodtime.domain.model.ProductModel
import javax.inject.Inject

class ProductMapper @Inject constructor() {

    fun mapToDomain(productEntity: ProductEntity) = ProductModel(
        id = productEntity.id,
        name = productEntity.name,
        number = productEntity.number,
        type = productEntity.type,
        ingredients = productEntity.ingredients,
        price = productEntity.price,
        picture = productEntity.picture
    )

    fun mapToEntity(productModel: ProductModel) = ProductEntity(
        id = productModel.id,
        name = productModel.name,
        number = productModel.number,
        type = productModel.type,
        ingredients = productModel.ingredients,
        price = productModel.price,
        picture = productModel.picture
    )
}