package com.example.todonotes.clickListeners

import com.example.todonotes.model.Notes

interface ItemClickListener {
    fun onClick(notes : Notes)
}