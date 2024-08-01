package com.avcoding.wear.ui

import android.graphics.Bitmap
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix

fun generateQRCode(text: String, width: Int, height: Int): BitMatrix {
    val writer = MultiFormatWriter()
    return writer.encode(text, BarcodeFormat.QR_CODE, width, height)
}


fun bitMatrixToBitmap(matrix: BitMatrix): Bitmap {
    val width = matrix.width
    val height = matrix.height
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
    for (x in 0 until width) {
        for (y in 0 until height) {
            bitmap.setPixel(x, y, if (matrix[x, y]) -0x1000000 else -0x1)
        }
    }
    return bitmap
}

fun String.createQrCode():Bitmap{

    val width = 300 // Set desired width
    val height = 300 // Set desired height

    val bitMatrix = generateQRCode(this, width, height)
    return bitMatrixToBitmap(bitMatrix)
}