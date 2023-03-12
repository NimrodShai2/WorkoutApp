package my.nimrodshai.workoutapp.db

import android.app.Application

class WorkoutApp : Application() {
    val db by lazy {
        HistoryDb.getInstance(this)
    }
}