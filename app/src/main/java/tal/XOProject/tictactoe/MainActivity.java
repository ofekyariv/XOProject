package tal.XOProject.tictactoe;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;
import java.util.Random;

import static tal.XOProject.tictactoe.ModeSelectActivity.mode;


public class MainActivity extends AppCompatActivity {
    private static final int GAME_OVER = 1;
    private Controller controller;
    private LinearLayout llmain;
    private ImageView imageView;
    private TextToSpeech tts;
    private TextView txtmode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        controller = new Controller();
        llmain = findViewById(R.id.mainBoard);
        imageView = findViewById(R.id.imgPlayer);
        txtmode = findViewById(R.id.txtmode);
        if(mode==1){
            txtmode.setText("Player vs Player");
        }
        else{
            txtmode.setText("PC vs Player");
        }
        controller.startGame();

        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status!= TextToSpeech.ERROR) {tts.setLanguage(Locale.US);}
            }
        });
    }

    public void select(View v) {

        // להעביר את זהות הכפתור הנלחץ לבקר
        //0-8
        int loc=0;
        char player = controller.getPlayer();
        Button b = findViewById(R.id.btn0);
        Random rand = new Random();
        if(mode==1||player=='x'){
            stopAlert();
            startAlert();
            loc = Integer.parseInt(v.getTag().toString());
            b = (Button) v;
        }
        else{
            while(!controller.checkEmptyPlace(loc)){
                loc=rand.nextInt(9);
            }
            switch (loc){
                case 0:
                    b=findViewById(R.id.btn0);
                    break;
                case 1:
                    b=findViewById(R.id.btn1);
                    break;
                case 2:
                    b=findViewById(R.id.btn2);
                    break;
                case 3:
                    b=findViewById(R.id.btn3);
                    break;
                case 4:
                    b=findViewById(R.id.btn4);
                    break;
                case 5:
                    b=findViewById(R.id.btn5);
                    break;
                case 6:
                    b=findViewById(R.id.btn6);
                    break;
                case 7:
                    b=findViewById(R.id.btn7);
                    break;
                case 8:
                    b=findViewById(R.id.btn8);
                    break;
            }
        }
        //geting last player and send location
        player = controller.userSelect(loc);
        // משנה את התמונה על הכפתור
        if (player == 'x') {b.setBackgroundResource(R.drawable.x);}
        else b.setBackgroundResource(R.drawable.o);

        //לנטרל את הכפתור
        b.setEnabled(false);

        //presenting the player
        char corent = controller.getPlayer();
        if (corent=='x'){imageView.setImageResource(R.drawable.x);}
        else imageView.setImageResource(R.drawable.o);
        if (controller.checkforwinner()){
            tts.speak("game over", TextToSpeech.QUEUE_FLUSH,null);
            stopAlert();
            Intent intent= new Intent(this,GameOverActivity.class);
            intent.putExtra("winner",controller.getWinner());
            startActivityForResult(intent,GAME_OVER);
        }
        else if(mode==2&&player=='x'){
            select(v);
        }
    }
    public void changeMode(View view) {
        Intent intent = new Intent(MainActivity.this, ModeSelectActivity.class);
        startActivity(intent);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GAME_OVER && resultCode == RESULT_OK) {
            String answer = data.getStringExtra("victoryQuote") ;
            Toast.makeText(this, answer, Toast.LENGTH_SHORT).show();
            newGame();
        }
    }

    public void startGame(View view) {
        newGame();
    }

    public void newGame(){

        for (int i=0;i<9;i++){
            Button b = llmain.findViewWithTag(String.valueOf(i));
            b.setBackgroundResource(android.R.drawable.btn_default);
            b.setEnabled(true);
        }
        controller.startGame();
    }
    public void startAlert() {
        int i = 30;
        Intent intent = new Intent(this, MyBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this.getApplicationContext(), 234324243, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        assert alarmManager != null;
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
                + (i * 1000), pendingIntent);
        //Toast.makeText(this, "your turn end in " + i + " seconds",Toast.LENGTH_LONG).show();
    }
    public void stopAlert() {
        Intent intent = new Intent(this, MyBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this.getApplicationContext(), 234324243, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        assert alarmManager != null;
        alarmManager.cancel(pendingIntent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_manu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.share_item){
            Toast.makeText(this, "share item chosen", Toast.LENGTH_SHORT).show();
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "I'm playing tic tac toe");
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        }
        else if (item.getItemId()==R.id.list_item){
            Intent intent = new Intent(this, ListActivity.class);
            startActivity(intent);
        }
        return true;
    }
}