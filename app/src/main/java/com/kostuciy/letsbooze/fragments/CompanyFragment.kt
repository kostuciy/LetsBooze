package com.kostuciy.letsbooze.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kostuciy.letsbooze.R
import com.kostuciy.letsbooze.companies.CompanyAdapter
import com.kostuciy.letsbooze.companies.CompanyMember
import com.kostuciy.letsbooze.companies.Drink

/**
 * A simple [Fragment] subclass.
 * Use the [CompanyFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CompanyFragment : Fragment() {
    private lateinit var companyRecyclerView: RecyclerView
    private lateinit var addMemberButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_company, container, false)

        initViews(view)
        initListeners()

//        val argums =
//            if (arguments != null) CompanyFragmentArgs.fromBundle(requireArguments())
//            else null

        return view
    }

    private fun initViews(view: View) {
        setRecyclerView(view)
        addMemberButton = view.findViewById(R.id.addMemberButton)
    }

    private fun initListeners() {
        addMemberButton.setOnClickListener {
            findNavController().navigate(R.id.startingMemberRegistration)
        }
    }

    private fun setRecyclerView(view: View) {
        val testData = mutableListOf<CompanyMember>(
            CompanyMember(
                "Константин",
                mapOf(Pair(Drink("dpfk"), 1))
            ),
            CompanyMember(
                "Константин",
                mapOf(Pair(Drink("dpfk"), 1))
            ),
            CompanyMember(
                "Константин",
                mapOf(Pair(Drink("dpfk"), 1))
            ),
            CompanyMember(
                "Константин",
                mapOf(Pair(Drink("dpfk"), 1))
            ),
            CompanyMember(
                "Константин",
                mapOf(Pair(Drink("dpfk"), 1))
            ),
            CompanyMember(
                "Константин",
                mapOf(Pair(Drink("dpfk"), 1))
            ),
            CompanyMember(
                "Константин",
                mapOf(Pair(Drink("dpfk"), 1))
            ),
            CompanyMember(
                "Константин",
                mapOf(Pair(Drink("dpfk"), 1))
            ),
            CompanyMember(
                "Константин",
                mapOf(Pair(Drink("dpfk"), 1))
            ),
            CompanyMember(
                "Константин",
                mapOf(Pair(Drink("dpfk"), 1))
            ),
            CompanyMember(
                "Константин",
                mapOf(Pair(Drink("dpfk"), 1))
            ),
            CompanyMember(
                "Константин",
                mapOf(Pair(Drink("dpfk"), 1))
            ),
            CompanyMember(
                "Константин",
                mapOf(Pair(Drink("dpfk"), 1))
            ),
            CompanyMember(
                "Константин",
                mapOf(Pair(Drink("dpfk"), 1))
            ),
            CompanyMember(
                "Константин",
                mapOf(Pair(Drink("dpfk"), 1))
            ),
            CompanyMember(
                "Константин",
                mapOf(Pair(Drink("dpfk"), 1))
            ),
        ) // TODO: remove

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

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) = CompanyFragment()
    }
}