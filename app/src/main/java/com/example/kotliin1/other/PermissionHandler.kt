package com.example.kotliin1.other

import android.content.pm.PackageManager
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class PermissionHandler(
    private val caller: ActivityResultCaller,
    private val onPermissionResult: (String, Boolean) -> Unit,
    private val onPermissionPermanentlyDenied: ((String) -> Unit)? = null,
    private val context: android.content.Context,
) {

    private val requestPermissionLauncher =
        caller.registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            currentPermission?.let { permission ->
                if (granted) {
                onPermissionResult(permission, true)
            } else {
                val activity = context as android.app.Activity
                val shouldShowRationale = activity.let {
                    ActivityCompat.shouldShowRequestPermissionRationale(it, permission)
                }
                if (shouldShowRationale) {
                    onPermissionResult(permission, false)
                } else {
                    onPermissionPermanentlyDenied?.let { it(permission) }
                }
            }
            currentPermission = null
        }
    }

    private var currentPermission: String? = null

    fun requestSinglePermission(permission: String, context: android.content.Context) {
        if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED) {
            onPermissionResult(permission, true)
            return
        }
        currentPermission = permission
        requestPermissionLauncher.launch(permission)
    }

}
