package com.wojciechkula.goodtime.ui.menu

import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.wojciechkula.goodtime.BottomNavigationView
import com.wojciechkula.goodtime.R
import com.wojciechkula.goodtime.domain.model.ProductModel
import com.wojciechkula.goodtime.theme.Error
import com.wojciechkula.goodtime.theme.Teal100
import com.wojciechkula.goodtime.ui.common.bottomNavItems.BottomNavItem
import com.wojciechkula.goodtime.ui.common.networkConnection.ConnectionState
import com.wojciechkula.goodtime.ui.common.networkConnection.connectivityState
import com.wojciechkula.goodtime.ui.shared.SharedViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class, ExperimentalAnimationApi::class)
@ExperimentalCoroutinesApi
@Composable
fun Menu(sharedViewModel: SharedViewModel = viewModel(), navController: NavController) {

    val backStackEntry = navController.currentBackStackEntryAsState()
    val connection by connectivityState()
    val isConnected = connection === ConnectionState.Available

    val selectedOptionFromBottomSheet = remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()
    val modalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = {
            if (it == ModalBottomSheetValue.Hidden) {
                selectedOptionFromBottomSheet.value = ""
            }
            true
        })
    val bottomSheetProduct = remember { mutableStateOf(ProductModel()) }
    val numberOfItemsInShoppingCart = remember { mutableStateOf(0) }

    ModalBottomSheetLayout(
        sheetContent = {
            BottomSheetContent(
                modalBottomSheetState,
                scope,
                product = bottomSheetProduct.value,
                selectedOptionFromBottomSheet,
                numberOfItemsInShoppingCart
            )
        },
        sheetState = modalBottomSheetState,
        sheetShape = RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp),
    ) {
        Scaffold(
            topBar = {
                TopAppBar {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(end = 26.dp),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        if (numberOfItemsInShoppingCart.value > 0) {
                            BadgedBox(
                                modifier = Modifier.scale(1.2f),
                                badge = {
                                    AnimatedContent(
                                        targetState = numberOfItemsInShoppingCart.value,
                                        transitionSpec = {
                                            if (targetState > initialState) {
                                                slideInVertically { height -> height } + fadeIn() with
                                                        slideOutVertically { height -> -height } + fadeOut()
                                            } else {
                                                slideInVertically { height -> -height } + fadeIn() with
                                                        slideOutVertically { height -> height } + fadeOut()
                                            }.using(
                                                SizeTransform(clip = false)
                                            )
                                        }
                                    ) { targetCount ->
                                        Badge {
                                            Text(text = targetCount.toString())
                                        }
                                    }
                                }) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_shopping_cart),
                                    contentDescription = stringResource(R.string.shopping_cart),
                                    tint = Color.White
                                )
                            }
                        } else {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_shopping_cart),
                                contentDescription = stringResource(R.string.shopping_cart)
                            )
                        }
                    }
                }
            },
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
            content = { padding ->
                LazyColumn(
                    modifier = Modifier
                        .padding(padding)
                        .background(colorResource(id = R.color.lightGray)),
                    horizontalAlignment = Alignment.CenterHorizontally
                )
                {
                    item {
                        AnimatedVisibility(visible = !isConnected) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Error)
                                    .padding(3.dp),
                                contentAlignment = Alignment.TopCenter
                            ) {
                                Text(
                                    text = stringResource(R.string.common_no_internet_connection),
                                    textAlign = TextAlign.Center,
                                    color = Color.White
                                )
                            }
                        }
                    }

                    item {
                        Text(
                            modifier = Modifier
                                .padding(top = 6.dp),
                            text = "PIZZA",
                            style = MaterialTheme.typography.h3,
                            color = MaterialTheme.colors.primary
                        )
                    }
                    itemsIndexed(items = sharedViewModel.viewState.value.filter { it.type == "pizza" }
                        .sortedBy { it.number }) { index, item ->
                        ProductItem(product = item, onItemClick = {
                            scope.launch {
                                bottomSheetProduct.value = item
                                modalBottomSheetState.animateTo(ModalBottomSheetValue.Expanded)
                            }
                        }, onAddToCardButton = {
                            scope.launch {
                                bottomSheetProduct.value = item
                                modalBottomSheetState.animateTo(ModalBottomSheetValue.Expanded)
                            }
                        })
                    }

                    item {
                        Text(
                            modifier = Modifier
                                .padding(top = 16.dp),
                            text = stringResource(R.string.SAUCE),
                            style = MaterialTheme.typography.h3,
                            color = MaterialTheme.colors.primary
                        )
                    }
                    itemsIndexed(items = sharedViewModel.viewState.value.filter { it.type == "sos" }
                        .sortedBy { it.number }) { index, item ->
                        ProductItem(product = item, onItemClick = {
                            scope.launch {
                                bottomSheetProduct.value = item
                                modalBottomSheetState.animateTo(ModalBottomSheetValue.Expanded)
                            }
                        }, onAddToCardButton = { numberOfItemsInShoppingCart.value++ })
                    }


                    item {
                        Text(
                            modifier = Modifier
                                .padding(top = 16.dp),
                            text = stringResource(R.string.DINNER),
                            style = MaterialTheme.typography.h3,
                            color = MaterialTheme.colors.primary
                        )
                    }
                    itemsIndexed(items = sharedViewModel.viewState.value.filter { it.type == "zestaw obiadowy" }
                        .sortedBy { it.number }) { index, item ->
                        ProductItem(product = item, onItemClick = {
                            scope.launch {
                                bottomSheetProduct.value = item
                                modalBottomSheetState.animateTo(ModalBottomSheetValue.Expanded)
                            }
                        }, onAddToCardButton = { numberOfItemsInShoppingCart.value++ })
                    }


