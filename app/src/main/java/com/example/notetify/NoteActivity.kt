package com.example.notetify // Make sure this package name is correct!

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class NoteActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Step 1: Get the shared text from the intent.
        // Use Intent.EXTRA_TEXT for standard text sharing.
        val passedNote = intent.getStringExtra(Intent.EXTRA_TEXT) ?: ""

        // Step 2: Set the content for the activity using Compose.
        setContent {
            // It's good practice to wrap your screen in your app's theme.
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    // Step 3: Pass the retrieved text as an initial value
                    // to your main screen composable.
                    NoteScreen(initialText = passedNote)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteScreen(initialText: String) {
    // A context can be used for things like showing Toasts.
    val context = LocalContext.current

    // Step 4: Use rememberSaveable INSIDE the composable.
    // It's initialized with the `initialText` passed from the Activity.
    // `by` is a property delegate that simplifies getting/setting the value.
    var noteText by rememberSaveable { mutableStateOf(initialText) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    // Your save logic goes here.
                    // For example, save `noteText` to a database or file.
                    println("Note saved: $noteText")
                    Toast.makeText(context, "Note Saved!", Toast.LENGTH_SHORT).show()
                }
            ) {
                Icon(Icons.Filled.Done, contentDescription = "Save Note")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp) // Add some padding around the content
        ) {
            Text(text = "Your Note", style = MaterialTheme.typography.titleLarge)
            TextField(
                value = noteText,
                onValueChange = { newText ->
                    // The state is updated whenever the user types.
                    noteText = newText
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f), // Take up available space
                label = { Text("Start writing your note...") }
            )
        }
    }
}

// A preview function helps you see your UI in Android Studio without running the app.
@Preview(showBackground = true)
@Composable
fun NoteScreenPreview() {
    MaterialTheme {
        NoteScreen(initialText = "This is a sample note from a preview.")
    }
}