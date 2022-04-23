package com.wojciechkula.goodtime.ui.shared

import com.wojciechkula.goodtime.domain.model.ProductModel

sealed class SharedViewEvent {
    data class ShowPossibleDialog(val products: List<ProductModel>): SharedViewEvent()
}