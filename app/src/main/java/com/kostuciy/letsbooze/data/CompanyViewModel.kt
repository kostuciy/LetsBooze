package com.kostuciy.letsbooze.data

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import com.kostuciy.letsbooze.companies.CompanyMember

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
        name: String, bitmapImage: Bitmap
    ): CompanyMember {
        val internalStorageManager = InternalStorageManager.get()
        val photoImagePath =
            internalStorageManager.saveImageToInternalStorage(bitmapImage, name)

        val newMember = CompanyMember(name, photoImagePath)
        _currentMembersList += newMember

        saveMemberList()

        return newMember
    }

    fun editMember(indexPosition: Int, newName: String, newBitmap: Bitmap?): CompanyMember {
        val internalStorageManager = InternalStorageManager.get()
        _currentMembersList[indexPosition].let {
            internalStorageManager.removeImageFileFromStorage(it.photoImagePath)
        }

        val newPath = if (newBitmap != null)
            internalStorageManager.saveImageToInternalStorage(newBitmap, newName)
        else ""

        val newMember = CompanyMember(newName, newPath)
        _currentMembersList[indexPosition] = newMember

        saveMemberList()

        return newMember
    }

    fun removeMember(indexPosition: Int) {
        val memberToRemove = _currentMembersList[indexPosition]
        _currentMembersList.removeAt(indexPosition)

        val internalStorageManager = InternalStorageManager.get()
        internalStorageManager.removeImageFileFromStorage(memberToRemove.photoImagePath)

        saveMemberList()
    }

    private fun saveMemberList() {
        PreferencesManager
            .get()
            .put(
                _currentMembersList.toTypedArray(), // doesn't work as list
                MEMBER_LIST_KEY
            )
    }
}
