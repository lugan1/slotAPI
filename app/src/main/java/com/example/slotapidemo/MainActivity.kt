package com.example.slotapidemo

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }
}


/**
* 체크박스 체크에 따라 조건에 해당되는 slot 컴포져블이 교체된다.
* */
@Composable
fun MainScreen() {
    var linearSelected by remember { mutableStateOf(true) }
    var imageSelected by remember { mutableStateOf(true) }

    SideEffect {
        Log.d("TEST", "linearSelected: $linearSelected  imageSelected: $imageSelected")
    }

    val onLinearClick = { value: Boolean -> linearSelected = value }
    val onTitleSelected = { value: Boolean -> imageSelected = value }

    ScreenContent(
        linearSelected = linearSelected,
        imageSelected = imageSelected,
        onLinearClick = onLinearClick,
        onTitleClick = onTitleSelected,

        // Slot API 사용
        titleContent = {
            if(imageSelected) {
                TitleImage(drawing = R.drawable.baseline_cloud_download_24)
            }
            else {
                Text(modifier = Modifier.padding(30.dp), style = MaterialTheme.typography.headlineSmall, text = "Downloading")
            }
        },

        // Sloat API 사용
        progressContent = {
            if (linearSelected) {
                LinearProgressIndicator(Modifier.height(40.dp))
            }
            else {
                CircularProgressIndicator(Modifier.size(200.dp), strokeWidth = 18.dp)
            }
        }
    )
}

@Composable
fun ScreenContent(
    // 상태 호이스팅
    linearSelected: Boolean,
    imageSelected: Boolean,
    onTitleClick: (Boolean) -> Unit,
    onLinearClick: (Boolean) -> Unit,

    // Slot API
    titleContent: @Composable () -> Unit,
    progressContent: @Composable () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween) {
        titleContent()
        progressContent()
        CheckBoxes(linearSelected, imageSelected, onTitleClick, onLinearClick)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckBoxes(
    // 상태 호이스팅
    linearSelected: Boolean,
    imageSelected: Boolean,
    onTitleClick: (Boolean) -> Unit,
    onLinearClick: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.padding(20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Checkbox(checked = imageSelected, onCheckedChange = onTitleClick)
        Text(text = "Image Title")
        Spacer(Modifier.width(20.dp))
        Checkbox(checked = linearSelected, onCheckedChange = onLinearClick)
        Text(text = "Linear Progress")
    }
}


@Composable
fun TitleImage(drawing: Int) {
    Image(painter = painterResource(drawing), contentDescription = "title image")
}

@Preview(showSystemUi = true)
@Composable
fun DemoPreview() {
    MainScreen()
}
