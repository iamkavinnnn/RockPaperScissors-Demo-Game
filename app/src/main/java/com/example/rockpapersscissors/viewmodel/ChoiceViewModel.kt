package com.example.rockpapersscissors.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.rockpapersscissors.model.Move
import com.example.rockpapersscissors.model.Round
import com.example.rockpapersscissors.model.Score
import com.example.rockpapersscissors.model.decide

// VIEWMODEL: owns the state and runs a round. Survives rotation.
class ChoiceViewModel: ViewModel() {

    var score by mutableStateOf(Score())
        private set

    var lastRound by mutableStateOf<Round?>(null)
        private set

    fun play(playerMove: Move) {
        val computerMove = Move.entries.random()
        val outcome = decide(playerMove, computerMove)   // the Model decides
        score = score.record(outcome)
        lastRound = Round(playerMove, computerMove, outcome)
    }

    fun reset() {
        score = Score()
        lastRound = null
    }
}