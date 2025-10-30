package com.example.pmdm.manana

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true)
@Composable
fun Demo() {
    var isEnabled by remember { mutableStateOf(false) }
    var counter by remember { mutableIntStateOf(0) }

    Column{
        Text(
            text = "Contador: ${counter}",
            modifier = Modifier.clickable { counter++ }
        )
        Switch(isEnabled, onCheckedChange = {isEnabled = it})
    }

}

@Preview
@Composable
fun UserTextFile(){

    var text by remember { mutableStateOf("hola") }

    TextField(
        value = text,
        onValueChange = { text = it}
    )
}

@Preview
@Composable
fun Login() {
    Column {
        var username by remember { mutableStateOf("brandon") }
        var password by remember { mutableStateOf("password") }

        var message by remember { mutableStateOf("") }

        MyTextField(username) {username = it}
        MyTextField(password) {password = it}
        Button(onClick = {message = "${username} y ${password}"}){
            Text(text = "login")
        }
        Text(message)
    }
}

@Composable
fun MyTextField(text: String, onTextChange: (String) -> Unit) {
    TextField(
        value = text,
        onValueChange = onTextChange
    )
}