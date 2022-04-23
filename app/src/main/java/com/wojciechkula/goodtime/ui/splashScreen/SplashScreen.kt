package com.wojciechkula.goodtime.ui.splashScreen

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.wojciechkula.goodtime.R
import com.wojciechkula.goodtime.domain.model.ProductModel
import com.wojciechkula.goodtime.theme.Error
import com.wojciechkula.goodtime.theme.Shapes
import com.wojciechkula.goodtime.ui.common.networkConnection.ConnectionState
import com.wojciechkula.goodtime.ui.common.networkConnection.connectivityState
import com.wojciechkula.goodtime.ui.shared.SharedViewEvent
import com.wojciechkula.goodtime.ui.shared.SharedViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun SplashScreen(sharedViewModel: SharedViewModel = viewModel(), navController: NavController) {

    val scale = remember {
        Animatable(0f)
    }
    val isDialogOpen = remember { mutableStateOf(false) }
    if (isDialogOpen.value) {
        DialogBoxLoading(sharedViewModel = sharedViewModel)
    }

    LaunchedEffect(key1 = true) {
        sharedViewModel.eventsFlow.collect { value ->
            when (value) {
                is SharedViewEvent.ShowPossibleDialog -> onShowPossibleDialog(
                    value.products,
                    navController,
                    isDialogOpen
                )
            }
        }
    }

    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 0.9f,
            animationSpec = tween(
                durationMillis = 700,
                easing = { OvershootInterpolator(2.5f).getInterpolation(it) })
        )
        sharedViewModel.getProducts()
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_splash_screen),
            contentDescription = "Logo",
            modifier = Modifier.scale(scale.value)
        )
    }
}

fun onShowPossibleDialog(
    products: List<ProductModel>,
    navController: NavController,
    isDialogOpen: MutableState<Boolean>
) {
    if (products.isNotEmpty()) {
        navController.navigate("menu_screen") {
            popUpTo("splash_screen") { inclusive = true }
        }
    } else {
        isDialogOpen.value = true
    }
}


@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun DialogBoxLoading(
    paddingStart: Dp = 32.dp,
    paddingEnd: Dp = 32.dp,
    paddingTop: Dp = 40.dp,
    paddingBottom: Dp = 32.dp,
    progressIndicatorColor: Color = MaterialTheme.colors.primary,
    progressIndicatorSize: Dp = 66.dp,
    sharedViewModel: SharedViewModel
) {

    val connection by connectivityState()
    val isConnected = connection === ConnectionState.Available

    if (isConnected) {
        sharedViewModel.getProducts()
    }

    Dialog(
        onDismissRequest = {
        }
    ) {
        Surface(
            elevation = 8.dp,
            shape = Shapes.large
        ) {
            Column(
                modifier = Modifier
                    .padding(start = paddingStart, end = paddingEnd, top = paddingTop),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = CenterHorizontally
            ) {
                if (!isConnected) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_no_internet_connection),
                        contentDescription = "No internet connection",
                        colorFilter = ColorFilter.tint(Error)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        modifier = Modifier
                            .padding(bottom = paddingBottom),
                        textAlign = TextAlign.Center,
                        text = stringResource(R.string.splash_screen_please_check_your_internet_connection),
                        style = MaterialTheme.typography.h3,
                        fontWeight = FontWeight.Bold
                    )
                } else {
                    ProgressIndicatorLoading(
                        progressIndicatorSize = progressIndicatorSize,
                        progressIndicatorColor = progressIndicatorColor
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        modifier = Modifier
                            .padding(bottom = paddingBottom),
                        text = stringResource(R.string.splash_screen_please_wait),
                        style = MaterialTheme.typography.h3,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun ProgressIndicatorLoading(progressIndicatorSize: Dp, progressIndicatorColor: Color) {

    val infiniteTransition = rememberInfiniteTransition()

    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 700
            }
        )
    )

    CircularProgressIndicator(
        progress = 1f,
        modifier = Modifier
            .size(progressIndicatorSize)
            .rotate(angle)
            .border(
                12.dp,
                brush = Brush.sweepGradient(
                    listOf(
                        Color.White,
                        progressIndicatorColor.copy(alpha = 0.1f),
                        progressIndicatorColor
                    )
                ),
                shape = CircleShape
            ),
        strokeWidth = 1.dp,
        color = Color.White
    )
}
