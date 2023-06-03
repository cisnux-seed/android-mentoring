package dev.cisnux.dicodingmentoring.ui.matchmentoring

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TriStateCheckbox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.cisnux.dicodingmentoring.R
import dev.cisnux.dicodingmentoring.ui.MainViewModel
import dev.cisnux.dicodingmentoring.ui.registerprofile.parentState
import dev.cisnux.dicodingmentoring.ui.registerprofile.rememberLearningPathsCheckBoxState
import dev.cisnux.dicodingmentoring.ui.theme.DicodingMentoringTheme
import dev.cisnux.dicodingmentoring.utils.CheckBoxItem

@Composable
fun MatchMakingScreen(
    navigateUp: () -> Unit,
    mainViewModel: MainViewModel,
    modifier: Modifier = Modifier,
    matchMakingViewModel: MatchMakingViewModel = hiltViewModel()
) {
    val oneTimeUpdateState by rememberUpdatedState(mainViewModel::updateBottomState)
    LaunchedEffect(Unit) {
        oneTimeUpdateState(false)
    }
    val problem by matchMakingViewModel.problem
    val experienceLevel by matchMakingViewModel.experienceLevel
    var checkBoxState by rememberLearningPathsCheckBoxState()

    MatchmakingContent(
        modifier = modifier,
        navigateUp = navigateUp,
        body = { innerPadding ->
            MatchmakingBody(
                experienceOptions = listOf(
                    stringResource(R.string.beginner),
                    stringResource(R.string.intermediate),
                    stringResource(
                        R.string.expert
                    )
                ),
                experienceLevel = experienceLevel,
                problem = problem,
                onProblemChanged = matchMakingViewModel::onProblemChanged,
                checkedParentState = checkBoxState.parentState,
                checkBoxes = listOf(
                    CheckBoxItem(title = stringResource(id = R.string.android),
                        checked = checkBoxState.checkBox1State,
                        onCheckedChange = { checked, title ->
                            checkBoxState = checkBoxState.copy(
                                checkBox1State = checked
                            )
                            matchMakingViewModel.addLearningPath(
                                checked = checked,
                                interest = title
                            )
                        }),
                    CheckBoxItem(title = stringResource(id = R.string.ios),
                        checked = checkBoxState.checkBox2State,
                        onCheckedChange = { checked, title ->
                            checkBoxState = checkBoxState.copy(
                                checkBox2State = checked
                            )
                            matchMakingViewModel.addLearningPath(
                                checked = checked,
                                interest = title
                            )
                        }),
                    CheckBoxItem(title = stringResource(id = R.string.frontend),
                        checked = checkBoxState.checkBox3State,
                        onCheckedChange = { checked, title ->
                            checkBoxState = checkBoxState.copy(
                                checkBox3State = checked
                            )
                            matchMakingViewModel.addLearningPath(
                                checked = checked,
                                interest = title
                            )
                        }),
                    CheckBoxItem(title = stringResource(id = R.string.backend),
                        checked = checkBoxState.checkBox4State,
                        onCheckedChange = { checked, title ->
                            checkBoxState = checkBoxState.copy(
                                checkBox4State = checked
                            )
                            matchMakingViewModel.addLearningPath(
                                checked = checked,
                                interest = title
                            )
                        }),
                    CheckBoxItem(title = stringResource(id = R.string.cloud_computing),
                        checked = checkBoxState.checkBox5State,
                        onCheckedChange = { checked, title ->
                            checkBoxState = checkBoxState.copy(
                                checkBox5State = checked
                            )
                            matchMakingViewModel.addLearningPath(
                                checked = checked,
                                interest = title
                            )
                        }),
                    CheckBoxItem(title = stringResource(id = R.string.machine_learning),
                        checked = checkBoxState.checkBox6State,
                        onCheckedChange = { checked, title ->
                            checkBoxState = checkBoxState.copy(
                                checkBox6State = checked
                            )
                            matchMakingViewModel.addLearningPath(
                                checked = checked,
                                interest = title
                            )
                        }),
                    CheckBoxItem(title = stringResource(id = R.string.ui_ux),
                        checked = checkBoxState.checkBox7State,
                        onCheckedChange = { checked, title ->
                            checkBoxState = checkBoxState.copy(
                                checkBox7State = checked
                            )
                            matchMakingViewModel.addLearningPath(
                                checked = checked,
                                interest = title
                            )
                        }),
                ),
                onExperienceLevelOption = matchMakingViewModel::onExperienceLevelOption,
                onCreateMentoring = {},
                modifier = Modifier.padding(innerPadding),
            )
        }
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MatchmakingContentPreview() {
    Surface {
        DicodingMentoringTheme {
            MatchmakingContent(
                navigateUp = {},
                body = {}
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MatchmakingContent(
    navigateUp: () -> Unit,
    body: @Composable (innerPadding: PaddingValues) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    IconButton(onClick = navigateUp) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "back to home page"
                        )
                    }
                },
                title = {
                    Text(text = "Find Mentor")
                }
            )
        },
        modifier = modifier,
    ) { innerPadding ->
        body(innerPadding)
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun MatchmakingBodyPreview() {
    Surface {
        DicodingMentoringTheme {
            MatchmakingBody(
                experienceOptions = listOf(
                    stringResource(R.string.beginner),
                    stringResource(R.string.intermediate),
                    stringResource(R.string.expert)
                ),
                experienceLevel = "",
                onExperienceLevelOption = {},
                problem = "",
                onProblemChanged = {},
                checkBoxes = listOf(
                    CheckBoxItem(
                        title = stringResource(id = R.string.android),
                        checked = false,
                        onCheckedChange = { _, _ -> }),
                    CheckBoxItem(
                        title = stringResource(id = R.string.ios),
                        checked = false,
                        onCheckedChange = { _, _ -> }),
                    CheckBoxItem(
                        title = stringResource(id = R.string.frontend),
                        checked = false,
                        onCheckedChange = { _, _ -> }),
                    CheckBoxItem(
                        title = stringResource(id = R.string.backend),
                        checked = false,
                        onCheckedChange = { _, _ -> }),
                    CheckBoxItem(
                        title = stringResource(id = R.string.machine_learning),
                        checked = false,
                        onCheckedChange = { _, _ -> }),
                    CheckBoxItem(
                        title = stringResource(id = R.string.cloud_computing),
                        checked = false,
                        onCheckedChange = { _, _ -> }),
                    CheckBoxItem(
                        title = stringResource(id = R.string.ui_ux),
                        checked = false,
                        onCheckedChange = { _, _ -> }),
                ),
                onCreateMentoring = {},
                checkedParentState = ToggleableState.Off
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MatchmakingBody(
    experienceOptions: List<String>,
    experienceLevel: String,
    problem: String,
    onCreateMentoring: () -> Unit,
    onProblemChanged: (String) -> Unit,
    checkedParentState: ToggleableState,
    checkBoxes: List<CheckBoxItem>,
    onExperienceLevelOption: (experienceLevel: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    var isExpanded by remember {
        mutableStateOf(false)
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
    ) {
        OutlinedTextField(
            value = problem,
            onValueChange = onProblemChanged,
            modifier = Modifier
                .fillMaxWidth(),
            placeholder = {
                Text(text = "Enter your problem")
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.onBackground,
                unfocusedBorderColor = MaterialTheme.colorScheme.onBackground,
                disabledBorderColor = MaterialTheme.colorScheme.onBackground,
            ),
        )
        Spacer(modifier = Modifier.height(8.dp))
        ExposedDropdownMenuBox(
            expanded = isExpanded,
            onExpandedChange = {
                isExpanded = it
            }
        ) {
            OutlinedTextField(
                value = experienceLevel,
                onValueChange = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                maxLines = 1,
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded) },
                placeholder = {
                    Text(text = "Experience Level")
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.onBackground,
                    unfocusedBorderColor = MaterialTheme.colorScheme.onBackground,
                    disabledBorderColor = MaterialTheme.colorScheme.onBackground,
                ),
            )
            ExposedDropdownMenu(
                expanded = isExpanded,
                onDismissRequest = {
                    isExpanded = false
                }
            ) {
                experienceOptions.forEach { selectionOption ->
                    DropdownMenuItem(
                        text = { Text(selectionOption) },
                        onClick = {
                            onExperienceLevelOption(selectionOption)
                            isExpanded = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
        ) {
            TriStateCheckbox(state = checkedParentState, onClick = {
                val state = checkedParentState != ToggleableState.On
                // check all checkboxes
                checkBoxes.forEach {
                    it.onCheckedChange(state, it.title)
                }
            })
            Spacer(modifier = Modifier.width(2.dp))
            Text(
                text = "Learning Paths"
            )
        }
        GridCheckBoxLearningPaths(count = 2, checkBoxes = checkBoxes)
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = onCreateMentoring,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Create Mentoring")
        }
    }
}

@Composable
fun GridCheckBoxLearningPaths(
    count: Int,
    checkBoxes: List<CheckBoxItem>,
    modifier: Modifier = Modifier
) {
    val gridItems = checkBoxes.chunked(count)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 12.dp)
    ) {
        gridItems.forEach {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                it.forEach { checkBoxItem ->
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Checkbox(checked = checkBoxItem.checked, onCheckedChange = {
                            checkBoxItem.onCheckedChange(it, checkBoxItem.title)
                        })
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(
                            text = checkBoxItem.title
                        )
                    }
                }
            }
        }
    }
}
