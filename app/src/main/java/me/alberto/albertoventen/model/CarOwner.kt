package me.alberto.albertoventen.model

data class CarOwner(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val email: String,
    val country: String,
    val carModel: String,
    val year: String,
    val carColor: String,
    val gender: String,
    val jobTitle: String,
    val bio: String,
    val initials: String = " ${firstName[0].toUpperCase()}${lastName[0].toUpperCase()}"
)