package Adapters

import Holders.LoadHolder
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import koleton.api.loadSkeleton

class LoadAdapter() : RecyclerView.Adapter<LoadHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoadHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.load_item, parent, false)
        return LoadHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: LoadHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 3
    }

}