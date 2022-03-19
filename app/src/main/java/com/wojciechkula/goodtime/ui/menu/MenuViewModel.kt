package com.wojciechkula.goodtime.ui.menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wojciechkula.goodtime.domain.model.ProductModel
import com.wojciechkula.goodtime.domain.usecase.getProductsInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val getProductsInteractor: getProductsInteractor
) : ViewModel() {

    private var _viewState = MutableLiveData<List<ProductModel>>()
    val viewState: LiveData<List<ProductModel>>
    get() = _viewState


    fun init() {
        viewModelScope.launch {
            val products = getProductsInteractor()
            Timber.d(products.toString())
            _viewState.value = products
        }
    }
}