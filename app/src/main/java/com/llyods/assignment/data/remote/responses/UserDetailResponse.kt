package com.llyods.assignment.data.remote.responses

import com.squareup.moshi.Json

data class UserDetailResponse(
    @Json(name = "avatar_url")
    val avatarUrl: String? = null,
    @Json(name = "bio")
    val bio: String? = null,
    @Json(name = "blog")
    val blog: String? = null,
    @Json(name = "company")
    val company: String? = null,
    @Json(name = "createdAt")
    val createdAt: String? = null,
    @Json(name = "email")
    val email: String? = null,
    @Json(name = "eventsUrl")
    val eventsUrl: String? = null,
    @Json(name = "followers")
    val followers: Int? = null,
    @Json(name = "followersUrl")
    val followersUrl: String? = null,
    @Json(name = "following")
    val following: Int? = null,
    @Json(name = "followingUrl")
    val followingUrl: String? = null,
    @Json(name = "gistsUrl")
    val gistsUrl: String? = null,
    @Json(name = "gravatarId")
    val gravatarId: String? = null,
    @Json(name = "hireable")
    val hireable: Boolean? = null,
    @Json(name = "htmlUrl")
    val htmlUrl: String? = null,
    @Json(name = "id")
    val id: Int,
    @Json(name = "location")
    val location: String? = null,
    @Json(name = "login")
    val login: String,
    @Json(name = "name")
    val name: String? = null,
    @Json(name = "nodeId")
    val nodeId: String? = null,
    @Json(name = "organizationsUrl")
    val organizationsUrl: String? = null,
    @Json(name = "publicGists")
    val publicGists: Int? = null,
    @Json(name = "publicRepos")
    val publicRepos: Int? = null,
    @Json(name = "receivedEventsUrl")
    val receivedEventsUrl: String? = null,
    @Json(name = "reposUrl")
    val reposUrl: String? = null,
    @Json(name = "siteAdmin")
    val siteAdmin: Boolean? = null,
    @Json(name = "starredUrl")
    val starredUrl: String? = null,
    @Json(name = "subscriptionsUrl")
    val subscriptionsUrl: String? = null,
    @Json(name = "twitterUsername")
    val twitterUsername: String? = null,
    @Json(name = "type")
    val type: String? = null,
    @Json(name = "updatedAt")
    val updatedAt: String? = null,
    @Json(name = "url")
    val url: String? = null
)