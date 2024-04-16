package com.dicoding.asclepius.helper

import android.content.Context
import android.net.Uri
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


private val date = SimpleDateFormat("yyyyMMMdd", Locale.getDefault()).format(Date())

fun temptFile(context: Context): File {
    val cacheDir = context.externalCacheDir
    return File.createTempFile(date, ".jpg", cacheDir)
}

fun uriToFile(imageUri: Uri, context: Context): File {
    val myFile = temptFile(context)
    val inputStream = context.contentResolver.openInputStream(imageUri) as InputStream
    val outputStream = FileOutputStream(myFile)
    val buffer = ByteArray(1024)
    var length: Int
    while (inputStream.read(buffer).also { length = it } > 0) outputStream.write(buffer, 0, length)
    outputStream.close()
    inputStream.close()
    return myFile
}