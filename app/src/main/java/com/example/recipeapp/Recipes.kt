package com.example.recipeapp

import android.net.Uri

class Recipes (
    var image: Uri? = null,
    var title: String = "",
    var duration: String = "",
    var servings: String = "",
    var ingredients: Array<String> = arrayOf(),
    var description: String = "",
    var madeDeleted: Boolean = false
) {
    fun truncateDescription(): String {
        return if (this.description.length >= 50) {
            this.description.substring(0..49) + "..."
        } else {
            this.description
        }
    }
}