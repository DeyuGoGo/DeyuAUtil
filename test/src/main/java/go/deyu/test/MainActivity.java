package go.deyu.test;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbAccessory;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.ParcelFileDescriptor;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    PendingIntent mPermissionIntent;
    Button btnCheck;
    TextView textInfo;
    UsbDevice device;
    UsbManager mUsbManager;
    UsbAccessory mAccessory;
    ParcelFileDescriptor mFileDescriptor;
    FileInputStream mInputStream;
    FileOutputStream mOutputStream;
    WebView mWebView;
    private static final String ACTION_USB_PERMISSION = "com.mobilemerit.usbhost.USB_PERMISSION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("Deyu" , " activity  : " + getIntent().getAction());
        mWebView = (WebView)findViewById(R.id.web);
        startLoad();
//

//             Handle IOException the way you like.
//        btnCheck = (Button) findViewById(R.id.check);
//        textInfo = (TextView) findViewById(R.id.info);
//        mUsbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
//        btnCheck.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View arg0) {
//                try {
//                    Process proc = Runtime.getRuntime().exec("su -c pm install -r " + "/sdcard/testing.apk ; " + "su -c am start -n "+getPackageName()+"/"+getPackageName()+".MainActivity");
//                } catch (IOException e) {
//                }
//            }
//        });
//        mPermissionIntent = PendingIntent.getBroadcast(this, 0, new Intent(
//                ACTION_USB_PERMISSION), 0);
//        IntentFilter filter = new IntentFilter(ACTION_USB_PERMISSION);
//        filter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED);
//        filter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED);
//        registerReceiver(mUsbReceiver, filter);
//        new ProgressDialog(MainActivity.this).show();
    }

    private void showMain(){
    }

    private void checkInfo() {

        /*
         * this block required if you need to communicate to USB devices it's
         * take permission to device
         * if you want than you can set this to which device you want to communicate
         */
        // ------------------------------------------------------------------

        // -------------------------------------------------------------------
        UsbAccessory[] list= mUsbManager.getAccessoryList();
        HashMap<String , UsbDevice> deviceList = mUsbManager.getDeviceList();
        Iterator<UsbDevice> deviceIterator = deviceList.values().iterator();
        String i = "";
        if(list!=null)
            for(UsbAccessory Accessory : list){
                mUsbManager.requestPermission(Accessory, mPermissionIntent);
                i += "\n" + "getSerial: " + Accessory.getSerial() + "\n"
                        + "getDescription: " + Accessory.getDescription() + "\n"
                        + "getManufacturer: " + Accessory.getManufacturer() + " - "
                        + "getModel: " + Accessory.getModel() + "\n"
                        + "getUri: " + Accessory.getUri() + "\n"
                        + "getVersion: " + Accessory.getVersion() + "\n";
            }
        while (deviceIterator.hasNext()) {
            device = deviceIterator.next();
            mUsbManager.requestPermission(device, mPermissionIntent);
            i += "\n" + "DeviceID: " + device.getDeviceId() + "\n"
                    + "DeviceName: " + device.getDeviceName() + "\n"
                    + "DeviceClass: " + device.getDeviceClass() + " - "
                    + "DeviceSubClass: " + device.getDeviceSubclass() + "\n"
                    + "VendorID: " + device.getVendorId() + "\n"
                    + "ProductID: " + device.getProductId() + "\n";
        }

        textInfo.setText(i);
    }

    private final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.e("Deyu","action : " + action);
            if(ACTION_USB_PERMISSION.equals(action)) {
                synchronized (this) {
                    mAccessory = (UsbAccessory) intent.getParcelableExtra(UsbManager.EXTRA_ACCESSORY);
                    if (intent.getBooleanExtra(
                            UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                        if (device != null) {
                            if(mAccessory == null){
                                //call method to set up accessory communication
                                Log.d("Deyu","mAccessory : " + mAccessory);
                            } else {
                            mFileDescriptor = mUsbManager.openAccessory(mAccessory);
                            if (mFileDescriptor != null) {
                                FileDescriptor fd = mFileDescriptor.getFileDescriptor();
                                mInputStream = new FileInputStream(fd);
                                mOutputStream = new FileOutputStream(fd);
                                Thread thread = new Thread(null, new Runnable() {
                                    @Override
                                    public void run() {
                                        runOnUiThread(() -> {
                                            Log.d("Deyu", "read : " + convertStreamToString(mInputStream));
                                            Toast.makeText(MainActivity.this, "" + convertStreamToString(mInputStream), Toast.LENGTH_SHORT).show();
                                        });
                                    }
                                },"AccessoryThread");
                                thread.start();
                            }
                            }
                            Log.d("ERROR", "permission \n"+ "DeviceID: " + device.getDeviceId() + "\n"
                                    + "DeviceName: " + device.getDeviceName() + "\n"
                                    + "DeviceClass: " + device.getDeviceClass() + " - "
                                    + "DeviceSubClass: " + device.getDeviceSubclass() + "\n"
                                    + "VendorID: " + device.getVendorId() + "\n"
                                    + "ProductID: " + device.getProductId() + "\n");
                        }
                    } else {
                        Log.d("ERROR", "permission denied for device " + device);
                    }
                }
            }
        }
    };
    static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }


//    WebView
private String[] urls = new String[]{"https://tw.yahoo.com","https://tw.campaign.bid.yahoo.com/activity?p=auc-1-0-170425-livemay&gg=0&co_servername=ayfp_ecm_170522_451&co_servername2=ecm0522&utm_source=yahoo&utm_medium=ecm&utm_campaign=na170522451#01",
        "https://www.human-element.com/easy-android-app-development-webview/","http://blog.csdn.net/rain_butterfly/article/details/42170601"};


    private void startLoad() {
        new Handler().postDelayed(()->{
            setup();
            String url = urls[new Random().nextInt(urls.length)];
            mWebView.loadUrl(url);
            startLoad();
        },5000);
    }

    private void setup() {
        mWebView.getSettings().setAppCacheMaxSize(5 * 1024 * 1024); // 5MB
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setAppCacheEnabled(true);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.getSettings().setDatabaseEnabled(true);
        mWebView.getSettings().setAppCachePath(getApplicationContext().getCacheDir().getAbsolutePath());
        mWebView.getSettings().setAllowFileAccess(true);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT); // load online by default
        mWebView.setWebViewClient(new WebViewClient());
        if (!isNetworkAvailable()) { // loading offline
            mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService( CONNECTIVITY_SERVICE );
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
