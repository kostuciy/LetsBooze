package com.kostuciy.letsbooze.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.PopupWindow
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kostuciy.letsbooze.R
import com.kostuciy.letsbooze.companies.CompanyAdapter
import com.kostuciy.letsbooze.companies.CompanyMember
import com.kostuciy.letsbooze.data.CompanyViewModel
import com.kostuciy.letsbooze.data.InternalStorageManager

class CompanyFragment : Fragment() {
    private lateinit var companyRecyclerView: RecyclerView
    private lateinit var companyPopup: CompanyPopup

    private lateinit var companyViewModel: CompanyViewModel
    private lateinit var membersListCopy: MutableList<CompanyMember>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_company, container, false)

        initViewModel()
        initViews(view)
        initListeners(view)

        return view
    }

    private fun initViewModel() {
        val _companyViewModel : CompanyViewModel by activityViewModels()
        companyViewModel = _companyViewModel

//        copying list to give to recycler view + creating
//        empty member, that will be used as add button
        membersListCopy =
            companyViewModel.currentMembersList.toMutableList()
        membersListCopy.add(
            0,
            CompanyMember("lets_booze_adder", "_")
        )  //TODO: figure out how to add empty member
    }

    private fun initViews(view: View) {
        companyPopup = CompanyPopup(requireActivity())
        initRecyclerView(view)
//        initPopupWindow()
    }

    private fun initListeners(view: View) {
//        companyPopup.apply {
//            applyChangesButton!!.setOnClickListener {
//                updateRecyclerView(uploadDataToViewModel(companyViewModel))
//            }
//            photoImageView!!.setOnClickListener {
//                openGalleryForResult()
//            }
//        }

//        (companyRecyclerView.adapter as CompanyAdapter).apply {
//            setAdderClickListener {
//                companyPopup.apply {
//                    setPopupForRegistration()
//                    showPopup(view)
//                }
//            }
//        }
    }

