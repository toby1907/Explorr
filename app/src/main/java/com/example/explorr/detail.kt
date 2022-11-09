package com.example.explorr

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.explorr.ui.theme.ExplorrTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(modifier:Modifier=Modifier) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Simple TopAppBar",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { /* doSomething() */ }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Localized description"
                        )
                    }
                }
            )
        },
        content = { innerPadding ->
            LazyColumn(
                contentPadding = innerPadding,
                verticalArrangement = Arrangement.spacedBy(8.dp),

            ) {
                items(1){
                  Row(modifier = Modifier.fillMaxWidth(),
                      verticalAlignment = Alignment.CenterVertically,
                      horizontalArrangement = Arrangement.Center
                  )  {
                        Image(modifier=Modifier.size(width = 380.dp, height = 200.dp),
                            painter = painterResource(id = R.drawable.ic_launcher_background),
                            contentDescription = ""
                        )
                    }
                }
            }
        }
    )
}
@Preview(showBackground = true)
@Composable
fun DefaultDetailPreview() {
    ExplorrTheme {
       DetailScreen()
    }
}