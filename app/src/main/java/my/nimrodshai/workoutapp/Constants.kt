package my.nimrodshai.workoutapp

object Constants {
    fun defaultExerciseList(): ArrayList<Exercise>{
        val res = ArrayList<Exercise>()
        val jumpingJacks = Exercise(1,"Jumping Jacks", R.drawable.ic_jumping_jacks)
        res.add(jumpingJacks)
        val abdominalCrunch = Exercise(2, "Abdominal Crunch",
            R.drawable.ic_abdominal_crunch)
        res.add(abdominalCrunch)
        val highKnees = Exercise(3, "High Knees Run In Place",
            R.drawable.ic_high_knees_running_in_place)
        res.add(highKnees)
        val lunge = Exercise(4,"Lunge", R.drawable.ic_lunge)
        res.add(lunge)
        val plank = Exercise(5, "Plank",
            R.drawable.ic_plank)
        res.add(plank)
        val pushUp = Exercise(6, "Push Up",
            R.drawable.ic_push_up)
        res.add(pushUp)
        val pushUpRotation= Exercise(7,"Push Up Rotation",
            R.drawable.ic_push_up_and_rotation)
        res.add(pushUpRotation)
        val sidePlank = Exercise(8, "Side Plank", R.drawable.ic_side_plank)
        res.add(sidePlank)
        val squat = Exercise(9, "Squat", R.drawable.ic_squat)
        res.add(squat)
        val stepUp = Exercise(10,"Step Up Onto Chair", R.drawable.ic_step_up_onto_chair)
        res.add(stepUp)
        val dip = Exercise(11, "Dip On Chair", R.drawable.ic_triceps_dip_on_chair)
        res.add(dip)
        val wallSit = Exercise(12, "Wall Sit", R.drawable.ic_wall_sit)
        res.add(wallSit)

        return res
    }
}