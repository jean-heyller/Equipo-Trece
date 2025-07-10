package com.example.dogapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dogapp.R
import com.example.dogapp.databinding.ItemCitaBinding
import com.example.dogapp.model.Cita

class CitaAdapter(
    private val onItemClick: (Cita) -> Unit
) : ListAdapter<Cita, CitaAdapter.CitaViewHolder>(CitaDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitaViewHolder {
        val binding = ItemCitaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CitaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CitaViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CitaViewHolder(private val binding: ItemCitaBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(cita: Cita) {
            binding.tvNombreMascota.text = cita.nombreMascota
            binding.tvSintoma.text = cita.sintoma
            binding.tvTurno.text = "# " + cita.id.toString()
            Glide.with(binding.root.context)
                .load(cita.urlImagen)
                .placeholder(R.drawable.dog)
                .circleCrop()
                .into(binding.imgMascota)
            binding.root.setOnClickListener { onItemClick(cita) }
        }
    }

    class CitaDiffCallback : DiffUtil.ItemCallback<Cita>() {
        override fun areItemsTheSame(oldItem: Cita, newItem: Cita) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Cita, newItem: Cita) = oldItem == newItem
    }
}