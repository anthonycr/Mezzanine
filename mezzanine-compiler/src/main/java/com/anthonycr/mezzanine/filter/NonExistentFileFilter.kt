package com.anthonycr.mezzanine.filter

import com.anthonycr.mezzanine.utils.MessagerUtils
import java.io.File
import javax.lang.model.element.TypeElement

/**
 * Filters out non existent files and reports an error.
 */
object NonExistentFileFilter : (Pair<TypeElement, File>) -> Boolean {

    override fun invoke(pair: Pair<TypeElement, File>): Boolean {
        val (element, file) = pair
        return file.exists().also {
            if (!it) {
                MessagerUtils.reportError(element, "File does not exist at path: ${file.path}")
            }
        }
    }

}