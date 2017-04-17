package go.deyu.util;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

public class DownloadModel {
    private Context mContext ;
    private DownloadManager downloadManager;
    private boolean isRegister = false;
    private onComplete onCompleteBR = null;
    private DownloadListener listener =  null;
    public DownloadModel(Context context){
        this.mContext = context;
        downloadManager = (DownloadManager)mContext.getSystemService(Context.DOWNLOAD_SERVICE);
    }
    public void startDownLoad(String url , String fileName){
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setDestinationInExternalFilesDir(mContext,Environment.DIRECTORY_DOWNLOADS ,fileName);
        request.setVisibleInDownloadsUi(false);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN);
        downloadManager.enqueue(request);
    }
    public void startDownLoad(String url ,String fileName ,  DownloadListener listener){
        this.listener = listener;
        if(onCompleteBR==null)
            onCompleteBR = new onComplete();
        if(!isRegister){
            mContext.registerReceiver(onCompleteBR , onCompleteBR.getInentFilter());
            isRegister = true;
        }
        startDownLoad(url, fileName);
    }
    public void finish(){
        listener = null;
        if(onCompleteBR==null)
            return;
        if(isRegister){
            isRegister = false;
            mContext.unregisterReceiver(onCompleteBR);
            onCompleteBR = null;
        }
    }
    private class onComplete extends BroadcastReceiver {

        public IntentFilter getInentFilter(){
            IntentFilter itf = new IntentFilter();
            itf.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
            return itf;
        }
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("onReceive : " ,""+ intent.getAction());
            if(listener==null)
                return;
            Bundle extras = intent.getExtras();
            DownloadManager.Query q = new DownloadManager.Query();
            q.setFilterById(extras.getLong(DownloadManager.EXTRA_DOWNLOAD_ID));
            Cursor c = downloadManager.query(q);
            if (c.moveToFirst()) {
                int status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
                if (status == DownloadManager.STATUS_SUCCESSFUL) {
                    // process download
                    String fileName =c.getString(c.getColumnIndex(DownloadManager.COLUMN_TITLE));
                    String path = mContext.getExternalFilesDir("").getPath() + "/" + Environment.DIRECTORY_DOWNLOADS + "/" + fileName;
                    if(listener!=null)
                        listener.OnFinish(path);
                    // get other required data by changing the constant passed to getColumnIndex
                }
            }
            c.close();
            finish();
        }
    }


    interface DownloadListener{
        void OnFinish(String path);
    }
}