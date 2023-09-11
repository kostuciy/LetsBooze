package com.kostuciy.letsbooze.companies

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kostuciy.letsbooze.R
import com.kostuciy.letsbooze.data.InternalStorageManager
import com.kostuciy.letsbooze.fragments.CompanyFragment

const val DEFAULT_MEMBER_PHOTO = R.drawable.ic_launcher_foreground // TODO: set another default

const val TYPE_MEMBER = 0
const val TYPE_ADDER = 1

class CompanyAdapter(
    private val memberList: List<CompanyMember>,
    private val viewContext: View,
    private val companyPopup: CompanyFragment.CompanyPopup
    ) :
    RecyclerView.Adapter<CompanyAdapter.MemberViewHolder>() {
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */

    inner class MemberViewHolder(
        private val isAdder: Boolean,
        memberView: View
    ) : RecyclerView.ViewHolder(memberView) {
        private val nameTextView: TextView = // TODO: change to nullable when redesigning
            memberView.findViewById(R.id.nameTextView)
        private val photoImageView: ImageView? = // TODO: change to non-nullable when redesigning
            memberView.findViewById(R.id.photoImageView)

//        listener to items that activate popup
        private var adderClickListener: OnClickListener = object : OnClickListener {
            override fun onClick(view: View?) {
                companyPopup.apply {
                    setPopupForRegistration()
                    showPopup(viewContext)
                    currentMemberPosition = -1
                }
            }
        }
        private var editingClickListener: OnClickListener = object : OnClickListener {
            override fun onClick(view: View?) {
                companyPopup.apply {
                    setPopupForEditing(memberList[layoutPosition])
                    showPopup(viewContext)
                    currentMemberPosition = layoutPosition // excluding add item TODO: rework
                }
            }
        }

        init {
            memberView.setOnClickListener(
                if (isAdder) adderClickListener
                else editingClickListener
            )
        }

        fun setMemberData(
            memberData: CompanyMember,
            viewContext: View,
        ) {
//            check if it uses add item view
            if (isAdder) return

            val memberName = memberData.name
            nameTextView.text = memberName

//            loading bitmapImage from storage, editing and setting it to imageView
            val internalStorageManager = InternalStorageManager.get()
            val bitmapScaler = InternalStorageManager.BitmapScaler()

            val bitmapImage: Bitmap? = internalStorageManager
                .getBitmapFromInternalStorage(memberData.photoImagePath)
                ?.let {
                    bitmapScaler.scaleBitmap(it, 312)
                }

            val sideSize = viewContext.width / 4
            photoImageView!!.apply {
                if (bitmapImage != null) setImageBitmap(bitmapImage)
                else setImageResource(DEFAULT_MEMBER_PHOTO)

                adjustViewBounds = true
                layoutParams = ViewGroup.LayoutParams(sideSize, sideSize)
                scaleType = ImageView.ScaleType.CENTER_CROP
            }
        }

    }

    override fun getItemViewType(position: Int): Int =
        if (position != 0) TYPE_MEMBER
        else TYPE_ADDER

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MemberViewHolder {
        // Create a new view, which defines the UI of the list item
        val isAdder = viewType == TYPE_ADDER
        val viewResource =
            if (!isAdder) R.layout.company_recycler_view_item
            else R.layout.company_recycler_view_add_item

        val view = LayoutInflater.from(viewGroup.context)
            .inflate(viewResource, viewGroup, false)

        return MemberViewHolder(
            isAdder,view
        )
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(memberViewHolder: MemberViewHolder, position: Int) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        val currentMember = memberList[position]

        memberViewHolder.setMemberData(
            currentMember,
            viewContext
        )
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = memberList.size
}