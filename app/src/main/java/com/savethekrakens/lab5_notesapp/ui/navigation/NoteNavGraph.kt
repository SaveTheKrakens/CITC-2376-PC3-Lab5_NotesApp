package com.savethekrakens.lab5_notesapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.savethekrakens.lab5_notesapp.ui.home.HomeDestination
import com.savethekrakens.lab5_notesapp.ui.home.HomeScreen
import com.savethekrakens.lab5_notesapp.ui.item.NoteDetailsDestination
import com.savethekrakens.lab5_notesapp.ui.item.NoteDetailsScreen
import com.savethekrakens.lab5_notesapp.ui.item.NoteEditDestination
import com.savethekrakens.lab5_notesapp.ui.item.NoteEditScreen
import com.savethekrakens.lab5_notesapp.ui.item.NoteEntryDestination
import com.savethekrakens.lab5_notesapp.ui.item.NoteEntryScreen

@Composable
fun NoteNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ) {
        composable(route = HomeDestination.route) {
            HomeScreen(
                navigateToNoteEntry = { navController.navigate(NoteEntryDestination.route) },
                navigateToNoteUpdate = {
                    navController.navigate("${NoteDetailsDestination.route}/${it}")
                })
        }
        composable(route = NoteEntryDestination.route) {
            NoteEntryScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }
        composable(
            route = NoteDetailsDestination.routeWithArgs,
            arguments = listOf(navArgument(NoteDetailsDestination.noteIdArg) {
                type = NavType.IntType
            })
        ) {
            NoteDetailsScreen(
                navigateToEditNote = { navController.navigate("${NoteEditDestination.route}/$it") },
                navigateBack = { navController.navigateUp() }
            )
        }
        composable(
            route = NoteEditDestination.routeWithArgs,
            arguments = listOf(navArgument(NoteEditDestination.noteIdArg) {
                type = NavType.IntType
            })
        ) {
            NoteEditScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }
    }
}