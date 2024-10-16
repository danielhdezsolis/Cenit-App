package com.example.cenit.ui.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.cenit.R
import com.example.cenit.ui.account.AccountScreen
import com.example.cenit.ui.certificates.CertificatesScreen
import com.example.cenit.ui.devices.DevicesScreen
import com.example.cenit.ui.login.LoginScreen
import com.example.cenit.ui.notifications.NotificationScreen
import com.example.cenit.ui.onboarding.OnBoardingScreen
import com.example.cenit.ui.runningproofs.RunningProofsScreen
import com.example.cenit.ui.theme.CenitTheme
import com.example.cenit.ui.theme.PrimaryColor
import com.example.cenit.ui.theme.whiteColor

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CenitTheme {
                MainScreen()
            }
        }
    }

    @Composable
    fun MainScreen() {
        val navController = rememberNavController()
        val currentBackStackEntry by navController.currentBackStackEntryAsState()
        val isOnboardingScreen = currentBackStackEntry?.destination?.route == "onBoarding"
        val isOnLoginScreen = currentBackStackEntry?.destination?.route == "login"
        Scaffold(
            topBar = {
                if (!isOnboardingScreen && !isOnLoginScreen) {
                    CustomToolbar(navController)
                }
            },
            floatingActionButton = {
                if (!isOnboardingScreen && !isOnLoginScreen) {
                    CustomFab(navController)
                }
            },
            floatingActionButtonPosition = FabPosition.Center,
            bottomBar = {
                if (!isOnboardingScreen && !isOnLoginScreen) {
                    CustomBottomNavigation(navController)
                }
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = "login",
                modifier = Modifier.padding(innerPadding)
            ) {
                composable("login") { LoginScreen() }
                composable("runningProofs") { RunningProofsScreen() }
                composable("onBoarding") { OnBoardingScreen() }
                composable("certificates") { CertificatesScreen() }
                composable("devices") { DevicesScreen() }
                composable("notifications") { NotificationScreen() }
                composable("account") { AccountScreen() }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun CustomToolbar(navController: NavHostController) {
        val currentBackStackEntry by navController.currentBackStackEntryAsState()
        val toolbarTitle = getToolbarTitle(currentBackStackEntry)

        val customFontFamily = FontFamily(
            Font(R.font.spacegrotesk_medium)
        )

        TopAppBar(
            title = {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Text(
                        text = toolbarTitle,
                        fontFamily = customFontFamily,
                        fontSize = 22.sp
                    )
                }
            },
        )
    }


    @Composable
    fun CustomFab(navController: NavHostController) {
        FloatingActionButton(
            onClick = {
                navController.navigate("onBoarding")
            }, containerColor = colorResource(R.color.primary),
            shape = CircleShape,
            modifier = Modifier
                .size(40.dp)
                .offset(y = 30.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "FAB", tint = colorResource(R.color.background))
        }
    }

    @Composable
    fun CustomBottomNavigation(navController: NavHostController) {
        val selectedIconColor = colorResource(R.color.text)
        val unselectedIconColor = colorResource(R.color.secondary)
        val selectedIndicatorColor = colorResource(R.color.transparentColor)
        val backgroundColor = colorResource(R.color.tab)
        NavigationBar(
            containerColor = backgroundColor,
        ) {
            NavigationBarItem(
                icon = {
                    Icon(
                        painterResource(R.drawable.thermometer),
                        contentDescription = "Running Proofs"
                    )
                },
                selected = navController.currentDestination?.route == "runningProofs",
                onClick = { navController.navigate("runningProofs") },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = selectedIconColor,
                    unselectedIconColor = unselectedIconColor,
                    indicatorColor = selectedIndicatorColor
                )
            )
            NavigationBarItem(
                icon = {
                    Icon(
                        painterResource(R.drawable.draft),
                        contentDescription = "Certificates"
                    )
                },
                selected = navController.currentDestination?.route == "certificates",
                onClick = { navController.navigate("certificates") },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = selectedIconColor,
                    unselectedIconColor = unselectedIconColor,
                    indicatorColor = selectedIndicatorColor
                )
            )
            NavigationBarItem(
                icon = {
                    Icon(
                        painterResource(R.drawable.deployed_code),
                        contentDescription = "Devices"
                    )
                },
                selected = navController.currentDestination?.route == "devices",
                onClick = { navController.navigate("devices") },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = selectedIconColor,
                    unselectedIconColor = unselectedIconColor,
                    indicatorColor = selectedIndicatorColor
                )
            )
            NavigationBarItem(
                icon = {
                    Icon(
                        painterResource(R.drawable.ic_notifications_black_24dp),
                        contentDescription = "Notifications"
                    )
                },
                selected = navController.currentDestination?.route == "notifications",
                onClick = { navController.navigate("notifications") },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = selectedIconColor,
                    unselectedIconColor = unselectedIconColor,
                    indicatorColor = selectedIndicatorColor
                )
            )
            NavigationBarItem(
                icon = { Icon(painterResource(R.drawable.person), contentDescription = "Account") },
                selected = navController.currentDestination?.route == "account",
                onClick = { navController.navigate("account") },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = selectedIconColor,
                    unselectedIconColor = unselectedIconColor,
                    indicatorColor = selectedIndicatorColor
                )
            )

        }
    }

    @Composable
    fun getToolbarTitle(backStackEntry: NavBackStackEntry?): String {
        return when (backStackEntry?.destination?.route) {
            "runningProofs" -> stringResource(R.string.RunningProofs_Header)
            "onBoarding" -> stringResource(R.string.OnBoarding_Header)
            "certificates" -> stringResource(R.string.Certificates_Header)
            "devices" -> stringResource(R.string.Devices_Header)
            "notifications" -> stringResource(R.string.Notifications_Header)
            "account" -> stringResource(R.string.Account_Header)
            else -> stringResource(R.string.Default_Header)
        }
    }

    @Preview
    @Composable
    fun MainScreenPreview() {
        MainScreen()
    }
}