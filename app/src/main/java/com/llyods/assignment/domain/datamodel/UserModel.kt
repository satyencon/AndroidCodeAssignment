package com.llyods.assignment.domain.datamodel

data class UserModel(
    val avatar_url: String,
    val id: Int,
    val login: String,
    val type: String,
    val url: String
)