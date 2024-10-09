package com.example.videogames_explorer.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import android.view.inputmethod.InputMethodManager;

import com.example.videogames_explorer.R;
import com.example.videogames_explorer.activities.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;


public class login_or_signup extends Fragment {

    private FirebaseAuth mAuth;

    public login_or_signup() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
    }

    public void login (View view) {

        EditText emailT = view.findViewById(R.id.userMail);
        EditText passT = view.findViewById(R.id.password);
        String email = emailT.getText().toString().trim();
        String password = passT.getText().toString().trim();

        if( email.isEmpty() || password.isEmpty() ) {
            Toast.makeText(getContext(), "One or more fields are empty.", Toast.LENGTH_LONG).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getContext(), "Login successful", Toast.LENGTH_LONG).show();

                            FirebaseUser user = mAuth.getCurrentUser();
                            String uid = user.getUid();
                            ((MainActivity) getActivity()).uid = uid;

                            Navigation.findNavController(view).navigate(R.id.action_login_or_signup_to_game_list);
                        } else {
                            Exception exception = task.getException();
                            if (exception instanceof FirebaseAuthInvalidUserException) {
                                Toast.makeText(getContext(), "No account found with this email.", Toast.LENGTH_LONG).show();
                            } else if (exception instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(getContext(), "Incorrect password.", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getContext(), "Authentication failed: " + exception.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragView = inflater.inflate(R.layout.fragment_login_or_signup, container, false);

        EditText password = fragView.findViewById(R.id.password);
        password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    }

                    login(fragView);
                    return true;
                }
                return false;
            }
        });

        Button loginBtn = (Button) fragView.findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login(fragView);
            }
        });

        Button signUpBtn = (Button) fragView.findViewById(R.id.signupBtn);
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(fragView).navigate(R.id.action_login_or_signup_to_signUp);
            }
        });

        return fragView;
    }
}