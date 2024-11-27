package com.example.idtex.utils

import android.text.SpannableString
import androidx.core.text.HtmlCompat

/**
 * This extension function converts an HTML string into a [SpannableString].
 *
 * The HTML tags in the string are parsed and displayed accordingly.
 *
 * @return A [SpannableString] representation of the HTML string.
 */
fun String.fromHtml(): SpannableString {
    val spanned = HtmlCompat.fromHtml(this, HtmlCompat.FROM_HTML_MODE_LEGACY)
    return SpannableString(spanned)
}