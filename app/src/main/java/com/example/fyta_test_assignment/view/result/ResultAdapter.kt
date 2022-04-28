import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.fyta_test_assignment.R
import com.example.fyta_test_assignment.fundation.Utility
import com.example.fyta_test_assignment.model.response.search.SearchModel
import java.io.File

class ResultAdapter(file : File, items :  List<SearchModel>,val clickListener: (SearchModel) -> Unit) : RecyclerView.Adapter<ResultViewHolder>() {
    private var allResults: List<SearchModel> = items
    private  var finalFile : File? = file


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
             ResultViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.search_item, parent, false)
        return ResultViewHolder(view,)
    }

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        val data = allResults[position]
        val name = data.species?.scientificNameWithoutAuthor
        val description = data?.species?.scientificNameAuthorship
        val rate = data?.score

        holder.mTitleText.text = name
        holder.mDescriptionText.text = description
        holder.mPercentText.text = (rate!! * 100).toInt().toString()

        holder.imgSearch.setImageBitmap(Utility.fileToBitmap(finalFile!!))
        holder.cardItem.setOnClickListener {
            clickListener(allResults[position])
        }


    }

    override fun getItemCount(): Int {
        return if (allResults == null) 0 else allResults!!.size
    }




}
class ResultViewHolder(itemView: View,) : RecyclerView.ViewHolder(itemView){
    // Find all the views of the list item
    val mTitleText: TextView = itemView.findViewById(R.id.txt_title)
    val mDescriptionText: TextView = itemView.findViewById(R.id.txt_desc)
    val mPercentText: TextView = itemView.findViewById(R.id.txt_percent)
    val imgSearch: ImageView = itemView.findViewById(R.id.img_search)
     val cardItem: CardView = itemView.findViewById(R.id.card_item)





}
interface ResultAdapterListener {
    fun onItemClick() : SearchModel
}
