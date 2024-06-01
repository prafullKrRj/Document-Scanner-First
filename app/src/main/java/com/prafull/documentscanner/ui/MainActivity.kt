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
import androidx.lifecycle.viewmodel.viewModelFactory
import com.google.mlkit.vision.documentscanner.GmsDocumentScanningResult.Pdf
import com.prafull.documentscanner.DocumentScannerApplication
import com.prafull.documentscanner.ui.home.HomeScreen
import com.prafull.documentscanner.ui.theme.DocumentScannerTheme

class MainActivity : ComponentActivity() {
    private val pdfViewModel by viewModels<PdfViewModel> {
        viewModelFactory {
            addInitializer(PdfViewModel::class) {
                PdfViewModel((application as DocumentScannerApplication))
            }
        }
    }
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