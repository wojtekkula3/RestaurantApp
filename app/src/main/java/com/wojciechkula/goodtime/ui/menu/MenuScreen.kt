package com.wojciechkula.goodtime.ui.menu

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.wojciechkula.goodtime.R
import com.wojciechkula.goodtime.ui.shared.SharedViewModel

@Composable
fun Menu(sharedViewModel: SharedViewModel = viewModel(), navController: NavController) {

    Scaffold(
        topBar = {
            TopAppBar {
                val numberOfItem = 2
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(end = 20.dp),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    if (numberOfItem > 0) {
                        BadgedBox(
                            modifier = Modifier.scale(1.2f),
                            badge = {
                                Badge {
                                    Text(text = numberOfItem.toString())
                                }
                            }) {
                            Icon(
                                painter = painterResource(R.drawable.ic_shopping_cart),
                                contentDescription = "Shopping cart",
                                tint = Color.White
                            )
                        }
                    } else {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_shopping_cart),
                            contentDescription = "Shopping cart"
                        )
                    }
                }
            }
        }
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(text = sharedViewModel.viewState.value.toString())
        }
    }
}