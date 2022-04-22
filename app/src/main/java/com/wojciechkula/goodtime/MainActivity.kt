package com.wojciechkula.goodtime

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.wojciechkula.goodtime.theme.GoodTimeTheme
import com.wojciechkula.goodtime.ui.common.bottomNavItems.BottomNavItem
import com.wojciechkula.goodtime.ui.menu.Menu
import com.wojciechkula.goodtime.ui.orders.Orders
import com.wojciechkula.goodtime.ui.shared.SharedViewModel
import com.wojciechkula.goodtime.ui.splashScreen.SplashScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController: SystemUiController = rememberSystemUiController()
            val navController = rememberNavController()
            val backStackEntry = navController.currentBackStackEntryAsState()
            systemUiController.isStatusBarVisible = backStackEntry.value?.destination?.route != "splash_screen"
            GoodTimeTheme {
                Main(navController, backStackEntry)
            }
        }
    }
}

@Composable
fun Main(navController: NavHostController, backStackEntry: State<NavBackStackEntry?>) {

    Scaffold(
        bottomBar = {
            if (backStackEntry.value?.destination?.route != "splash_screen") {
                BottomNavigationView(
                    items = BottomNavItem.bottomNavItems,
                    navController = navController,
                    onItemClick = {
                        navController.navigate(it.route)
                    }
                )
            }
        },
        content = {
            Navigation(navController = navController)
        })

}

@Composable
fun Navigation(navController: NavHostController) {
    val sharedViewModel = hiltViewModel<SharedViewModel>()

    NavHost(navController = navController, startDestination = "splash_screen") {
        composable("splash_screen") {
            SplashScreen(navController)
        }
        composable("menu_screen") {
            Menu(sharedViewModel, navController)
        }
        composable("orders_screen") { Orders(navController) }
    }
}

@Composable
fun BottomNavigationView(
    items: List<BottomNavItem>,
    navController: NavController,
    modifier: Modifier = Modifier,
    onItemClick: (BottomNavItem) -> Unit
) {
    val backStackEntry = navController.currentBackStackEntryAsState()
    BottomNavigation(
        modifier = modifier,
        elevation = 5.dp
    ) {
        items.forEach { item ->
            val isSelected = item.route == backStackEntry.value?.destination?.route
            BottomNavigationItem(
                selected = isSelected,
                onClick = { onItemClick(item) },
                icon = {
                    Column(horizontalAlignment = CenterHorizontally) {
                        if (item.badgeCount > 0) {
                            BadgedBox(badge = { Badge { Text(text = "${item.badgeCount}") } }) {
                                Icon(painter = painterResource(id = item.icon), contentDescription = item.route)
                            }
                        } else {
                            Icon(painter = painterResource(id = item.icon), contentDescription = item.route)
                        }
                        if (isSelected) {
                            Text(
                                text = stringResource(
                                    id =
                                    when (item.route) {
                                        "menu_screen" -> R.string.menu
                                        "orders_screen" -> R.string.orders
                                        else -> R.string.orders
                                    }
                                ),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            )
        }
    }
}