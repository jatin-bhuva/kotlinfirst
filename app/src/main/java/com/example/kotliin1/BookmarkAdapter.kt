import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kotliin1.Location
import com.example.kotliin1.R

class BookmarkAdapter(
    private val locations: List<Location>,
    private val onDeleteClick: (Location) -> Unit,
    private val onItemClicked: (Location) -> Unit
) : RecyclerView.Adapter<BookmarkAdapter.LocationViewHolder>() {

    class LocationViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val latitudeText: TextView = view.findViewById(R.id.latitude_value)
        val longitudeText: TextView = view.findViewById(R.id.longitude_value)
        val deleteButton: ImageButton = view.findViewById(R.id.delete_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.location_item, parent, false)
        return LocationViewHolder(view)
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        val location = locations[position]
        holder.latitudeText.text = "Lat: ${location.latitude}"
        holder.longitudeText.text = "Long: ${location.longitude}"

        holder.deleteButton.setOnClickListener {
            onDeleteClick(location)
        }
        holder.itemView.setOnClickListener {
            onItemClicked(location)
        }

    }

    override fun getItemCount(): Int {
        return locations.size
    }
}
