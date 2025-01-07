import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kotliin1.Lesson3S1
import com.example.kotliin1.R

class BookAdapter(private var bookList: List<Lesson3S1.Book>, private val onItemClicked:(Lesson3S1.Book)->Unit) :
    RecyclerView.Adapter<BookAdapter.BookViewHolder>() {
    inner class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val bookNameText: TextView = itemView.findViewById(R.id.book_name)
        val authorNameText: TextView = itemView.findViewById(R.id.author_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.book_item, parent, false)
        return BookViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = bookList[position]
        holder.bookNameText.text = book.bookName
        holder.authorNameText.text = book.authorName

        holder.itemView.setOnClickListener{
            onItemClicked(book)
        }
    }

    override fun getItemCount(): Int {
        return bookList.size
    }

    fun filter(query: String, data: List<Lesson3S1.Book>) {
        bookList = data
        bookList = if (query.isEmpty()) {
            bookList.toMutableList()
        } else {
            bookList.filter {
                it.bookName.contains(query, ignoreCase = true)
            }.toMutableList()
        }
        notifyDataSetChanged()
    }
}