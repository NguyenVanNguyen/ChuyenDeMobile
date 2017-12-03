package com.example.holu.hinhtetvs1.helper

import com.google.firebase.database.FirebaseDatabase

/**
 * Project Name Hinhtetvs1
 * Created by HoLu on 11/21/2017.
 * All rights reserved
 */
class FirebaseUtils {
    companion object {
        val mRef = FirebaseDatabase.getInstance().reference
    }
}