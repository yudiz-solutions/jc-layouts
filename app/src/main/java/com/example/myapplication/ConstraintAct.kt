package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import com.example.myapplication.ui.theme.MyApplicationTheme

class ConstraintAct : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    ConstraintDemo()
                }
            }
        }
    }
}

@Composable
fun ConstraintDemo() {
    val constraints = ConstraintSet {
        val image = createRefFor("image")
        val circle_iv = createRefFor("circle_iv")
        val outLineButton = createRefFor("outline_btn")
        val btn = createRefFor("btn")

        constrain(image) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            width = Dimension.fillToConstraints
            height = Dimension.value(250.dp)
        }

        constrain(circle_iv) {
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            top.linkTo(image.bottom)
            bottom.linkTo(image.bottom)
            width = Dimension.value(100.dp)
            height = Dimension.value(100.dp)
        }

        constrain(outLineButton) {
            end.linkTo(parent.end)
            top.linkTo(image.bottom)
        }

        constrain(btn) {
            start.linkTo(parent.start)
            top.linkTo(image.bottom)
        }
    }
    ConstraintLayout(constraints, modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = null,
            modifier = Modifier.layoutId("image"),
            contentScale = ContentScale.Crop
        )
        ImageCard(
            painter = painterResource(id = R.drawable.dog),
            modifier = Modifier
                .size(60.dp)
                .layoutId("circle_iv"),
        )
        OutlineBtn(
            text = "Hello!!",
            modifier = Modifier
                .layoutId("outline_btn")
                .offset(x = (-10).dp, y = 10.dp)
        )
        Btn(
            text = "Hii!!", modifier = Modifier
                .layoutId("btn")
                .offset(x = (10).dp, y = 10.dp)
        )
    }
}

@Composable
fun ImageCard(
    painter: Painter,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {

        Image(
            modifier = Modifier
                .fillMaxSize()
                .clip(CircleShape),
            painter = painter,
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )

        Card(
            modifier = Modifier
                .clip(CircleShape)
                .background(Color.Gray)
                .align(Alignment.BottomEnd),
            elevation = 8.dp
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_photo_camera_24),
                modifier = Modifier
                    .padding(4.dp),
                contentDescription = null
            )
        }
    }
}

@Composable
fun OutlineBtn(modifier: Modifier = Modifier, text: String) {
    OutlinedButton(modifier = modifier, onClick = {}) {
        Text(text = text)
    }
}

@Composable
fun Btn(modifier: Modifier = Modifier, text: String) {
    Button(modifier = modifier, onClick = {}) {
        Text(text = text)
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    MyApplicationTheme {
        ConstraintDemo()
    }
}
