package com.heavin.criminalintent

import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.heavin.criminalintent.databinding.ListItemCrimeBinding
import com.heavin.criminalintent.databinding.ListItemCrimePoliceBinding

open class CrimeHolder (
    private val binding: ViewBinding
) : RecyclerView.ViewHolder(binding.root) {
    open fun bind(crime: Crime) {
    }
}

class CrimeHolderWithoutPolice(
    private val bindingCrime: ListItemCrimeBinding
) : CrimeHolder(bindingCrime) {
    override fun bind(crime: Crime) {
        bindingCrime.crimeTitle.text = crime.title
        bindingCrime.crimeDate.text = DateFormat.format("MMM dd, yyyy.", crime.date)
        bindingCrime.root.setOnClickListener {
            Toast.makeText(
                bindingCrime.root.context,
                "${crime.title} clicked!",
                Toast.LENGTH_SHORT
            ).show()
        }

        bindingCrime.crimeSolved.visibility = if (crime.isSolved) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }
}

class CrimeHolderWithPolice(
    private val bindingCrimePolice: ListItemCrimePoliceBinding
) : CrimeHolder(bindingCrimePolice) {
    override fun bind(crime: Crime) {
        bindingCrimePolice.crimeTitle.text = crime.title
        bindingCrimePolice.crimeDate.text = DateFormat.format("MMM dd, yyyy.", crime.date)
        bindingCrimePolice.root.setOnClickListener {
            Toast.makeText(
                bindingCrimePolice.root.context,
                "${crime.title} clicked!",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}


class CrimeListAdapter (
    private val crimes: List<Crime>
) : RecyclerView.Adapter<CrimeHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CrimeHolder {
        val inflater = LayoutInflater.from(parent.context)
        //val binding = ListItemCrimeBinding.inflate(inflater, parent, false)
        //return CrimeHolder(binding)

        if (viewType == 1) {
            val binding = ListItemCrimeBinding.inflate(inflater, parent, false)
            return CrimeHolderWithoutPolice(binding)
        }
        else {
            val binding = ListItemCrimePoliceBinding.inflate(inflater, parent, false)
            return CrimeHolderWithPolice(binding)
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (crimes[position].requiresPolice) {
            return 1
        }
        else {
            return 0
        }
    }

    override fun onBindViewHolder(holder: CrimeHolder, position: Int) {
        val crime = crimes[position]

        holder.bind(crime)
    }

    override fun getItemCount() = crimes.size
}