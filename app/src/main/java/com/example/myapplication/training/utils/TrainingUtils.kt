package com.example.myapplication.training.utils

import com.example.myapplication.R
import com.example.myapplication.training.data.TrainingTopCardModel

object TrainingUtils {
    const val EASY = "easy"
    const val MIDDLE = "middle"
    const val HARD = "hard"

    val difListType = listOf(
        EASY,
        MIDDLE,
        HARD
    )

    val tabTitle = listOf(
        R.string.easy,
        R.string.middle,
        R.string.hard
    )

    val topCardList = listOf(
        TrainingTopCardModel(
            R.drawable.one,
            R.string.easy,
            0,
            0,
            "easy"
        ),
        TrainingTopCardModel(
            R.drawable.two,
            R.string.middle,
            0,
            0,
            "middle"
        ),
        TrainingTopCardModel(
            R.drawable.tree,
            R.string.hard,
            0,
            0,
            "hard"
        )
    )
}