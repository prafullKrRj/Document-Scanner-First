package com.prafull.documentscanner

import android.content.Context
import android.widget.Toast

fun Context.showToasts (message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}