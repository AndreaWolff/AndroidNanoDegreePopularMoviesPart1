package com.andrea.popularmovies.util;


import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class GlideUtil {

    public static void displayImage(@NonNull Context context, @NonNull String photo, @NonNull ImageView imageView) {
        Glide.with(context)
             .load(photo)
             .into(imageView);
    }
}
