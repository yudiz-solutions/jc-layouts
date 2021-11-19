package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.ui.theme.MyApplicationTheme


class PhotosGridLayout : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                        Grid(
                            images = listOf(
                                R.drawable.wallpaper5,
                                R.drawable.wallpaper1,
                                R.drawable.wallpaper2,
                                R.drawable.wallpaper3,
                                R.drawable.wallpaper4,
                                R.drawable.wallpaper5,
                                R.drawable.dog
                            ),
                            numberOfColumns = 2
                        )
                    }
                }
            }
        }
    }
}

/**
 * [numberOfColumns] is use for how many columns we want
 */
@Composable
fun Grid(numberOfColumns: Int, images: List<Int>, modifier: Modifier = Modifier) {
    Layout(content = {
        images.subList(0, images.size).forEach {
            Image(
                painter = painterResource(id = it),
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(4.dp))
                    .padding(4.dp),
                contentScale = ContentScale.Crop,
                contentDescription = "Contact profile picture"
            )
        }
    }, modifier = modifier) { measurables, constraints ->

        //use for find width of column
        val columnWidth = (constraints.maxWidth) / numberOfColumns

        //this will use for track my each column's total height
        val columnHeight = IntArray(numberOfColumns) { 0 }
        val imageConstraints = constraints.copy(
            maxWidth = columnWidth
        )

        // List of measured children
        val placeables = measurables.map {
            // column is index of column
            val column = shortestColumn(columnHeights = columnHeight)
            Log.e("index", "Grid: $column")
            val placable = it.measure(imageConstraints)
            // add height for measure total height
            columnHeight[column] += placable.height
            placable
        }

        val height = columnHeight.maxOrNull()
            ?.coerceIn(constraints.minHeight, constraints.maxHeight)
            ?: constraints.minHeight

        layout(constraints.maxWidth, height) {
            // Keep track of the current Y position for each column
            val columnYPointers = IntArray(numberOfColumns) { 0 }

            placeables.forEach { placeable ->
                // Determine which column to place this item in
                val column = shortestColumn(columnYPointers)

                placeable.place(
                    x = columnWidth * column,
                    y = columnYPointers[column],
                )
//                 Update the Y pointer column vise based on the item
//                 we just placed.
                columnYPointers[column] += placeable.height
            }
        }
    }
}

// use for find  shortest column
private fun shortestColumn(columnHeights: IntArray): Int {
    var minHeight = Int.MAX_VALUE
    var column = 0

    columnHeights.forEachIndexed { index, height ->
        if (height < minHeight) {
            minHeight = height
            column = index
        }
    }
    return column
}

@Preview(showBackground = true)
@Composable
fun DefaultPreviewStaggerd() {
    MyApplicationTheme {
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            Grid(
                images = listOf(
                    R.drawable.wallpaper5,
                    R.drawable.wallpaper1,
                    R.drawable.wallpaper2,
                    R.drawable.wallpaper3,
                    R.drawable.wallpaper4,
                    R.drawable.wallpaper5,
                    R.drawable.dog
                ),
                numberOfColumns = 2
            )
        }
    }
}
