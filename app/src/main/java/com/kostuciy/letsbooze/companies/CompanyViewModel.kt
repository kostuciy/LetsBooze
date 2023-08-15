package com.kostuciy.letsbooze.companies

import androidx.lifecycle.ViewModel

class CompanyViewModel : ViewModel() {
    private var _currentMembersList =
        mutableListOf<CompanyMember>()
    val currentMembersList: List<CompanyMember>
        get() = _currentMembersList.toList()

    fun updateList(newMemberList: List<CompanyMember>) {
        _currentMembersList = newMemberList.toMutableList()
    }
}
