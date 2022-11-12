package com.example.explorr.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.explorr.ui.detail.DetailScreen
import com.example.explorr.ui.main.FullScreen
import com.example.explorr.ui.main.TopSection

@Composable
fun ExplorrNavHost(
    modifier: Modifier=Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screen.MainScreen.route,
    viewModel: MainViewModel
) {
   NavHost(
       modifier = modifier,
       navController = navController,
       startDestination = startDestination,
        ){

       composable( route = Screen.MainScreen.route
       ){
           FullScreen(viewModel =viewModel , nav =navController )
       }

       composable(    route = Screen.DetailScreen.route)
           {
           DetailScreen(
               viewModel = viewModel, navController = navController
           )
       }

   }
    
}