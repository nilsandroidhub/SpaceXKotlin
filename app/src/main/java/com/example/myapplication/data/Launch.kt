package com.example.myapplication.data

data class Launch (
    val name: String,
    val flight_number: Double,
    val success: Boolean,
    val date_local: String,
    val details: String,
    val links: Link,
)

data class Link(
    val patch: Patch,
)
data class Patch(
    val small: String
)





