package com.llyods.assignment.data.remote.responses

import com.squareup.moshi.Json

data class UserListResponseItem(
    @Json(name = "avatar_url")
    val avatar_url: String,
    @Json(name = "events_url")
    val events_url: String,
    @Json(name = "followers_url")
    val followers_url: String,
    @Json(name = "following_url")
    val following_url: String,
    @Json(name = "gists_url")
    val gists_url: String,
    @Json(name = "gravatar_id")
    val gravatar_id: String,
    @Json(name = "html_url")
    val html_url: String,
    @Json(name = "id")
    val id: Int,
    @Json(name = "login")
    val login: String,
    @Json(name = "node_id")
    val node_id: String,
    @Json(name = "organizations_url")
    val organizations_url: String,
    @Json(name = "received_events_url")
    val received_events_url: String,
    @Json(name = "repos_url")
    val repos_url: String,
    @Json(name = "site_admin")
    val site_admin: Boolean,
    @Json(name = "starred_url")
    val starred_url: String,
    @Json(name = "subscriptions_url")
    val subscriptions_url: String,
    @Json(name = "type")
    val type: String,
    @Json(name = "url")
    val url: String
)