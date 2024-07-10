package com.example.recipeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.recipeapp.ui.theme.RecipeAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RecipeAppTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    App()
                }
            }
        }
    }
}

@Composable
fun App() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {

        composable(route = "home") {
            HomeScreen(onNextScreen = { id: Int ->
                navController.navigate("recipe/$id")
            })
        }

        composable(route = "recipe/{id}") {
            RecipeScreen()
        }

        composable(route = "add") {
            // AddRecipeScreen()
        }

    }
}

@Composable
fun HomeScreen(onNextScreen: (Int) -> Unit) {
    var recipeArr: Array<Recipes> = arrayOf(
        Recipes(1,"Test", "", "60", 2,
            arrayOf("2 tomatoes, 3 potatoes"),
            "idkdsffesdfges"
        ),
        Recipes(2,"Test 2", "", "80", 2,
            arrayOf("3 tomatoes, 2 potatoes"),
            "idkidkdsffesdfgeedgfdgidkdsffesdfgeedgfdgidkdsffesdfgeedgfdgidkdsidkdsffesdfgeedgfdgidkdsffesdfgeedgfdgidkdsffesdfgeedgfdgidkds"
        )
    )

    Column (modifier = Modifier
        .fillMaxSize()
        .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Our App",
            modifier = Modifier
                .padding(10.dp),
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold
            )
        for (recipe in recipeArr) {
            MakeCard(recipe, onNextScreen)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }

}


@Composable
fun RecipeScreen() {
Box(modifier = Modifier) {

}


}


@Composable
fun MakeCard(recipe: Recipes, onNextScreen: (Int) -> Unit) {
    return Card(onClick = {onNextScreen(recipe.id)}) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
//            Row {
//
//            }
            Text(text = recipe.title)
            Text(text = recipe.duration)
            Text(text = truncateDescription(recipe.description))
        }
    }
}

fun truncateDescription(description: String): String {
    if (description.length >= 50) {
        return description.substring(0..49) + "..."
    } else {
        return description
    }
}




@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RecipeAppTheme {
        App()
    }
}