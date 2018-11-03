package com.ramapps.ramplaces.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ramapps.ramplaces.R;

/**
 * Created by Ramsheed on 11/03/2018.
 * Navigator class for all handling all the navigation in this application
 */

public class Navigator {

    private static volatile Navigator instance;

    public static Navigator getInstance() {
        Navigator service = instance;
        if (service == null) {
            synchronized (Navigator.class) {
                service = instance;
                if (service == null) {
                    service = new Navigator();
                    instance = service;
                }
            }
        }
        return service;
    }

    public void navigate(Activity parent, Class target, Bundle bundle, boolean finishParent, boolean clearHistory) {
        Intent intent = new Intent(parent, target);
        if (bundle != null)
            intent.putExtras(bundle);
        if (clearHistory)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        parent.startActivity(intent);
        if (finishParent)
            parent.finish();
    }

    public void navigate(Activity parent, Class target, Bundle bundle, boolean finishParent, boolean clearHistory, int enterAnim, int exitAnim) {
        Intent intent = new Intent(parent, target);
        if (bundle != null)
            intent.putExtras(bundle);
        if (clearHistory)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        parent.startActivity(intent);
        if (finishParent)
            parent.finish();
        parent.overridePendingTransition(enterAnim, exitAnim);
    }


    public void startCall(Context parent, String number) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + number));
        parent.startActivity(intent);
    }

    public void startEmail(Context parent, String email) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", email, null));
        parent.startActivity(Intent.createChooser(emailIntent, "Send email..."));
    }

    public void showDirections(AppCompatActivity parent, double lat, double lng) {
        try {
            Uri gmmIntentUri = Uri.parse("google.navigation:q=" + lat + "," + lng);
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            parent.startActivity(mapIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void shareText(Context context,String text){
        String shareBody = text;
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Ram Places");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        context.startActivity(Intent.createChooser(sharingIntent, context.getResources().getString(R.string.share_using)));

    }
}
