package tech.toshitworks.blogapp.presentation.profile_screen

sealed class ProfileEvents {
    data object OnLogout: ProfileEvents()
}