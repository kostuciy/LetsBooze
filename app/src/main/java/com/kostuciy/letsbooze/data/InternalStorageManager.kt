package com.kostuciy.letsbooze.data

import android.app.Application
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

private const val IMAGE_DIRECTORY = "image_dir"

class InternalStorageManager(
    private val applicationContext: Application
) {
    private val contextWrapper = ContextWrapper(applicationContext)

    fun saveImageToInternalStorage(bitmapImage: Bitmap, memberName: String): String {
//        val contextWrapper = ContextWrapper(applicationContext)

//        path to /data/data/letsbooze/app_data/image_dir
        val directoryPath = contextWrapper.getDir(
            IMAGE_DIRECTORY, Context.MODE_PRIVATE
        )
        val imageFileName = "$memberName.png".replace(' ', '_')
        val imageFile = File(directoryPath, imageFileName)

        val fileOutputStream = FileOutputStream(imageFile) // loading data to imageFile
        bitmapImage.compress(
            Bitmap.CompressFormat.PNG, 100, fileOutputStream
        ) // compressing imageFile

        try {
                fileOutputStream.close()
        } catch (e: IOException) {
                e.printStackTrace()
        }

        return imageFileName
    }

    fun getBitmapFromInternalStorage(imageFileName: String): Bitmap? {
//        val contextWrapper = ContextWrapper(applicationContext)

        val directoryPath = contextWrapper.getDir(
            IMAGE_DIRECTORY, Context.MODE_PRIVATE
        )
        val imageFile = File(directoryPath, imageFileName)

        return if (imageFile.exists()) {
            val fileInputStream =
                FileInputStream(imageFile) // reading data from imageFile
            val bitmapImage =
                BitmapFactory.decodeStream(fileInputStream)

            try {
                fileInputStream.close()
            } catch(e :IOException) {
                e.printStackTrace()
            }

            bitmapImage
        } else null
    }
}