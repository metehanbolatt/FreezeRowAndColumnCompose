package com.metehanbolat.freezerowandcolumncompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.metehanbolat.freezerowandcolumncompose.ui.theme.FreezeRowAndColumnComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FreezeRowAndColumnComposeTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(
                                    text = "Freeze Row's & Column's",
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Center
                                )
                            },
                            actions = {
                                Icon(
                                    imageVector = Icons.Outlined.MoreVert,
                                    contentDescription = "More"
                                )
                            }
                        )
                    }
                ) {
                    FreezedRowAndColumn(sampleEvents)
                }
            }
        }
    }
}

@Composable
fun FreezedRowAndColumn(
    events: List<Event>
) {

}

@Composable
fun BasicEvent(
    event: Event,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(end = 2.dp, bottom = 2.dp)
            .background(color = event.color, shape = RoundedCornerShape(4.dp))
            .padding(4.dp)
    ) {
        Text(
            text = "${event.start.format(EventTimeFormatter)} - ${event.end.format(EventTimeFormatter)}",
            style = MaterialTheme.typography.caption
        )
        Text(
            text = event.name,
            style = MaterialTheme.typography.body1,
            fontWeight = FontWeight.Bold
        )
        if (event.desc != null) {
            Text(
                text = event.desc,
                style = MaterialTheme.typography.body2,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}