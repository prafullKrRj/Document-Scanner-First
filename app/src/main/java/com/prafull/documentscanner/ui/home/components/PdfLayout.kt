package com.prafull.documentscanner.ui.home.components

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.prafull.documentscanner.R
import com.prafull.documentscanner.Utils.getFileUri
import com.prafull.documentscanner.ui.PdfViewModel
import com.prafull.documentscanner.data.models.PdfEntity

@Composable
fun PdfLayout(pdfEntity: PdfEntity, viewModel: PdfViewModel) {
    val context = LocalContext.current
    val activity = LocalContext.current as Activity
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 12.dp, vertical = 8.dp)
        .clickable {
            val getFileUri = getFileUri(context, pdfEntity.name)
            val browserIntent = Intent(
                    Intent.ACTION_VIEW, getFileUri
            )
            browserIntent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            activity.startActivity(browserIntent)
        }
    ) {
        Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Icon(painter = painterResource(id = R.drawable.pdf), contentDescription = null)
            Column {
                Text(pdfEntity.name)
                Text(pdfEntity.size)
                Text(pdfEntity.lastModified.toString())
            }
            IconButton(onClick = {
                viewModel.showRenameDialog = true
                viewModel.currPdfEntity = pdfEntity
            }) {
                Icon(imageVector = Icons.Default.MoreVert, contentDescription = null)
            }
        }
    }
}