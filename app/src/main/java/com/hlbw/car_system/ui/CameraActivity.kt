package com.hlbw.car_system.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import com.hlbw.car_system.R
import com.hlbw.car_system.api.HttpResultSubscriber
import com.hlbw.car_system.api.HttpServerImpl
import com.hlbw.car_system.base.BaseActivity
import com.hlbw.car_system.base.MyApplication
import com.hlbw.car_system.kotlin.gone
import com.hlbw.car_system.kotlin.visible
import com.hlbw.car_system.utils.UriUtils
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import id.zelory.compressor.Compressor
import id.zelory.compressor.constraint.quality
import kotlinx.coroutines.runBlocking
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class CameraActivity : BaseActivity() {


    private var preview: Preview? = null
    private var imageCapture: ImageCapture? = null
    private var camera: Camera? = null

    private lateinit var outputDirectory: File
    private lateinit var cameraExecutor: ExecutorService

    private val takePhoto: View by lazy {
        findViewById(R.id.take_photo)
    }
    private val viewFinder: PreviewView by lazy {
        findViewById(R.id.viewFinder)
    }

    private var type: Int = 0

    override fun getLayout(): Int {
        return R.layout.act_carema
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent.extras?.let {
            type = it.getInt("type")
        }
        findViewById<View>(R.id.back).setOnClickListener {
            finish()
        }
        if (MyApplication.getSpUtils().getBoolean("isOpenXiangCe",true)) {
            findViewById<View>(R.id.go_xiangce).visible()
        } else {
            findViewById<View>(R.id.go_xiangce).gone()
        }
        findViewById<View>(R.id.go_xiangce).setOnClickListener {
            Matisse.from(this)
                .choose(MimeType.of(MimeType.JPEG)) //ofImage()
                .countable(false)
                .maxSelectable(1)
                .forResult(0x11)
        }
        // Request camera permissions
        startCamera() // Setup the listener for take photo button
        takePhoto.setOnClickListener { takePhoto() }
        outputDirectory = getOutputDirectory()
        cameraExecutor = Executors.newSingleThreadExecutor()
    }


    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        imageCapture = ImageCapture.Builder().build()
        cameraProviderFuture.addListener({ // Used to bind the lifecycle of cameras to the lifecycle owner
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get() // Preview
            preview = Preview.Builder().build() //创建图片的 capture
            imageCapture = ImageCapture.Builder().setFlashMode(ImageCapture.FLASH_MODE_AUTO)
                .build() // Select back camera
            val cameraSelector =
                CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK).build()
            try { // Unbind use cases before rebinding
                cameraProvider.unbindAll() // Bind use cases to camera
                camera = cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture)
                preview?.setSurfaceProvider(viewFinder.createSurfaceProvider(camera?.cameraInfo))
            } catch (exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }
        }, ContextCompat.getMainExecutor(this))
    }


    private fun takePhoto() { // Get a stable reference of the modifiable image capture use case
        val imageCapture =
            imageCapture ?: return // Create timestamped output file to hold the image
        val photoFile = File(
            outputDirectory,
            SimpleDateFormat(
                FILENAME_FORMAT,
                Locale.US
            ).format(System.currentTimeMillis()) + ".jpg"
        ) // Create output options object which contains file + metadata
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile)
            .build() // Setup image capture listener which is triggered after photo has
        // been taken
        imageCapture.takePicture(outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Log.e(TAG, "Photo capture failed: ${exc.message}", exc)
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    val savedUri = Uri.fromFile(photoFile)
                    val msg = "Photo capture succeeded: $savedUri"
                    uploadImg(photoFile)
                    Log.d(TAG, msg)
                }
            })
    }


    private fun uploadImg(file: File) {
        showProgress()
        runBlocking {
            val compressedImageFile = Compressor.compress(this@CameraActivity, file) {
                quality(30)
            }
            HttpServerImpl.updateImg(compressedImageFile)
                .subscribe(object : HttpResultSubscriber<String>() {
                    override fun onSuccess(t: String?) {
                        stopProgress()
                        val bundle = Bundle()
                        bundle.putInt("type", type)
                        bundle.putString("image", t)
                        gotoActivity(CarenaResultActivity::class.java, bundle, true)
                    }

                    override fun onFiled(message: String?) {
                        stopProgress()
                        showToast(message)
                    }
                })
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0x11 && resultCode == RESULT_OK) {
            val uris = Matisse.obtainResult(data)
            if (uris.size < 1) {
                return
            }
            val fileUrl = File(UriUtils.getFilePathFromURI(this, uris[0]))
            uploadImg(fileUrl)
        }
    }


    private fun getOutputDirectory(): File {
        val mediaDir = externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() }
        }
        return if (mediaDir != null && mediaDir.exists()) mediaDir else filesDir
    }

    companion object {
        private const val TAG = "CameraXBasic"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
    }
}