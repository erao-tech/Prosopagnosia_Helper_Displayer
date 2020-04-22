package tech.erao.prosopagnosiahelperdisplayer;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.vuzix.hud.actionmenu.ActionMenuActivity;

import net.iharder.Base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;




@SuppressWarnings("deprecation")
public class MainActivity extends ActionMenuActivity {
    private TextView user_name;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private  ProgressBar progressBar;
    private static final String URL = "http://52.55.117.58:5000/whichface-api";
    private boolean isInit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user_name = findViewById(R.id.user_name);
        progressBar = findViewById(R.id.inProgress);
        progressBar.setVisibility(View.INVISIBLE);

    }



    @Override
    public boolean dispatchKeyEvent(KeyEvent ev) {

        switch (ev.getKeyCode()) {

            case KeyEvent.KEYCODE_FORWARD_DEL: {
                // in case moving forward
                isInit = false;
                progressBar.setVisibility(View.VISIBLE);
                dispatchTakePictureIntent();
                break;
            }
            case  KeyEvent.KEYCODE_DEL: {
                // in case moving backword
                isInit = true;
                progressBar.setVisibility(View.VISIBLE);
                dispatchTakePictureIntent();
                break;
            }
        }
        return true;
    }

    private void sendThroughHTTP(Bitmap pic) throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        pic.compress(Bitmap.CompressFormat.PNG, 100, stream);
        final byte[] image = stream.toByteArray();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!isInit) {
                    progressBar.setVisibility(View.INVISIBLE);
                    user_name.setText(response.toString());
                } else {
                    progressBar.setVisibility(View.INVISIBLE);
                    user_name.setText(R.string.init_hint);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace(); //log the error resulting from the request for diagnosis/debugging

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> postMap = new HashMap<>();

                String base64Encoded = null;

                base64Encoded = Base64.encodeBytes(image);

                postMap.put("img", base64Encoded);
                return postMap;
            }
        };
        stringRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 10000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 5;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });

        //make the request to your server as indicated in your request url
        Volley.newRequestQueue(getContext()).add(stringRequest);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            byte[] image = data.getExtras().getByteArray("image_arr");
            final Bitmap bmp = BitmapFactory.decodeByteArray(image, 0, image.length);

            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    try {
                        sendThroughHTTP(bmp);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            thread.start();
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(this, PicAutoCapture.class);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
}
