package com.example.explorr

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.explorr.ui.theme.ExplorrTheme


@Composable
fun TopSection(){
    Column {
        SearchBar()
        FilterSection()
        CountrySection()
    }
}
@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SearchBar(){
    Column() {
        Surface(
            modifier = Modifier
                .fillMaxWidth(),
            color = MaterialTheme.colorScheme.primary,
            shadowElevation = 8.dp,

            ) {
            Row (
                modifier = Modifier.fillMaxWidth()
            ){
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    value ="Search" , onValueChange ={ TODO()},
                    label = {
                        Text(text = "Search")
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Search,
                    ),
                    keyboardActions = KeyboardActions (onSearch ={
                        TODO()
                    } )
                    ,
                    leadingIcon = {
                        Icon(painter = painterResource(
                            id = R.drawable.search_fill0_wght400_grad0_opsz48),
                            contentDescription ="Search"
                        )
                    },
                    textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface),

                    )
            }

        }
    }
}

@Composable
fun FilterSection(modifier: Modifier = Modifier){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,

        modifier= Modifier.fillMaxWidth()) {
        IconButton(onClick = { /*TODO*/ }) {
            Row(verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start)  {
                Icon(
                    painter = painterResource(id = R.drawable.language_fill0_wght400_grad0_opsz48),
                    contentDescription = "language"
                )
                Text(text = "EN")
            }
        }
        Button(onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.onSurface,

                )
        ) {
            Row( verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_outline_filter_alt_24),
                    contentDescription = "filter"
                )
                Text(text = "Filter")
            }
        }
    }
}

@Composable
fun CountrySection(){
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val list = (0..75).map { it.toString() }
        items(count = list.size) {
            Text(
                text = list[it],
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountryListItem(){
    Column {
        ListItem(
            headlineText = { Text("Two line list item with trailing") },
            supportingText = { Text("Secondary text") },
            leadingContent = {
                Image(painter = painterResource(
                    id = R.drawable.ic_launcher_background),
                    contentDescription ="" )
            }
        )
        Divider()
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ExplorrTheme {
        TopSection()
    }
}
@Preview(showBackground = true)
@Composable
fun DefaultListPreview() {
    ExplorrTheme {
        CountryListItem()
    }
}