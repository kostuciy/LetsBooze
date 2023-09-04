package com.kostuciy.letsbooze.data

import android.app.Application
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

private const val IMAGE_DIRECTORY = "image_dir"

class InternalStorageManager private constructor(
    private val applicationContext: Application
) {
//    private val contextWrapper = ContextWrapper(applicationContext)

    fun saveImageToInternalStorage(bitmapImage: Bitmap, memberName: String): String {
        val contextWrapper = ContextWrapper(applicationContext)
//        path to /data/data/letsbooze/app_data/image_dir
        val directoryPath = contextWrapper.getDir(
            IMAGE_DIRECTORY, Context.MODE_PRIVATE
        )
        val imageFileName = "$memberName.png".replace(' ', '_')
        val imageFile = File(directoryPath, imageFileName)

        val fileOutputStream = FileOutputStream(imageFile) // loading data to imageFile
        bitmapImage.compress(
            Bitmap.CompressFormat.PNG, 75, fileOutputStream
        ) // compressing imageFile to png

        try {
                fileOutputStream.close()
        } catch (e: IOException) {
                e.printStackTrace()
        }

        return imageFileName
    }

    fun getBitmapFromInternalStorage(imageFileName: String): Bitmap? {
        val contextWrapper = ContextWrapper(applicationContext)
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

    fun removeImageFileFromStorage(imageFileName: String) {
        val contextWrapper = ContextWrapper(applicationContext)
        val directoryPath = contextWrapper.getDir(
            IMAGE_DIRECTORY, Context.MODE_PRIVATE
        )

        File(directoryPath, imageFileName).delete()
    }

    fun changeImageFileName(oldName: String, newName: String) {
        val contextWrapper = ContextWrapper(applicationContext)
        val directoryPath = contextWrapper.getDir(
            IMAGE_DIRECTORY, Context.MODE_PRIVATE
        )

        val oldImageFile = File(directoryPath, oldName)
        val newImageFile = File(directoryPath, newName)

        if (oldImageFile.exists()) {
            try {
                oldImageFile.renameTo(newImageFile)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    companion object {
        private var instance: InternalStorageManager? = null

        fun initialize(application: Application) {
            if (instance == null)
                instance = InternalStorageManager(application)
        }

        fun get(): InternalStorageManager =
            instance ?: throw IllegalStateException(
                "InternalStorageManager must be initialized."
            )
    }

    class BitmapScaler {
        fun scaleBitmap(
            bitmapImage: Bitmap,
            drawable: Drawable,
            multiplier: Float
        ): Bitmap {
            val lowerWidth = bitmapImage.width * multiplier
            val lowerHeight =
                bitmapImage.height * (lowerWidth / bitmapImage.width)

            val scaledBitmap =
                Bitmap.createScaledBitmap(
                    bitmapImage,
                    lowerWidth.toInt(),
                    lowerHeight.toInt(),
                    true
                )

            return scaledBitmap
        }

        fun scaleBitmap(
            bitmapImage: Bitmap,
            newWidth: Int
        ): Bitmap{
            val newHeight =
                bitmapImage.height * (newWidth.toFloat() / bitmapImage.width)

            val scaledScaled =
                Bitmap.createScaledBitmap(
                    bitmapImage,
                    newWidth,
                    newHeight.toInt(),
                    true
                )

            return scaledScaled
        }
    }

}