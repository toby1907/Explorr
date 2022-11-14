package com.example.explorr.ui.main

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material3.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.explorr.R
import com.example.explorr.data.domain.Country
import com.example.explorr.ui.MainViewModel
import com.example.explorr.ui.Screen
import com.example.explorr.ui.theme.FilterButton
import com.example.explorr.ui.theme.fonts
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class,
    ExperimentalMaterialApi::class
)
@Composable
fun FullScreen(viewModel: MainViewModel,nav: NavController){
    var menuExpanded by remember{
        mutableStateOf(false)
    }

    val context = LocalContext.current.applicationContext
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState()
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
    )
    BottomSheetScaffold(
        scaffoldState=bottomSheetScaffoldState,
        sheetPeekHeight = 0.dp,
        sheetShape = RoundedCornerShape(16.dp),
        topBar = {
            TopAppBar(
                title = {  Spacer(Modifier.width(8.dp))
                    Text(
                        text = buildAnnotatedString {
                            withStyle(SpanStyle(fontFamily = fonts, fontSize = 20.sp)) {
                                append("Explorr")
                            }
                            withStyle(SpanStyle(fontFamily = fonts, fontSize = 20.sp, color = FilterButton)) {
                                append(".")
                            }
                        },
                        fontSize = 24.sp,
                        fontFamily =fonts,
                        color = MaterialTheme.colorScheme.onSurface
                    ) },
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
           TopSection(viewModel = viewModel, nav =nav, Modifier.padding(innerPadding), butttonOnClick = {
               scope.launch {

                   if (bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                       bottomSheetScaffoldState.bottomSheetState.expand()
                   } else {
                       bottomSheetScaffoldState.bottomSheetState.collapse()
                   }
               }
           })
        },
        sheetContent = {

            FilterScreen(close={ scope.launch { bottomSheetScaffoldState.bottomSheetState.collapse() } }, viewModel = viewModel)
           /* Box(
                Modifier
                    .fillMaxWidth()
                    .height(164.dp)
                    .clip(RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text("Swipe up to expand sheet")
            }
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(64.dp)
                    .clip(RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp))
                ,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Sheet content")
                Spacer(Modifier.height(20.dp))
                Button(
                    onClick = {
                        scope.launch { bottomSheetScaffoldState.bottomSheetState.collapse() }
                    }
                ) {
                    Text("Click to collapse sheet")
                }
            }*/
            }
            )
        }


@Composable
fun TopSection(viewModel: MainViewModel, nav:NavController,modifier: Modifier=Modifier, butttonOnClick: () -> Unit){

    Column {
        SearchBar( viewModel)
        FilterSection(butttonOnClick = butttonOnClick)
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
            color = MaterialTheme.colorScheme.surface,

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
                    )                    ,
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
fun FilterSection(modifier: Modifier = Modifier, butttonOnClick:()->Unit){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier= Modifier
            .fillMaxWidth()
            .padding(start = 4.dp, end = 4.dp)
    ) {
        Button(
            onClick = { /*TODO*/ },
            Modifier.size(width = 73.dp, height = 40.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.primary
            ),
            shape = RectangleShape,
            border = BorderStroke(2.dp, MaterialTheme.colorScheme.onSurface),
            contentPadding = PaddingValues(0.dp),
        ) {
            Row(modifier = Modifier.padding(start = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
            )  {
                Icon(
                    painter = painterResource(id = R.drawable.language_fill0_wght400_grad0_opsz48),
                    contentDescription = "language"
                )
                Text(text = "EN")
            }
        }
        Button(onClick = { butttonOnClick.invoke() },
            Modifier.size(width = 86.dp, height = 40.dp),
            shape = RectangleShape,
            contentPadding = PaddingValues(0.dp),
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
            Card( shape = RectangleShape,
                backgroundColor = MaterialTheme.colorScheme.surface
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


