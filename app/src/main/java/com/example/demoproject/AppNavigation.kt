package com.example.demoproject

import android.net.Uri
import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Composable
fun AppNavigation() {
    //In navigation impl we just need navController for controlling and NavHost.
    //nav_graph used in XML approach here we just define like below.

    val navController = rememberNavController()

    //Here instead of navGraph we set startDestination parameter.
    NavHost(navController = navController, startDestination = Screen.MainScreen.route) {
        composable(route = Screen.MainScreen.route) {
            MainScreen(navController)
        }

        composable(
            //Like if you want to pass any params, like how browser URLs we pass same manner do here. ? means optional and /pramater is mandatary if you don't send may throw error.
            // Here passing parameter too we are sending / and here too adding /.
            // Both are different, here it is structural bluePrint, Here it tells navController to take detailedData after /..Just Info. While passing parameter needs to send / mandatory.
            // As mentioned this is clear bluePrint for NavController, While passing optional parameters after = if you set space maintain same while sending else will lead to crash.
            route = Screen.DetailedScreen.route + "/{detailedData}" + "?optionalData={optionalData}", // Like URLs in case of optionals how we pass ?name = "value"
            arguments = listOf(
                navArgument(name = "detailedData") {
                    type = NavType.StringType
                    defaultValue = "Hareesh"
                    nullable = false //That means value never be null
                },
                navArgument("optionalData") {
                    type = NavType.IntType
                    defaultValue = 100
                }
            )
        ) { navBackStackEntry ->
            navBackStackEntry.arguments?.getString("detailedData")?.let {
                DetailedScreen(
                    navController,
                    it + " " + navBackStackEntry.arguments?.getInt("optionalData")
                )
            }
        }

        //Write new nav, from DetailedScreen to see how we can pass custom classes
        composable(
            route = Screen.CustomDataScreen.route + "/{customClassData}",
            arguments = listOf(
                navArgument("customClassData") {
                    type = MyCustomDataType
                }
            ),
        ) { navBackStackEntry ->
            val arguments = navBackStackEntry.arguments
            val customData = arguments?.let { bundle -> //Fetch data using bundle concept.
                MyCustomDataType.get(bundle, "customClassData")
            }
            if (customData != null) {
                CustomDataScreen(customData)
            }
        }

        composable(route = Screen.ImageViewScreen.route) {
            ImageViewScreen()
        }

        composable(route = Screen.AnnotatedStringScreen.route) {
            BuildAnnotatedStringScreen()
        }

        composable(route = Screen.BrowseImageViewScreen.route) {
            BrowseImageViewScreen()
        }

        composable(route = Screen.DownloadImageViewScreen.route) {
            DownloadImageViewScreen()
        }

        composable(route = Screen.ListsScreen.route) {
            LazyListsViewScreen()
        }

        composable(route = Screen.ConstraintLayoutScreen.route) {
            ConstraintLayoutScreen()
        }
    }
}

val MyCustomDataType = object : NavType<MyCustomData>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): MyCustomData? {
        return bundle.getString(key)?.let { Json.decodeFromString(it) }
    }

    override fun parseValue(value: String): MyCustomData {
        return Json.decodeFromString(Uri.decode(value))
    }

    override fun put(bundle: Bundle, key: String, value: MyCustomData) {
        bundle.putString(key, Json.encodeToString(value))
    }

    override fun serializeAsValue(value: MyCustomData): String {
        return Uri.encode(Json.encodeToString(value))
    }
}

@Serializable
data class MyCustomData(
    val id: String,
    val title: String
)

sealed class Screen(val route: String) {
    object MainScreen : Screen("main_screen")
    object DetailedScreen : Screen("detailed_screen")
    object CustomDataScreen : Screen("custom_screen")
    object ImageViewScreen : Screen("image_screen")
    object BrowseImageViewScreen : Screen("browse_image_screen")
    object DownloadImageViewScreen : Screen("download_image_screen")
    object AnnotatedStringScreen : Screen("custom_text_screen")
    object ListsScreen : Screen("lists_screen")

    object ConstraintLayoutScreen : Screen("constraint_layout_screen")
}

