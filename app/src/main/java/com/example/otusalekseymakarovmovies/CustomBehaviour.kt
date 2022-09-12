package com.example.otusalekseymakarovmovies

import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.animation.doOnEnd
import androidx.core.view.ViewCompat
import androidx.core.widget.NestedScrollView
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlin.math.abs
import kotlin.math.min

//class CustomBehaviour(): AppBarLayout.ScrollingViewBehavior() {
//    lateinit var ctx: Context
//    constructor(ctx: Context, attrs:AttributeSet):this(){
//        this.ctx = ctx
//    }
////    override fun onDependentViewChanged(
////        parent: CoordinatorLayout,
////        child: View,
////        dependency: View
////    ): Boolean {
//////        return super.onDependentViewChanged(parent, child, dependency)
////        dependency.translationY = 200f
////        return true
////
////    }
//
//    override fun onStopNestedScroll(
//        coordinatorLayout: CoordinatorLayout,
//        child: View,
//        target: View,
//        type: Int
//    ) {
////        super.onStopNestedScroll(coordinatorLayout, child, target, type)
//        target.translationY = 100f
//    }
//}



class CustomBehaviour(): AppBarLayout.Behavior(){
    constructor(ctx: Context, attrs: AttributeSet):this()
    private lateinit var targetView:View
    private lateinit var collapsingView: CollapsingToolbarLayout
//    private var targetHeight: Int = 0
    private var parentHeight: Int = 0
    private var totalDy: Int = 0
    private var lastScale: Float = 0f
    private var lastBottom: Int = 0
    private var isStopped:Boolean = false
    private var isNotFixed = true




    override fun onLayoutChild(
        parent: CoordinatorLayout,
        abl: AppBarLayout,
        layoutDirection: Int
    ): Boolean {
        val superResult = super.onLayoutChild(parent, abl, layoutDirection)
        if(!::targetView.isInitialized)initialize(abl, parent)
        return superResult
    }

    private fun initialize(abl: AppBarLayout, parent: CoordinatorLayout) {

        targetView = parent.findViewById(R.id.movie_image)
        collapsingView = (abl.getChildAt(0) as NestedScrollView).getChildAt(0) as CollapsingToolbarLayout
        parentHeight = abl.height
//        targetHeight = targetView.height


    }




    override fun onStartNestedScroll(
        parent: CoordinatorLayout,
        child: AppBarLayout,
        directTargetChild: View,
        target: View,
        nestedScrollAxes: Int,
        type: Int
    ): Boolean {
        isStopped = false
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL

    }




    override fun onStopNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        abl: AppBarLayout,
        target: View,
        type: Int
    ) {
        isStopped = true
        Log.e("fuck", "fuck")
        super.onStopNestedScroll(coordinatorLayout, abl, target, type)
        restore(abl)



    }

    private fun restore(abl: AppBarLayout) {
//        if (abl.bottom in (0.5*abl.height).toInt()..(0.75*abl.height).toInt())
//            return
        val ablHeight = abl.height

        if(isStopped && isNotFixed) {
            when (abl.bottom) {

                in (0.5 * abl.totalScrollRange).toInt()..(0.75 * abl.totalScrollRange).toInt() -> {
                    isNotFixed = false
                    val anim = ValueAnimator.ofInt(abl.bottom, (abl.totalScrollRange / 2).toInt())
                    anim.addUpdateListener {
//                    val value = it.animatedValue as Float
//                    targetView.scaleX = value
//                    targetView.scaleY = value
//                    val bottomValue = (lastBottom - (lastBottom - parentHeight)*it.animatedFraction).toInt()
                        val bottomValue = it.animatedValue as Int
                        topAndBottomOffset = -bottomValue

//                    collapsingView.bottom = bottomValue
                    }
                    anim.duration = 300
//                    anim.doOnEnd { isNotFixed=true }
                    anim.start()


                }
                else -> return


            }
        }


//        if (totalDy > 0){
//            totalDy = 0
//            val anim = ValueAnimator.ofFloat(lastScale, 1f)
//            anim.addUpdateListener {
//                val value = it.animatedValue as Float
//                targetView.scaleX = value
//                targetView.scaleY = value
//                val bottomValue = (lastBottom - (lastBottom - parentHeight)*it.animatedFraction).toInt()
//                abl.bottom = bottomValue
//                collapsingView.bottom = bottomValue
//            }
//            anim.start()
//        }

    }

    private fun scale(abl:AppBarLayout, dY:Int){
        if (isStopped)return
        // если прокрутка вниз, то dY отрицателен
        // а totalDy положителен
        totalDy = -dY
        // ограничиваем totalDy сверху высотой targetView
//        totalDy = min(totalDy, parentHeight)
        abl.bottom = abl.bottom + totalDy
        collapsingView.bottom = collapsingView.bottom + totalDy

//        lastScale = max(1f, 1f+totalDy.toFloat()/targetView.height)
//        targetView.scaleX = lastScale
//        targetView.scaleY = lastScale
//        lastBottom = parentHeight + (targetView.height * (lastScale -1)).toInt()
//        abl.bottom = lastBottom
//        collapsingView.bottom = lastBottom

    }

    override fun onNestedPreScroll(
        coordinatorLayout: CoordinatorLayout,
        child: AppBarLayout,
        target: View,
        dx: Int,
        dy: Int,
        consumed: IntArray,
        type: Int
    ) {
//        val ablBottom = child.bottom
//        scale(child, dy)
        if (abs(dy) > 1) isNotFixed = true
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type)

//        if ((dy < 0 && ablBottom >= parentHeight) || (dy > 0 && ablBottom >parentHeight)){
//            scale(child, dy)
//        } else super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type)





    }
}

