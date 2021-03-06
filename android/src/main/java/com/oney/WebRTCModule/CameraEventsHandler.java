package com.oney.WebRTCModule;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import org.webrtc.VideoCapturerAndroid;

class CameraEventsHandler implements VideoCapturerAndroid.CameraEventsHandler {
    private final static String TAG = WebRTCModule.TAG;
    private int cameraID = -1;

    private void sendMessage(int cameraId) {
        Log.d("sender", "Broadcasting message");
        if(WebRTCModule.staticContext != null) {
            Intent intent = new Intent("camera_id_detected");
            // You can also include some extra data.
            Log.v(TAG, "SENT CAMERA ID: " + cameraId);
            intent.putExtra("message", cameraId);
            LocalBroadcastManager.getInstance(WebRTCModule.staticContext).sendBroadcast(intent);
        }
    }

    // Camera error handler - invoked when camera can not be opened
    // or any camera exception happens on camera thread.
    @Override
    public void onCameraError(String errorDescription) {
        Log.d(TAG, String.format("CameraEventsHandler.onCameraError: errorDescription=%s", errorDescription));
    }

    // Invoked when camera stops receiving frames
    @Override
    public void onCameraFreezed(String errorDescription) {
        Log.d(TAG, String.format("CameraEventsHandler.onCameraFreezed: errorDescription=%s", errorDescription));
    }

    // Callback invoked when camera is opening.
    @Override
    public void onCameraOpening(int cameraId) {
        Log.d(TAG, String.format("CameraEventsHandler.onCameraOpening: cameraId=%s", cameraId));
        cameraID = cameraId;
    }

    // Callback invoked when first camera frame is available after camera is opened.
    @Override
    public void onFirstFrameAvailable() {
        Log.d(TAG, "CameraEventsHandler.onFirstFrameAvailable");
        sendMessage(cameraID);
    }

    // Callback invoked when camera closed.
    @Override
    public void onCameraClosed() {
        Log.d(TAG, "CameraEventsHandler.onFirstFrameAvailable");
    }
}
