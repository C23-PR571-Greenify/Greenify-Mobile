package com.rememberdev.greenify.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class VerifyOTPResponse(
    @field:SerializedName("status")
    val status: String,

    @field:SerializedName("msg")
    val msg: String,
) : Parcelable
