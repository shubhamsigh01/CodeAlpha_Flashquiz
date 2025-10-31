package com.example.flashquiz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.flashquiz.model.Flashcard
import com.example.flashquiz.view.AddFlashcardDialog
import com.example.flashquiz.view.EditFlashcardDialog
import com.example.flashquiz.view.FlashcardScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val flashcards = remember {
                mutableStateListOf(
                    Flashcard("What is the capital of France?", "Paris"),
                    Flashcard("What is 2 + 2?", "4"),
                    Flashcard("What is the largest planet in our solar system?", "Jupiter"),
                    Flashcard("What is the highest mountain in the world?", "Mount Everest"),
                    Flashcard("Who wrote 'Hamlet'?", "William Shakespeare"),
                    Flashcard("What is the chemical symbol for gold?", "Au"),
                    Flashcard("Which planet is known as the Red Planet?", "Mars"),
                    Flashcard("In which country is the Great Pyramid of Giza?", "Egypt"),
                    Flashcard("What is the currency of Japan?", "Japanese Yen"),
                    Flashcard("Who painted the Mona Lisa?", "Leonardo da Vinci"),
                    Flashcard("What is the largest ocean on Earth?", "Pacific Ocean"),
                    Flashcard("What is the capital of Australia?", "Canberra"),
                    Flashcard("Who invented the telephone?", "Alexander Graham Bell")
                )
            }
            var showAddDialog by remember { mutableStateOf(false) }
            var editingFlashcard by remember { mutableStateOf<Flashcard?>(null) }

            Column {
                Button(onClick = { showAddDialog = true }, modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                    Text(text = "Add Flashcard")
                }
                FlashcardScreen(
                    flashcards = flashcards,
                    onDelete = { flashcard: Flashcard -> flashcards.remove(flashcard) },
                    onEdit = { flashcard: Flashcard -> editingFlashcard = flashcard }
                )
            }

            if (showAddDialog) {
                AddFlashcardDialog(
                    onDismiss = { showAddDialog = false },
                    onAdd = { flashcard: Flashcard -> flashcards.add(flashcard) }
                )
            }

            editingFlashcard?.let { flashcard ->
                EditFlashcardDialog(
                    flashcard = flashcard,
                    onDismiss = { editingFlashcard = null },
                    onSave = { updatedFlashcard: Flashcard ->
                        val index = flashcards.indexOf(flashcard)
                        if (index != -1) {
                            flashcards[index] = updatedFlashcard
                        }
                        editingFlashcard = null
                    }
                )
            }
        }
    }
}
