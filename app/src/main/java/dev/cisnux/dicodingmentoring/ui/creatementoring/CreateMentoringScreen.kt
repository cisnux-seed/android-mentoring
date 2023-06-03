package dev.cisnux.dicodingmentoring.ui.creatementoring

import android.os.Build
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.cisnux.dicodingmentoring.R
import dev.cisnux.dicodingmentoring.ui.MainViewModel
import dev.cisnux.dicodingmentoring.ui.theme.DicodingMentoringTheme
import java.time.DayOfWeek
import java.time.Instant
import java.time.ZoneId
import java.util.Calendar
import java.util.TimeZone

@Composable
fun CreateMentoringScreen(
    mainViewModel: MainViewModel,
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit,
    createMentoringViewModel: CreateMentoringViewModel = hiltViewModel(),
) {
    val title by createMentoringViewModel.title
    val description by createMentoringViewModel.description
    val mentoringType by createMentoringViewModel.mentoringType

    CreateMentoringContent(
        modifier = modifier,
        navigateUp = navigateUp,
        body = { innerPadding ->
            CreateMentoringBody(
                title = title,
                onTitleChanged = createMentoringViewModel::onTitleChanged,
                description = description,
                onDescriptionChanged = createMentoringViewModel::onDescriptionChanged,
                onMentoringType = createMentoringViewModel::onMentoringTypeSelected,
                mentoringType = mentoringType,
                radioTitles = listOf(
                    stringResource(id = R.string.chat),
                    stringResource(id = R.string.meet)
                ),
                modifier = Modifier.padding(innerPadding),
            )
        }
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CreateMentoringContentPreview() {
    Surface {
        DicodingMentoringTheme {
            CreateMentoringContent(
                navigateUp = { /*TODO*/ },
                body = { innerPadding ->
                    CreateMentoringBody(
                        title = "",
                        onTitleChanged = {},
                        description = "",
                        onDescriptionChanged = {},
                        onMentoringType = {},
                        mentoringType = "",
                        modifier = Modifier.padding(innerPadding),
                        radioTitles = listOf(
                            stringResource(id = R.string.chat),
                            stringResource(id = R.string.meet)
                        ),
                    )
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateMentoringBody(
    title: String,
    onTitleChanged: (String) -> Unit,
    mentoringType: String,
    description: String,
    onDescriptionChanged: (String) -> Unit,
    onMentoringType: (String) -> Unit,
    radioTitles: List<String>,
    modifier: Modifier = Modifier,
) {
    val openDialog = remember { mutableStateOf(true) }
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
    ) {
        OutlinedTextField(
            value = title,
            onValueChange = onTitleChanged,
            maxLines = 2,
            modifier = Modifier
                .fillMaxWidth(),
            placeholder = {
                Text(text = "Enter your problem title")
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.onBackground,
                unfocusedBorderColor = MaterialTheme.colorScheme.onBackground,
                disabledBorderColor = MaterialTheme.colorScheme.onBackground,
            ),
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = description,
            onValueChange = onDescriptionChanged,
            modifier = Modifier
                .fillMaxWidth(),
            placeholder = {
                Text(text = "Enter your problem description")
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.onBackground,
                unfocusedBorderColor = MaterialTheme.colorScheme.onBackground,
                disabledBorderColor = MaterialTheme.colorScheme.onBackground,
            ),
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Mentoring Type:"
        )
        Column(
            Modifier
                .selectableGroup()
                .padding(start = 12.dp)
        ) {
            radioTitles.forEach { title ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    RadioButton(
                        selected = title == mentoringType,
                        onClick = {
                            onMentoringType(title)
                        },
                        modifier = Modifier.semantics { contentDescription = title }
                    )
                    Text(text = title)
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        if (openDialog.value){
            val datePickerState = rememberDatePickerState()
            val confirmEnabled = remember {
                derivedStateOf { datePickerState.selectedDateMillis != null }
            }

            DatePickerDialog(
                onDismissRequest = {
                    // Dismiss the dialog when the user clicks outside the dialog or on the back
                    // button. If you want to disable that functionality, simply use an empty
                    // onDismissRequest.
                    openDialog.value = false
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            openDialog.value = false
                            datePickerState.selectedDateMillis
                        },
                        enabled = confirmEnabled.value
                    ) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            openDialog.value = false
                        }
                    ) {
                        Text("Cancel")
                    }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateMentoringContent(
    navigateUp: () -> Unit,
    body: @Composable (innerPadding: PaddingValues) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    IconButton(onClick = navigateUp) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "back to detail mentor page"
                        )
                    }
                },
                title = {
                    Text(text = "Create Mentoring")
                }
            )
        },
    ) { innerPadding ->
        body(innerPadding)
    }
}
