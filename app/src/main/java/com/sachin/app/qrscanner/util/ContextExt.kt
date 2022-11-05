package com.sachin.app.qrscanner.util

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import com.sachin.app.qrscanner.BuildConfig
import com.sachin.app.qrscanner.R

fun Context.openPlayStore() {
    try {
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("market://details?id=${BuildConfig.APPLICATION_ID}")
            )
        )
    } catch (e: ActivityNotFoundException) {
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}")
            )
        )
    }
}

fun Context.showToast(msg: String, duration: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(this, msg, duration).show()

fun Context.openDeveloperPage() {
    try {
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://play.google.com/store/apps/dev?id=6475482614401654947")
            )
        )
    } catch (e: ActivityNotFoundException) {
        showToast("No application found to handle this action")
    }
}

private val Context.isTelegramInstalled: Boolean
    get() = packageManager.getInstalledPackages(PackageManager.GET_META_DATA)
        .find { it.packageName == "org.telegram.messenger" } != null


fun Context.sendFeedback() {
    if (isTelegramInstalled) openTelegramChannel()
    else sendEmail(
        "[FEEDBACK] ${getString(R.string.app_name)} | App by Sachin",
        "Dear developer!\n"
    )
}

fun Context.openTelegramChannel() {
    if (isTelegramInstalled) {
        try {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://t.me/skappdevelopment")
                )
            )
        } catch (e: ActivityNotFoundException) {
            e.printStackTrace()
            sendEmail()
        }
    } else sendEmail()
}

private fun Context.sendEmail(subject: String? = null, text: String? = null) {
    val intent = Intent(Intent.ACTION_SENDTO)
    intent.data = Uri.parse("mailto:")
    intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("feedbacks.sachin@gmail.com"))

    if (subject != null)
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)

    if (text !== null)
        intent.putExtra(Intent.EXTRA_TEXT, text)

    try {
        startActivity(Intent.createChooser(intent, "Send email using"))
    } catch (ex: ActivityNotFoundException) {
        showToast("There are no email clients installed.")
    }
}


fun Context.sendAppLink() {
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(
            Intent.EXTRA_TEXT,
            "https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}"
        )
        type = "text/plain"
    }

    val shareIntent = Intent.createChooser(sendIntent, null)
    startActivity(shareIntent)
}
