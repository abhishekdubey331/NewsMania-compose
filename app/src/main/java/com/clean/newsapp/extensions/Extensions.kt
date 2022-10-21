package com.clean.newsapp.extensions

import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil.load
import com.clean.newsapp.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/***
 *  Set the View visibility to visible
 */
fun View.visible() {
    this.visibility = View.VISIBLE
}

/***
 *  Set the View visibility to gone
 */
fun View.gone() {
    this.visibility = View.GONE
}

/***
 *  Load images as an extension fun
 *  Currently using Coil for image loading
 */
fun ImageView.loadImage(imageUrl: String) {
    load(imageUrl) {
        placeholder(R.drawable.ic_place_holder)
    }
}

/**
 * Launches a new coroutine and repeats `block` every time the Fragment's viewLifecycleOwner
 * is in and out of `minActiveState` lifecycle state.
 */
inline fun Fragment.launchAndRepeatWithViewLifecycle(
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    crossinline block: suspend CoroutineScope.() -> Unit
) {
    viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.lifecycle.repeatOnLifecycle(minActiveState) {
            block()
        }
    }
}

