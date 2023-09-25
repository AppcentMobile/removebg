package dev.eren.removebg.utils

import android.graphics.Bitmap

/**
 * Created by erenalpaslan on 18.08.2023
 */
object NetUtils {
    fun convertArrayToBitmap(arr: FloatArray, width: Int, height: Int): Bitmap? {
        val grayToneImage = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        for (i in 0 until width) {
            for (j in 0 until height) {
                grayToneImage.setPixel(j, i, (arr[i * height + j] * 255f).toInt() shl 24)
            }
        }
        return grayToneImage
    }
}