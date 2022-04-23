package com.wojciechkula.goodtime.ui.shared

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wojciechkula.goodtime.domain.model.ProductModel
import com.wojciechkula.goodtime.domain.usecase.getProductsInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val getProductsInteractor: getProductsInteractor
) : ViewModel() {

    val viewState: MutableState<List<ProductModel>> = mutableStateOf(ArrayList())
    private val _eventsFlow = MutableSharedFlow<SharedViewEvent>()
    val eventsFlow = _eventsFlow.asSharedFlow()

    fun getProducts() {
        viewModelScope.launch {
            try {
                val products = getProductsInteractor()
                viewState.value = products
                _eventsFlow.emit(SharedViewEvent.ShowPossibleDialog(products))
            } catch (e: Exception) {
                Timber.e("Exception while getting products", e)
            }
        }
    }
}