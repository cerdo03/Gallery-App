package com.example.gallery

import android.R.attr
import android.content.ActivityNotFoundException
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gallery.model.image
import android.R.attr.button
import android.media.ExifInterface
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.view.View
import java.io.File
import android.database.Cursor


class MainActivity : AppCompatActivity(),ImageItemClicked{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val dataset = Datasource().returnImages()
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

    override fun onItemClicked(item: image){

        val img = item
        callActivity(img)
    }
    fun callActivity(img:image){

        val intent = Intent(this,DisplayImage::class.java)
        intent.putExtra(
            DisplayImage.Image_Extra, img.imageResourceId.toString()
        )
        startActivity(intent)
    }

}

