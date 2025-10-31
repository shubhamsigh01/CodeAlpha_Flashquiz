package com.example.flashquiz.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.flashquiz.model.Flashcard

@Composable
fun AddFlashcardDialog(
    onDismiss: () -> Unit,
    onAdd: (Flashcard) -> Unit
) {
    var question by remember { mutableStateOf("") }
    var answer by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Add Flashcard") },
        text = {
            Column {
                OutlinedTextField(
                    value = question,
                    onValueChange = { question = it },
                    label = { Text("Question") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = answer,
                    onValueChange = { answer = it },
                    label = { Text("Answer") }
                )
            }
        },
        confirmButton = {
            Button(onClick = {
                if (question.isNotBlank() && answer.isNotBlank()) {
                    onAdd(Flashcard(question, answer))
                    onDismiss()
                }
            }) {
                Text("Add")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        },
        modifier = Modifier.padding(16.dp)
    )
}
