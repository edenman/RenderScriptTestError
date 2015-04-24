package com.renderscripttesterror;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v8.renderscript.Allocation;
import android.support.v8.renderscript.Element;
import android.support.v8.renderscript.RenderScript;
import android.support.v8.renderscript.ScriptIntrinsicBlur;
import android.widget.ImageView;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends Activity {
  @InjectView(R.id.image_view) ImageView imageView;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.inject(this);
    Drawable drawable = getResources().getDrawable(
        R.drawable.augustinerbrau);
    Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
    Bitmap blurredBitmap = Bitmap.createBitmap(bitmap);
    RenderScript rs = RenderScript.create(this);
    // Allocate memory for Renderscript to work with
    Allocation input = Allocation.createFromBitmap(rs, bitmap, Allocation.MipmapControl.MIPMAP_FULL,
        Allocation.USAGE_SHARED);
    Allocation output = Allocation.createTyped(rs, input.getType());

    // Load up an instance of the specific script that we want to use.
    ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
    script.setInput(input);

    // Set the blur radius
    script.setRadius(15);

    // Start the ScriptIntrinisicBlur
    script.forEach(output);

    // Copy the output to the blurred bitmap
    output.copyTo(blurredBitmap);

    if (bitmap != blurredBitmap) {
      bitmap.recycle();
    }
    imageView.setImageBitmap(blurredBitmap);
  }
}
