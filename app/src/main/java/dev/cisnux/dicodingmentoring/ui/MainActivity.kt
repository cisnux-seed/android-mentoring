package dev.cisnux.dicodingmentoring.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.AndroidEntryPoint
import dev.cisnux.dicodingmentoring.ui.chatting.ChattingScreen
import dev.cisnux.dicodingmentoring.ui.findmentor.FindMentorScreen
import dev.cisnux.dicodingmentoring.ui.splash.SplashViewModel
import dev.cisnux.dicodingmentoring.ui.theme.DicodingMentoringTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val splashViewModel: SplashViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        splashScreen.setKeepOnScreenCondition{ splashViewModel.isLoading.value}

        setContent {
            DicodingMentoringTheme {
//                DicodingMentoringApp()
                FindMentorScreen()
            }
        }
    }
}