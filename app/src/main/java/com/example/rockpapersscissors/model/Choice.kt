package com.example.rockpapersscissors.model
enum class Move {
    // provided by workshop.
    ROCK, PAPER, SCISSORS;

    // The ONE place that says which move beats which.
    fun beats(other: Move): Boolean = other in defeats()

    private fun defeats(): Set<Move> = when (this) {
        ROCK -> setOf(SCISSORS)      // rock blunts scissors
        PAPER -> setOf(ROCK)         // paper covers rock
        SCISSORS -> setOf(PAPER)     // scissors cut paper
    }
}

enum class Outcome { WIN, LOSE, DRAW }

// Compares two moves using the rule above.
fun decide(player: Move, computer: Move): Outcome = when {
    player == computer -> Outcome.DRAW
    player.beats(computer) -> Outcome.WIN
    else -> Outcome.LOSE
}

// One round, remembered so the screen can show it.
data class Round(val player: Move, val computer: Move, val outcome: Outcome)

// Immutable score: record() returns a NEW Score, like contacts + newContact.
data class Score(val wins: Int = 0, val losses: Int = 0, val draws: Int = 0) {

    fun record(outcome: Outcome): Score = when (outcome) {

        Outcome.WIN -> copy(wins = wins + 1)
        Outcome.LOSE -> copy(losses = losses + 1)
        Outcome.DRAW -> copy(draws = draws + 1)
    }
}