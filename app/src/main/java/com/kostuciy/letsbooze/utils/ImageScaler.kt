package com.kostuciy.letsbooze.utils

import android.graphics.Bitmap
import android.graphics.drawable.Drawable

class ImageScaler {

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