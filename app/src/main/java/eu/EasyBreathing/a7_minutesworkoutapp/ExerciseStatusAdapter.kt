package eu.EasyBreathing.a7_minutesworkoutapp

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import eu.EasyBreathing.a7_minutesworkoutapp.databinding.ItemExerciseStatusBinding

class ExerciseStatusAdapter(val items: ArrayList<ExerciseModel>) :
    RecyclerView.Adapter<ExerciseStatusAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemExerciseStatusBinding.inflate(
            LayoutInflater.from(parent.context),parent,false
            )
        )
    }


    // Leagă fiecare element din ArrayList la un view
    //  Apelat cand RecyclerView are nevoie de un nou {@link ViewHolder} de tipul dat reprezentat de un obiect.
    // Acest nou ViewHolder ar trebui să fie construit cu un nou View care poate reprezenta elementele de tipul dat. Poți fie să creezi unul nou View manual, sau inflate dintr-un XML layout fisier.
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model: ExerciseModel = items[position]
        holder.tvItem.text = model.getId().toString()

        when {
            model.getIsSelected() -> {
                holder.tvItem.background =
                    ContextCompat.getDrawable(
                        holder.itemView.context,
                        R.drawable.item_circular_thin_color_accent_border
                    )
                holder.tvItem.setTextColor(Color.parseColor("#212121")) // Analizează șirul de culori și returnează culoarea corespunzătoare.
            }
            model.getIsCompleted() -> {
                holder.tvItem.background =
                    ContextCompat.getDrawable(holder.itemView.context, R.drawable.item_circular_color_accent_background)
                holder.tvItem.setTextColor(Color.parseColor("#FFFFFF"))
            }
            else -> {
                holder.tvItem.background =
                    ContextCompat.getDrawable(holder.itemView.context, R.drawable.item_circular_color_gray_background)
                holder.tvItem.setTextColor(Color.parseColor("#212121"))
            }
        }

    }


    // Obține numărul de articole din listă
    override fun getItemCount(): Int {
        return items.size
    }

    // A ViewHolder descrie o vizualizare a unui articol și metadate despre locul său în cadrul RecyclerView.
    class ViewHolder(binding: ItemExerciseStatusBinding) : RecyclerView.ViewHolder(binding.root) {
        val tvItem = binding.tvItem
    }
}
