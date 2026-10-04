package UI

import ViweModel.Status
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNav() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "splash",


        enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Up,
                animationSpec = tween(500)
            ) + scaleIn(
                initialScale = 0.3f,
                animationSpec = tween(1000)
            )
        },


        exitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Up,
                animationSpec = tween(500)
            ) + scaleOut(
                targetScale = 0.3f,
                animationSpec = tween(1000)
            )
        },


        popEnterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Up,
                animationSpec = tween(500)
            ) + scaleIn(
                initialScale = 0.3f,
                animationSpec = tween(1000)
            )
        },


        popExitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Up,
                animationSpec = tween(500)
            ) + scaleOut(
                targetScale = 0.3f,
                animationSpec = tween(1000)
            )
        }

    ) {

        composable("splash") {
            SplashScreen(
                onFinished = {
                    navController.navigate("home") {
                        popUpTo("splash") {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable("home") {
            if (Status.isLoggedIn) {
                HomeScreen(navController)
            } else {
                LoginScreen(navController)
            }
        }
        composable("home") {
            HomeScreen(navController)
        }

        composable("login") {
            LoginScreen(navController)
        }

        composable("register") {
            RegisterUser(navController)
        }
    }
}