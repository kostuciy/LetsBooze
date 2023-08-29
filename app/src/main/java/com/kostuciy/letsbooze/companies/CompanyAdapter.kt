package com.kostuciy.letsbooze.companies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kostuciy.letsbooze.utils.ImageResolutionChanger
import com.kostuciy.letsbooze.R


class CompanyAdapter(
    private val memberList: List<CompanyMember>,
    private val viewContext: View,
    private val imageResolutionChanger: ImageResolutionChanger
    ) :
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

        fun setData(
            memberData: CompanyMember,
            viewContext: View,
            imageResolutionChanger: ImageResolutionChanger
        ) {
            nameTextView.text = memberData.name

//            setLowerImageResolution(photoImageView, memberData.photo)
            imageResolutionChanger.changeResolution(photoImageView, memberData.photo, 312)
            setImageSize(photoImageView, viewContext.width / 4)
        }

        private fun setImageSize(photoImageView: ImageView, sideSize: Int) {
            photoImageView.apply {
                adjustViewBounds = true
                layoutParams = ViewGroup.LayoutParams(sideSize, sideSize)
                scaleType = ImageView.ScaleType.CENTER_CROP
            }
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
        val currentMember = memberList[position]

        memberViewHolder.setData(currentMember, viewContext, imageResolutionChanger)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = memberList.size

}