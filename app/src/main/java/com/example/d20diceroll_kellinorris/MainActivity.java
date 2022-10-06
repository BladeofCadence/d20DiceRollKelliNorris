package com.example.d20diceroll_kellinorris;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private ImageView imageViewDice;
    private Random rng = new Random();
    private TextView Critical;

    private SoundPool soundPool;

    int sound1;
    int sound2,sound3,sound4;

    private SensorManager sensorManager;
    private Sensor accelerometerSensor;
    private boolean isAccelerometerSensorAvailable, notFirstTime = false;
    private float currentX, currentY, currentZ, lastX, lastY, lastZ, xDifference, yDifference, zDifference;
    private float shakeThreshold = 5f;
    private Vibrator vibrator;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        if(sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null)
        {
            accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            isAccelerometerSensorAvailable = true;
        }
        else
        {
            isAccelerometerSensorAvailable = false;
        }
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setUsage(AudioAttributes.USAGE_GAME)
                .build();

        soundPool = new SoundPool.Builder()
                .setMaxStreams(2)
                .setAudioAttributes(audioAttributes)
                .build();

        sound1 = soundPool.load(this,R.raw.ahh, 1);
        sound2 = soundPool.load(this,R.raw.fart, 1);
        sound3 = soundPool.load(this,R.raw.cheese, 1);
        sound4 = soundPool.load(this,R.raw.bonk, 1);


        Critical = findViewById(R.id.Critical);
        imageViewDice = findViewById(R.id.image_view_dice);
        imageViewDice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundPool.play(sound4,1,1,1,3,5);
                rollDice();
            }
        });
    }
    private void rollDice(){
        int randomNumber = rng.nextInt(20) + 1;

        switch (randomNumber){
            case 1:
                imageViewDice.setImageResource(R.drawable.d1);
                soundPool.play(sound1,1,1,1,0,1);
                Critical.setText("Critical Miss! You Suck");
                break;
            case 2:
                imageViewDice.setImageResource(R.drawable.d2);
                Critical.setText("");
                break;
            case 3:
                imageViewDice.setImageResource(R.drawable.d3);
                Critical.setText("");
                break;
            case 4:
                imageViewDice.setImageResource(R.drawable.d4);
                Critical.setText("");
                break;
            case 5:
                imageViewDice.setImageResource(R.drawable.d5);
                Critical.setText("");
                break;
            case 6:
                imageViewDice.setImageResource(R.drawable.d6);
                Critical.setText("");
                break;
            case 7:
                imageViewDice.setImageResource(R.drawable.d7);
                Critical.setText("");
                break;
            case 8:
                imageViewDice.setImageResource(R.drawable.d8);
                Critical.setText("");
                break;
            case 9:
                imageViewDice.setImageResource(R.drawable.d9);
                Critical.setText("");
                break;
            case 10:
                imageViewDice.setImageResource(R.drawable.d10);
                Critical.setText("");
                break;
            case 11:
                imageViewDice.setImageResource(R.drawable.d11);
                Critical.setText("");
                break;
            case 12:
                imageViewDice.setImageResource(R.drawable.d12);
                Critical.setText("");
                break;
            case 13:
                imageViewDice.setImageResource(R.drawable.d13);
                Critical.setText("");
                break;
            case 14:
                imageViewDice.setImageResource(R.drawable.d14);
                Critical.setText("");
                break;
            case 15:
                imageViewDice.setImageResource(R.drawable.d15);
                Critical.setText("");
                break;
            case 16:
                imageViewDice.setImageResource(R.drawable.d16);
                Critical.setText("");
                break;
            case 17:
                imageViewDice.setImageResource(R.drawable.d17);
                Critical.setText("");
                break;
            case 18:
                imageViewDice.setImageResource(R.drawable.d18);
                Critical.setText("");
                break;
            case 19:
                imageViewDice.setImageResource(R.drawable.d19);
                Critical.setText("");
                break;
            case 20:
                imageViewDice.setImageResource(R.drawable.d20);
                soundPool.play(sound3,1,1,1,0,1);
                Critical.setText("Critical Hit! You suck less");
                break;

        }

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        currentX = sensorEvent.values[0];
        currentY = sensorEvent.values[1];
        currentZ = sensorEvent.values[2];

        if(notFirstTime)
        {
            xDifference = Math.abs(lastX - currentX);
            yDifference = Math.abs(lastY - currentY);
            zDifference = Math.abs(lastZ - currentZ);

            if((xDifference > shakeThreshold && yDifference > shakeThreshold) || (xDifference > shakeThreshold && zDifference > shakeThreshold) || (yDifference > shakeThreshold && zDifference > shakeThreshold))
            {
                vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                soundPool.play(sound2,1,1,1,0,1);
                rollDice();
            }

        }

        lastX = currentX;
        lastY = currentY;
        lastZ = currentZ;
        notFirstTime = true;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(isAccelerometerSensorAvailable)
        {
            sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(isAccelerometerSensorAvailable)
        {
            sensorManager.unregisterListener(this);
        }
    }

}