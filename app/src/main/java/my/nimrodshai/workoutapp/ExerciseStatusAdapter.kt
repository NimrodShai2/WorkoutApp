package my.nimrodshai.workoutapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import my.nimrodshai.workoutapp.databinding.ItemExerciseStatusBinding

class ExerciseStatusAdapter(val items: ArrayList<Exercise>) :
    RecyclerView.Adapter<ExerciseStatusAdapter.ViewHolder>() {
    inner class ViewHolder(binding: ItemExerciseStatusBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val tvItem = binding.tvItem

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemExerciseStatusBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model : Exercise = items[position]
        holder.tvItem.text = model.id.toString()
    }
}