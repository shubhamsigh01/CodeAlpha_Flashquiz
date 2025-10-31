package com.example.flashquiz.view

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.example.flashquiz.model.Flashcard

@Composable
fun FlashcardScreen(
    flashcards: MutableList<Flashcard>,
    onDelete: (Flashcard) -> Unit,
    onEdit: (Flashcard) -> Unit
) {
    var currentCardIndex by remember { mutableStateOf(0) }
    var flipped by remember { mutableStateOf(false) }

    val rotationY by animateFloatAsState(targetValue = if (flipped) 180f else 0f, label = "")

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center
        ) {
            if (flashcards.isNotEmpty()) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .aspectRatio(1.5f)
                        .graphicsLayer {
                            this.rotationY = rotationY
                            cameraDistance = 12 * density
                        },
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    if (rotationY <= 90f) {
                        // Front of the card
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(text = "Question: ${flashcards[currentCardIndex].question}")
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(onClick = { flipped = true }) {
                                Text(text = "Answer")
                            }
                        }
                    } else {
                        // Back of the card
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .graphicsLayer { this.rotationY = 180f },
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(text = "Answer: ${flashcards[currentCardIndex].answer}")
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(onClick = { flipped = false }) {
                                Text(text = "Question")
                            }
                        }
                    }
                }
            } else {
                Text(text = "No flashcards available.")
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Button(onClick = { currentCardIndex = (currentCardIndex - 1).coerceAtLeast(0) }) {
                Text(text = "Previous")
            }
            Button(onClick = {
                if (flashcards.isNotEmpty()) {
                    onEdit(flashcards[currentCardIndex])
                }
            }) {
                Text(text = "Edit")
            }
            Button(onClick = {
                if (flashcards.isNotEmpty()) {
                    onDelete(flashcards[currentCardIndex])
                    currentCardIndex = currentCardIndex.coerceAtMost(flashcards.size - 2).coerceAtLeast(0)
                }
            }) {
                Text(text = "Delete")
            }
            Button(onClick = { currentCardIndex = (currentCardIndex + 1).coerceAtMost(flashcards.size - 1) }) {
                Text(text = "Next")
            }
        }
    }
}
