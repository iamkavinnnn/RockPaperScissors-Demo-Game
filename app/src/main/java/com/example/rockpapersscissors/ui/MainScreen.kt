package com.example.rockpapersscissors.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.rockpapersscissors.model.Move
import com.example.rockpapersscissors.model.Outcome
import com.example.rockpapersscissors.viewmodel.ChoiceViewModel

// VIEW: draws the state and reports taps. No game logic, no score of its own.
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    vm: ChoiceViewModel = viewModel()
) {
    Column(
        modifier = modifier.fillMaxSize().safeDrawingPadding().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Rock Paper Scissors", style = MaterialTheme.typography.headlineSmall)

        val round = vm.lastRound
        if (round == null) {
            Text("Pick a move")
        } else {
            Text("You played ${label(round.player)}")
            Text("Computer played ${label(round.computer)}")
            Text(
                outcomeText(round.outcome),
                style = MaterialTheme.typography.headlineMedium,
                color = outcomeColor(round.outcome)
            )
        }

        // One button per move, so new moves get a button automatically.
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Move.entries.forEach { move ->
                Button(onClick = { vm.play(move) }, modifier = Modifier.weight(1f)) {
                    Text(label(move))
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            ScoreCell("Wins", vm.score.wins)
            ScoreCell("Losses", vm.score.losses)
            ScoreCell("Draws", vm.score.draws)
        }

        OutlinedButton(onClick = { vm.reset() }, modifier = Modifier.fillMaxWidth()) {
            Text("Reset")
        }
    }
}

@Composable
private fun ScoreCell(title: String, value: Int) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value.toString(), style = MaterialTheme.typography.headlineSmall)
        Text(title, style = MaterialTheme.typography.bodySmall)
    }
}

// How things are WORDED is the View's decision, not the ViewModel's.
private fun label(move: Move): String = when (move) {
    Move.ROCK -> "Rock"
    Move.PAPER -> "Paper"
    Move.SCISSORS -> "Scissors"
}

private fun outcomeText(outcome: Outcome): String = when (outcome) {
    Outcome.WIN -> "You win!"
    Outcome.LOSE -> "You lose"
    Outcome.DRAW -> "Draw"
}

@Composable
private fun outcomeColor(outcome: Outcome): Color = when (outcome) {
    Outcome.WIN -> Color(0xFF2E7D32)
    Outcome.LOSE -> MaterialTheme.colorScheme.error
    Outcome.DRAW -> MaterialTheme.colorScheme.onSurfaceVariant
}