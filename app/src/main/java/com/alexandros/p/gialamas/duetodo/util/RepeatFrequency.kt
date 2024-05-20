package com.alexandros.p.gialamas.duetodo.util

import com.alexandros.p.gialamas.duetodo.R

enum class RepeatFrequency(val displayText : Int) {
    SNOOZE_10_MINUTES(R.string.Snooze_10_minutes),
    SNOOZE_1_HOUR(R.string.Snooze_1_hour),
    SNOOZE_2_HOUR(R.string.Snooze_2_hour),
    NONE(R.string.Does_not_repeat_text),
    DAILY(R.string.Daily_text),
    WEEKLY(R.string.Weekly_text),
    MONTHLY(R.string.Monthly_text),
    YEARLY(R.string.Yearly_text),
    CUSTOM(R.string.Custom_text),
}