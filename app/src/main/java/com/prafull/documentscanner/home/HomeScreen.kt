package com.prafull.documentscanner.home

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.IntentSender
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.mlkit.vision.documentscanner.GmsDocumentScannerOptions
import com.google.mlkit.vision.documentscanner.GmsDocumentScanning
import com.google.mlkit.vision.documentscanner.GmsDocumentScanningResult
import com.prafull.documentscanner.PdfViewModel
import com.prafull.documentscanner.R
import com.prafull.documentscanner.home.components.PdfLayout
import com.prafull.documentscanner.home.components.RenameDeleteDialog
import com.prafull.documentscanner.models.PdfEntity
import com.prafull.documentscanner.showToasts
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: PdfViewModel) {

    RenameDeleteDialog(viewModel = viewModel)
    val activity = LocalContext.current as Activity
    val context = LocalContext.current
    val pdfList = remember {
        mutableListOf<PdfEntity>()
    }

    val scannerLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.StartIntentSenderForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val scannerData = GmsDocumentScanningResult.fromActivityResultIntent(result.data)
            scannerData?.pdf?.let { pdf ->
                Log.d("PDF", pdf.uri.lastPathSegment.toString())
                val date = Date()
                val fileName = SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.getDefault()).format(date) + ".pdf"
                val pdfEntity = PdfEntity(UUID.randomUUID().toString(), fileName, "10KB", date)
                pdfList.add(pdfEntity)
            }
        }
    }
    val scanner = remember {
        GmsDocumentScanning.getClient(
                GmsDocumentScannerOptions.Builder()
                    .setGalleryImportAllowed(true)
                    .setResultFormats(GmsDocumentScannerOptions.RESULT_FORMAT_PDF)
                    .setScannerMode(GmsDocumentScannerOptions.SCANNER_MODE_FULL)
                    .build()
        )
    }
    Scaffold (
            topBar = {
                CenterAlignedTopAppBar(title = {
                    Text(text = stringResource(id = R.string.app_name))
                })
            },
            floatingActionButton =  {
                ExtendedFloatingActionButton(onClick = {
                    scanner.getStartScanIntent(activity).addOnSuccessListener { it: IntentSender? ->
                        it?.let { it1 -> IntentSenderRequest.Builder(it1).build() }?.let { it2 ->
                            scannerLauncher.launch (
                                    it2
                            )
                        }
                    }
                        .addOnFailureListener {
                            it.printStackTrace()
                            context.showToasts(it.localizedMessage?:"")
                        }
                }) {
                    Text(text = stringResource(R.string.scan))
                    Icon(painter = painterResource(id = R.drawable.baseline_camera_alt_24), contentDescription = "Scan Document")
                }
            }
    ){  paddingValues ->
        if (pdfList.isEmpty()) {
            Text(text = "No pdf", modifier = Modifier.padding(paddingValues))
        } else {
            LazyColumn(
                    contentPadding = paddingValues,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(pdfList) {
                    PdfLayout(viewModel = viewModel, pdfEntity = it)
                }
            }
        }
    }
}