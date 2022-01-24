package com.example.gallery

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import kotlinx.android.synthetic.main.activity_main.*
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gallery.model.image
import android.content.ContentUris
import android.content.pm.PackageManager
import android.net.Uri
import android.view.View
import androidx.core.app.ActivityCompat


class MainActivity : AppCompatActivity(),ImageItemClicked{
    val imageURIS: MutableList<Uri> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                Array(1) { Manifest.permission.READ_EXTERNAL_STORAGE },
                121
            )

        }

        loadImages()


        val dataset = imageURIS
        GridLayoutManager(
            this, // context
            3, // span count
            RecyclerView.VERTICAL, // orientation
            false // reverse layout,
        ).apply {
            // specify the layout manager for recycler view
            recyclerView.layoutManager = this
        }
        /* finally, data bind the recycler view with adapter */
        recyclerView.adapter = ItemAdapter(this,dataset,this)
        camera_button.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val REQUEST_IMAGE_CAPTURE = 1
                val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                try {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)

                } catch (e: ActivityNotFoundException) {
                    // display error state to the user
                }
            }
        })
    }

    private fun loadImages() {
        val projection = listOf<String>(MediaStore.Images.Media._ID).toTypedArray()
        val selection = null
        val selectionArgs = null
        val sortOrder = null

        applicationContext.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            selectionArgs,
            sortOrder
        )?.use { cursor ->
            while (cursor?.moveToNext()) {
                val id=cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID))
                val image_uri=
                    ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,id)
                imageURIS.add(image_uri)
                if(cursor.isLast){
                    break
                }
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode:Int,permissions: Array<out String>,grantResults: IntArray){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode==121 && grantResults[0]==PackageManager.PERMISSION_GRANTED){

        }
    }



    override fun onItemClicked(item: Uri){

        val img = item
        callActivity(img)
    }
    fun callActivity(img:Uri){

        val intent = Intent(this,DisplayImage::class.java)
        intent.putExtra(
            DisplayImage.Image_Extra, img.toString()
        )
        startActivity(intent)
    }

}

