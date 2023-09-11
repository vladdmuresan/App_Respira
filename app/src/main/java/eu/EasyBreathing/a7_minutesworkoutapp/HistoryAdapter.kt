package eu.EasyBreathing.a7_minutesworkoutapp

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Delete
import eu.EasyBreathing.a7_minutesworkoutapp.databinding.ItemHistoryRowBinding

// Am creat o clasă de adaptor pentru a lega RecyclerView pentru a afișa lista de date finalizate în ecranul Istoric.
class HistoryAdapter(private val items: ArrayList<String>) :
    RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    // Inflates vizualizarea articolului care este proiectat în fișierul xml
    // {@link ViewHolder}  inițializează unele câmpuri private pentru a fi utilizate de RecyclerView.

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemHistoryRowBinding.inflate(
            LayoutInflater.from(parent.context),parent,false)
            )
    }

    // Leagă fiecare element din ArrayList la un view
    // Acest nou ViewHolder ar trebui să fie construit cu un nou View care pot reprezenta elementele de tipul dat.

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val date: String = items.get(position)

        holder.tvPosition.text = (position + 1).toString()
        holder.tvItem.text = date


        // Actualizarea culorii de fundal în funcție de pozițiile impare/pare din listă.
        if (position % 2 == 0) {
            holder.llHistoryItemMain.setBackgroundColor(
                Color.parseColor("#EBEBEB")
            )
        } else {
            holder.llHistoryItemMain.setBackgroundColor(
                Color.parseColor("#FFFFFF")
            )
        }
    }

    // Obține numărul de articole din listă

    override fun getItemCount(): Int {
        return items.size
    }

    // ViewHolder descrie o vizualizare a unui articol și metadate despre locul său în cadrul RecyclerView.

    class ViewHolder(binding: ItemHistoryRowBinding) : RecyclerView.ViewHolder(binding.root) {
        // Deține TextView care va adăuga fiecare articol
        val llHistoryItemMain = binding.llHistoryItemMain
        val tvItem = binding.tvItem
        val tvPosition = binding.tvPosition
    }
}
