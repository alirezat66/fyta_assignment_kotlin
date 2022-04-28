package com.example.fyta_test_assignment.view.home

import android.app.Activity
import android.app.appsearch.SearchResult
import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.example.fyta_test_assignment.R
import com.example.fyta_test_assignment.di.Injection
import com.example.fyta_test_assignment.fundation.Utility
import com.example.fyta_test_assignment.view.result.ResultActivity
import com.example.fyta_test_assignment.viewmodel.search.SearchViewModel
import com.otaliastudios.cameraview.BitmapCallback
import com.otaliastudios.cameraview.CameraListener
import com.otaliastudios.cameraview.CameraView
import com.otaliastudios.cameraview.PictureResult
import com.otaliastudios.cameraview.controls.Audio
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import kotlin.properties.Delegates


class MainActivity : AppCompatActivity() {
    private val camera: CameraView by lazy { findViewById(R.id.camera) }
    private val takingPictureLayout: ConstraintLayout by lazy { findViewById(R.id.layout_waiting) }
    private val btnCamera: ImageButton by lazy { findViewById(R.id.btn_camera) }
    private val btnGallery: ImageButton by lazy { findViewById(R.id.btn_gallery) }
    var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupUI()




    }
    private var isUnderTake by Delegates.observable(false) { property, oldValue, newValue ->
        takingPictureLayout.isVisible = newValue
    }

    override fun onStop() {
        // we show camera view again
        runCamera()
        super.onStop()
    }
    private fun runCamera(){
        isUnderTake = false;
    }
    private  fun stopCamera(){
        isUnderTake = true;
    }
    private fun setupUI() {
        camera.addCameraListener(MyCameraListener())
        camera.audio = Audio.OFF;
        camera.setLifecycleOwner(this);
        btnCamera.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                // we show camera view again
                stopCamera();
                camera.takePicture();
            }

        })
        btnGallery.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                val pickIntent = Intent(Intent.ACTION_PICK)


                pickIntent.setDataAndType(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    "image/*"
                )
                pickImageFromGalleryForResult.launch(pickIntent)

            }

        })

    }
    val pickImageFromGalleryForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {


            imageUri = result.data?.data

            val bitmap =     Utility.uriToBitmap(contentResolver, imageUri!!)
            val selectedFile = Utility.bitmapToFile(cacheDir, bitmap!!);
            openResultDialog(selectedFile);

        }
    }

    private inner class MyCameraListener : CameraListener() {
        override fun onPictureTaken(result: PictureResult) {
            super.onPictureTaken(result)

            result.toBitmap(500, 500, BitmapCallback {

                // in real project we dont use logic in ui part but I dont have enough time for seperate theme
                try {
                  val capturedFile = Utility.bitmapToFile(cacheDir,it!!);
                    openResultDialog(capturedFile);
                } catch (e : Exception) {
                    e.printStackTrace();
                     // we show camera view again
                    runCamera()
                    Toast.makeText(applicationContext, it.toString(), Toast.LENGTH_LONG).show()
                }

            })





        }
    }
    private fun openResultDialog(file : File){
        val intent = Intent(this@MainActivity, ResultActivity::class.java)
        intent.putExtra("file", file)
        startActivity(intent)
    }
}

