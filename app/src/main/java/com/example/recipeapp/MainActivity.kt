package com.example.recipeapp

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
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

    NavHost(navController = navController, startDestination = "add") {

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
             AddRecipeScreen(onNextScreen = { navController.navigate("home") })
        }

    }
}

var recipeArr: Array<Recipes> = arrayOf(
    Recipes(0, R.drawable.recipe1,"Test", "60", "2",
        arrayOf("2 tomatoes, 3 potatoes"),
        "idkdsffesdfges"
    ),
    Recipes(1, R.drawable.recipe1,"Test 2", "80", "2",
        arrayOf("3 tomatoes", "2 potatoes"),
        "idkidkdsffesdfgeedgfdgidkdsffesdfgeedgfdgidkdsffesdfgeedgfdgidkdsidkdsffesdfgeedgfdgidkdsffesdfgeedgfdgidkdsffesdfgeedgfdgidkds"
    )
)

@Composable
fun HomeScreen(onNextScreen: (Int) -> Unit) {

    Column (modifier = Modifier
        .fillMaxSize()
        .padding(10.dp, 50.dp, 10.dp, 10.dp),
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
        Button(onClick = { /*TODO*/ }) {

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
        Spacer(modifier = Modifier.height(8.dp))
        Image(painter = painterResource(id =recipe.image) , contentDescription = "Recipe photo", modifier = Modifier.align(Alignment.CenterHorizontally) )
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "Duration: ${recipe.duration} minutes", fontStyle = FontStyle.Italic)
        Text(text = "Serving size: ${recipe.servings}", fontStyle = FontStyle.Italic)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Ingredients:", modifier = Modifier.padding(7.dp), fontSize = 20.sp)
        for (i in recipe.ingredients) {
            Text(text = i)
            Text(text = "- $i", modifier = Modifier.padding(5.dp, 0.dp, 0.dp, 0.dp))
            }
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Description:", modifier = Modifier.padding(7.dp), fontSize = 20.sp)
        Text(text = recipe.description)
    }
}


@Composable
fun AddRecipeScreen(onNextScreen: () -> Unit) {
    var titleInput by remember { mutableStateOf("") }
    var durationInput by remember { mutableStateOf("") }
    var servingsInput by remember { mutableStateOf("") }
    var singleIngredientInput by remember { mutableStateOf("") }
    var descriptionInput by remember { mutableStateOf("") }
    var allIngredients: Array<String> = arrayOf()
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let {
                imageUri = it
            }
        }
    )

    Column {
        Text(text = "Add recipe")
        imageUri?.let {
            Image(
                painter = rememberAsyncImagePainter(model = imageUri),
                contentDescription = null,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(36.dp)
            )
        }

        TextButton(
            onClick = {
                galleryLauncher.launch("image/*")
            }
        ) {
            Text(
                text = "Pick image"
            )
        }
        OutlinedTextField(value = titleInput, onValueChange = {titleInput = it}, label = {Text("Recipe name:")})
        OutlinedTextField(value = durationInput, onValueChange ={durationInput = it}, label = {Text("Duration:")} )
        OutlinedTextField(value = servingsInput, onValueChange ={servingsInput = it}, label = {Text("Servings:")} )
        OutlinedTextField(value = singleIngredientInput, onValueChange ={singleIngredientInput = it}, label = {Text("Ingredients:")} )
        Button(onClick = {allIngredients += singleIngredientInput; println("this is the ingredient $singleIngredientInput"); println("this is the list ingredient $allIngredients"); singleIngredientInput = "" }){
            Text(text = "Add ingredient")
        }
        OutlinedTextField(value = descriptionInput, onValueChange ={descriptionInput = it}, label = {Text("Description:")} )
        Button(onClick = { val recipe = Recipes(id =  2, title= titleInput, duration =durationInput, servings=servingsInput, ingredients = allIngredients, description = descriptionInput)
        recipeArr += recipe; println("LIST  OF INGREDIENTS ${allIngredients.size}"); onNextScreen() }) {
            Text(text = "Add Recipe")
        }
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
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(painter = painterResource(id = recipe.image), contentDescription = "Image thumbnail", modifier = Modifier
                    .size(60.dp)
                    .clip(
                        RoundedCornerShape(7.dp)
                    ), contentScale = ContentScale.FillBounds )
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = recipe.title, modifier = Modifier, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "Duration: ${recipe.duration} minutes", fontStyle = FontStyle.Italic, fontSize = 15.sp)
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = recipe.truncateDescription())
        }
    }
}




@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RecipeAppTheme {
        App()
//        RecipeScreen(id = 3)
//       AddRecipeScreen()
    }
}