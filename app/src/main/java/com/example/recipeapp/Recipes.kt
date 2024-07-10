package com.example.recipeapp

class Recipes (
    var title: String = "",
    var img: String = "",
    var duration: String = "",
    var servings: Int = 0,
    var ingredients: Array<String> = arrayOf(),
    var description: String =  ""
) {
}