package beatmap.me.beatmap.stepdetection;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Message;

import java.util.ArrayList;

import beatmap.me.beatmap.StepActivity;

/**
 * Created by mike on 9/6/2014.
 */
public class StepDetector implements SensorEventListener {

    Handler handler;

    public StepDetector(Handler handler){
        this.handler = handler;
        handler.sendEmptyMessage(6);
    }

    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            handler.sendEmptyMessage(5);
        } else {
            handler.sendEmptyMessage(7);
        }
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy){
        
    }
}
