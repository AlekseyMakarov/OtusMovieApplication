package com.example.otusalekseymakarovmovies

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class CustomDialog() : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.quit_dialog_text))

            .setNegativeButton(getString(R.string.dialog_cancel)) { _, _ -> }
            .setPositiveButton(getString(R.string.dialog_quit)) { it1, it2 ->
                (activity as? OnCustomDialogDismissClickListener)?.onClickDismiss()
            }
            .create()
    }

//    override fun onDismiss(dialog: DialogInterface) {
//        super.onDismiss(dialog)
//        (activity as? OnCustomDialogDismissClickListener)?.onClickDismiss()
//    }
}


interface OnCustomDialogDismissClickListener {
    fun onClickDismiss()
}