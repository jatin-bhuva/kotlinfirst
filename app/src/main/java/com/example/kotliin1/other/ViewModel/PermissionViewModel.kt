package com.example.kotliin1.other.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PermissionViewModel: ViewModel() {
    private val _grantedPermission = MutableLiveData<List<String>>(emptyList())
    val grantedPermission: LiveData<List<String>> = _grantedPermission

    private val _deniedPermissionQueue = MutableLiveData<List<String>>(emptyList())
    val deniedPermissionQueue: LiveData<List<String>> = _deniedPermissionQueue

    private val _deniedPermanentPermissionQueue = MutableLiveData<List<String>>(emptyList())
    val deniedPermanentPermissionQueue: LiveData<List<String>> = _deniedPermanentPermissionQueue
    fun onPermissionResult(permission: String, isGranted: Boolean, isRejectedPermanently: Boolean) {
        if(isGranted){
            val updated = _grantedPermission.value?.toMutableList() ?: mutableListOf()
            if (!updated.contains(permission)) {
                updated.add(permission)
                _grantedPermission.value = updated
            }
        }
        if (!isGranted && isRejectedPermanently) {
            val updated = _deniedPermissionQueue.value?.toMutableList() ?: mutableListOf()
            if (!updated.contains(permission)) {
                updated.add(permission)
                _deniedPermissionQueue.value = updated
            }
        }
        if(!isRejectedPermanently){
            val updated = _deniedPermanentPermissionQueue.value?.toMutableList() ?: mutableListOf()
            if (!updated.contains(permission)) {
                updated.add(permission)
                _deniedPermanentPermissionQueue.value = updated
            }
        }
    }

    fun dismissDialog(permission: String) {
        val updated = _deniedPermissionQueue.value?.toMutableList() ?: return
        updated.remove(permission)
        _deniedPermissionQueue.value = updated
    }

    fun dismissDialog2(permission: String) {
        val updated = _deniedPermanentPermissionQueue.value?.toMutableList() ?: return
        updated.remove(permission)
        _deniedPermanentPermissionQueue.value = updated
    }
}