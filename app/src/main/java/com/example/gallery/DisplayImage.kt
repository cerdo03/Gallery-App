package com.example.gallery

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Contacts
import android.widget.ImageView
import android.widget.Toast
import com.example.gallery.model.image
import kotlinx.android.synthetic.main.activity_display_image.*


class DisplayImage : AppCompatActivity() {
    companion object{
        const val Image_Extra="Image_Extra"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_image)
        val cur = intent.getStringExtra(Image_Extra)
        if (cur != null) {
            full_image_view.setImageURI(Uri.parse(cur))
        }



    }
}


