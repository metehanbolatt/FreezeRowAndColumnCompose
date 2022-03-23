package com.metehanbolat.freezerowandcolumncompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.metehanbolat.freezerowandcolumncompose.ui.theme.FreezeRowAndColumnComposeTheme
import java.time.LocalDate
import java.time.LocalTime
import java.time.temporal.ChronoUnit
import kotlin.math.roundToInt

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

@Composable
fun BasicDayHeader(
    day: LocalDate,
    modifier: Modifier = Modifier
) {
    Text(
        text = day.format(DayFormatter),
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        modifier = modifier
            .fillMaxSize()
            .padding(4.dp)
    )
}

@Composable
fun ScheduleHeader(
    minDate: LocalDate,
    maxDate: LocalDate,
    dayWidth: Dp,
    modifier: Modifier = Modifier,
    dayHeader: @Composable (day: LocalDate) -> Unit = { BasicDayHeader(day = it) }
) {
    Row(
        modifier = modifier
    ) {
        val numDays = ChronoUnit.DAYS.between(minDate, maxDate).toInt() + 1
        repeat(numDays) { i ->
            Box(
                modifier = Modifier
                    .width(dayWidth)
                    .height(40.dp)
                    .background(Color.Green)
            ) {
                dayHeader(minDate.plusDays(i.toLong()))
            }
        }
    }
}

@Composable
fun ScheduleSidebar(
    hourHeight: Dp,
    modifier: Modifier = Modifier,
    label: @Composable (time: LocalTime) -> Unit = { BasicSidebarLabel(time = it) }
) {
    Column(
        modifier = modifier
    ) {
        val startTime = LocalTime.MIN
        repeat(24) { i ->
            Box(
                modifier = Modifier
                    .height(hourHeight)
                    .width(60.dp)
                    .background(Color.Red)
            ) {
                label(startTime.plusHours(i.toLong()))
            }
        }
    }
}

@Composable
fun BasicSidebarLabel(
    time: LocalTime,
    modifier: Modifier = Modifier
) {
    Text(
        text = time.format(HourFormatter),
        modifier = modifier
            .fillMaxHeight()
            .padding(4.dp),
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )
}

@Composable
fun BasicSchedule(
    events: List<Event>,
    modifier: Modifier = Modifier,
    eventContent: @Composable (event: Event) -> Unit = { BasicEvent(event = it) },
    minDate: LocalDate = events.minByOrNull(Event::start)!!.start.toLocalDate(),
    maxDate: LocalDate = events.maxByOrNull(Event::end)!!.end.toLocalDate(),
    dayWidth: Dp,
    hourHeight: Dp
) {
    val numDays = ChronoUnit.DAYS.between(minDate, maxDate).toInt() + 1
    val dividerColor = if (MaterialTheme.colors.isLight) Color.LightGray else Color.DarkGray
    Layout(
        content = {
            events.sortedBy(Event::start).forEach { event ->
                Box(modifier = Modifier.eventData(event = event)) {
                    eventContent(event)
                }
            }
        },
        modifier = modifier
            .drawBehind {
                repeat(23) {
                    drawLine(
                        color = dividerColor,
                        start = Offset(0f, (it + 1) * hourHeight.toPx()),
                        end = Offset(size.width, (it + 1) * hourHeight.toPx()),
                        strokeWidth = 1.dp.toPx()
                    )
                }
                repeat(numDays - 1) {
                    drawLine(
                        color = dividerColor,
                        start = Offset((it + 1) * dayWidth.toPx(), 0f),
                        end = Offset((it + 1) * dayWidth.toPx(), size.height),
                        strokeWidth = 1.dp.toPx()
                    )
                }
            }
    ) { measureable, constraints ->
        val height = hourHeight.roundToPx() * 24
        val width = dayWidth.roundToPx() * numDays
        val placeablesWithEvents = measureable.map { measurable ->
            val event = measurable.parentData as Event
            val eventDurationMinutes = ChronoUnit.MINUTES.between(event.start, event.end)
            val eventHeight = ((eventDurationMinutes / 60f) * hourHeight.toPx()).roundToInt()
            val placeable = measurable.measure(
                constraints = constraints.copy(
                    minWidth = dayWidth.roundToPx(),
                    maxWidth = dayWidth.roundToPx(),
                    minHeight = eventHeight,
                    maxHeight = eventHeight
                )
            )
            Pair(placeable, event)
        }
        layout(
            width = width,
            height = height
        ) {
            placeablesWithEvents.forEach { (placeable, event) ->
                val eventOffsetMinutes = ChronoUnit.MINUTES.between(LocalTime.MIN, event.start.toLocalTime())
                val eventY = ((eventOffsetMinutes / 60f) * hourHeight.toPx()).roundToInt()
                val eventOffsetDays = ChronoUnit.DAYS.between(minDate, event.start.toLocalDate()).toInt()
                val eventX = eventOffsetDays * dayWidth.roundToPx()
                placeable.place(eventX, eventY)
            }
        }
    }
}