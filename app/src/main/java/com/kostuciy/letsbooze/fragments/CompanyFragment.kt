package com.kostuciy.letsbooze.fragments

import android.graphics.drawable.Drawable
import android.net.Uri
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
import com.kostuciy.letsbooze.companies.CompanyViewModel
import com.kostuciy.letsbooze.companies.MemberRegistrationManager

class CompanyFragment : Fragment() {
    private lateinit var companyRecyclerView: RecyclerView
    private lateinit var addMemberButton: Button

    private lateinit var memberRegistrationManager: MemberRegistrationManager

    private lateinit var companyViewModel: CompanyViewModel
    private lateinit var membersList: MutableList<CompanyMember> // TODO: remove

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_company, container, false)

        initViews(view)
        initListeners(view)

        return view
    }

    private fun initViewModel() {
        val _companyViewModel : CompanyViewModel by activityViewModels()
        companyViewModel = _companyViewModel

        membersList = companyViewModel.currentMembersList.toMutableList()
    }
    private fun initViews(view: View) {
        initRecyclerView(view)
        addMemberButton = view.findViewById(R.id.addMemberButton)

        memberRegistrationManager = MemberRegistrationManager(requireActivity())
        memberRegistrationManager.setupPopup()
    }

    private fun initListeners(view: View) {
        addMemberButton.setOnClickListener {
            memberRegistrationManager.showPopup(view)
        }

        memberRegistrationManager.addButton.setOnClickListener {
            val name = memberRegistrationManager
                .nameEditText.text.toString()
            val imageDrawable =
                memberRegistrationManager.photoImageView.drawable

            addMemberToRecyclerView(name, imageDrawable)
            memberRegistrationManager.dismiss()
        }

        memberRegistrationManager.pictureSelectButton.setOnClickListener {
            memberRegistrationManager.openGalleryForResult()
        }
    }
    private fun initRecyclerView(view: View) {
        companyRecyclerView = view.findViewById(R.id.companyRecyclerView)

        val companyAdapter = CompanyAdapter(membersList, view)
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

    private fun addMemberToRecyclerView(name: String, photoDrawable: Drawable) {
        val newMember = CompanyMember(name, photoDrawable)

        membersList += newMember
        companyViewModel.addMember(newMember)

        companyRecyclerView.adapter!!.notifyItemInserted(
            membersList.size - 1
        )
    }

    companion object {
        @JvmStatic
        fun newInstance() = CompanyFragment()
    }
}