package tal.XOProject.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;


public class Register extends AppCompatActivity {
    private TextView userlogin;
    private EditText username,userpassword,useremail,userphone;
    private Button regButton;
    private FirebaseAuth firebaseAuth;
    private RadioButton rbmale;
    private RadioGroup radioGroup;
    private String gender="";
    private int id=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        radioGroup = findViewById(R.id.rb);
        rbmale = findViewById(R.id.maleBt);
        rbmale.setId(id);
        username=(EditText)findViewById(R.id.user2);
        userpassword=(EditText)findViewById(R.id.password2);
        useremail=(EditText)findViewById(R.id.email2);
        regButton=(Button) findViewById(R.id.send);
        userlogin=(TextView) findViewById(R.id.reg2);
        userphone=(EditText)findViewById(R.id.phone2);

        firebaseAuth= FirebaseAuth.getInstance();
        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    final String user_name = username.getText().toString().trim();
                    final String user_email = useremail.getText().toString().trim();
                    final String user_password = userpassword.getText().toString().trim();
                    final String user_phone = userphone.getText().toString().trim();
                    int genderId = radioGroup.getCheckedRadioButtonId();
                    if (genderId==id){gender="male";}
                    else {gender = "female";}
                    try
                    {
                    firebaseAuth.createUserWithEmailAndPassword(user_email, user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                sendUserData(user_name, user_email,gender, user_password, user_phone);
                                Toast.makeText(getApplicationContext(), "Successfully Registered", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(tal.XOProject.tictactoe.Register.this, ModeSelectActivity.class));
                            } else {
                                Toast.makeText(getApplicationContext(), "Registration Failed", Toast.LENGTH_LONG).show();
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

        userlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void sendUserData(String username,String email, String gender, String password,String phone) {
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        firebaseAuth= FirebaseAuth.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference("Users/"+Objects.requireNonNull(firebaseAuth.getUid())+"/UserData");
        Userprofile userprofile=new Userprofile(phone,email,gender,username,password);
        myRef.setValue(userprofile);
    }
    private boolean validate(){
       return true;
    }
}