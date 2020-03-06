package tech.erao.prosopagnosiahelperdisplayer;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;

import java.util.UUID;

public class SendingdataActivity extends Activity {
    static final UUID MY_UUID =
            UUID.fromString("fa87c0d0-afac-11de-8a39-0800200c9a66");
    static String address = "50:C3:00:00:00:00";
    /**
     * Called when the activity is first created.
     */
    private BluetoothAdapter mBluetoothAdapter = null;

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.main);
//        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//        if (mBluetoothAdapter == null) {
//            Toast.makeText(this,
//                    "Bluetooth is not available.",
//                    Toast.LENGTH_LONG).show();
//            finish();
//            return;
//        }
//
//        if (!mBluetoothAdapter.isEnabled()) {
//            Toast.makeText(this,
//                    "Please enable your BT and re-run this program.",
//                    Toast.LENGTH_LONG).show();
//            finish();
//            return;
//        }
//        final SendData sendData = new SendData();
//        Button sendButton = (Button) findViewById(R.id.send);
//        sendButton.setOnClickListener(new OnClickListener() {
//            public void onClick(View view) {
//                sendData.sendMessage();
//            }
//        });
//    }


}
