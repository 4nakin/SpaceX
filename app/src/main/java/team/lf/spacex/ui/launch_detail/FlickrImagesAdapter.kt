package team.lf.spacex.ui.launch_detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import team.lf.spacex.data.ui_models.FlickrImage
import team.lf.spacex.data.ui_models.Launch
import team.lf.spacex.databinding.ItemFlickrBinding

class FlickrImagesAdapter :
    ListAdapter<FlickrImage, RecyclerView.ViewHolder>(FlickrImagesDiffCallback()) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    fun submitLaunchImagesList(launch: Launch) {
        adapterScope.launch {
            val images: List<FlickrImage> = launch.links.flickr_images.map {
                FlickrImage(it)
            }
            withContext(Dispatchers.Main) {
                submitList(images)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return FlickrViewholder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as FlickrViewholder).let {
            val item = getItem(position) as FlickrImage
            holder.bind(item)
        }
    }

    class FlickrViewholder(private val binding: ItemFlickrBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FlickrImage) {
            binding.flickrImage = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): FlickrViewholder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemFlickrBinding.inflate(layoutInflater, parent, false)
                return FlickrViewholder(binding)
            }
        }
    }
}

class FlickrImagesDiffCallback : DiffUtil.ItemCallback<FlickrImage>() {
    override fun areItemsTheSame(oldItem: FlickrImage, newItem: FlickrImage): Boolean {
        return oldItem.url == newItem.url
    }

    override fun areContentsTheSame(oldItem: FlickrImage, newItem: FlickrImage): Boolean {
        return oldItem == newItem
    }
}