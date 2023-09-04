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
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.FragmentActivity
import com.kostuciy.letsbooze.R
import com.kostuciy.letsbooze.data.CompanyViewModel


class MemberRegistrationPopup(
    private val context: FragmentActivity,
)
    : PopupWindow() {
    private val registrationView: View = LayoutInflater.from(context)
            .inflate(R.layout.member_registration_popup, null)

    private val photoImageView: ImageView =
        registrationView.findViewById(R.id.photoImageView)
    private val nameEditText: EditText =
        registrationView.findViewById(R.id.nameEditText)
    val addButton: Button =
        registrationView.findViewById(R.id.addButton)
    val pictureSelectButton: Button =
        registrationView.findViewById(R.id.pictureSelectButton)

    private val galleryLauncher = context.registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
//            There are no request codes
            val data: Intent? = result.data

//            getting uri, setting it to image and uploading to internal storage
            if (data != null) {
                val selectedImageUri: Uri? = data.data
                setImageView(selectedImageUri)
            }
        }
    }

    fun openGalleryForResult() {
        val galleryIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )

        galleryLauncher.launch(galleryIntent)
    }

    fun setupPopup() {
        photoImageView.setImageResource(R.drawable.ic_launcher_foreground) // TODO: set another default

        contentView = registrationView
        height = WindowManager.LayoutParams.WRAP_CONTENT
        width = WindowManager.LayoutParams.MATCH_PARENT
        isFocusable = true

    }

    fun uploadDataToViewModel(companyViewModel: CompanyViewModel): CompanyMember {
        val memberName = nameEditText.text.toString()
        val bitmapImage = photoImageView.drawable.toBitmap()

        val newMember =
            companyViewModel.addNewMember(memberName, bitmapImage)
        companyViewModel.saveMemberList()

        dismiss()

        return newMember
    }

    fun showPopup(view: View) {
        showAtLocation(view, Gravity.TOP, 0, 0)
    }

    override fun dismiss() {
        super.dismiss()
        nameEditText.text.clear()
        photoImageView.setImageResource(R.drawable.ic_launcher_foreground)
    }


    private fun setImageView(imageUri: Uri?) {
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