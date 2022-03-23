package com.metehanbolat.freezerowandcolumncompose

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ParentDataModifier
import androidx.compose.ui.unit.Density
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class Event(
    val name: String,
    val color: Color,
    val start: LocalDateTime,
    val end: LocalDateTime,
    val desc: String? = null
)

val EventTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("h:mm a")
val DayFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("EE, MMM d")
val HourFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("h a")

private class EventDataModifier(
    val event: Event
): ParentDataModifier {
    override fun Density.modifyParentData(parentData: Any?) = event
}

fun Modifier.eventData(event: Event) = this.then(EventDataModifier(event))

val sampleEvents = listOf(
    Event(
        name = "Jetpack Compose",
        color = Color(0xFFAFBBF2),
        start = LocalDateTime.parse("2022-01-12T09:00:00"),
        end = LocalDateTime.parse("2022-01-12T11:00:00"),
        desc = "Using modern design practices, Jetpack libraries enable fewer crashes and memory leaks.",
    ),
    Event(
        name = "Metehan Bolat",
        color = Color(0xFFAFBBF2),
        start = LocalDateTime.parse("2022-01-12T11:15:00"),
        end = LocalDateTime.parse("2022-01-12T12:15:00"),
        desc = "Learn about the latest updates to Jetpack Compose Everyday in our Channel.",
    ),
    Event(
        name = "What's new in Android",
        color = Color(0xFF1B998B),
        start = LocalDateTime.parse("2022-01-13T12:30:00"),
        end = LocalDateTime.parse("2022-01-13T15:00:00"),
        desc = "You can easily read and send new messages directly from apps like WhatsApp or Messages — now available globally.",
    ),
    Event(
        name = "Jetpack Compose MVVM",
        color = Color(0xFFF4BFDB),
        start = LocalDateTime.parse("2022-01-14T09:30:00"),
        end = LocalDateTime.parse("2022-01-14T11:00:00"),
        desc = "If you use the Architecture Components ViewModel library, you can access a ViewModel from any composable by calling the viewModel() function.",
    ),
    Event(
        name = "Jetpack Compose Room",
        color = Color(0xFF6DD3CE),
        start = LocalDateTime.parse("2022-01-14T11:00:00"),
        end = LocalDateTime.parse("2022-01-14T12:15:00"),
        desc = "Room is a Database Object Mapping library that makes it easy to access database on Android applications.",
    ),
    Event(
        name = "Jetpack Compose Basics",
        color = Color(0xFF1B998B),
        start = LocalDateTime.parse("2022-01-15T12:00:00"),
        end = LocalDateTime.parse("2022-01-15T13:00:00"),
        desc = "Jetpack Compose is the latest Google bet on a modern UI toolkit for Android Development.",
    )
)