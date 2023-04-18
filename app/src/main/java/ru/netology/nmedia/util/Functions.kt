package ru.netology.nmedia.util

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import ru.netology.nmedia.viewmodel.PostViewModel

object Functions {
    fun thousandsAndMillionsChanger(count: Int): String {
        return when (count) {
            in 0..999 -> count.toString()
            in 1000..9999 -> (count / 1000).toString() +
                    if (count / 100 - count / 1000 * 10 > 0) {
                        "." + (count / 100 - count / 1000 * 10).toString() + "K"
                    } else {
                        "K"
                    }
            in 10000..999999 -> (count / 1000).toString() + "K"
            else -> (count / 1000000).toString() +
                    if (count / 100000 - count / 1000000 * 10 > 0) {
                        "." + (count / 100000 - count / 1000000 * 10).toString() + "M"
                    } else {
                        "M"
                    }
        }
    }


    fun hideKeyboard(view: View) {
        val inputMethodManager =
            view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}