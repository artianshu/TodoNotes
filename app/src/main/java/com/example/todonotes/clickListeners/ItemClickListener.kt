package com.example.todonotes.clickListeners

import com.example.todonotes.model.Notes

interface ItemClickListener {
    fun onClick(notes : com.example.todonotes.db.Notes)
    fun onUpdate(notes: com.example.todonotes.db.Notes)
}