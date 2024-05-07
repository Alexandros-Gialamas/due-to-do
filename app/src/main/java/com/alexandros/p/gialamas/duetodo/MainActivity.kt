package com.alexandros.p.gialamas.duetodo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.alexandros.p.gialamas.duetodo.navigation.NavigationComposable
import com.alexandros.p.gialamas.duetodo.ui.theme.DueToDoTheme
import com.alexandros.p.gialamas.duetodo.ui.viewmodels.TaskViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var navController : NavHostController
    private val taskViewModel : TaskViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {

            DueToDoTheme {

                navController = rememberNavController()

                NavigationComposable(
                    context = applicationContext, // TODO { context }
                    navController = navController,
                    taskViewModel = taskViewModel
                    )

            }
        }
    }
}

