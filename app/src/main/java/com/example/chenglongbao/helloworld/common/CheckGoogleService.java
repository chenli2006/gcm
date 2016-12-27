package com.example.chenglongbao.helloworld.common;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gcm.GCMConstants;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CheckGoogleService {

    public static Context mContext;

    public static boolean checkDevice() {
        int version = Build.VERSION.SDK_INT;
        if (version < 8) {
            Toast.makeText(mContext, "Your phone's version is too low.", Toast.LENGTH_LONG).show();
            return false;
        }

        PackageManager packageManager = mContext.getPackageManager();

        try {

            packageManager.getPackageInfo(Constants.GSF_PACKAGE, 0);
        } catch (NameNotFoundException e) {
            Toast.makeText(mContext, "Your phone isn't install google service.", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public static boolean checkManifest() {
        PackageManager packageManager = mContext.getPackageManager();
        String packageName = mContext.getPackageName();
        String permissionName = packageName + ".permission.C2D_MESSAGE";

        try {
            packageManager.getPermissionInfo(permissionName,
                    PackageManager.GET_PERMISSIONS);
        } catch (NameNotFoundException e) {
            Toast.makeText(mContext, "No permission to access,are you add 'permission.C2D_MESSAGE' in AndroidManifest.xml?", Toast.LENGTH_LONG).show();
            return false;
        }


        PackageInfo receiversInfo = null;
        try {
            receiversInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_RECEIVERS);
        } catch (NameNotFoundException e) {
            Toast.makeText(mContext, "RECEIVERS has some exception", Toast.LENGTH_LONG).show();
            return false;
        }

        ActivityInfo[] receivers = receiversInfo.receivers;
        if (receivers == null || receivers.length == 0) {

            return false;
        }
        if (Log.isLoggable(Constants.TAG_CHECK, Log.VERBOSE)) {
            Log.v(Constants.TAG_CHECK, "number of receivers for " + packageName + ": " +
                    receivers.length);
        }


        Set<String> allowedReceivers = new HashSet<String>();
        for (ActivityInfo receiver : receivers) {
            if (GCMConstants.PERMISSION_GCM_INTENTS.equals(receiver.permission)) {
                allowedReceivers.add(receiver.name);
            }
        }
        if (allowedReceivers.isEmpty()) {
            Toast.makeText(mContext, "No receiver allowed to receive " + GCMConstants.PERMISSION_GCM_INTENTS, Toast.LENGTH_LONG).show();
            return false;
        }

        checkReceiver(mContext, allowedReceivers,
                GCMConstants.INTENT_FROM_GCM_REGISTRATION_CALLBACK);
        checkReceiver(mContext, allowedReceivers,
                GCMConstants.INTENT_FROM_GCM_MESSAGE);


        return true;
    }


    private static void checkReceiver(Context context,
                                      Set<String> allowedReceivers, String action) {
        PackageManager pm = context.getPackageManager();
        String packageName = context.getPackageName();
        Intent intent = new Intent(action);
        intent.setPackage(packageName);
        List<ResolveInfo> receivers = pm.queryBroadcastReceivers(intent,
                PackageManager.GET_INTENT_FILTERS);
        if (receivers.isEmpty()) {
            throw new IllegalStateException("No receivers for action " +
                    action);
        }
        if (Log.isLoggable(Constants.TAG_CHECK, Log.VERBOSE)) {
            Log.v(Constants.TAG_CHECK, "Found " + receivers.size() + " receivers for action " +
                    action);
        }
        // make sure receivers match
        for (ResolveInfo receiver : receivers) {
            String name = receiver.activityInfo.name;
            if (!allowedReceivers.contains(name)) {
                Toast.makeText(context, "Receiver " + name + " is not set with permission " + GCMConstants.PERMISSION_GCM_INTENTS, Toast.LENGTH_LONG).show();
            }
        }
    }

}
