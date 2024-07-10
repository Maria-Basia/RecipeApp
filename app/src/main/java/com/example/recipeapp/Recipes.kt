package com.example.recipeapp

class Recipes (
    val id: Int,
    var title: String,
    var img: String,
    var duration: String,
    var servings: String,
    var ingredients: Array<String>,
    var description: String
) {
    fun truncateDescription(): String {
        return if (this.description.length >= 50) {
            this.description.substring(0..49) + "..."
        } else {
            this.description
        }
    }
}