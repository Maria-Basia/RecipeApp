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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
            val id = it.arguments?.getString("id")?.toInt()
            if (id != null) {
                RecipeScreen(id)
            }
        }

        composable(route = "add") {
            // AddRecipeScreen()
        }

    }
}

var recipeArr: Array<Recipes> = arrayOf(
    Recipes(0,"Test", "", "60", "2",
        arrayOf("2 tomatoes, 3 potatoes"),
        "idkdsffesdfges"
    ),
    Recipes(1,"Test 2", "", "80", "2",
        arrayOf("3 tomatoes", "2 potatoes"),
        "idkidkdsffesdfgeedgfdgidkdsffesdfgeedgfdgidkdsffesdfgeedgfdgidkdsidkdsffesdfgeedgfdgidkdsffesdfgeedgfdgidkdsffesdfgeedgfdgidkds"
    )
)

@Composable
fun HomeScreen(onNextScreen: (Int) -> Unit) {

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
fun RecipeScreen(id: Int) {
    val recipe = recipeArr[id]
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp, 70.dp, 10.dp, 10.dp)
    ) {
        Text(text = recipe.title,
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold
        )
        Text(text = "Duration: ${recipe.duration} minutes", fontStyle = FontStyle.Italic)
        Text(text = "Serving size: ${recipe.servings}", fontStyle = FontStyle.Italic)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Ingredients:")
        for (i in recipe.ingredients) {
            Text(text = "- $i", modifier = Modifier.padding(5.dp, 0.dp, 0.dp, 0.dp))
            }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Description:\n${recipe.description}")
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
            Text(text = "Duration: ${recipe.duration} minutes")
            Text(text = recipe.truncateDescription())
        }
    }
}




@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RecipeAppTheme {
        App()
    }
}