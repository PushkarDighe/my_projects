package com.example.rto_login;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import androidmads.library.qrgenearator.QRGSaver;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.WriterException;

import androidx.appcompat.widget.Toolbar;

public class Generate_QR extends AppCompatActivity {

    String TAG = "GenerateQRCode";
    Toolbar toolbar_2;
    ImageView qr_img;
    Button generate,save_qr;
    String savePath = Environment.getExternalStorageDirectory().getPath() + "/QRCode/";
    Bitmap bitmap;
    QRGEncoder qrgEncoder;
    String inputValue;
    EditText qr_value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate__q_r);

        toolbar_2 = (Toolbar)findViewById(R.id.toolbar_2);
        setSupportActionBar(toolbar_2);
        TextView toolbar_title = (TextView) toolbar_2.findViewById(R.id.tool_title);

        Bundle b = getIntent().getExtras();
        try{

            toolbar_title.setText(b.getString("title"));
        }
        catch (Exception e){}

        qr_value = (EditText)findViewById(R.id.qr_value);
        qr_img = (ImageView)findViewById(R.id.qr_img);
        generate = (Button)findViewById(R.id.generate);
       // save_qr = (Button)findViewById(R.id.save_qr);

        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputValue = qr_value.getText().toString().trim();
                if (inputValue.length() > 0) {
                    WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
                    Display display = manager.getDefaultDisplay();
                    Point point = new Point();
                    display.getSize(point);
                    int width = point.x;
                    int height = point.y;
                    int smallerDimension = width < height ? width : height;
                    smallerDimension = smallerDimension * 3 / 4;

                    qrgEncoder = new QRGEncoder(
                            inputValue, null,
                            QRGContents.Type.TEXT,
                            smallerDimension);
                    try {
                        bitmap = qrgEncoder.encodeAsBitmap();
                        qr_img.setImageBitmap(bitmap);
                    } catch (WriterException e) {
                        Log.v(TAG, e.toString());
                    }
                } else {
                    qr_value.setError("Required");
                }
            }
        });

//        save_qr.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                boolean save;
//                String result;
//                try {
//                    save = QRGSaver.save(savePath, qr_value.getText().toString().trim(), bitmap, QRGContents.ImageType.IMAGE_JPEG);
//                    result = save ? "Image Saved" : "Image Not Saved";
//                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
    }
}