//    private fun initPopupWindow() {
//        companyMemberPopupWindow = PopupWindow(requireActivity())
//
//        companyMemberPopupWindow.apply {
//            photoImageView.setImageResource(R.drawable.ic_launcher_foreground) // TODO: set another default
//
//            contentView = registrationView
//            height = WindowManager.LayoutParams.WRAP_CONTENT
//            width = WindowManager.LayoutParams.MATCH_PARENT
//            isFocusable = true
//        }
//    }

    private fun initRecyclerView(view: View) {
        companyRecyclerView = view.findViewById(R.id.companyRecyclerView)

        val companyAdapter = CompanyAdapter(
            membersListCopy,
            view,
            companyPopup
        )
        val gridLayoutManager = GridLayoutManager(
            activity, 4,
            GridLayoutManager.VERTICAL,
            false
        )

        companyRecyclerView.apply {
            layoutManager = gridLayoutManager
            adapter = companyAdapter
        }
    }

    private fun updateRecyclerView(
        member: CompanyMember,
        isRemoving: Boolean,
        memberPosition: Int) {
        val adapter = companyRecyclerView.adapter!!

        when {
            isRemoving -> {
                membersListCopy.removeAt(memberPosition)
                adapter.notifyItemRemoved(memberPosition)
            }

            memberPosition == -1 ->  {
                membersListCopy += member // updating copy for CompanyRecyclerView
                adapter.notifyItemInserted(
                    membersListCopy.size - 1)

            }

            else -> {
                adapter.notifyItemChanged(memberPosition)
                membersListCopy[memberPosition] = member
            }
        }
    }

    inner class CompanyPopup(fragmentActivity: FragmentActivity)
        : PopupWindow(fragmentActivity) {
        private val registrationView: View = LayoutInflater.from(fragmentActivity)
            .inflate(R.layout.company_registration_popup, null, false)
        private val editingView: View = LayoutInflater.from(fragmentActivity)
            .inflate(R.layout.company_edit_popup, null, false)

        var currentMemberPosition: Int = -1

        private var nameEditText: EditText? = null
        private var photoImageView: ImageView? = null
        private var applyChangesButton: Button? = null
        private var deleteMemberButton: Button? = null

        private val galleryLauncher =
            fragmentActivity.registerForActivityResult(
                ActivityResultContracts.StartActivityForResult()
            ) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val data: Intent? = result.data

//              getting uri, setting it to image and uploading to internal storage
                    if (data != null) {
                        val selectedImageUri: Uri? = data.data
                        setImageView(selectedImageUri)
                    }
                }
            }

        private fun openGalleryForResult() {
            val galleryIntent = Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )

            galleryLauncher.launch(galleryIntent)
        }

        fun setPopupForRegistration() {
            contentView = registrationView
            initViews(false)
            photoImageView!!.setImageResource(R.drawable.ic_launcher_foreground) // TODO: set another default
            setupParams()

            initListeners(false)
        }

        fun setPopupForEditing(selectedMember: CompanyMember) {
            contentView = editingView
            setupParams()
            initViews(true)

            InternalStorageManager.get().let {
                val path = selectedMember.photoImagePath
                val bitmap = it.getBitmapFromInternalStorage(path)

                photoImageView!!.setImageBitmap(bitmap)
            }
            nameEditText!!.setText(selectedMember.name)

            initListeners(true)
        }

        private fun initListeners(isEditing: Boolean) {
            applyChangesButton!!.setOnClickListener {
                val member =
                    if (currentMemberPosition == -1)
                        addNewMemberToViewModel(companyViewModel)
                    else {
                        editMemberInViewModel(companyViewModel)
                    }

                updateRecyclerView(
                    member,
                    false, currentMemberPosition
                )
            }

            photoImageView!!.setOnClickListener {
                openGalleryForResult()
            }

            if (isEditing) {
                deleteMemberButton!!.setOnClickListener {
                    companyViewModel.removeMember(currentMemberPosition - 1)

                    updateRecyclerView(
                        CompanyMember("", ""),
                        true, currentMemberPosition
                    )

                    dismiss()
                }
            }
        }

        private fun setupParams() {
            height = WindowManager.LayoutParams.WRAP_CONTENT
            width = WindowManager.LayoutParams.MATCH_PARENT
            isFocusable = true
        }

        fun showPopup(view: View) {
            showAtLocation(view, Gravity.TOP, 0, 0)
        }

        private fun initViews(isEditing: Boolean) {
            photoImageView = contentView.findViewById(R.id.photoImageView)
            nameEditText = contentView.findViewById(R.id.nameEditText)
            applyChangesButton = contentView.findViewById(R.id.applyButton)

            if (isEditing)
                deleteMemberButton = contentView.findViewById(R.id.deleteMemberButton)
        }

        private fun addNewMemberToViewModel(companyViewModel: CompanyViewModel): CompanyMember {
            val memberName = nameEditText!!.text.toString()
            val bitmapImage = photoImageView!!.drawable.toBitmap()

            val newMember =
                companyViewModel.addNewMember(memberName, bitmapImage)

            dismiss()

            return newMember
        }

        private fun editMemberInViewModel(companyViewModel: CompanyViewModel): CompanyMember {
            val memberName = nameEditText!!.text.toString()
            val bitmapImage = photoImageView!!.drawable?.toBitmap()

            val editedMember =
                companyViewModel.editMember(currentMemberPosition - 1, memberName, bitmapImage)

            dismiss()

            return editedMember
        }

        override fun dismiss() {
            super.dismiss()
            nameEditText!!.text.clear()
            photoImageView!!.setImageResource(R.drawable.ic_launcher_foreground)
        }

        private fun setImageView(imageUri: Uri?) {
            if (imageUri == null) return

            val sideSize = registrationView.width / 2
            photoImageView!!.apply {
                setImageURI(imageUri)
                scaleType = ImageView.ScaleType.CENTER_CROP
                layoutParams.width = sideSize
                layoutParams.height = layoutParams.width
                requestLayout()
            }
        }
    }
}