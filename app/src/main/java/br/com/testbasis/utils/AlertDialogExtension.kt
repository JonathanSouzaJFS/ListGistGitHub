package br.com.testbasis.utils

import androidx.fragment.app.Fragment

inline fun Fragment.showAlertDialog(func: AlertDialogHelper.() -> Unit): android.app.AlertDialog =
    AlertDialogHelper(context!!).apply {
        func()
    }.create()

inline fun Fragment.showCreateAddress(func: CreateAddressDialog.() -> Unit): android.app.AlertDialog =
    CreateAddressDialog(context!!).apply {
        func()
    }.create()