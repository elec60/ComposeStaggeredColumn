package com.mousavi.composestaggerdgridcolumn

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.core.util.rangeTo
import com.mousavi.composestaggerdgridcolumn.ui.theme.ComposeStaggerdGridColumnTheme
import kotlin.math.max

val topics = listOf(
    "Arts & Crafts Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. \nNewLine\n\nnew line",
    "Arts & Crafts \nNewLine\n\nnew line",
    "Beauty",
    "Books",
    "Business",
    "Comics",
    "Culinary",
    "Design",
    "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
    "Film",
    "History",
    "Maths",
    "Music",
    "People",
    "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
    "Religion",
    "Social sciences",
    "Technology",
    "TV",
    "Writing",
    "Arts & Crafts \nNewLine\n\nnew line",
    "Beauty",
    "Books",
    "Business",
    "Comics",
    "Culinary",
    "Design",
    "Fashion",
    "Film",
    "History",
    "Maths",
    "Music",
    "People",
    "Philosophy",
    "Religion",
    "Social sciences",
    "Technology",
    "TV",
    "Writing",
    "Arts & Crafts \nNewLine\n\nnew line",
    "Beauty",
    "Books",
    "Business",
    "Comics",
    "Culinary",
    "Design",
    "Fashion",
    "Film",
    "History",
    "Maths",
    "Music",
    "People",
    "Philosophy",
    "Religion",
    "Social sciences",
    "Technology",
    "TV",
    "Writing",
    "Arts & Crafts \nNewLine\n\nnew line",
    "Beauty",
    "Books",
    "Business",
    "Comics",
    "Culinary",
    "Design",
    "Fashion",
    "Film",
    "History",
    "Maths",
    "Music",
    "People",
    "Philosophy",
    "Religion",
    "Social sciences",
    "Technology",
    "TV",
    "Writing",
    "Arts & Crafts \nNewLine\n\nnew line",
    "Beauty",
    "Books",
    "Business",
    "Comics",
    "Culinary",
    "Design",
    "Fashion",
    "Film",
    "History",
    "Maths",
    "Music",
    "People",
    "Philosophy",
    "Religion",
    "Social sciences",
    "Technology",
    "TV",
    "Writing",
    "Arts & Crafts \nNewLine\n\nnew line",
    "Beauty",
    "Books",
    "Business",
    "Comics",
    "Culinary",
    "Design",
    "Fashion",
    "Film",
    "History",
    "Maths",
    "Music",
    "People",
    "Philosophy",
    "Religion",
    "Social sciences",
    "Technology",
    "TV",
    "Writing",
    "Arts & Crafts \nNewLine\n\nnew line",
    "Beauty",
    "Books",
    "Business",
    "Comics",
    "Culinary",
    "Design",
    "Fashion",
    "Film",
    "History",
    "Maths",
    "Music",
    "People",
    "Philosophy",
    "Religion",
    "Social sciences",
    "Technology",
    "TV",
    "Writing",
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeStaggerdGridColumnTheme {
                Surface(color = MaterialTheme.colors.background) {

                    val size = remember {
                        mutableStateOf(IntSize.Zero)
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .onGloballyPositioned {
                                size.value = it.size
                            },
                        contentAlignment = Alignment.TopCenter
                    ) {
                        val columns = 3
                        StaggerdGridColumn(
                            columns = columns
                        ) {
                            topics.forEach {
                                Chip(
                                    text = it,
                                    modifier = Modifier
                                        .width(with(LocalDensity.current) { (size.value.width / columns).toDp() })
                                        .padding(8.dp),
                                )
                            }
                        }
                    }

                }
            }
        }
    }
}

@Composable
fun StaggerdGridColumn(
    modifier: Modifier = Modifier,
    columns: Int = 3,
    content: @Composable () -> Unit,
) {
    Layout(content = content, modifier = modifier) { measurables, constraints ->
        val columnWidths = IntArray(columns) { 0 }
        val columnHeights = IntArray(columns) { 0 }

        val placables = measurables.mapIndexed { index, measurable ->
            val placable = measurable.measure(constraints)

            val col = index % columns
            columnHeights[col] += placable.height
            columnWidths[col] = max(columnWidths[col], placable.width)
            placable
        }

        val height = columnHeights.maxOrNull()
            ?.coerceIn(constraints.minHeight.rangeTo(constraints.maxHeight))
            ?: constraints.minHeight

        val width =
            columnWidths.sumOf { it }.coerceIn(constraints.minWidth.rangeTo(constraints.maxWidth))

        val colX = IntArray(columns) { 0 }
        for (i in 1 until columns) {
            colX[i] = colX[i - 1] + columnWidths[i - 1]
        }

        layout(width, height) {
            val colY = IntArray(columns) { 0 }
            placables.forEachIndexed { index, placeable ->
                val col = index % columns
                placeable.placeRelative(
                    x = colX[col],
                    y = colY[col]
                )
                colY[col] += placeable.height
            }
        }
    }

}

@Composable
fun Chip(modifier: Modifier = Modifier, text: String) {
    Card(
        modifier = modifier,
        border = BorderStroke(color = Color.Black, width = 1.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = 10.dp
    ) {
        Column(
            modifier = Modifier.padding(start = 8.dp, top = 4.dp, end = 8.dp, bottom = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(16.dp, 16.dp)
                    .background(color = MaterialTheme.colors.secondary)
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = text,
                style = TextStyle(color = Color.DarkGray, textAlign = TextAlign.Center)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeStaggerdGridColumnTheme {
        StaggerdGridColumn {
            topics.forEach {
                Chip(text = it, modifier = Modifier.padding(8.dp))
            }
        }
    }
}