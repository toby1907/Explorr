package com.example.explorr.ui.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.explorr.R
import com.example.explorr.data.domain.Country
import com.example.explorr.ui.MainViewModel
import com.example.explorr.ui.Screen


@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun FullScreen(viewModel: MainViewModel,nav: NavController){
    var menuExpanded by remember{
        mutableStateOf(false)
    }

    val context = LocalContext.current.applicationContext
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Explorr.") },
                actions = {
                    IconButton(
                        onClick = {
                            menuExpanded=!menuExpanded
                            if (menuExpanded){
viewModel.onEvent(MainViewModel.UiState.DarkMode)
                            }
                            else
                                viewModel.onEvent(MainViewModel.UiState.LightMode)
                        }
                    ) {
                        var  res: Int = R.drawable.light_mode_fill0_wght400_grad0_opsz48
                        if (menuExpanded) {
                            res= R.drawable.ic_baseline_dark_mode_24
                        }
                        Icon(painter = painterResource(
                           id = res),
                            contentDescription = "Localized description"
                        )
                    }
                }
            )
        },
        content = { innerPadding ->
           TopSection(viewModel = viewModel, nav =nav, Modifier.padding(innerPadding))
        }
    )
}

@Composable
fun TopSection(viewModel: MainViewModel, nav:NavController,modifier: Modifier=Modifier){

    Column {
        Spacer(modifier =Modifier.size(64.dp))
        SearchBar( viewModel)
        FilterSection()
        CountrySection(viewModel,nav)
    }
}
@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SearchBar( viewModel: MainViewModel){
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
                        .padding(4.dp),
                    value =viewModel.countryListScreenState.value.searchQuery , onValueChange ={query ->
                        viewModel.onEvent(
                            MainViewModel.CountryListScreenEvent.OnSearchQueryChange(
                                query
                            )
                        )
                    },
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
                            id = R.drawable.search_fill0_wght400_grad0_opsz48
                        ),
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
                horizontalArrangement = Arrangement.Start,
            )  {
                Icon(
                    painter = painterResource(id = R.drawable.language_fill0_wght400_grad0_opsz48),
                    contentDescription = "language"
                )
                Text(modifier = Modifier.size(height = 48.dp, width = 64.dp),text = "EN")
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CountrySection(viewModel: MainViewModel, navController: NavController){
    val countries = viewModel.countryListScreenState.value
    val grouped = countries.countries.sortedBy { it.name }.groupBy { it.name[0] }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
       grouped.forEach{ (initial,countriesForIntial) ->
           stickyHeader {
            Card( shape = RectangleShape
                ,modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 4.dp)
            ) { Text(modifier = Modifier.padding(start = 4.dp)
                ,text = initial.toString()) }
           }

        items(count = countriesForIntial.size) { it ->
            val country: Country = countriesForIntial[it]
            CountryListItem(country = country, onItemClick = {
                viewModel.setSelectedCountry(country)
                navController.navigate(Screen.DetailScreen.route)
            })
            /* Text(
                text = list[it],
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )*/
        }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountryListItem(country: Country, onItemClick: (Country) -> Unit){
    Column {
        ListItem(
            modifier = Modifier.clickable { onItemClick(country) },
            headlineText = { Text(country.name) },
            supportingText = { Text(country.capital) },
            leadingContent = {
                AsyncImage(
                    model = country.flagImage,
                    contentDescription ="country flag",
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(6.dp),),
                    contentScale = ContentScale.Crop
                )
            }
        )
        Divider()
    }
}


