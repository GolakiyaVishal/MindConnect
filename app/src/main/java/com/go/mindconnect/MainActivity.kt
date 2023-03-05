package com.go.mindconnect

import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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

    var count by remember {
        mutableStateOf(20)
    }

    Scaffold(topBar = {
        CenterAlignedTopAppBar(
            title = { Text("FOCUS") },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                titleContentColor = MaterialTheme.colorScheme.error
            ),
        )
    }, bottomBar = {
        BottomAppBar {
            BottomContent(mMediaPlayer, count)
        }
    }) { paddingValues ->
        Body(paddingValues, count = count, onAddTap = { count += 10 },
            onRemoveTap = {
                if (count > 10) {
                    count -= 10
                }
            })
    }
}

@Composable
fun Body(
    paddingValues: PaddingValues,
    count: Int,
    onAddTap: () -> Unit,
    onRemoveTap: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BodyText("Keep Doing It...")
        BodyText("This is the Way.")
        BodyText2(
            "Tired                —>     Nap \n" +
                    "Sad                   —>     Music \n" +
                    "Stressed          —>     Walk  \n" +
                    "Angry               —>     Exercise \n" +
                    "Burnt out         —>     Read \n" +
                    "Feeling lost     —>     Pray \n" +
                    "Overthinking   —>     Write \n" +
                    "Anxious           —>     Meditate "
        )
        Spacer(modifier = Modifier.weight(1.0f))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TimeButton(Icons.Rounded.Clear) { onRemoveTap() }
            Text(count.toString())
            TimeButton(Icons.Rounded.AddCircle) { onAddTap() }
        }
    }
}

@Composable
fun BottomContent(mMediaPlayer: MediaPlayer, count: Int) {
    var timer: Timer? = null
    val gap = count * 1000

    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        BottomButton(label = "Start") {
            timer = Timer()
            timer!!.scheduleAtFixedRate(WolfTimer(mMediaPlayer), 0, gap.toLong())
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

@OptIn(ExperimentalTextApi::class)
@Composable
fun BodyText2(text: String) {
    Text(
        text = text,
        style = LocalTextStyle.current.merge(
            TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                lineHeight = 1.em,
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

@Composable
fun TimeButton(icon: ImageVector, onTap: () -> Unit) {
    IconButton(onClick = { onTap() }, Modifier.padding(12.dp)) {
        Icon(icon, contentDescription = "Add 10 seconds", Modifier.size(42.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MindConnectTheme {
        MainContent(MediaPlayer())
    }
}