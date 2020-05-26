package com.hivian.common.utils

/**
 * Find the percentaga given the partial amount and the total amount.
 * @param count the partial amount
 * @param total the total amount
 */
fun percent(count: Int, total: Int): Float = count * 100f / total