package com.susir.multimodule.adapter

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.ArrayMap
import android.util.Base64
import android.util.Log
import android.widget.ImageView
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import com.tencent.kuikly.core.render.android.adapter.IKRImageAdapter
import com.tencent.kuikly.core.render.android.adapter.HRImageLoadOption
import java.lang.Exception
import java.util.concurrent.*
import kotlin.math.roundToInt

object KRImageAdapter : IKRImageAdapter {

    private val imageTargetMap = ArrayMap<Int, Target>()

    override fun fetchDrawable(
        imageLoadOption: HRImageLoadOption,
        callback: (drawable: Drawable?) -> Unit,
    ) {
        if (imageLoadOption.src.startsWith("data:image/")) {
            loadFromBase64(imageLoadOption, callback)
        } else {
            requestImage(imageLoadOption, callback)
        }
    }

    private fun requestImage(
        imageLoadOption: HRImageLoadOption,
        callback: (drawable: Drawable?) -> Unit,
    ) {
        val creator = if (imageLoadOption.isAssets()) {
            val assetPath = imageLoadOption.src.substring(HRImageLoadOption.SCHEME_ASSETS.length)
            Picasso.get().load(Uri.parse("file:///android_asset/$assetPath"))
        } else {
            Picasso.get().load(imageLoadOption.src)
        }
        if (imageLoadOption.needResize) {
            creator.resize(imageLoadOption.requestWidth, imageLoadOption.requestHeight)
            when (imageLoadOption.scaleType) {
                ImageView.ScaleType.CENTER_CROP -> creator.centerCrop()
                ImageView.ScaleType.FIT_CENTER -> creator.centerInside()
                else -> {}
            }
        }
        val target = object : Target {
            override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom) {
                imageTargetMap.remove(hashCode())
                callback.invoke(BitmapDrawable(bitmap))
            }

            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                imageTargetMap.remove(hashCode())
                callback.invoke(null)
            }

            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
            }
        }
        imageTargetMap[target.hashCode()] = target
        creator.into(target)
    }

    private fun loadFromBase64(
        imageLoadOption: HRImageLoadOption,
        callback: (drawable: Drawable?) -> Unit,
    ) {
        execOnSubThread {
            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            val bytes = Base64.decode(imageLoadOption.src.split(",")[1], Base64.DEFAULT)
            BitmapFactory.decodeByteArray(bytes, 0, bytes.size, options)
            try {
                options.inPreferredConfig = Bitmap.Config.ARGB_8888
                options.inJustDecodeBounds = false
                try {
                    options.inSampleSize = calculateInSampleSize(
                        options,
                        imageLoadOption.requestWidth,
                        imageLoadOption.requestHeight
                    )
                } catch (e: ArithmeticException) { // 偶现报除以0，可能是inSampleSize超过int的范围溢出了。这里catch兜底使用原始inSampleSize
                    Log.d("ECHRImageAdapter", "loadFromBase64: $e")
                }
                val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size, options)
                callback.invoke(BitmapDrawable(bitmap))
            } catch (e: OutOfMemoryError) {
                Log.d("ECHRImageAdapter", "oom happen: $e")
            }
        }
    }

    private fun calculateInSampleSize(
        options: BitmapFactory.Options,
        reqWidth: Int,
        reqHeight: Int,
    ): Int {
        return if (reqWidth != 0 && reqHeight != 0 && reqWidth != -1 && reqHeight != -1) {
            var height = options.outHeight
            var width = options.outWidth
            var inSampleSize: Int
            inSampleSize = 1
            while (height > reqHeight && width > reqWidth) {
                val heightRatio = (height.toFloat() / reqHeight.toFloat()).roundToInt()
                val widthRatio = (width.toFloat() / reqWidth.toFloat()).roundToInt()
                val ratio = if (heightRatio > widthRatio) heightRatio else widthRatio
                if (ratio < 2) {
                    break
                }
                width = width shr 1
                height = height shr 1
                inSampleSize = inSampleSize shl 1
            }
            inSampleSize
        } else {
            1
        }
    }

}

private val subThreadPoolExecutor by lazy {
    Executors.newFixedThreadPool(2)
}

fun execOnSubThread(runnable: () -> Unit) {
    subThreadPoolExecutor.execute(runnable)
}