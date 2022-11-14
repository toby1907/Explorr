package com.example.explorr.ui.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.explorr.R
import com.example.explorr.data.domain.Country
import com.example.explorr.ui.MainViewModel
import com.example.explorr.ui.Screen
import com.example.explorr.ui.theme.ExplorrTheme
import com.example.explorr.ui.theme.LightTextBlack

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    viewModel: MainViewModel, navController: NavController,
    modifier:Modifier=Modifier) {
val countryName: String = viewModel.countryDetailScreenState.value.country?.name ?: ""

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = countryName,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigateUp()
                    /* navController.navigate(Screen.MainScreen.route)*/}) {
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
                verticalArrangement = Arrangement.spacedBy(4.dp),

            ) {
                items(1){

                    val country:Country?= viewModel.countryDetailScreenState.value.country
                    if (country != null) {
                        CountryDetailScreen(country)
                    }
                }
            }
        }
    )
}
@Composable
fun CountryDetailScreen(country: Country) {


    Column(modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 24.dp)


    ) {
        CountryImage(country)

        Spacer(modifier = Modifier.height(24.dp))

        CountryDetailsSectionOne(country = country)

        Spacer(modifier = Modifier.height(24.dp))

        CountryDetailsSectionTwo(country = country)

        Spacer(modifier = Modifier.height(24.dp))

        CountryDetailsSectionThree(country = country)

        Spacer(modifier = Modifier.height(24.dp))

        CountryDetailsSectionFour(country = country)
        Spacer(modifier = Modifier.height(64.dp))
    }
}


@Composable
fun CountryImage(country: Country) {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .decoderFactory(SvgDecoder.Factory())
            .data(country.flagImageSVG)
            .size(height = 200, width = 380) // Set the target size to load the image at.
            .build()
    )
    Image(
        painter = painter,
        contentDescription = null,
        modifier = Modifier
            .size(height = 190.dp, width = 400.dp)
            .clip(shape = RoundedCornerShape(8.dp)),
        contentScale = ContentScale.FillBounds
    )
}

@Composable
fun CountryDetailsSectionOne(country: Country){
    Column {
        Text(text = buildAnnotatedString {
            withStyle(style = SpanStyle( fontWeight = FontWeight.Medium)) {
                append(stringResource(id = R.string.population))
            }
            append(" ")
            withStyle(style = SpanStyle( fontWeight = FontWeight.Light)){
                append(country.population.toString())
            }
        })

        Text(text = buildAnnotatedString {
            withStyle(style = SpanStyle( fontWeight = FontWeight.Medium)) {
                append(stringResource(id = R.string.region))
            }
            append(" ")
            withStyle(style = SpanStyle( fontWeight = FontWeight.Light)){
                append(country.region)
            }
        }, modifier = Modifier.padding(top = 4.dp))

        Text(text = buildAnnotatedString {
            withStyle(style = SpanStyle( fontWeight = FontWeight.Medium)) {
                append(stringResource(id = R.string.capital))
            }
            append("   ")
            withStyle(style = SpanStyle( fontWeight = FontWeight.Light)){
                append(country.capital)
            }
        }, modifier = Modifier.padding(top = 4.dp))
    }
}

@Composable
fun CountryDetailsSectionTwo(country: Country){
    Column {
        Text(text = buildAnnotatedString {
            withStyle(style = SpanStyle( fontWeight = FontWeight.Medium)) {
                append(stringResource(id = R.string.official_language))
            }
            append(" ")
            withStyle(style = SpanStyle( fontWeight = FontWeight.Light)){
                append(country.languages)
            }
        })

        Text(text = buildAnnotatedString {
            withStyle(style = SpanStyle( fontWeight = FontWeight.Medium)) {
                append(stringResource(id = R.string.demonym))
            }
            append(" ")
            withStyle(style = SpanStyle( fontWeight = FontWeight.Light)){
                append(country.demonyns)
            }
        }, modifier = Modifier.padding(top = 4.dp))

        Text(text = buildAnnotatedString {
            withStyle(style = SpanStyle( fontWeight = FontWeight.Medium)) {
                append(stringResource(id = R.string.independent))
            }
            append("  ")
            withStyle(style = SpanStyle( fontWeight = FontWeight.Light)){
                append(if (country.independent == true) "Yes" else "No")
            }
        }, modifier = Modifier.padding(top = 4.dp))

        Text(text = buildAnnotatedString {
            withStyle(style = SpanStyle( fontWeight = FontWeight.Medium)) {
                append(stringResource(id = R.string.un_member))
            }
            append("  ")
            withStyle(style = SpanStyle(fontWeight = FontWeight.Light)){
                append(if (country.unMember) "Yes" else "No")
            }
        }, modifier = Modifier.padding(top = 4.dp))
    }
}


