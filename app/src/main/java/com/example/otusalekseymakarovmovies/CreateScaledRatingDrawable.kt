package com.example.otusalekseymakarovmovies

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.toBitmap
import com.example.otusalekseymakarovmovies.extensions.dpToPx

fun createScaledRatingDrawable(context: Context): Drawable {
    val starEmptyBitmap = AppCompatResources.getDrawable(context, R.drawable.ic_star_empty)
        ?.toBitmap(context.dpToPx(15F), context.dpToPx(15F))
    val starBitmap = AppCompatResources.getDrawable(context, R.drawable.ic_star)
        ?.toBitmap(context.dpToPx(15F), context.dpToPx(15F))
    val newStarEmptyDrawable = BitmapDrawable(context.resources, starEmptyBitmap)
    val newStarDrawable = BitmapDrawable(context.resources, starBitmap)
    val finalDrawable = LayerDrawable(
        arrayOf(
            newStarEmptyDrawable,
            newStarEmptyDrawable,
            newStarDrawable,
        )
    )
    finalDrawable.setId(0, android.R.id.background)
    finalDrawable.setId(1, android.R.id.secondaryProgress)
    finalDrawable.setId(2, android.R.id.progress)
    return finalDrawable
}