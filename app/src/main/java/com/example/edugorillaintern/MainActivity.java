package com.example.edugorillaintern;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.eduGorilla.flavour.Constant;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity {
private String KEY ="KEY";
    SignInButton signInButton;
    private ProgressBar progressBar;
    private GoogleSignInClient googleApiClient;
    private static final int SIGN_IN_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //  flavor code implementation

//        if (Constant.type== Constant.Type.FREE){
//            Log.i("TAG", "free flavour");
//        }else{
//
//            Log.i("TAG", "paid flavour");
//        }



        progressBar = findViewById(R.id.MainActivity_progressbar);





        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.


        signInButton = findViewById(R.id.googleSginIn);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               progressBar.setVisibility(View.VISIBLE);
                switch (view.getId()) {
                    case R.id.googleSginIn:
                        signIn();
                        break;
                    // ...
                }

            }
        });



        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        googleApiClient = GoogleSignIn.getClient(this, gso);

    // handling the fcm message and showing the alert box

//        if (getIntent()!=null&&getIntent().hasExtra(KEY)){
//            for (String key : getIntent().getExtras().keySet()){
//                Log.i("", "onCreate:================ "+key+"==============dataa"+getIntent().getExtras().getString(key));
//            }
//        }






    }

    @Override


    protected void onStart() {

        super.onStart();
  // if the notification is not taped than the normal automatic sign will happen


            // Check for existing Google Sign In account, if the user is already signed in
            // the GoogleSignInAccount will be non-null.
              GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
              signIn();


 }





    private  void signIn() {
       progressBar.setVisibility(View.INVISIBLE);
        Intent signInIntent = googleApiClient.getSignInIntent();
        startActivityForResult(signInIntent, SIGN_IN_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == SIGN_IN_CODE) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }

    }
      private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            // Signed in successfully, show authenticated UI.
            progressBar.setVisibility(View.INVISIBLE);
            Intent intent = new Intent(this,MainActivity2.class);
            startActivity(intent);
     } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
           Log.w("MainActivity", "signInResult:failed code=" + e.getStatusCode());

        }
    }
}
