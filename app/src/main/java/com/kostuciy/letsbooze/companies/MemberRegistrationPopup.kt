package com.kostuciy.letsbooze.companies

import android.app.Activity
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.PopupWindow
import androidx.fragment.app.FragmentActivity
import com.kostuciy.letsbooze.R

class MemberRegistrationPopup(private val context: FragmentActivity)
    : PopupWindow() {
    private val registrationView: View =
        LayoutInflater.from(context)
            .inflate(R.layout.fragment_member_registration, null)

    val photoImageView: ImageView =
        registrationView.findViewById(R.id.photoImageView)
    val addButton: Button =
        registrationView.findViewById(R.id.addButton)
    val nameEditText: EditText =
        registrationView.findViewById(R.id.nameEditText)


    fun setup() {
        photoImageView.setImageResource(R.drawable.ic_launcher_foreground) // TODO(set another default)

        contentView = registrationView
        height = WindowManager.LayoutParams.WRAP_CONTENT
        width = WindowManager.LayoutParams.MATCH_PARENT
        isFocusable = true
    }

    fun show(view: View) {
        showAtLocation(view, Gravity.TOP, 0, 0)
    }

    fun reset() {
        super.dismiss()
        nameEditText.text.clear()
    }


}