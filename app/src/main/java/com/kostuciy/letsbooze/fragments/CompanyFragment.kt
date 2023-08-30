package com.kostuciy.letsbooze.fragments

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kostuciy.letsbooze.R
import com.kostuciy.letsbooze.companies.CompanyAdapter
import com.kostuciy.letsbooze.companies.CompanyMember
import com.kostuciy.letsbooze.data.CompanyViewModel
import com.kostuciy.letsbooze.companies.MemberRegistrationPopup
import com.kostuciy.letsbooze.data.InternalStorageManager
import com.kostuciy.letsbooze.utils.ImageScaler

class CompanyFragment : Fragment() {
    private lateinit var addMemberButton: Button
    private lateinit var companyRecyclerView: RecyclerView

    private lateinit var companyViewModel: CompanyViewModel
//    private val companyViewModel: CompanyViewModel by activityViewModels()
    private lateinit var internalStorageManager: InternalStorageManager

    private lateinit var memberRegistrationPopup: MemberRegistrationPopup
    private lateinit var imageScaler: ImageScaler

    private lateinit var membersListCopy: MutableList<CompanyMember>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        initDataStorages()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_company, container, false)

        initDataStorages()

        imageScaler = ImageScaler()
        initViews(view)
        initListeners(view)

        return view
    }

//    override fun onPause() {
//        super.onPause()
//        companyViewModel.saveMemberList()
//    }

    private fun initDataStorages() {
////        ViewModel
        val _companyViewModel : CompanyViewModel by activityViewModels()
        companyViewModel = _companyViewModel

//        companyViewModel.uploadMemberList()
        membersListCopy = companyViewModel.currentMembersList.toMutableList()

//        InternalStorageManager
        internalStorageManager = InternalStorageManager(
            requireActivity().application
        )
    }

    private fun initViews(view: View) {
        initRecyclerView(view)
        addMemberButton = view.findViewById(R.id.addMemberButton)

        memberRegistrationPopup = MemberRegistrationPopup(requireActivity())
        memberRegistrationPopup.setupPopup()
    }

    private fun initListeners(view: View) {
        addMemberButton.setOnClickListener {
            memberRegistrationPopup.showPopup(view)
        }

        memberRegistrationPopup.apply {
            addButton.setOnClickListener {
                val name =
                    this.nameEditText.text.toString()
                val imageBitmap =
                    this.photoImageView.drawable.toBitmap()

                processNewMember(name, imageBitmap)
                memberRegistrationPopup.dismiss()
            }

            pictureSelectButton.setOnClickListener {
                memberRegistrationPopup.openGalleryForResult()
            }
        }
    }

    private fun initRecyclerView(view: View) {
        companyRecyclerView = view.findViewById(R.id.companyRecyclerView)

        val companyAdapter = CompanyAdapter(
            membersListCopy,
            view,
            internalStorageManager,
            imageScaler
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

    private fun processNewMember(name: String, imageBitmap: Bitmap) {
        companyViewModel.apply {
            addNewMember(
                name, imageBitmap,
                internalStorageManager
            ).let { newMember ->
                membersListCopy += newMember // updating copy for CompanyRecyclerView
            }

            saveMemberList()
        }
        
        companyRecyclerView.adapter!!.notifyItemInserted(
            membersListCopy.size - 1
        )
    }

//    companion object {
//        @JvmStatic
//        fun newInstance() = CompanyFragment()
//    }
}