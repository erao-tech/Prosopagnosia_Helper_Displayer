package tech.erao.prosopagnosiahelperdisplayer;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;

import com.vuzix.hud.actionmenu.ActionMenuActivity;

import java.io.ByteArrayOutputStream;

@SuppressWarnings("deprecation")
public class MainActivity extends ActionMenuActivity {
    private TextView user_name;
    private static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user_name = findViewById(R.id.user_name);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        single click for the touchpad
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            dispatchTakePictureIntent();
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            byte[] image = data.getExtras().getByteArray("image_arr");
            Bitmap bmp = BitmapFactory.decodeByteArray(image, 0, image.length);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
            byte[] b = baos.toByteArray();
            //todo bluetooth here: send b to phone
            user_name.setText("Hi, my name is EraO");
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(this, PicAutoCapture.class);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
}
