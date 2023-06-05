package dev.cisnux.dicodingmentoring.ui.findmentor

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.cisnux.dicodingmentoring.R
import dev.cisnux.dicodingmentoring.ui.theme.DicodingMentoringTheme
import java.util.Calendar

@Composable
fun FindMentorScreen() {
    Column(modifier = Modifier
        .fillMaxSize(),
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp, top = 24.dp)) {
            Text(
                text = "Mentoring",
                fontWeight = FontWeight.SemiBold,
                fontSize = 26.sp
            )
        }
        Column(modifier = Modifier.padding(36.dp)) {
            SelectDate()
            Spacer(modifier = Modifier.height(24.dp))
            SelectLearningPath()
            Spacer(modifier = Modifier.height(24.dp))
            SelectedCourse()
            Spacer(modifier = Modifier.height(24.dp))
            ProblemItem()
            Spacer(modifier = Modifier.height(28.dp))
            FindMentorButton()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectDate() {
    var date: String by rememberSaveable { mutableStateOf("")}
    val year: Int
    val month: Int
    val day: Int
    val mCalendar: Calendar = Calendar.getInstance()
    year = mCalendar.get(Calendar.YEAR)
    month = mCalendar.get(Calendar.MONTH)
    day = mCalendar.get(Calendar.DAY_OF_MONTH)

    val mDatePickerDialog = DatePickerDialog(
        LocalContext.current,
        { _:DatePicker, year: Int, month: Int, day: Int ->
            date = "$day/${month+1}/$year"
        },year,month,day
    )
        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = date,
                onValueChange = { date = it },
                readOnly = true,
                label = { Text("Date")},
                trailingIcon = {
                    IconButton(onClick = { mDatePickerDialog.show() }) {
                        Icon(
                            imageVector = Icons.Filled.DateRange,
                            contentDescription = null)
                    }
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                    disabledBorderColor = MaterialTheme.colorScheme.primary,
                ),
                modifier = Modifier.fillMaxWidth()
            )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectLearningPath() {
    val learningPathItem = arrayOf("Choose Learning Path","Android", "Backend", "FrontEnd", "Machine Learning", "Cloud Computing")
    var isExpanded by remember {
        mutableStateOf(false)
    }
    var selectedLearningPath by remember {
        mutableStateOf(learningPathItem[0])
    }

    Box(modifier = Modifier.fillMaxWidth()) {
        ExposedDropdownMenuBox(expanded = isExpanded, onExpandedChange = { isExpanded = !isExpanded}) {
            OutlinedTextField(
                value = selectedLearningPath,
                onValueChange = {},
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                readOnly = true,
                label = {Text("Choose Learning Path")},
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded) },
                supportingText = {
                    if (selectedLearningPath.isEmpty())
                        Text(text = "Choose Learning Path")
                },
                placeholder = {
                    if (selectedLearningPath.isEmpty()) {
                        Text(text = "Choose Learning Path")
                    }
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                    disabledBorderColor = MaterialTheme.colorScheme.primary,
                ),
            )

            ExposedDropdownMenu(
                expanded = isExpanded,
                onDismissRequest = { isExpanded = false}
            ) {
                learningPathItem.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(item)},
                        onClick = {
                            selectedLearningPath = item
                            isExpanded = false
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectedCourse() {
    val courseItem = arrayOf("Choose Course", "Memulai pemrograman Kotlin", "Belajar Android Pemula", "Belajar Pemrograman Javascript", "Memulai Pemrograman Python")
    var isExpanded by remember {
        mutableStateOf(false)
    }
    var selectedCourseItem by remember {
        mutableStateOf(courseItem[0])
    }

    Box(modifier = Modifier.fillMaxWidth()) {
        ExposedDropdownMenuBox(expanded = isExpanded, onExpandedChange = { isExpanded = !isExpanded}) {
            OutlinedTextField(
                value = selectedCourseItem,
                onValueChange = {},
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                readOnly = true,
                label = {Text("Choose Course")},
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded) },
                supportingText = {
                    if (selectedCourseItem.isEmpty())
                        Text(text = "Choose Course")
                },
                placeholder = {
                    if (selectedCourseItem.isEmpty()) {
                        Text(text = "Choose Course")
                    }
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                    disabledBorderColor = MaterialTheme.colorScheme.primary,
                ),
            )

            ExposedDropdownMenu(
                expanded = isExpanded,
                onDismissRequest = { isExpanded = false}
            ) {
                courseItem.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(item)},
                        onClick = {
                            selectedCourseItem = item
                            isExpanded = false
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProblemItem() {
    val text: String by rememberSaveable {
        mutableStateOf("")
    }
    Box(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = text,
            onValueChange = {},
            label = {Text("Problem")},
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                disabledBorderColor = MaterialTheme.colorScheme.primary,
            ),
        )
    }
}

@Composable
fun FindMentorButton() {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Button(
            onClick = {},
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.Center)
                .size(width = 180.dp, height = 60.dp)
                .clip(RoundedCornerShape(24.dp)),
            contentPadding = PaddingValues(8.dp)
        ) {
            Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_hourglass),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Find Mentor",
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FindMentorPreview() {
    DicodingMentoringTheme {
        FindMentorScreen()
    }
}