package com.example.holu.hinhtetvs1.ui.main

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import com.example.holu.hinhtetvs1.R
import com.example.holu.hinhtetvs1.helper.CheckDiskPermission
import com.example.holu.hinhtetvs1.helper.DownloadFileFromURL
import com.example.holu.hinhtetvs1.helper.FirebaseUtils
import com.example.holu.hinhtetvs1.ui.SplashActivity
import com.facebook.login.LoginManager
import com.facebook.share.model.ShareLinkContent
import com.facebook.share.widget.ShareDialog
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError


class HomeActivity : AppCompatActivity() {

    var mMenu: DrawerLayout? = null
    var mLayout: LinearLayout? = null
    var mLinearHomeContent: FrameLayout? = null
    var mImgMenu: ImageView? = null
    var mRecyclerView: RecyclerView? = null
    var mListData: ArrayList<PictureModel>? = null
    var mAdapter: HomeAdapter? = null
    var mLoading : ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        CheckDiskPermission.checkDiskPermission(this)

        initUI()

        mLoading = ProgressDialog.show(this,"Đang tải","vui lòng chờ ...")

        mImgMenu!!.setOnClickListener {
            if (mMenu!!.isDrawerOpen(mLayout)) {
                mMenu!!.closeDrawer(mLayout)
            } else {
                mMenu!!.openDrawer(mLayout)
            }
        }
        setupMenu()
        loadRecy()


    }

    private fun setupMenu() {
        val fLinearTop = findViewById<LinearLayout>(R.id.linearTop)
        val fLinearAll = findViewById<LinearLayout>(R.id.linearAll)
        val fLinearLogout = findViewById<LinearLayout>(R.id.linearLogout)

        fLinearTop.setOnClickListener {
            finishAffinity()
            val top = Intent(this, HomeActivity::class.java)
            startActivity(top)
        }
        fLinearAll.setOnClickListener {

            supportFragmentManager.beginTransaction().replace(R.id.home_frame_content,AllPictureFragment()).commit()
            mMenu!!.closeDrawer(mLayout)

        }
        fLinearLogout.setOnClickListener {
            LoginManager.getInstance().logOut()
            finishAffinity()
            val start = Intent(this,SplashActivity::class.java)
            startActivity(start)
        }
    }


    private fun loadRecy() {
        getData()

        mRecyclerView!!.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mAdapter = HomeAdapter(mListData!!, {

            DownloadFileFromURL.downloadFile(this,it)
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
                getTop10(mListData!!)
            }

            override fun onChildAdded(p0: DataSnapshot?, p1: String?) {
                val data = p0!!.getValue(PictureModel::class.java)
                mListData!!.add(data!!)
                mAdapter!!.notifyDataSetChanged()

                getTop10(mListData!!)
                mLoading!!.dismiss()

            }

            override fun onChildRemoved(p0: DataSnapshot?) {
            }

        }

        FirebaseUtils.mRef.child("Picture").addChildEventListener(listenerData)
    }

    private fun getTop10(listData: ArrayList<PictureModel>) {
        val size = listData.size
        var modelOne = PictureModel()
        var modelTwo = PictureModel()
        var modelBetween = PictureModel()
        var countModelOne = 0
        var countModelTwo = 0
        var i =0
        var j = 0
        while (i<size-1){
            j=i+1
            while (j<size){
                modelOne = listData.get(i)
                modelTwo = listData.get(j)

                countModelOne = modelOne.download + modelOne.share
                countModelTwo = modelTwo.download + modelTwo.share
                if (countModelOne<countModelTwo){
                    modelBetween = modelOne
                    listData.set(i,modelTwo)
                    listData.set(j,modelBetween)
                }
                j++
            }
            i++
        }
        mAdapter!!.notifyDataSetChanged()
    }

    private fun initUI() {
        mMenu = findViewById(R.id.homeDrawer)
        mLayout = findViewById(R.id.linearMenu)
        mImgMenu = findViewById(R.id.home_img_menu)
        mRecyclerView = findViewById(R.id.home_recycelerView)
        mLinearHomeContent = findViewById(R.id.home_frame_content)
    }

}
