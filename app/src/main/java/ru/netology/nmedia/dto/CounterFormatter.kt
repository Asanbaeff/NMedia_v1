package ru.netology.nmedia.dto

import kotlin.math.roundToInt

class CounterFormatter {
    fun format(value: Int): String {
        return when {
            value < 1_000 -> value.toString()
            value < 10_000 -> {
                val thousands = value / 1_000.0
                val rounded = (thousands * 10).roundToInt() / 10.0
                if (rounded % 1 == 0.0) "${rounded.toInt()}K" else "${rounded}K"
            }

            value < 1_000_000 -> "${value / 1_000}K"
            else -> {
                val millions = value / 1_000_000.0
                val rounded = (millions * 10).roundToInt() / 10.0
                if (rounded % 1 == 0.0) "${rounded.toInt()}M" else "${rounded}M"
            }
        }
    }
}