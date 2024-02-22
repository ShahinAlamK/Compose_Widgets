package com.example.composewidgets.screens

import android.app.TimePickerDialog
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DateTimeScreen() {

    val context = LocalContext.current
    val date by remember { mutableStateOf(LocalDate.now()) }
    val dateState = rememberDatePickerState()
    val timeState = rememberTimePickerState()

    var isOpenDate by remember { mutableStateOf(false) }
    var isOpenTime by remember { mutableStateOf(false) }
    
    
    if (isOpenTime){

    }

    if (isOpenDate) {
        DatePickerDialog(
            onDismissRequest = { isOpenDate = false },
            confirmButton = {
                TextButton(onClick = { isOpenDate = false }) {
                    Text(text = "Ok")
                }
            },
            dismissButton = {
                TextButton(onClick = { isOpenDate = false }) {
                    Text(text = "Cancel")
                }
            }
        )
        {
            DatePicker(
                state = dateState,
                dateValidator = {timeStamp->
                    val selectDate = Instant.ofEpochMilli(timeStamp).atZone(ZoneId.systemDefault()).toLocalDate()
                    val  currentDate = LocalDate.now(ZoneId.systemDefault())
                    selectDate >=currentDate
                }
            )
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(text = dateState.selectedDateMillis.dateFormat(), fontWeight = FontWeight.Bold, fontSize = 22.sp)

        Spacer(modifier = Modifier.height(10.dp))
        Button(
            shape = MaterialTheme.shapes.small,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            onClick = { isOpenDate = true }) {
            Text(text = "DatePicker")
        }

        Divider(modifier = Modifier.fillMaxWidth().padding(vertical = 20.dp))

        Text(text = dateState.selectedDateMillis.dateFormat(), fontWeight = FontWeight.Bold, fontSize = 22.sp)

        Spacer(modifier = Modifier.height(10.dp))
        Button(
            shape = MaterialTheme.shapes.small,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            onClick = { isOpenDate = true }) {
            Text(text = "Time Picker")
        }

    }
}


@RequiresApi(Build.VERSION_CODES.O)
fun Long?.dateFormat():String{
    val date: LocalDate = this?.let {
        Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault()).toLocalDate()
    }?: LocalDate.now()
    return date.format(DateTimeFormatter.ofPattern("dd:MM:yyyy"))
}
