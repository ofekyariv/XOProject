package tal.XOProject.tictactoe;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Forgot_pass extends AppCompatActivity {

    private EditText email_et;
    private Button send_password_reset_btn;
    private FirebaseAuth firebaseAuth;
    private String email_string;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);

        firebaseAuth= FirebaseAuth.getInstance();
        email_et=findViewById(R.id.emailet);
        send_password_reset_btn=findViewById(R.id.send_password_reset_btn);
        send_password_reset_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email_string=email_et.getText().toString().trim();
                if (validate())
                {
                    try {
                        firebaseAuth.sendPasswordResetEmail(email_string).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Password reset email was sent", Toast.LENGTH_LONG).show();
                                    finish();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Password reset failed", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                    catch (Exception e)
                    {
                        Toast.makeText(getApplicationContext(), "Invalid/Empty Email", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    public boolean validate()
    {
        if (email_string.isEmpty())
        {
            Toast.makeText(getApplicationContext(),"Email cant be empty",Toast.LENGTH_LONG).show();
            return false;
        }
       return true;
    }
}
