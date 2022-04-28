package com.example.fyta_test_assignment.fundation

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import java.io.*

object Utility {
    fun uriToBitmap(contentResolver: ContentResolver,selectedFileUri: Uri): Bitmap? {
        try {
            val parcelFileDescriptor = contentResolver.openFileDescriptor(selectedFileUri, "r")
            val fileDescriptor: FileDescriptor = parcelFileDescriptor!!.fileDescriptor
            val image = BitmapFactory.decodeFileDescriptor(fileDescriptor)
            parcelFileDescriptor.close()
            return image
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }
    fun fileToBitmap(file: File): Bitmap? {
        val options = BitmapFactory.Options()
        options.inSampleSize = 8
        val bitmapPicture = BitmapFactory.decodeFile(file.absolutePath)
        return bitmapPicture
    }
    fun bitmapToFile(cacheDir : File,bitmap: Bitmap) : File{
        val file = File(cacheDir, "temp.png")
        file.createNewFile()
        val bos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos) // YOU can also save it in JPEG
        val bitmapdata = bos.toByteArray()
        val fos = FileOutputStream(file)
        fos.write(bitmapdata)
        fos.flush()
        fos.close()
        Log.e("file",file.name)
        return file;
    }
}