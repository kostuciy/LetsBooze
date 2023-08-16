package com.kostuciy.letsbooze.companies

import androidx.lifecycle.ViewModel

class CompanyViewModel : ViewModel() {
    private val _currentMembersList =
        mutableListOf<CompanyMember>()
    val currentMembersList: List<CompanyMember>
        get() = _currentMembersList.toList()

    fun addMember(newMember: CompanyMember) {
        _currentMembersList += newMember
    }
}
