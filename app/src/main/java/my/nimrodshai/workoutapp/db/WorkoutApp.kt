package my.nimrodshai.workoutapp.db

import com.zeugmasolutions.localehelper.LocaleAwareApplication

class WorkoutApp : LocaleAwareApplication() {
    val db by lazy {
        HistoryDb.getInstance(this)
    }
}