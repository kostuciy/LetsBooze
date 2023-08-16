package com.kostuciy.letsbooze.companies

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.RecyclerView
import com.kostuciy.letsbooze.R


class CompanyAdapter(
    private val memberList: List<CompanyMember>,
    private val viewContext: View
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

        fun setData(memberData: CompanyMember, viewContext: View) {
            nameTextView.text = memberData.name

            setLowerImageResolution(photoImageView, memberData.photo)
            setImageSize(photoImageView, viewContext.width / 4)
        }

        private fun setImageSize(photoImageView: ImageView, sideSize: Int) {
            photoImageView.apply {
                adjustViewBounds = true
                layoutParams = ViewGroup.LayoutParams(sideSize, sideSize)
                scaleType = ImageView.ScaleType.CENTER_CROP
            }
        }

        private fun setLowerImageResolution(photoImageView: ImageView, drawable: Drawable) {
            val bitmapImage = drawable.toBitmap()
            val lowerWidth = 312
            val lowerHeight =
                (bitmapImage.height * (lowerWidth * 1.0 / bitmapImage.width)).toInt()
            val scaledDrawable =
                Bitmap.createScaledBitmap(bitmapImage, lowerWidth, lowerHeight, true)

            photoImageView.setImageBitmap(scaledDrawable)
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

        memberViewHolder.setData(currentMember, viewContext)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = memberList.size

}