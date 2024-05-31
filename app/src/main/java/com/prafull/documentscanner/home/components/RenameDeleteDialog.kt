package com.prafull.documentscanner.home.components

import android.app.Dialog
import android.widget.Space
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
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
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.prafull.documentscanner.PdfViewModel

@Composable
fun RenameDeleteDialog(viewModel: PdfViewModel) {

    if (viewModel.showRenameDialog.not()) return
    var newPdfName by remember {
        mutableStateOf("")
    }
    Dialog(onDismissRequest = {
        viewModel.showRenameDialog  = false
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
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                            IconButton(onClick = { /*TODO*/ }) {
                                Icon(imageVector = Icons.Default.Delete, contentDescription = null)
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Button(onClick = { /*TODO*/ }) {
                                Text(text = "Cancel")
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Button(onClick = { /*TODO*/ }) {
                                Text(text = "Update")
                            }
                        }
                    }
            )

        }
    }
}