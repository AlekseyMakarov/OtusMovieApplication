package com.example.otusalekseymakarovmovies

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.otusalekseymakarovmovies.extensions.dpToPx


const val MARGIN_LEFT = 20f
const val MARGIN_RIGHT = 20f
const val MARGIN_TOP = 50f
const val MARGIN_TOP_FIRST = 10f
const val MARGIN_BOTTOM = 16f
const val MARGIN_HORIZONTAL_BETWEEN = 10f
const val NO_MARGIN = 0f

object RecyclerDecorationPortrait : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val parentAdapter = parent.adapter
        val itemPosition = parent.getChildAdapterPosition(view)
        if (parentAdapter != null) {
            outRect.set(
                if (itemPosition % 2 == 0) parent.dpToPx(MARGIN_LEFT) else parent.dpToPx(
                    MARGIN_HORIZONTAL_BETWEEN
                ),
                if (itemPosition < 2) parent.dpToPx(MARGIN_TOP_FIRST) else parent.dpToPx(MARGIN_TOP),
                if (itemPosition % 2 == 1) parent.dpToPx(MARGIN_RIGHT) else parent.dpToPx(
                    MARGIN_HORIZONTAL_BETWEEN
                ),
                when (parentAdapter.itemCount % 2) {
                    0 -> if (itemPosition == parentAdapter.itemCount - 1 || itemPosition == parentAdapter.itemCount - 2) parent.dpToPx(
                        MARGIN_BOTTOM
                    ) else parent.dpToPx(NO_MARGIN)
                    1 -> if (itemPosition == parentAdapter.itemCount - 1) parent.dpToPx(
                        MARGIN_BOTTOM
                    ) else parent.dpToPx(NO_MARGIN)
                    else -> parent.dpToPx(NO_MARGIN)
                }
            )
        } else {
            super.getItemOffsets(outRect, view, parent, state)
        }

    }
}

object RecyclerDecorationLandscape : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val parentAdapter = parent.adapter
        val itemPosition = parent.getChildAdapterPosition(view)
        if (parentAdapter != null) {
            outRect.set(
                if (itemPosition % 3 == 0) parent.dpToPx(MARGIN_LEFT) else parent.dpToPx(
                    MARGIN_HORIZONTAL_BETWEEN
                ),
                if (itemPosition < 3) parent.dpToPx(MARGIN_TOP_FIRST) else parent.dpToPx(MARGIN_TOP),
                if (itemPosition % 3 == 2) parent.dpToPx(MARGIN_RIGHT) else parent.dpToPx(
                    MARGIN_HORIZONTAL_BETWEEN
                ),
                when (parentAdapter.itemCount % 3) {
                    0 -> if (itemPosition == parentAdapter.itemCount - 1 || itemPosition == parentAdapter.itemCount - 2 || itemPosition == parentAdapter.itemCount - 3) parent.dpToPx(
                        MARGIN_BOTTOM
                    ) else parent.dpToPx(NO_MARGIN)
                    in listOf(1, 2) -> if (itemPosition in listOf(parentAdapter.itemCount - 1, parentAdapter.itemCount - 2)) parent.dpToPx(
                        MARGIN_BOTTOM
                    ) else parent.dpToPx(NO_MARGIN)
                    else -> parent.dpToPx(NO_MARGIN)
                }
            )
        } else {
            super.getItemOffsets(outRect, view, parent, state)
        }

    }
}