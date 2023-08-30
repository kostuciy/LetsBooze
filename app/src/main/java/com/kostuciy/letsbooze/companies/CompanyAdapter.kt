package com.kostuciy.letsbooze.companies

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kostuciy.letsbooze.utils.ImageScaler
import com.kostuciy.letsbooze.R
import com.kostuciy.letsbooze.data.InternalStorageManager


class CompanyAdapter(
    private val memberList: List<CompanyMember>,
    private val viewContext: View,
    private val internalStorageManager: InternalStorageManager,
    private val imageScaler: ImageScaler
    ) :
    RecyclerView.Adapter<CompanyAdapter.MemberViewHolder>() {
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */

    class MemberViewHolder(
        memberView: View
    ) : RecyclerView.ViewHolder(memberView) {
        private val nameTextView: TextView =
            memberView.findViewById(R.id.nameTextView)
        private val photoImageView: ImageView =
            memberView.findViewById(R.id.photoImageView)

        fun setData(
            memberData: CompanyMember,
            viewContext: View,
            internalStorageManager: InternalStorageManager,
            imageScaler: ImageScaler
        ) {
            val memberName = memberData.name
            nameTextView.text = memberName

//            loading bitmapImage from storage, editing and setting it to imageView
            val bitmapImage: Bitmap? = internalStorageManager
                .getBitmapFromInternalStorage(memberData.photoImagePath)?.let {
                    imageScaler.scaleBitmap(it, 312)
                }


            val sideSize = viewContext.width / 4
            photoImageView.apply {
                if (bitmapImage != null) setImageBitmap(bitmapImage)
                else setImageResource(R.drawable.ic_launcher_background)
                adjustViewBounds = true
                layoutParams = ViewGroup.LayoutParams(sideSize, sideSize)
                scaleType = ImageView.ScaleType.CENTER_CROP
            }
//
//                ?.let {
//                    imageScaler.scaleBitmap(it, 312)
//                } ?: throw IllegalArgumentException("Null bitmap was received")
//
//            editAndSetImage(photoImageView, bitmapImage, viewContext.width / 4)
        }

//        private fun editAndSetImage(
//            photoImageView: ImageView,
//            bitmapImage: Bitmap,
//            sideSize: Int
//        ) {
//            photoImageView.apply {
//                setImageBitmap(bitmapImage)
//                adjustViewBounds = true
//                layoutParams = ViewGroup.LayoutParams(sideSize, sideSize)
//                scaleType = ImageView.ScaleType.CENTER_CROP
//            }
//        }
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

        memberViewHolder.setData(
            currentMember,
            viewContext,
            internalStorageManager,
            imageScaler
        )
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = memberList.size

}