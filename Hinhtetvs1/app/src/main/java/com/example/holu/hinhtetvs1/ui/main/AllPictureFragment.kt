package com.example.holu.hinhtetvs1.ui.main

import android.app.Activity
import android.app.ProgressDialog
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.holu.hinhtetvs1.R
import com.example.holu.hinhtetvs1.helper.DownloadFileFromURL
import com.example.holu.hinhtetvs1.helper.FirebaseUtils
import com.facebook.share.model.ShareLinkContent
import com.facebook.share.widget.ShareDialog
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError

/**
 * Project Name Hinhtetvs1
 * Created by HoLu on 11/22/2017.
 * All rights reserved
 */
class AllPictureFragment : Fragment() {

    var mRecyclerView: RecyclerView? = null
    var mListData: ArrayList<PictureModel>? = null
    var mAdapter: AllPictureAdapter? = null
    var mLoading : ProgressDialog? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_all_picture,container,false)
        mRecyclerView = view.findViewById(R.id.fragment_recyclerView)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mLoading = ProgressDialog.show(context,"Đang tải","vui lòng chờ ...")

        setupRecy()

    }

    private fun setupRecy() {
        getData()

        mRecyclerView!!.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        mAdapter = AllPictureAdapter(mListData!!, {

            DownloadFileFromURL.downloadFile(Activity(),it)
            var model = PictureModel()
            model = it
            model.download = model.download + 1

            FirebaseUtils.mRef.child("Picture").child(it.id).setValue(model)

        }) {
            val linkContent = ShareLinkContent.Builder()
                    .setContentTitle(it.id)
                    .setContentDescription(it.url)
                    .setContentUrl(Uri.parse(it.url))
                    .setImageUrl(Uri.parse(it.url))
                    .build()
            ShareDialog.show(this,linkContent)

            var model = PictureModel()
            model = it
            model.share = model.share + 1

            FirebaseUtils.mRef.child("Picture").child(it.id).setValue(model)

        }
        mRecyclerView!!.adapter = mAdapter
    }

    private fun getData() {
        mListData = ArrayList()

        val listenerData = object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError?) {
            }

            override fun onChildMoved(p0: DataSnapshot?, p1: String?) {
            }

            override fun onChildChanged(p0: DataSnapshot?, p1: String?) {
                val model = p0!!.getValue(PictureModel::class.java)
                var i = 0
                while (i < mListData!!.size){
                    if (mListData!!.get(i).id.equals(model!!.id)){
                        mListData!!.set(i,model)
                    }
                    i = i +1
                }
                mAdapter!!.notifyDataSetChanged()
            }

            override fun onChildAdded(p0: DataSnapshot?, p1: String?) {
                val data = p0!!.getValue(PictureModel::class.java)
                mListData!!.add(data!!)
                mAdapter!!.notifyDataSetChanged()

                mLoading!!.dismiss()

            }

            override fun onChildRemoved(p0: DataSnapshot?) {
            }

        }

        FirebaseUtils.mRef.child("Picture").addChildEventListener(listenerData)
    }
}