//                    item {
//                        Text(
//                            modifier = Modifier
//                                .padding(top = 16.dp),
//                            text = "SAŁATKI",
//                            style = MaterialTheme.typography.h3,
//                            color = MaterialTheme.colors.primary
//                        )
//                    }
//                    itemsIndexed(items = sharedViewModel.viewState.value.filter { it.type == "sałatka" }
//                        .sortedBy { it.number }) { index, item ->
//                        ProductItem(product = item, onItemClick = {
//                            scope.launch {
//                                bottomSheetProduct.value = item
//                                modalBottomSheetState.animateTo(ModalBottomSheetValue.Expanded)
//                            }
//                        }, onAddToCardButton = { numberOfItemsInShoppingCart.value++ })
//                    }
//
//
//                    itemsIndexed(items = sharedViewModel.viewState.value.filter { it.type == "fast food" }
//                        .sortedBy { it.number }) { index, item ->
//                        ProductItem(
//                            product = item,
//                            onItemClick = {},
//                            onAddToCardButton = { numberOfItemsInShoppingCart.value++ })
//                    }
//
//
//                    itemsIndexed(items = sharedViewModel.viewState.value.filter { it.type == "dodatki" }
//                        .sortedBy { it.number }) { index, item ->
//                        ProductItem(
//                            product = item,
//                            onItemClick = {},
//                            onAddToCardButton = { numberOfItemsInShoppingCart.value++ })
//                    }
                }
            })
    }
}

