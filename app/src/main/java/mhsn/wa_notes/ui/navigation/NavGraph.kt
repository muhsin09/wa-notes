package mhsn.wa_notes.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import mhsn.wa_notes.ui.screens.search.SearchScreen
import mhsn.wa_notes.ui.screens.threaddetail.ThreadDetailScreen
import mhsn.wa_notes.ui.screens.threadlist.ThreadListScreen

sealed class Screen(val route: String) {
    object ThreadList : Screen("thread_list")
    object ThreadDetail : Screen("thread_detail/{threadId}") {
        fun createRoute(threadId: Long) = "thread_detail/$threadId"
    }
    object Search : Screen("search")
    object ThreadSearch : Screen("thread_search/{threadId}") {
        fun createRoute(threadId: Long) = "thread_search/$threadId"
    }
}

@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String = Screen.ThreadList.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = Screen.ThreadList.route) {
            ThreadListScreen(
                onThreadClick = { threadId ->
                    navController.navigate(Screen.ThreadDetail.createRoute(threadId))
                },
                onSearchClick = {
                    navController.navigate(Screen.Search.route)
                }
            )
        }

        composable(
            route = Screen.ThreadDetail.route,
            arguments = listOf(
                navArgument("threadId") {
                    type = NavType.LongType
                }
            )
        ) { backStackEntry ->
            val threadId = backStackEntry.arguments?.getLong("threadId") ?: 1L
            ThreadDetailScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                threadId = threadId,
                onSearchClick = {
                    navController.navigate(Screen.ThreadSearch.createRoute(threadId))
                }
            )
        }

        composable(
            route = Screen.ThreadSearch.route,
            arguments = listOf(
                navArgument("threadId") {
                    type = NavType.LongType
                }
            )
        ) { backStackEntry ->
            val threadId = backStackEntry.arguments?.getLong("threadId") ?: 1L
            SearchScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                currentThreadId = threadId,
                onNoteClick = { noteId ->
                    // Navigate to thread detail and scroll to note
                    navController.navigate(Screen.ThreadDetail.createRoute(threadId))
                }
            )
        }

        composable(route = Screen.Search.route) {
            SearchScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNoteClick = { noteId ->
                    // TODO: Navigate to specific note in thread
                }
            )
        }
    }
}

