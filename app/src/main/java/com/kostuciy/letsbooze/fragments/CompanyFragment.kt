package com.kostuciy.letsbooze.fragments

import android.graphics.drawable.Drawable
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
import com.kostuciy.letsbooze.companies.MemberRegistrationPopup
import com.kostuciy.letsbooze.utils.ImageResolutionChanger

class CompanyFragment : Fragment() {
    private lateinit var addMemberButton: Button
    private lateinit var companyRecyclerView: RecyclerView
    private lateinit var companyViewModel: CompanyViewModel

    private lateinit var memberRegistrationPopup: MemberRegistrationPopup
    private lateinit var imageResolutionChanger: ImageResolutionChanger

    private lateinit var membersList: MutableList<CompanyMember>

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

        imageResolutionChanger = ImageResolutionChanger()
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

        memberRegistrationPopup = MemberRegistrationPopup(requireActivity())
        memberRegistrationPopup.setupPopup()
    }

    private fun initListeners(view: View) {
        addMemberButton.setOnClickListener {
            memberRegistrationPopup.showPopup(view)
        }

        memberRegistrationPopup.addButton.setOnClickListener {
            val name = memberRegistrationPopup
                .nameEditText.text.toString()
            val imageDrawable =
                memberRegistrationPopup.photoImageView.drawable

            addMemberToRecyclerView(name, imageDrawable)
            memberRegistrationPopup.dismiss()
        }

        memberRegistrationPopup.pictureSelectButton.setOnClickListener {
            memberRegistrationPopup.openGalleryForResult()
        }
    }

    private fun initRecyclerView(view: View) {
        companyRecyclerView = view.findViewById(R.id.companyRecyclerView)

        val companyAdapter = CompanyAdapter(membersList, view, imageResolutionChanger)
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