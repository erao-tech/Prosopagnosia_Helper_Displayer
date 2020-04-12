package tech.erao.prosopagnosiahelperdisplayer;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.StorageReference;
import com.vuzix.hud.actionmenu.ActionMenuActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.RequiresApi;


@SuppressWarnings("deprecation")
public class MainActivity extends ActionMenuActivity {
    private TextView user_name;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private StorageReference mStorageRef;
    private FirebaseAuth myFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user_name = findViewById(R.id.user_name);


        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    sendThroughHTTP();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();


    }

    private void sendThroughHTTP() throws IOException {
        URL source = new URL("https://www.ece.utoronto.ca/wp-content/uploads/2013/02/SteveMann2-45.jpg");
        Bitmap bmp = BitmapFactory.decodeStream(source.openConnection().getInputStream());
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        final byte[] image = stream.toByteArray();

        String url = "http://192.168.0.13:5000/whichface-api";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace(); //log the error resulting from the request for diagnosis/debugging

            }
        }) {

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> postMap = new HashMap<>();
                String base64Encoded = Base64.getEncoder().encodeToString(image);
                postMap.put("img", base64Encoded);
                System.out.println(image);
                return postMap;
            }
        };
//make the request to your server as indicated in your request url
        Volley.newRequestQueue(getContext()).add(stringRequest);
    }


//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
////        single click for the touchpad
//        if (keyCode == KeyEvent.KEYCODE_ENTER) {
//            dispatchTakePictureIntent();
//        }
//        return true;
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
//            byte[] image = data.getExtras().getByteArray("image_arr");
//            Bitmap bmp = BitmapFactory.decodeByteArray(image, 0, image.length);
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
//            byte[] imgfile = baos.toByteArray();
//            Toast.makeText(getApplicationContext(), "Finished",
//            Toast.LENGTH_SHORT).show();
//            String url = "http://54.161.20.123:5000/whichface";
//            //todo: Create a POST HTTP REQUEST with url above, convert the bitmap file to .JPG and attach it in request.files with key="img", see ApiTester.py in Flask Server
//        }
//    }
//
//    private void dispatchTakePictureIntent() {
//        Intent takePictureIntent = new Intent(this, PicAutoCapture.class);
//        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
//        }
//    }
}
