package com.example.todonotes.model

class Notes {
     var title : String ?= null
     var description : String ?= null

    constructor(title: String?, description: String?) {
        this.title = title
        this.description = description
    }
}