@Composable
fun ProductItem(product: ProductModel, onItemClick: () -> Unit, onAddToCardButton: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .requiredHeight(130.dp)
            .padding(7.dp)
            .clickable { onItemClick() },
        shape = MaterialTheme.shapes.large,
        elevation = 4.dp
    ) {
        Row(
            modifier = Modifier.fillMaxSize()
        ) {
            Card(
                modifier = Modifier
                    .weight(1f),
                shape = MaterialTheme.shapes.small,
                elevation = 2.dp
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_splash_screen),
                    contentDescription = stringResource(R.string.menu_add_to_card, product.name),
                )
            }
            Box(
                modifier = Modifier
                    .weight(2f)
                    .fillMaxHeight()
                    .padding(6.dp)
            ) {
                Column(modifier = Modifier.align(Alignment.TopStart)) {
                    Text(
                        text = "${product.number}. ${product.name}",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.h4,
                    )
                    Text(
                        text = product.ingredients, fontStyle = FontStyle.Italic
                    )
                }

                Button(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .requiredWidth(126.dp),
                    onClick = {
                        onAddToCardButton()
                    },
                    shape = RoundedCornerShape(8.dp)
                ) {
                    if (product.type == "pizza") {
                        Text(
                            text = stringResource(
                                id = R.string.menu_from_price,
                                String.format("%.2f", product.price[0])
                            )
                        )
                    } else {
                        Text(text = "${String.format("%.2f", product.price[0])} zł")

                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheetContent(
    modalBottomSheetState: ModalBottomSheetState,
    scope: CoroutineScope,
    product: ProductModel,
    selectedOption: MutableState<String>,
    numberOfItemsInShoppingCart: MutableState<Int>
) {

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            modifier = Modifier.padding(top = 10.dp, bottom = 2.dp),
            text = product.name,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.h4
        )

        when (product.type) {
            "pizza" -> {
                val radioOptions = listOf(
                    stringResource(id = R.string.menu_small_size, String.format("%.2f", product.price[0])),
                    stringResource(id = R.string.menu_medium_size, String.format("%.2f", product.price[1])),
                    stringResource(id = R.string.menu_big_size, String.format("%.2f", product.price[2])),
                    stringResource(id = R.string.menu_mega_size, String.format("%.2f", product.price[3]))
                )
                radioOptions.forEach { option ->
                    OutlinedButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 12.dp, end = 12.dp, top = 6.dp),
                        onClick = {
                            if (selectedOption.value == option) {
                                selectedOption.value = ""
                            } else {
                                selectedOption.value = option
                            }
                        },
                        shape = CircleShape,
                        contentPadding = PaddingValues(20.dp, 12.dp),
                        border =
                        if (selectedOption.value == option)
                            BorderStroke(2.dp, MaterialTheme.colors.primary)
                        else BorderStroke(2.dp, Color.LightGray),
                        colors =
                        if (selectedOption.value == option)
                            ButtonDefaults.buttonColors(backgroundColor = Teal100, contentColor = Color.Black)
                        else
                            ButtonDefaults.buttonColors(backgroundColor = Color.White, contentColor = Color.Black),

                        ) {
                        Text(
                            text = option,
                            style = MaterialTheme.typography.body1,
                            fontWeight =
                            if (selectedOption.value == option)
                                FontWeight.Bold
                            else
                                FontWeight.Normal
                        )
                    }
                }
                Button(
                    modifier = Modifier.padding(top = 8.dp, bottom = 64.dp),
                    onClick = {
                        numberOfItemsInShoppingCart.value++
                        scope.launch {
                            selectedOption.value = ""
                            modalBottomSheetState.hide()
                        }
                    },
                    enabled = selectedOption.value != "",
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(22.dp, 6.dp),
                    colors =
                    if (selectedOption.value == "")
                        ButtonDefaults.buttonColors(backgroundColor = Color.DarkGray)
                    else
                        ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
                ) {
                    Text(text = stringResource(R.string.menu_add_to_card))
                }
            }
            else -> {
                Button(
                    modifier = Modifier.padding(top = 8.dp, bottom = 64.dp),
                    onClick = {
                        numberOfItemsInShoppingCart.value++
                        scope.launch {
                            selectedOption.value = ""
                            modalBottomSheetState.hide()
                        }
                    },
                    contentPadding = PaddingValues(22.dp, 6.dp),
                    enabled = true,
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
                ) {
                    Text(text = stringResource(R.string.menu_add_to_card))
                }
            }
        }
    }
}


