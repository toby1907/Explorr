package com.example.explorr.ui.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.explorr.ui.MainViewModel

@Composable
fun FilterScreen(close:()->Unit, viewModel: MainViewModel) {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(color = MaterialTheme.colorScheme.background)
        .padding(start = 24.dp, end = 24.dp, top = 24.dp)
        .verticalScroll(rememberScrollState())) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Filter", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onBackground)
            Icon(imageVector = Icons.Default.Clear, contentDescription = "", modifier = Modifier.clickable {
               close.invoke()
            }, tint = MaterialTheme.colorScheme.onBackground)
        }
        Spacer(modifier = Modifier.height(24.dp))
        ContinentFilterSection(viewModel.continentList){index, isSelected ->
            viewModel.setContinentSelectedAtIndex(index, isSelected)
        }
        Spacer(modifier = Modifier.height(24.dp))
        TimeZoneFilterSection(viewModel.timeZoneList){index, isSelected ->
            viewModel.setTimeZoneSelectedAtIndex(index, isSelected)
        }

        Spacer(modifier = Modifier.height(24.dp))

        FilterButtonSection(
            onRestButtonClicked = {
                viewModel.resetCountryListFilter()
                close.invoke()
            },
            onShowResultButtonClicked = {
                viewModel.applyFiltersToCountryList()
                close.invoke()
            }
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun ContinentFilterSection(
    continents: List<MainViewModel.Continent>,
    onContinentSelectedAtIndex: (Int, Boolean) -> Unit
){

    var isExpanded by remember { mutableStateOf(false) }
    Column(modifier = Modifier
        .fillMaxWidth()
        .wrapContentSize()) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .clickable {
                isExpanded = !isExpanded
            }, horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment
        = Alignment.CenterVertically) {
            Text(text = "Continent", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
            Icon(imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown, contentDescription =
            "", tint = MaterialTheme.colorScheme.onBackground)
        }
        Spacer(modifier = Modifier.height(16.dp))

        AnimatedVisibility(visible = isExpanded) {
            Column() {

                continents.forEachIndexed {  index, continent ->
                    Column(modifier = Modifier.wrapContentSize()) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Text(text = continent.name, color = MaterialTheme.colorScheme.onBackground)
                            Checkbox(
                                checked =continent.isSelected,
                                onCheckedChange = {
                                    onContinentSelectedAtIndex(index, it)
                                },
                                enabled = true,
                                colors =
                                CheckboxDefaults.colors(
                                    checkedColor = MaterialTheme.colorScheme.onBackground,
                                    uncheckedColor = MaterialTheme.colorScheme.onBackground,
                                    checkmarkColor = MaterialTheme.colorScheme.background
                                )
                            )

                        }
                    }

                }
            }

        }

    }
}


@Composable
fun TimeZoneFilterSection(
    timeZones: List<MainViewModel.TimeZone>,
    onTimeZoneSelectedAtIndex: (Int, Boolean) -> Unit
){

    var isExpanded by remember { mutableStateOf(false) }
    Column(modifier = Modifier.fillMaxWidth()) {

        Row(modifier = Modifier
            .fillMaxWidth()
            .clickable {
                isExpanded = !isExpanded
            }, horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment
        = Alignment.CenterVertically) {
            Text(text = "Time Zone",  style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
            Icon(imageVector =  if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                contentDescription = "", tint = MaterialTheme.colorScheme.onBackground)
        }
        Spacer(modifier = Modifier.height(16.dp))
        AnimatedVisibility(visible = isExpanded) {
            Column {
                timeZones.forEachIndexed { index, timezone ->
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentSize()) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Text(text = timezone.timeZone, color = MaterialTheme.colorScheme.onBackground)
                            Checkbox(
                                checked = timezone.isSelected,
                                onCheckedChange = { onTimeZoneSelectedAtIndex(index, it) },
                                enabled = true,
                                colors = CheckboxDefaults.colors(
                                    checkedColor = Color.Black,
                                    uncheckedColor = Color.DarkGray,
                                    checkmarkColor = Color.White
                                )
                            )

                        }
                    }

                }
            }

        }


    }
}

@Composable
fun FilterButtonSection(onRestButtonClicked: () -> Unit, onShowResultButtonClicked: () -> Unit) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        OutlinedButton(modifier = Modifier.size(height = 48.dp, width = 104.dp),
            onClick = onRestButtonClicked,
            border = BorderStroke(2.dp, color = MaterialTheme.colorScheme.onBackground)) {
            Text(text = "Reset", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onBackground)
        }

        Button(modifier = Modifier.size(height = 48.dp, width = 200.dp),onClick = onShowResultButtonClicked) {
            Text(text = "Show results",style = MaterialTheme.typography.labelMedium, color = Color.White)
        }
    }
}