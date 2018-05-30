package com.handy.qrcode.app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.handy.qrcode.ScanSingleBuild;
import com.handy.qrcode.utils.BitmapUtils;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.zxing).setOnClickListener(v -> {
            ((TextView) findViewById(R.id.result)).setText("");
            ((ImageView) findViewById(R.id.image)).setImageBitmap(null);

            new ScanSingleBuild().setScanResultListener((rawResult, barcode, scaleFactor) -> {
                Bitmap bitmap;
                bitmap = BitmapUtils.drawResultPoints(MainActivity.this, barcode, scaleFactor, rawResult, true);
                bitmap = BitmapUtils.compressByScale(bitmap, 360, 640, true);
                bitmap = BitmapUtils.addTextWatermark(MainActivity.this, bitmap, "HandyQRCode\nhttps://www.handy045.com", 11, Color.BLUE, 4, 4, true);
                ((TextView) findViewById(R.id.result)).setText("扫描结果：" + rawResult.getText());
                ((ImageView) findViewById(R.id.image)).setImageBitmap(bitmap);
            }).start(MainActivity.this);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            if (data != null && data.getExtras() != null && data.getExtras().size() > 0) {
                Log.d("++++++++++++++++++", data.getStringExtra("Result"));
            }
        }
    }
}
