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
import com.kostuciy.letsbooze.companies.CompanyViewModel
import com.kostuciy.letsbooze.companies.MemberRegistrationPopup

class CompanyFragment : Fragment() {
    private lateinit var companyRecyclerView: RecyclerView
    private lateinit var addMemberButton: Button

    private lateinit var memberRegistrationPopup: MemberRegistrationPopup

    private lateinit var companyViewModel: CompanyViewModel
    private lateinit var membersList: MutableList<CompanyMember> // TODO: remove

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

    private fun initViews(view: View) {
        setRecyclerView(view)
        addMemberButton = view.findViewById(R.id.addMemberButton)

        memberRegistrationPopup = MemberRegistrationPopup(requireActivity())
        memberRegistrationPopup.setup()
    }

    private fun initListeners(view: View) {
        addMemberButton.setOnClickListener {
            memberRegistrationPopup.show(view)
        }

        memberRegistrationPopup.addButton.setOnClickListener {
            val name = memberRegistrationPopup
                .nameEditText.text.toString()

            addMember(name)
            memberRegistrationPopup.reset()
        }
    }

    private fun initViewModel() {
        val _companyViewModel : CompanyViewModel by activityViewModels()
        companyViewModel = _companyViewModel

        membersList = companyViewModel.currentMembersList.toMutableList()
    }

    private fun setRecyclerView(view: View) {
        companyRecyclerView = view.findViewById(R.id.companyRecyclerView)

        val companyAdapter = CompanyAdapter(membersList)
        val gridLayoutManager = GridLayoutManager(
            activity, 3,
            GridLayoutManager.VERTICAL,
            false
        )

        companyRecyclerView.apply {
            layoutManager = gridLayoutManager
            adapter = companyAdapter
        }
    }

    private fun addMember(name: String /*photo: TODO()*/) {
        val newMember = CompanyMember(name)

        membersList += newMember
        companyViewModel.updateList(membersList)

        companyRecyclerView.adapter!!.notifyItemInserted(
            membersList.size - 1
        )
    }

    companion object {
        @JvmStatic
        fun newInstance() = CompanyFragment()
    }
}