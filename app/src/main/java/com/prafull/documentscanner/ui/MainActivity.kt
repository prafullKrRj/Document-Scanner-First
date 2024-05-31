package com.prafull.documentscanner.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.prafull.documentscanner.home.HomeScreen
import com.prafull.documentscanner.PdfViewModel
import com.prafull.documentscanner.ui.theme.DocumentScannerTheme

class MainActivity : ComponentActivity() {
    private val pdfViewModel by viewModels<PdfViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashScreen = installSplashScreen()
        enableEdgeToEdge()
        setContent {
            splashScreen.setKeepOnScreenCondition { pdfViewModel.isSplashScreenVisible }
            DocumentScannerTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    HomeScreen(viewModel = pdfViewModel)
                }
            }
        }
    }
}