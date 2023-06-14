package eu.respira.a7_minutesworkoutapp

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import eu.respira.a7_minutesworkoutapp.databinding.ItemExerciseStatusBinding

class ExerciseStatusAdapter(val items: ArrayList<ExerciseModel>) :
    RecyclerView.Adapter<ExerciseStatusAdapter.ViewHolder>() {

    /**
     * Inflates vizualizarea articolului care este proiectată în fișierul de aspect xml
     *
     * creaza unl nou
     * {@link ViewHolder} și inițializează unele câmpuri private pentru a fi utilizate de RecyclerView.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemExerciseStatusBinding.inflate(
            LayoutInflater.from(parent.context),parent,false
            )
        )
    }

    /**
     * Leagă fiecare element din ArrayList la un view
     *
     * Apelat cand RecyclerView are nevoie de un nou {@link ViewHolder} de tipul dat de reprezentat
     * un obiect.
     *
     * Acest nou ViewHolder ar trebui să fie construit cu un nou View care poate reprezenta elementele
     * de tipul dat. Poți fie să creezi unul nou View manual sau inflate dintr-un XML
     * layout fisier.
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val model: ExerciseModel = items[position]

        holder.tvItem.text = model.getId().toString()

        // https://stackoverflow.com/questions/8472349/how-to-set-text-color-to-a-text-view-programmatically
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
        // END
    }

    /**
     * Obține numărul de articole din listă
     */
    override fun getItemCount(): Int {
        return items.size
    }

    /**
     * A ViewHolder descrie o vizualizare a unui articol și metadate despre locul său în cadrul RecyclerView.
     */
    class ViewHolder(binding: ItemExerciseStatusBinding) : RecyclerView.ViewHolder(binding.root) {
        // Ține TextView care va adăuga fiecare articol la
        val tvItem = binding.tvItem
    }
}
// END