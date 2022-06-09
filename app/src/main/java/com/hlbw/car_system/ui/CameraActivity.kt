package com.hlbw.car_system.ui

import android.content.Intent
import android.graphics.PointF
import android.os.Bundle
import android.view.View
import com.blankj.utilcode.util.FileUtils
import com.hlbw.car_system.R
import com.hlbw.car_system.api.HttpResultSubscriber
import com.hlbw.car_system.api.HttpServerImpl
import com.hlbw.car_system.base.BaseActivity
import com.hlbw.car_system.base.MyApplication
import com.hlbw.car_system.kotlin.gone
import com.hlbw.car_system.kotlin.visible
import com.hlbw.car_system.utils.SaveImageUtils
import com.hlbw.car_system.utils.UriUtils
import com.otaliastudios.cameraview.*
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

    private lateinit var outputDirectory: File
    private lateinit var cameraExecutor: ExecutorService

    private val takePhoto: View by lazy {
        findViewById(R.id.take_photo)
    }

    private val camera: CameraView by lazy { findViewById(R.id.camera) }

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
        if (MyApplication.getSpUtils().getBoolean("isOpenXiangCe", true)) {
            findViewById<View>(R.id.go_xiangce).visible()
        } else {
            findViewById<View>(R.id.go_xiangce).gone()
        }
        findViewById<View>(R.id.go_xiangce).setOnClickListener {
            Matisse.from(this).choose(MimeType.of(MimeType.JPEG)) //ofImage()
                .countable(false).maxSelectable(1).forResult(0x11)
        } // Request camera permissions
        CameraLogger.setLogLevel(CameraLogger.LEVEL_VERBOSE)
        camera.setLifecycleOwner(this)
        camera.addCameraListener(Listener())
        takePhoto.setOnClickListener { camera.takePicture() }
        outputDirectory = getOutputDirectory()
        cameraExecutor = Executors.newSingleThreadExecutor()
    }

    private inner class Listener : CameraListener() {
        override fun onCameraOpened(options: CameraOptions) {
        }

        override fun onPictureTaken(result: PictureResult) {
            super.onPictureTaken(result)
            val photoFile = File(outputDirectory,
                                 SimpleDateFormat(FILENAME_FORMAT,
                                                  Locale.US).format(System.currentTimeMillis()) + ".jpg")
            result.toBitmap(camera.width, camera.height) { bitmap ->
                SaveImageUtils.saveImageToFile(this@CameraActivity, photoFile, bitmap)
                uploadImg(photoFile, false)
            }
        }

        override fun onExposureCorrectionChanged(newValue: Float, bounds: FloatArray, fingers: Array<PointF>?) {
            super.onExposureCorrectionChanged(newValue, bounds, fingers)
        }

        override fun onZoomChanged(newValue: Float, bounds: FloatArray, fingers: Array<PointF>?) {
            super.onZoomChanged(newValue, bounds, fingers)
        }
    }


    private fun uploadImg(file: File, isOpenXC: Boolean) {
        showProgress()
        runBlocking {
            val compressedImageFile = Compressor.compress(this@CameraActivity, file) {
                quality(50)
            }
            HttpServerImpl.updateImg(compressedImageFile)
                .subscribe(object : HttpResultSubscriber<String>() {
                    override fun onSuccess(t: String?) {
                        stopProgress()
                        if (MyApplication.getSpUtils()
                                .getBoolean("isAddXiangCe", true) && !isOpenXC) { //保存到相册
                            SaveImageUtils.saveFileToGallery(this@CameraActivity, file)
                        } else {
                            FileUtils.delete(file)
                        }

                        if (type == -1) {
                            val intent = Intent()
                            intent.putExtra("image", t)
                            setResult(0x11, intent)
                            finish()
                        } else {
                            val bundle = Bundle()
                            bundle.putInt("type", type)
                            bundle.putString("image", t)
                            gotoActivity(CarenaResultActivity::class.java, bundle, true)
                        }
                    }

                    override fun onFiled(message: String?) {
                        FileUtils.delete(file)
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
            uploadImg(fileUrl, true)
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