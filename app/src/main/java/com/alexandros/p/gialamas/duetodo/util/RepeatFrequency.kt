package com.alexandros.p.gialamas.duetodo.util

import com.alexandros.p.gialamas.duetodo.R

enum class RepeatFrequency(val displayText : Int) {
    NONE(R.string.Does_not_repeat_text),
    DAILY(R.string.Daily_text),
    WEEKLY(R.string.Weekly_text),
    MONTHLY(R.string.Monthly_text),
    YEARLY(R.string.Yearly_text),
    CUSTOM(R.string.Custom_text),
    SECONDS_30(R.string.Seconds_30_text),
    MINUTE_1(R.string.Minute_1_text),

}