@Composable
fun CountryDetailsSectionThree(country: Country){
    Column {
        Text(text = buildAnnotatedString {
            withStyle(style = SpanStyle( fontWeight = FontWeight.Medium)) {
                append(stringResource(id = R.string.area))
            }
            append(" ")
            withStyle(style = SpanStyle( fontWeight = FontWeight.Light)){
                append(country.area + " km2")
            }
        })

        Text(text = buildAnnotatedString {
            withStyle(style = SpanStyle( fontWeight = FontWeight.Medium)) {
                append(stringResource(id = R.string.landlocked))
            }
            append(" ")
            withStyle(style = SpanStyle( fontWeight = FontWeight.Light)){
                append(if (country.isLandLocked) "Yes" else "No")
            }
        }, modifier = Modifier.padding(top = 4.dp))

        Text(text = buildAnnotatedString {
            withStyle(style = SpanStyle( fontWeight = FontWeight.Medium)) {
                append(stringResource(id = R.string.currency))
            }
            append("  ")
            withStyle(style = SpanStyle( fontWeight = FontWeight.Light)){
                if (country.currency != null) {
                    append(country.currency)
                } else {
                    append("None")
                }

            }
        }, modifier = Modifier.padding(top = 4.dp))

        Text(text = buildAnnotatedString {
            withStyle(style = SpanStyle( fontWeight = FontWeight.Medium)) {
                append(stringResource(id = R.string.currency_symbol))
            }
            append("  ")
            withStyle(style = SpanStyle(fontWeight = FontWeight.Light)){
                if (country.currencySymbol != null) {
                    append(country.currencySymbol)
                } else {
                    append("None")
                }
            }
        }, modifier = Modifier.padding(top = 4.dp))
    }
}


@Composable
fun CountryDetailsSectionFour(country: Country){
    Column {
        Text(text = buildAnnotatedString {
            withStyle(style = SpanStyle( fontWeight = FontWeight.Medium)) {
                append(stringResource(id = R.string.time_zone))
            }
            append(" ")
            withStyle(style = SpanStyle( fontWeight = FontWeight.Light)){
                append(country.timeZone)
            }
        })

        Text(text = buildAnnotatedString {
            withStyle(style = SpanStyle( fontWeight = FontWeight.Medium)) {
                append(stringResource(id = R.string.dialling_code))
            }
            append(" ")
            withStyle(style = SpanStyle( fontWeight = FontWeight.Light)){
                append(country.diallingCode)
            }
        }, modifier = Modifier.padding(top = 4.dp))

        Text(text = buildAnnotatedString {
            withStyle(style = SpanStyle( fontWeight = FontWeight.Medium)) {
                append(stringResource(id = R.string.driving_side))
            }
            append("  ")
            withStyle(style = SpanStyle( fontWeight = FontWeight.Light)){
                append(country.drivingSide)


            }
        }, modifier = Modifier.padding(top = 4.dp))

        Text(text = buildAnnotatedString {
            withStyle(style = SpanStyle( fontWeight = FontWeight.Medium)) {
                append(stringResource(id = R.string.geographical_location))
            }
            append("  ")
            withStyle(style = SpanStyle( fontWeight = FontWeight.Light)){

                append(country.geographicalLocation)

            }
        }, modifier = Modifier.padding(top = 4.dp))
    }
}