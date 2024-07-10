package com.example.recipeapp

class Recipes (
    val id: Int,
    var title: String,
    var img: String,
    var duration: String,
    var servings: Int,
    var ingredients: Array<String>,
    var description: String
) {
}