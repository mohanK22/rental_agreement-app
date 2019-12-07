package in.aartimkarande.rentalagreement.myrentalagreement.config;

import android.Manifest;
import android.app.Activity;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.single.PermissionListener;

public class PermissionStatusTracker {

    public static boolean cameraPermissionStatus = false;

    public static boolean readStoragePermissionStatus = false;
    public static boolean writeStoragePermissionStatus = false;


    //Camera Permission

    public boolean isCameraPermissionSet(){

        if(!cameraPermissionStatus){
            return false;
        }else {
            return true;
        }

    }

    public void requestCameraPermission(final Activity activity) {

        Dexter.withActivity(activity)
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        // permission is granted, open the camera

                        cameraPermissionStatus = true;
                        Toast.makeText(activity, "Camera Permission GRANTED", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        // check for permanent denial of permission

                        if (response.isPermanentlyDenied()) {

                            // navigate user to app settings

                            Toast.makeText(activity, "Camera Permission DENIED", Toast.LENGTH_SHORT).show();

                        }
                        Toast.makeText(activity, "Camera Permission DENIED", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                })
                .withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {

                        Toast.makeText(activity, "Error occurred! " + error.toString(), Toast.LENGTH_SHORT).show();

                    }
                })
                .check();


    }





    //Storage Read Permission

    public boolean isReadStoragePermissionSet(){

        if(!readStoragePermissionStatus){
            return false;
        }else {
            return true;
        }

    }

    public void requestReadStoragePermission(final Activity activity) {

        Dexter.withActivity(activity)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        // permission is granted, open the camera

                        readStoragePermissionStatus = true;
                        Toast.makeText(activity, "Read Storage Permission GRANTED", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        // check for permanent denial of permission
                        if (response.isPermanentlyDenied()) {
                            // navigate user to app settings

                            Toast.makeText(activity, "Read Storage Permission DENIED", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                })
                .withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {

                        Toast.makeText(activity, "Error occurred! " + error.toString(), Toast.LENGTH_SHORT).show();

                    }
                })
                .check();

    }




    //Storage Write Permission

    public boolean isWriteStoragePermissionSet(){

        if(!writeStoragePermissionStatus){
            return false;
        }else {
            return true;
        }

    }

    public void requestWriteStoragePermission(final Activity activity) {

        Dexter.withActivity(activity)
                .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        // permission is granted, open the camera

                        writeStoragePermissionStatus = true;
                        Toast.makeText(activity, "Write Storage Permission GRANTED", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        // check for permanent denial of permission
                        if (response.isPermanentlyDenied()) {
                            // navigate user to app settings

                            Toast.makeText(activity, "Write Storage Permission DENIED", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                })
                .withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {

                        Toast.makeText(activity, "Error occurred! " + error.toString(), Toast.LENGTH_SHORT).show();

                    }
                })
                .check();

    }





}
