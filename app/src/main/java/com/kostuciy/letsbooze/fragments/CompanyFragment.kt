package com.kostuciy.letsbooze.fragments

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.PopupWindow
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kostuciy.letsbooze.R
import com.kostuciy.letsbooze.companies.CompanyAdapter
import com.kostuciy.letsbooze.companies.CompanyMember
import com.kostuciy.letsbooze.companies.MemberRegistrationPopup


/**
 * A simple [Fragment] subclass.
 * Use the [CompanyFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CompanyFragment : Fragment() {
    private lateinit var companyRecyclerView: RecyclerView
    private lateinit var addMemberButton: Button

    private lateinit var memberRegistrationPopup: MemberRegistrationPopup

    val testData = mutableListOf<CompanyMember>() // TODO: remove

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

    private fun setRecyclerView(view: View) {
        companyRecyclerView = view.findViewById(R.id.companyRecyclerView)

        val companyAdapter = CompanyAdapter(testData)
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
        testData += newMember

        companyRecyclerView.adapter!!.notifyItemInserted(testData.size - 1)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) = CompanyFragment()
    }
}