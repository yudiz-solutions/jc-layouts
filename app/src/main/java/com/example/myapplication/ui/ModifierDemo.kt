package com.example.myapplication.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

class ModifierDemo {
    @Composable
    fun ModifierExample() {
        Text(
            "Hello World",
            Modifier
                .background(color = Color.Cyan)
                .border(4.dp, Color.Blue)
                .padding(15.dp)
                .border(2.dp, Color.Green)
                .padding(30.dp)
                .border(2.dp, Color.Red)
                .padding(80.dp)
                .border(2.dp, Color.Black)
                .padding(4.dp)
        )
    }
}