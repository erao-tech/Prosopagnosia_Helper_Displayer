package tech.erao.prosopagnosiahelperdisplayer.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.ParcelUuid;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;
import java.util.Set;
import java.util.UUID;

public class BlueToothHelper {

    static final UUID MY_UUID =
            UUID.fromString("fa87c0d0-afac-11de-8a39-0800200c9a66");
    private static final int REQUEST_ENABLE_BT = 1;
    static String address = "50:C3:00:00:00:00";

    InputStream inStream;
    OutputStream outputStream;
    private BluetoothAdapter mBluetoothAdapter = null;

    public void pairDevice() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        System.out.println("pairedDevices:" + pairedDevices.size());
        if (pairedDevices.size() > 0) {

            Object[] devices = pairedDevices.toArray();
            BluetoothDevice device = (BluetoothDevice) devices[0];
            System.out.println("pairedDevices:" + device.getName());
            ParcelUuid[] uuid = device.getUuids();
            try {
                System.out.println("1");
                BluetoothSocket socket = device.createInsecureRfcommSocketToServiceRecord(uuid[0].getUuid());
                System.out.println("2");
                socket.connect();
                System.out.println("Connected to:" + uuid[0].toString());
                outputStream = socket.getOutputStream();
                System.out.println("3");
                inStream = socket.getInputStream();
                System.out.println("4");
            } catch (IOException e) {
                System.out.println("Error:" + e.getMessage());

            }

        } else {
            System.out.println("No Paired Devices!!!");
        }
    }


    public void SendMessage(byte[] b) {
        pairDevice();
        b = "Hello".getBytes();
        System.out.println("5");
        try {
            if (outputStream != null)
                System.out.println("6");
            outputStream.write(b);
            System.out.println("7");
            Scanner s = new Scanner(inStream).useDelimiter("\\A");
            System.out.println(s);
        } catch (IOException e) {/*Do nothing*/}

    }


//    public void sendData(byte[] output){
//        String o = "Hi!";
//        output = o.getBytes();
//
//        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//        System.out.println(mBluetoothAdapter.getName());
//        if (mBluetoothAdapter == null) {
//                    //"Bluetooth is not available.",
//        }
//
//        if (!mBluetoothAdapter.isEnabled()) {
//                    //"Please enable your BT and re-run this program.",
//            return;
//        }
//        SendData sendData = new SendData();
//        sendData.sendMessage(output);
//    }
//
//    class SendData extends Thread {
//        private BluetoothDevice device = null;
//        private BluetoothSocket btSocket = null;
//        private OutputStream outStream = null;
//
//
//        public SendData(){
//
//            device = mBluetoothAdapter.getRemoteDevice(mBluetoothAdapter.getBondedDevices().iterator().next().getAddress());
//            Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
//            System.out.println("paired devices size:"+ pairedDevices.size());
//            if (pairedDevices.size() > 0) {
//                // There are paired devices. Get the name and address of each paired device.
//                for (BluetoothDevice device : pairedDevices) {
//                    String deviceName = device.getName();
//                    String deviceHardwareAddress = device.getAddress(); // MAC address
//                    System.out.println("devices name:"+ deviceName + "devices address:"+ deviceHardwareAddress);
//                }
//            }
//
//            //I/System.out: devices name:Chrisdevices address:98:09:CF:7D:46:CA
//
//
////            try
////            {
////                btSocket = device.createRfcommSocketToServiceRecord(MY_UUID);
////            }
////            catch (Exception e) {
////                // TODO: handle exception
////            }
////            mBluetoothAdapter.cancelDiscovery();
////            try {
////                btSocket.connect();
////            } catch (IOException e) {
////                try {
////                    btSocket.close();
////                } catch (IOException e2) {
////                }
////            }
////            //bluetooth connected
////            try {
////                outStream = btSocket.getOutputStream();
////            } catch (IOException e) {
////            }
//
//            EditText outMessage = (EditText) findViewById(R.id.editText);
//            try {
//                if (outputStream != null)
//                    outputStream.write(outMessage.toString().getBytes());
//                TextView displayMessage = (TextView) findViewById(R.id.textView);
//                Scanner s = new Scanner(inStream).useDelimiter("\\A");
//                displayMessage.setText(s.hasNext() ? s.next() : "");
//            } catch (IOException e) {/*Do nothing*/}
//            Toast.makeText(this,"No output stream", Toast.LENGTH_LONG).show();
//        }
//
//
//
//
//
//        public void sendMessage(byte[] output)
//        {
//            try {
//                mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
////                ByteArrayOutputStream baos = new ByteArrayOutputStream();
////                bm.compress(Bitmap.CompressFormat.JPEG, 100,baos); //bm is the bitmap object
////                byte[] b = baos.toByteArray();
//                outStream.write(output);
//                outStream.flush();
//            } catch (IOException e) {
//            }
//        }
//    }

}
