package tal.XOProject.tictactoe;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private TextView textView,forgot;
    private Button loginbtn;
    private EditText useremail,userpassword;
    private FirebaseAuth firebaseAuth;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);

            textView=(TextView)findViewById(R.id.reg);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(), Register.class));
                }
            });

            forgot=(TextView)findViewById(R.id.forgetpassword);
            forgot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(), Forgot_pass.class));
                }
            });
            useremail= (EditText) findViewById(R.id.email1);
            userpassword=(EditText)findViewById(R.id.password1);
            firebaseAuth= FirebaseAuth.getInstance();
            loginbtn=(Button) findViewById(R.id.loginbtn);
            loginbtn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    if (validate()) {
                        final String user_email=useremail.getText().toString().trim();
                        final String user_password=userpassword.getText().toString().trim();
                        try {
                            firebaseAuth.signInWithEmailAndPassword(user_email, user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getApplicationContext(), "Successfully Logged In", Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(getApplicationContext(), ModeSelectActivity.class));
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Log In Failed", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }
                        catch (IllegalArgumentException e)
                        {
                            Toast.makeText(getApplicationContext(), "Illegal Arguments", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            });
        }
    private boolean validate(){
        return true;
    }

}


