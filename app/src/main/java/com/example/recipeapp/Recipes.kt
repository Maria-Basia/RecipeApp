package com.example.recipeapp

class Recipes (
    val id: Int,
    val image: Int = R.drawable.recipe1,
    var title: String,
    var duration: String,
    var servings: String,
    var ingredients: Array<String> = arrayOf("2 lemons", "3 cucumbers", "4 carrots"),
    var description: String,
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