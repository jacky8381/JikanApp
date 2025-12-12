package com.example.jikanapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.jikanapp.presentation.AnimeDetailScreen
import com.example.jikanapp.presentation.AnimeListScreen
import com.example.jikanapp.presentation.viewmodels.AnimeDetailViewmodel
import com.example.jikanapp.presentation.viewmodels.AnimeViewModel
import com.example.jikanapp.ui.theme.DeepCharcoal
import com.example.jikanapp.ui.theme.JikanAppTheme
import com.example.jikanapp.ui.theme.PureWhite
import com.example.jikanapp.utils.Screen
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JikanAppTheme {
                val navController = rememberNavController()
                val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
                Scaffold(
                    topBar = {
                        if (currentRoute == Screen.Home.route) {
                            AnimeTopBar()
                        }
                    },
                    containerColor = Color(0xFF0F172A)
                ) { padding ->
                    NavGraph(
                        navController = navController,
                        modifier = Modifier.padding(padding),
                        onDismiss = {
                            finish()
                        }
                    )
                }
            }
        }
    }
}


@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    onDismiss : () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        composable(Screen.Home.route) {
            val viewModel: AnimeViewModel = hiltViewModel()
            AnimeListScreen(
                viewModel = viewModel,
                onAnimeClick = { id ->
                    navController.navigate(Screen.Detail.route + "/${id}")
                },
                {
                    onDismiss()
                }
            )
        }

        composable(Screen.Detail.route+ "/{animeId}",
            arguments = listOf(
                navArgument("animeId"){type= NavType.IntType}
            )) {
            val viewModel: AnimeDetailViewmodel = hiltViewModel()
            AnimeDetailScreen(
                detailViewmodel = viewModel,
                navController
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimeTopBar() {
    TopAppBar(
        title = {
            Text(
                text = "Top Anime",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = PureWhite
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = DeepCharcoal
        )
    )
}

