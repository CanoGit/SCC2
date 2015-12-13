package com.scc2.cano.scc2;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;


/**
 * Created by CANO on 08/12/2015.
 */


public class SCC2 extends Service {


    private TelephonyManager manager;
    private MediaRecorder recorder;

    @Override
    public void onCreate()
    {
        PhoneStateListener stateListener = new PhoneStateListener() {
        @Override
        public void onCallStateChanged (int state, String incomingNumber) {
            switch (state) {
                case TelephonyManager.CALL_STATE_IDLE :
                    StopRecording();
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK :
                    StartRecording(incomingNumber);
                    break;
                case TelephonyManager.CALL_STATE_RINGING :
                    break;
            }
        }
    };
        manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        manager.listen(stateListener, PhoneStateListener.LISTEN_CALL_STATE);
    }
    @Override
    public IBinder onBind(Intent arg0)
    {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        return super.onStartCommand(intent, flags, startId);
    }

    private void StartRecording(String fileName)
    {
        recorder = new MediaRecorder();
        recorder.reset();
        //recorder.setAudioEncoder(1);
        recorder.setAudioSource(MediaRecorder.AudioSource.VOICE_DOWNLINK);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        recorder.setOutputFile("/sdcard/" + fileName + ".mp4");
        try {
            recorder.prepare();
        } catch (IllegalStateException e) {
            Log.e("StartRecording", e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("StartRecording", "IOException " + e.getMessage());
            e.printStackTrace();
        }
        recorder.start();
        Toast.makeText(getBaseContext(), "On lance l'enregistrement", Toast.LENGTH_LONG).show();
    }

    private void StopRecording() {
        if (recorder != null) {
            Log.e("StopRecording", "On stop l'enregistrement");
            recorder.stop();
            recorder.reset();
            recorder.release();
            recorder = null;
            Toast.makeText(getBaseContext(), "On stop l'enregistrement", Toast.LENGTH_LONG).show();
        }
    }

}
