package com.kostuciy.letsbooze.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kostuciy.letsbooze.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [MemberCreationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MemberCreationFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =
            inflater.inflate(R.layout.member_registration_popup, container, false)



        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() = MemberCreationFragment()
    }
}