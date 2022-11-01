package by.homework.hlazarseni.tfgridexplorer.presentation.extensions

import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.core.graphics.Insets
import androidx.core.view.*


fun Toolbar.applyWindowInsets() {
    doOnApplyWindowInsets { view, insets, _ ->
        val systemBarInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
        view.updatePadding(
            top = systemBarInsets.top,
            left = systemBarInsets.left,
            right = systemBarInsets.right
        )
        view.updateLayoutParams {
            height = view.minimumHeight + view.paddingTop
        }
        WindowInsetsCompat.Builder(insets)
            .setInsets(WindowInsetsCompat.Type.systemBars(), Insets.NONE)
            .build()
    }
}

fun View.applyHorizontalWindowInsets() {
    doOnApplyWindowInsets(
        initialOffset = Offset(
            left = paddingLeft,
            right = paddingRight
        )
    ) { view, insets, offset ->
        val systemBarInsets = insets.getInsets(WindowInsetsCompat.Type.navigationBars())
        view.updatePadding(
            left = systemBarInsets.left + offset.left,
            right = systemBarInsets.right + offset.right
        )
        insets
    }
}

fun View.doOnApplyWindowInsets(
    initialOffset: Offset = Offset(),
    action: (view: View, insets: WindowInsetsCompat, offset: Offset) -> WindowInsetsCompat
) {
    ViewCompat.setOnApplyWindowInsetsListener(this) { v, insets ->
        action(v, insets, initialOffset)
    }

    doOnAttach {
        it.requestApplyInsets()
    }
}

data class Offset(
    val left: Int = 0,
    val top: Int = 0,
    val right: Int = 0,
    val bottom: Int = 0
)