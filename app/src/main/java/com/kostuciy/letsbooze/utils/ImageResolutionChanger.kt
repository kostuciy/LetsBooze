package com.kostuciy.letsbooze.utils

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.core.graphics.drawable.toBitmap

class ImageResolutionChanger {

    fun changeResolution(photoImageView: ImageView, drawable: Drawable, multiplier: Float) {
        val bitmapImage = drawable.toBitmap()

        val lowerWidth = bitmapImage.width * multiplier
        val lowerHeight =
            bitmapImage.height * (lowerWidth / bitmapImage.width)

        val scaledDrawable =
            Bitmap.createScaledBitmap(
                bitmapImage,
                lowerWidth.toInt(),
                lowerHeight.toInt(),
                true
            )

        photoImageView.setImageBitmap(scaledDrawable)
    }

    fun changeResolution(photoImageView: ImageView, drawable: Drawable, newWidth: Int) {
        val bitmapImage = drawable.toBitmap()

        val newHeight =
            bitmapImage.height * (newWidth / bitmapImage.width)

        val scaledDrawable =
            Bitmap.createScaledBitmap(
                bitmapImage,
                newWidth,
                newHeight,
                true
            )

        photoImageView.setImageBitmap(scaledDrawable)
    }
}