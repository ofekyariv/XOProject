package tal.XOProject.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;

public class ModeSelectActivity extends AppCompatActivity {
    public static int mode =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode_select);
    }
    public void pvp(View v) {
        mode=1;
        Intent intent= new Intent(this,MainActivity.class);
        startActivity(intent);
    }
    public void pcvp(View v) {
        mode=2;
        Intent intent= new Intent(this,MainActivity.class);
        startActivity(intent);
    }

}