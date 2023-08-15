package com.kostuciy.letsbooze.companies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kostuciy.letsbooze.R

class CompanyAdapter(private val dataSet: List<CompanyMember>) :
    RecyclerView.Adapter<CompanyAdapter.MemberViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    class MemberViewHolder(memberView: View) : RecyclerView.ViewHolder(memberView) {
        private val nameTextView: TextView =
            memberView.findViewById(R.id.nameTextView)
        private val photoImageView: ImageView =
            memberView.findViewById(R.id.photoImageView)

        fun setData(memberData: CompanyMember) {
            nameTextView.text = memberData.name
            photoImageView.setImageResource(R.drawable.ic_launcher_background)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MemberViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.company_member_card, viewGroup, false)

        return MemberViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(memberViewHolder: MemberViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        val currentMember = dataSet[position]

        memberViewHolder.setData(currentMember)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}