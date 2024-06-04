//package com.drafe.yogatrainingapp.model
//
//import android.content.Context
//import com.google.mediapipe.framework.image.MPImage
//import com.google.mediapipe.tasks.core.BaseOptions
//import com.google.mediapipe.tasks.vision.poselandmarker.PoseLandmarker
//import com.google.mediapipe.tasks.vision.poselandmarker.PoseLandmarkerResult
//
//class PoseProcessor(context: Context) {
//    private val poseLandmarker: PoseLandmarker
//
//    init {
//        // Инициализация PoseLandmarker с параметрами по умолчанию
//        val options = PoseLandmarker.PoseLandmarkerOptions.builder()
//            .setBaseOptions(
//                BaseOptions.builder()
//                    .setModelAssetPath("model/pose_landmarker_lite.task")
//                    .build()
//            )
//            .setMinPoseDetectionConfidence(0.5f)
//            .setMinTrackingConfidence(0.5f)
//            .build()
//        poseLandmarker = PoseLandmarker.createFromOptions(context, options)
//    }
//
//    fun processImage(image: MPImage): PoseLandmarkerResult {
//        return poseLandmarker.detect(image)
//    }
//}