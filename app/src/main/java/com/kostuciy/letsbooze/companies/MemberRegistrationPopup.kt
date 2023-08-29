package com.kostuciy.letsbooze.companies

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.FragmentActivity
import com.kostuciy.letsbooze.R


class MemberRegistrationPopup(context: FragmentActivity)
    : PopupWindow() {
    private val registrationView: View =
        LayoutInflater.from(context)
            .inflate(R.layout.member_registration_popup, null)

    val photoImageView: ImageView =
        registrationView.findViewById(R.id.photoImageView)
    val addButton: Button =
        registrationView.findViewById(R.id.addButton)
    val pictureSelectButton: Button =
        registrationView.findViewById(R.id.pictureSelectButton)
    val nameEditText: EditText =
        registrationView.findViewById(R.id.nameEditText)

    fun setupPopup() {
        photoImageView.setImageResource(R.drawable.ic_launcher_foreground) // TODO(set another default)

        contentView = registrationView
        height = WindowManager.LayoutParams.WRAP_CONTENT
        width = WindowManager.LayoutParams.MATCH_PARENT
        isFocusable = true
    }

    fun showPopup(view: View) {
        showAtLocation(view, Gravity.TOP, 0, 0)
    }

    override fun dismiss() {
        super.dismiss()
        nameEditText.text.clear()
        photoImageView.setImageResource(R.drawable.ic_launcher_foreground)
    }

    fun openGalleryForResult() {
        val galleryIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )

        galleryLauncher.launch(galleryIntent)
    }

    private var galleryLauncher = context.registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // There are no request codes
            val data: Intent? = result.data

            if (data != null) {
                val selectedImageUri: Uri? = data.data
                setPhoto(selectedImageUri)
            }
        }
    }

    private fun setPhoto(imageUri: Uri?) {
        if (imageUri == null) return

        val sizeParams = LinearLayout.LayoutParams(
            registrationView.width / 2, registrationView.width / 2
        )

        photoImageView.apply {
            setImageURI(imageUri)
            layoutParams = sizeParams
            scaleType = ImageView.ScaleType.CENTER_CROP
        }
    }
}