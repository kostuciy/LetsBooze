package com.kostuciy.letsbooze.companies

import android.graphics.drawable.Drawable
import android.net.Uri

data class CompanyMember(
    val name: String,
    val photo: Drawable,
    val consumedDrinks: Map<Drink, Int> = mutableMapOf()
)