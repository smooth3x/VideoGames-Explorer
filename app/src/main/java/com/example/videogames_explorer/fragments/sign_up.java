package com.example.videogames_explorer.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.videogames_explorer.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class sign_up extends Fragment {

    private FirebaseAuth mAuth;

    public sign_up() {}

    public static sign_up newInstance(String param1, String param2) {
        sign_up fragment = new sign_up();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
    }

    /*public void addData(View view) {

        EditText phoneT = view.findViewById(R.id.registerPhoneNumber);
        EditText emailT = view.findViewById(R.id.registerUserEmailAddress);

        FirebaseUser user = mAuth.getCurrentUser();
        String uid = user.getUid();

        ((MainActivity)getActivity()).uid = uid;

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users").child(uid);

        Person p = new Person(phoneT.getText().toString(), emailT.getText().toString());
        myRef.setValue(p);
    }*/

    public void signUp(View view) {
        EditText emailT = view.findViewById( R.id.registerUserMail);
        EditText passT = view.findViewById(R.id.registerUserPassword);
        EditText retypePassT = view.findViewById(R.id.registerUserRetypePassword);
        String email = emailT.getText().toString().trim();
        String password = passT.getText().toString().trim();
        String retypePassword = retypePassT.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty() || retypePassword.isEmpty() ) {
            Toast.makeText(getContext() , "One or more fields are empty." , Toast.LENGTH_LONG).show();
            return;
        }

        if( !password.equals(retypePassword) ) {
            Toast.makeText(getContext() , "Passwords don't match" , Toast.LENGTH_LONG).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(getContext() , "Sign-up Successful" , Toast.LENGTH_LONG).show();
                            //addData(view);
                            Navigation.findNavController(view).navigate(R.id.action_signUp_to_game_list);
                        }
                        else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getContext() , "Sign-up Failed" , Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragView = inflater.inflate(R.layout.fragment_sign_up, container, false);
        Button signupBtn = (Button) fragView.findViewById(R.id.registerSignUp);
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp(fragView);
            }
        });

        return fragView;
    }
}