package com.wojciechkula.goodtime.ui.common.networkConnection

sealed class ConnectionState {
    object Available : ConnectionState()
    object Unavailable : ConnectionState()
}