package com.hivian.common.extension

import java.util.*

/**
 * Returns a string with first letter capitalized
 * and all other characters lowercased
 * It doesn't modify the original string.
 */
fun String.capitalize(): String =
    firstOrNull()?.let { firstChar ->
        firstChar.toTitleCase() + substring(1).toLowerCase(Locale.getDefault())
    } ?: this