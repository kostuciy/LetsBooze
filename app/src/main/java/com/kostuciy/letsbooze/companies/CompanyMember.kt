package com.kostuciy.letsbooze.companies

data class CompanyMember(
    val name: String,
//    val photo: TODO()
    val consumedDrinks: Map<Drink, Int> = mutableMapOf()
)