package com.wojciechkula.goodtime.ui.common.bottomNavItems

import androidx.annotation.StringRes
import com.wojciechkula.goodtime.R

data class BottomNavItem(
    @StringRes val name: Int,
    val route: String,
    val icon: Int,
    val badgeCount: Int = 0

) {
    companion object {
        val bottomNavItems = listOf(
            BottomNavItem(
                name = R.string.menu,
                route = "menu_screen",
                icon = R.drawable.ic_menu
            ),
            BottomNavItem(
                name = R.string.orders,
                route = "orders_screen",
                icon = R.drawable.ic_orders
            )
        )
    }
}