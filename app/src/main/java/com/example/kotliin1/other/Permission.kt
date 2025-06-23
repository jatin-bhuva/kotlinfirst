package com.example.kotliin1.other

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.kotliin1.R
import com.example.kotliin1.databinding.FragmentPermissionBinding
import com.example.kotliin1.other.ViewModel.PermissionViewModel

class Permission : Fragment() {

    private lateinit var binding: FragmentPermissionBinding
    private lateinit var viewModel: PermissionViewModel
    private lateinit var permissionHandler: PermissionHandler

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPermissionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        Log.d("ONRESUME","resume///./././././.")
        permissionHandler.requestSinglePermission(
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            requireContext()
        )
        super.onResume()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupObservers()
        setupPermissionHandler()
        setupListeners()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this)[PermissionViewModel::class.java]
    }

    private fun setupObservers() {
        viewModel.grantedPermission.observe(viewLifecycleOwner) { grantedList ->
            binding.grantedPermissionsText.text = if (grantedList.isEmpty()) {
                getString(R.string.no_permissions_granted_yet)
            } else {
                getString(R.string.granted_permissions, grantedList.joinToString("\n"))
            }
        }

        viewModel.deniedPermissionQueue.observe(viewLifecycleOwner) { queue ->
            queue.firstOrNull()?.let { showPermissionDialog(it) }
        }

        viewModel.deniedPermanentPermissionQueue.observe(viewLifecycleOwner) { queue ->
            queue.firstOrNull()?.let { showGoToSettingsDialog(it) }
        }
    }

    private fun setupPermissionHandler() {
        permissionHandler = PermissionHandler(
            this,
            onPermissionResult = { permission, isGranted ->
                viewModel.onPermissionResult(permission, isGranted, isRejectedPermanently = true)
            },
            onPermissionPermanentlyDenied = { permission ->
                viewModel.onPermissionResult(permission, isGranted = false, isRejectedPermanently = false)
            }, requireActivity()
        )
    }

    private fun setupListeners() {
        binding.cameraPermission.setOnClickListener {
            Log.d(getString(R.string.permissions_request), getString(R.string.requesting_access_coarse_location))
            permissionHandler.requestSinglePermission(
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                requireContext()
            )
        }
    }

    private fun showPermissionDialog(permission: String) {
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.permission_needed))
            .setMessage(getString(R.string.this_permission_is_required, permission))
            .setPositiveButton(getString(R.string.allow)) { _, _ ->
                permissionHandler.requestSinglePermission(permission, requireContext())
                viewModel.dismissDialog(permission)
            }
            .setNegativeButton(getString(R.string.deny)) { _, _ ->
                viewModel.dismissDialog(permission)
            }
            .setCancelable(false)
            .show()
    }

    private fun showGoToSettingsDialog(permission: String) {
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.permission_required))
            .setMessage(
                getString(
                    R.string.permission_was_denied_permanently_please_enable_it_in_app_settings,
                    permission
                ))
            .setPositiveButton(getString(R.string.go_to_settings)) { _, _ ->
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = Uri.fromParts("package", requireContext().packageName, null)
                }
                startActivity(intent)
            }
            .setNegativeButton(getString(R.string.cancel)) { _, _ ->
                viewModel.dismissDialog2(permission)
            }
            .show()
    }
}
