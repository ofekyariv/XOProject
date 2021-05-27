package tal.XOProject.tictactoe;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

import static tal.XOProject.tictactoe.ModeSelectActivity.mode;


public class GameOverActivity extends AppCompatActivity {


    private EditText etVictoryQuote;
    private TextView txtwinner;
    private ImageButton mic;
    private Button btnsave;
    private static final int REQ_CODE_SPEECH_INPUT = 2;
    private FirebaseAuth firebaseAuth;
    String gender = "";
    String userName = "";
    String victoryQuote ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        mic=findViewById(R.id.btnmic);
        btnsave=findViewById(R.id.btn2);
        etVictoryQuote = findViewById(R.id.etVictoryQuote);
        txtwinner=findViewById(R.id.txtwinner);
        btnsave.setVisibility(View.INVISIBLE);
        Bundle extras = getIntent().getExtras();
        char winner = extras.getChar("winner");
        if(mode==2){
            if(winner=='o'){
                txtwinner.setText("The Winner Is: PC");
                etVictoryQuote.setHint("Inspiration Quote..");
            }
            else if(winner=='d'){
                txtwinner.setText("Its a draw!");
                etVictoryQuote.setHint("Inspiration Quote..");
            }
            else{
                txtwinner.setText("You Are The Winner!");
                btnsave.setVisibility(View.VISIBLE);
            }
        }
        else{
            if(winner=='d'){
                txtwinner.setText("Its a draw!");
                etVictoryQuote.setHint("Inspiration Quote..");
            }
            else{
                txtwinner.setText("The Winner Is: "+winner);
            }
        }
        mic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en");
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                        getString(R.string.speech));
                try {
                    startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
                } catch (ActivityNotFoundException a) {
                    Toast.makeText(getApplicationContext(),
                            getString(R.string.speech_not_supported),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE_SPEECH_INPUT) {
            if (resultCode == RESULT_OK && null != data) {
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                assert result != null;
                etVictoryQuote.setText(result.get(0));
            }
        }
    }
    public void startover(View view) {
        victoryQuote = etVictoryQuote.getText().toString();
        if (victoryQuote.isEmpty()){victoryQuote = "No Victory Quote";}
        Intent intent1= new Intent();
        intent1.putExtra("victoryQuote", victoryQuote);
        setResult(RESULT_OK, intent1);
        finish();
    }

    public void save(View view) {
        // creation of Player instance!
        victoryQuote = etVictoryQuote.getText().toString();
        if (victoryQuote.isEmpty()){victoryQuote = "No Victory Quote";}
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        firebaseAuth= FirebaseAuth.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference("Users/"+Objects.requireNonNull(firebaseAuth.getUid())+"/UserData");
        myRef.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot datas: dataSnapshot.getChildren()){
                    if(datas.getKey().equals("gender"))
                        gender = datas.getValue().toString();
                    if(datas.getKey().equals("name"))
                        userName = datas.getValue().toString();
                    if(datas.getKey().equals("phone")){
                        Player player = new Player(userName, victoryQuote, 1 , gender);

                        // create DB (Table)
                        PlayerDataBase playerDataBase = new PlayerDataBase(GameOverActivity.this);

                        // upload the data---> save!
                        playerDataBase.setRecord(player);

                        // move to ListActivity
                        Intent intent = new Intent(GameOverActivity.this, ListActivity.class);
                        startActivity(intent);
                    }

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(GameOverActivity.this, "error saving data", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(GameOverActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}