package com.prafull.documentscanner.Utils

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

fun Context.showToasts(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun copyPdfFileToAppDirectory(context: Context, pdfUri: Uri, destinationFileName: String) {
    val inputStream: InputStream? = context.contentResolver.openInputStream(pdfUri)
    /*  val outputStream: OutputStream = context.openFileOutput("${destinationFileName}.pdf", Context.MODE_PRIVATE)
      inputStream?.use { input ->
          outputStream.use { output ->
              input.copyTo(output)
          }
      }*/
    val outputFile = File(context.filesDir, destinationFileName)
    FileOutputStream(outputFile).use { output ->
        inputStream?.copyTo(output)
    }
}

fun getFileUri(context: Context, fileName: String): Uri {
    val file = File(context.filesDir, fileName)
    return FileProvider
        .getUriForFile(
                context,
                "${context.packageName}.provider",
                file
        )
// context.packageName + ".provider"
}
fun deleteFile(context: Context, fileName: String) : Boolean {
    return File(context.filesDir, fileName).deleteRecursively()
}
fun renameFile(context: Context, oldFileName: String, newFileName: String) : Boolean {
    val oldFile = File(context.filesDir, oldFileName)
    val newFile = File(context.filesDir, newFileName)
    return oldFile.renameTo(newFile)
}
fun getFileSize(context: Context, fileName: String) : String {
    val size =  File(context.filesDir, fileName).length()
    val sizeInKB = size / 1024
    if (sizeInKB < 1024) {
        return "$sizeInKB KB"
    }
    val sizeInMB = sizeInKB / 1024
    return "$sizeInMB MB"
}