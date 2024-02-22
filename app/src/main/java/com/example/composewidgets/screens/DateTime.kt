package com.example.composewidgets.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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

    val date by remember { mutableStateOf(LocalDate.now()) }
    val dateState = rememberDatePickerState()

    var isOpenDate by remember { mutableStateOf(false) }

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
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(20.dp))
        Text(text = dateState.selectedDateMillis.dateFormat())

        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = { isOpenDate = true }) {
            Text(text = "DatePicker")
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
