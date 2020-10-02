package com.e.dpkartavyaserver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.e.dpkartavyaserver.Common.CurrentAdmin;
import com.e.dpkartavyaserver.Model.Admin;
import com.e.dpkartavyaserver.Preference.SaveSharedPreference;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity {
    private LinearLayout send,verify;
    private PinView otp;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private Button btnContinue,btnTakemein;
    private String number;
    private TextView resendNow;
    private FirebaseAuth firebaseAuth;
    private String mVerificationId;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference,data2;
    private EditText phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        phone = findViewById(R.id.login_email);
        /* To make Box At time of Typing */
        phone.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                phone.setBackgroundResource(R.drawable.edit_text_design_on);
                return false;
            }
        });
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("admin");
        data2 = firebaseDatabase.getReference("admin_check");
        send = findViewById(R.id.getOtpLayout);
        verify = findViewById(R.id.verifyOtpLayout);
        otp = findViewById(R.id.pinViewOTP);
        resendNow = findViewById(R.id.textResendNow);
        btnContinue = findViewById(R.id.login_click);
        btnTakemein = findViewById(R.id.takeMeIn);
        try {
            firebaseAuth= FirebaseAuth.getInstance();
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"Auth is null",Toast.LENGTH_LONG).show();
        }
        resendNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendCode(number,mCallbacks);
            }
        });

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                number = phone.getText().toString();
                number = "+91" + number;
                if(number.length()==13)
                {
                    data2.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.child(number).exists()){
                                sendCode(number,mCallbacks);
                                verify.setVisibility(View.VISIBLE);
                                send.setVisibility(View.INVISIBLE);
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"ADMIN DOESN'T EXISTS",Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"ENTER VALID PHONE NUMBER",Toast.LENGTH_LONG).show();
                }
            }
        });

        btnTakemein.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code_entered = otp.getText().toString();
                if (TextUtils.isEmpty(otp.getText()) || otp.getText().toString().length()!=6){
                    Toast.makeText(getApplicationContext(),"PLEASE ENTER OTP",Toast.LENGTH_LONG).show();
                }
                else {
                    verifyCode(code_entered);
                }
            }
        });

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.
                //Log.d(TAG, "onVerificationCompleted:" + credential);
                String code = credential.getSmsCode();
                otp.setText(code);
                verifyCode(code);
                //signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                //Log.w(TAG, "onVerificationFailed", e);


                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    Toast.makeText(getApplicationContext(),"Invalid",Toast.LENGTH_SHORT).show();
                    // ...
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    // ...
                    Toast.makeText(getApplicationContext(),"TOO MANY REQUESTS",Toast.LENGTH_SHORT).show();
                }

                // Show a message and update the UI
                // ...
            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                //Log.d(TAG, "onCodeSent:" + verificationId);

                // Save verification ID and resending token so we can use them later
                //Toast.makeText(getApplicationContext(),"Code sent",Toast.LENGTH_SHORT).show();
                mVerificationId = verificationId;

                // ...
            }
        };

        phone.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                phone.setBackgroundResource(R.drawable.edit_text_design_on);
                return false;
            }
        });
    }
    private void verifyCode(String code_entered) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId,code_entered);
        signInWithPhoneAuthCredential(credential);

    }
    public void sendCode(String number, PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks){
        PhoneAuthProvider.getInstance().verifyPhoneNumber(number,
                60,
                TimeUnit.SECONDS,
                LoginActivity.this,
                mCallbacks);
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = task.getResult().getUser();
                            SaveSharedPreference.setUserName(LoginActivity.this,user.getPhoneNumber());
                            startActivity(new Intent(LoginActivity.this, DashActivity.class));
                            finish();
                            // ...
                        }
                        else {
                            // Sign in failed, display a message and update the UI
                            //Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Toast.makeText(getApplicationContext(),"INVALID CODE",Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
    }
}