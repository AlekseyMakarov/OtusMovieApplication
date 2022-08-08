package com.example.otusalekseymakarovmovies

import android.graphics.Rect
import androidx.recyclerview.widget.RecyclerView
import com.example.otusalekseymakarovmovies.extensions.dpToPx


const val MARGIN_LEFT = 20f
const val MARGIN_RIGHT = 20f
const val MARGIN_TOP = 50f
const val MARGIN_BOTTOM = 16f
const val MARGIN_HORIZONTAL_BETWEEN = 10f
const val NO_MARGIN = 0f

object RecyclerDecoration : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, itemPosition: Int, parent: RecyclerView) {
        val parent_adapter = parent.adapter
        if (parent_adapter != null) {
            outRect.set(
                if (itemPosition % 2 == 0) parent.dpToPx(MARGIN_LEFT) else parent.dpToPx(
                    MARGIN_HORIZONTAL_BETWEEN
                ),
                if (itemPosition < 2) parent.dpToPx(NO_MARGIN) else parent.dpToPx(MARGIN_TOP),
                if (itemPosition % 2 == 1) parent.dpToPx(MARGIN_RIGHT) else parent.dpToPx(
                    MARGIN_HORIZONTAL_BETWEEN
                ),
                when (parent_adapter.itemCount % 2) {
                    0 -> if (itemPosition == parent_adapter.itemCount - 1 || itemPosition == parent_adapter.itemCount - 2) parent.dpToPx(
                        MARGIN_BOTTOM
                    ) else parent.dpToPx(NO_MARGIN)
                    1 -> if (itemPosition == parent_adapter.itemCount - 1) parent.dpToPx(
                        MARGIN_BOTTOM
                    ) else parent.dpToPx(NO_MARGIN)
                    else -> parent.dpToPx(NO_MARGIN)
                }
            )
        } else {
            super.getItemOffsets(outRect, itemPosition, parent)
        }
    }
}