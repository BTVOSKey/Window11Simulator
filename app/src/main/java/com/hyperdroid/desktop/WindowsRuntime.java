package com.hyperdroid.desktop;

import android.content.Context;
import android.net.Uri;

public final class WindowsRuntime {

    private WindowsRuntime() {}

    public static boolean isInstalled(Context context) {
        return false;
    }

    public static String launch(Context context, Uri exe) {
        return "Wine runtime is not installed.";
    }
}
