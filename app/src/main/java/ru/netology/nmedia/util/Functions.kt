package ru.netology.nmedia.util

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


}