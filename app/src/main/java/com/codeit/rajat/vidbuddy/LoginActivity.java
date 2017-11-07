package com.codeit.rajat.vidbuddy;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.codeit.rajat.objects.User;
import com.codeit.rajat.utilities.CheckNetworkConnection;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 1;
    private SignInButton signInButton;
    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private ProgressDialog progressDialog;
    private DatabaseReference dbRef;
    private String refName = "users";
    private String TAG="LOGIN_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // check for internet connection
        CheckNetworkConnection networkConnection = new CheckNetworkConnection(getApplicationContext());
        boolean netStat = networkConnection.isConnected();
        if(netStat == false){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Oops..!!");
            builder.setMessage("You Need an Internet Connection to use this App..");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            builder.setCancelable(false);
            builder.show();
        }
        setContentView(R.layout.activity_login);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Signing in With Google...");
        signInButton = (SignInButton)findViewById(R.id.google_sign_in_button);

        mAuth = FirebaseAuth.getInstance();


        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()!=null){
                    startActivity(new Intent(getApplicationContext(),FeedsActivity.class));
                    finish();
                }
            }
        };

        dbRef = FirebaseDatabase.getInstance().getReference(refName);

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(getApplicationContext(),"There was some error connecting to Google Api"
                        ,Toast.LENGTH_SHORT).show();
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso).build();

           signInButton.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   signIn();
               }
           });
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        progressDialog.show();
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } catch (Exception e) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(getApplicationContext(),e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            addUserToDb();
                            // Sign in success, update UI with the signed-in user's information
                            startActivity(new Intent(getApplicationContext(),FeedsActivity.class));
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                    }
                });
    }

    private void addUserToDb() {
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        String uId = firebaseUser.getUid();
        String userName = firebaseUser.getDisplayName();
        String emailId = firebaseUser.getEmail();
        Uri photoUri = firebaseUser.getPhotoUrl();
        User user = new User(uId,userName,emailId,photoUri);
        dbRef.child(uId).setValue(user);
    }
}
