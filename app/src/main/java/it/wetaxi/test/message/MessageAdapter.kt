package it.wetaxi.test.message

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import it.wetaxi.test.message.data.Message
import it.wetaxi.test.message.databinding.MessageItemViewBinding

class MessageAdapter : ListAdapter<Message, MessageAdapter.MessageViewHolder>(DIFF_CALLBACK) {

    class MessageViewHolder private constructor(private val binding: MessageItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(message: Message) {
            binding.bodyTextView.text = message.text
            binding.dateTextView.text = message.date
            binding.priorityTextView.text = message.priority
            binding.imageView.setImageResource(
                if (message.read) {
                    R.drawable.ic_read_24px
                } else {
                    R.drawable.ic_unread_24px
                }
            )
        }

        companion object {
            fun from(parent: ViewGroup): MessageViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = MessageItemViewBinding.inflate(inflater, parent, false)
                return MessageViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return MessageViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = getItem(position)
        holder.bind(message)
    }

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<Message> =
            object : DiffUtil.ItemCallback<Message>() {
                override fun areItemsTheSame(
                    oldItem: Message,
                    newItem: Message
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: Message,
                    newItem: Message
                ): Boolean {
                    return oldItem == newItem
                }

            }
    }
}