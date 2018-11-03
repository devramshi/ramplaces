package com.ramapps.ramplaces.common;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;

/**
 * Created by Ramsheed on 11/03/2018
 * Permission Service
 */

public class PermissionService {

    private static volatile PermissionService instance;

    public static PermissionService getInstance() {
        PermissionService service = instance;
        if (service == null) {
            synchronized (PermissionService.class) {
                service = instance;
                if (service == null) {
                    service = new PermissionService();
                    instance = service;
                }
            }
        }
        return service;
    }

    public void reqLocPermission(Activity context) {
        if (!hasLocationPermission(context)) {
            ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, Constants.LOC_PERMISSION_REQ);
        }
    }

    public boolean hasLocationPermission(Activity context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (context.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }
}