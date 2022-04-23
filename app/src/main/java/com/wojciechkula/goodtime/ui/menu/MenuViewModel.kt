package com.wojciechkula.goodtime.ui.menu

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wojciechkula.goodtime.domain.usecase.getProductsInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val getProductsInteractor: getProductsInteractor
) : ViewModel() {

    val viewState: MutableState<MenuViewState> = mutableStateOf(MenuViewState(listOf()))

    init {
        viewModelScope.launch {
            try {
                val products = getProductsInteractor()
                viewState.value.products = products
            } catch (e: Exception) {
                Timber.e("Exception occurred while getting products", e.message)
            }
        }
    }
}