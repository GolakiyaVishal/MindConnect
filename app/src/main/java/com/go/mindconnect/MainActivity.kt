package com.go.mindconnect

import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.go.mindconnect.ui.theme.MindConnectTheme
import java.util.*

class MainActivity : ComponentActivity() {

    private lateinit var mMediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mMediaPlayer = MediaPlayer.create(this, R.raw.wolf_howling)

        setContent {
            MindConnectTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainContent(mMediaPlayer)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainContent(mMediaPlayer: MediaPlayer) {
    Scaffold(topBar = {
        CenterAlignedTopAppBar(
            title = { Text("FOCUS") },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                titleContentColor = MaterialTheme.colorScheme.error
            ),
        )
    }, bottomBar = {
        BottomAppBar {
            BottomContent(mMediaPlayer)
        }
    }) { paddingValues ->
        Body(paddingValues)
    }
}

@Composable
fun Body(paddingValues: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BodyText("Keep Doing It...")
        BodyText("This is the Way.")
    }
}

@Composable
fun BottomContent(mMediaPlayer: MediaPlayer) {
    var timer: Timer? = null

    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        BottomButton(label = "Start") {
            timer = Timer()
            timer!!.scheduleAtFixedRate(WolfTimer(mMediaPlayer), 0, 20000)
        }
        BottomButton(label = "Stop") {
            timer?.cancel()
        }
    }
}

@OptIn(ExperimentalTextApi::class)
@Composable
fun BodyText(text: String) {
    Text(
        text = text,
        style = LocalTextStyle.current.merge(
            TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                lineHeight = 2.5.em,
                platformStyle = PlatformTextStyle(includeFontPadding = false),
                lineHeightStyle = LineHeightStyle(
                    alignment = LineHeightStyle.Alignment.Center,
                    trim = LineHeightStyle.Trim.None
                )
            )
        )
    )
}

@Composable
fun BottomButton(label: String, onTap: () -> Unit) {
    TextButton(onClick = { onTap() }) {
        Text(text = label, fontSize = 18.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MindConnectTheme {
        MainContent(MediaPlayer())
    }
}