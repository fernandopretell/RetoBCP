package com.fulbiopretell.retobcp.core.extensions

fun <T> List<T>.applyForEach(function: (T) -> Unit): List<T> = apply { forEach(function) }