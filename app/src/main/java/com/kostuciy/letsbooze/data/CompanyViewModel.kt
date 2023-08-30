package com.kostuciy.letsbooze.data

import android.graphics.Bitmap
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.lifecycle.ViewModel
import com.google.gson.internal.LinkedTreeMap
import com.kostuciy.letsbooze.R
import com.kostuciy.letsbooze.companies.CompanyMember
import java.util.TreeMap

private const val MEMBER_LIST_KEY = "Member List"

class CompanyViewModel : ViewModel() {
    private val _currentMembersList =
        mutableListOf<CompanyMember>()

    init {
        val preferencesManager = PreferencesManager.get()
        preferencesManager.setPreferences()

//        loading member list data from internal storage
        preferencesManager
            .getValue<Array<CompanyMember>>(MEMBER_LIST_KEY)
            .let { membersList: Array<CompanyMember>? ->
                membersList?.forEach {
                    _currentMembersList += it }
            }
    }

    val currentMembersList: List<CompanyMember>
        get() = _currentMembersList.toList()

    fun addNewMember(
        name: String, bitmapImage: Bitmap,
        internalStorageManager: InternalStorageManager
    ): CompanyMember {
        val photoImagePath =
            internalStorageManager.saveImageToInternalStorage(bitmapImage, name)

        val newMember = CompanyMember(name, photoImagePath)
        _currentMembersList += newMember

        return newMember
    }


    fun saveMemberList() {
        PreferencesManager
            .get()
            .put(
                _currentMembersList.toTypedArray(), // doesn't work as list
                MEMBER_LIST_KEY
            )
    }
}
