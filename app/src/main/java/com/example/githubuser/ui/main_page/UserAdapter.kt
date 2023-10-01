package com.example.githubuser.ui.main_page
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.githubuser.data.response.ItemsItem
import com.example.githubuser.databinding.UserRowBinding
import com.bumptech.glide.Glide
import com.example.githubuser.ui.user_detail.UserDetailActivity

class UserAdapter : ListAdapter<ItemsItem,UserAdapter.ViewHolder>(DIFF_CALLBACK) {

        class ViewHolder(private val binding: UserRowBinding) :
                RecyclerView.ViewHolder(binding.root) {
                    fun bind(item: ItemsItem) {
                        binding.tvName.text = item.login
                        Glide.with(itemView)
                            .load(item.avatarUrl)
                            .into(binding.ivPicture)

                        binding.root.setOnClickListener{
                            val intent = Intent(itemView.context, UserDetailActivity::class.java)
                            intent.putExtra(UserDetailActivity.USER_DETAIL, item.login)
                            itemView.context.startActivity(intent)
                        }
                        }
                    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view= UserRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
    }
    companion object{
        private val DIFF_CALLBACK = object: DiffUtil.ItemCallback<ItemsItem>(){
            override fun areItemsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}