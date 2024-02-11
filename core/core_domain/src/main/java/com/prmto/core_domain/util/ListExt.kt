package com.prmto.core_domain.util

import android.net.Uri

fun List<String>.toUris(): List<Uri> = map { Uri.parse(it) }