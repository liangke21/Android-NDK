package com.muc.myapplication.entity

class Animal(names: String) {

    var name=names

    companion object NUM{
        @JvmStatic
        var num=0
    }


}