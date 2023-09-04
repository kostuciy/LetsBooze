package com.kostuciy.letsbooze.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kostuciy.letsbooze.R
import com.kostuciy.letsbooze.companies.CompanyAdapter
import com.kostuciy.letsbooze.companies.CompanyMember
import com.kostuciy.letsbooze.data.CompanyViewModel
import com.kostuciy.letsbooze.companies.MemberRegistrationPopup

class CompanyFragment : Fragment() {
    private lateinit var addMemberButton: Button
    private lateinit var companyRecyclerView: RecyclerView

    private lateinit var companyViewModel: CompanyViewModel
//    private val companyViewModel: CompanyViewModel by activityViewModels()

    private lateinit var memberRegistrationPopup: MemberRegistrationPopup

    private lateinit var membersListCopy: MutableList<CompanyMember>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_company, container, false)

        initDataStorages()

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
                uploadDataToViewModel(companyViewModel).let { newMember ->
                    updateRecyclerView(newMember)
                }
            }
            pictureSelectButton.setOnClickListener {
                openGalleryForResult()
            }
        }
    }

    private fun initRecyclerView(view: View) {
        companyRecyclerView = view.findViewById(R.id.companyRecyclerView)

        val companyAdapter = CompanyAdapter(
            membersListCopy,
            view
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

    private fun updateRecyclerView(newMember: CompanyMember) {
        membersListCopy += newMember // updating copy for CompanyRecyclerView
        
        companyRecyclerView.adapter!!.notifyItemInserted(
            membersListCopy.size - 1
        )
    }
}