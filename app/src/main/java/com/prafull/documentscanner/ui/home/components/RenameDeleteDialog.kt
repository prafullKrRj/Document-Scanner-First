package com.prafull.documentscanner.ui.home.components

import android.content.ClipData
import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.prafull.documentscanner.Utils.deleteFile
import com.prafull.documentscanner.Utils.getFileUri
import com.prafull.documentscanner.Utils.renameFile
import com.prafull.documentscanner.Utils.showToasts
import com.prafull.documentscanner.ui.PdfViewModel
import java.util.Date

@Composable
fun RenameDeleteDialog(viewModel: PdfViewModel) {

    if (viewModel.showRenameDialog.not()) return

    var newPdfName by remember(viewModel.currPdfEntity) {
        mutableStateOf(viewModel.currPdfEntity?.name ?: "")
    }

    val context = androidx.compose.ui.platform.LocalContext.current
    Dialog(onDismissRequest = {
        viewModel.showRenameDialog = false
    }) {
        Surface {
            Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    content = {
                        Text(text = "Rename", style = MaterialTheme.typography.headlineSmall)
                        Spacer(modifier = Modifier.padding(8.dp))
                        OutlinedTextField(
                                value = newPdfName,
                                onValueChange = {
                                    newPdfName = it
                                },
                                label = { Text(text = "Enter new name") },
                        )
                        Spacer(modifier = Modifier.padding(8.dp))
                        Row(
                                Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            IconButton(onClick = {
                                viewModel.currPdfEntity?.let {
                                    viewModel.showRenameDialog = false
                                    if (deleteFile(context, it.name)) {
                                        viewModel.deletePdf(it)
                                    } else{
                                        context.showToasts("File not deleted")
                                    }
                                }
                            }) {
                                Icon(imageVector = Icons.Default.Delete, contentDescription = null)
                            }
                            IconButton(onClick = {
                                viewModel.currPdfEntity?.let {
                                    val getFileUri = getFileUri(context, it.name)
                                    val shareIntent = Intent(
                                            Intent.ACTION_SEND, getFileUri
                                    )
                                    shareIntent.clipData = ClipData.newRawUri("", getFileUri)
                                    shareIntent.type = "application/pdf"
                                    shareIntent.putExtra(Intent.EXTRA_STREAM, getFileUri)
                                    shareIntent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                                    context.startActivity(Intent.createChooser(shareIntent, "Share PDF"))
                                }
                            }) {
                                Icon(imageVector = Icons.Default.Share, contentDescription = null)
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Button(onClick = { /*TODO*/ }) {
                                Text(text = "Cancel")
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Button(onClick = {
                                viewModel.currPdfEntity?.let {
                                    if (!it.name.equals(newPdfName, true)) {
                                        renameFile(context, it.name, newPdfName)
                                        viewModel.updatePdf(it.copy(name = newPdfName, lastModified = Date()))
                                    } else {
                                        viewModel.showRenameDialog = false
                                    }
                                }
                            }) {
                                Text(text = "Update")
                            }
                        }
                    }
            )

        }
    }
}