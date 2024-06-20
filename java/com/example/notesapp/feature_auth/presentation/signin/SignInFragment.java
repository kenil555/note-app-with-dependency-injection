package com.example.notesapp.feature_auth.presentation.signin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.notesapp.MainActivity;
import com.example.notesapp.R;
import com.example.notesapp.core.PreferenceWrapper;
import com.example.notesapp.databinding.FragmentSignInBinding;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.BeginSignInResult;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class SignInFragment extends Fragment {
    private GoogleSignInClient googleSignInClient;
    private FragmentSignInBinding binding ;
    private final ActivityResultContracts.StartActivityForResult signInResultContract =
            new ActivityResultContracts.StartActivityForResult();
    private ActivityResultLauncher<Intent> signInLauncher;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSignInBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//
//        initializeGoogleSignInClient(getContext());
//        handleSignInActivityResult();
//
//        binding.signInBtn.setOnClickListener(listener -> {
//            Intent intent = googleSignInClient.getSignInIntent();
//            signInLauncher.launch(intent);
//        });
        navigateToNotesListScreen("emailId", "hardik@gmail.com");
    }
    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            // Skip Log In Screen here
            navigateToNotesListScreen("emailId", user.getEmail());
        }
    }
    private void handleSignInActivityResult(){
        signInLauncher = registerForActivityResult(signInResultContract, result -> {
            if(result.getResultCode() == Activity.RESULT_OK){
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
                try{
                    GoogleSignInAccount account = task.getResult(ApiException.class);
                    AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);

                    signInWithFireBase(credential, account);

                }
                catch (Exception ignored){
                }
            }
        });
    }
    private void signInWithFireBase(AuthCredential credential, GoogleSignInAccount account){
        FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            String email = account.getEmail();
                            String name = account.getDisplayName();
                            String icon = String.valueOf(account.getPhotoUrl());

                            Log.e("User Logged In " , name);

                            PreferenceWrapper preference = new PreferenceWrapper(getContext());
                            preference.putStringOnPreference("userEmail", email);
                            preference.putStringOnPreference("userName" , name);
                            preference.putStringOnPreference("userIcon" , icon);

                            navigateToNotesListScreen("emailId", email);

                        }
                        else{
                            Toast.makeText(getContext() , task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void initializeGoogleSignInClient(Context context){

        boolean[] isCompleted = {false};

        GoogleSignIn.getClient(context, GoogleSignInOptions.DEFAULT_SIGN_IN)
                .revokeAccess()
                .addOnCompleteListener(
                        task -> {
                            GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                                    .requestIdToken(getString(R.string.web_client_id))
                                    .requestEmail()
                                    .build();
                            googleSignInClient = GoogleSignIn.getClient(context, options);

                            isCompleted[0] = true;
                        }
                );

        if(isCompleted[0]) return;

        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(context, options);
    }
    private void navigateToNotesListScreen(String key, String value){
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_container);
        Bundle bundle = new Bundle();
        bundle.putString(key , value);
        navController.navigate(R.id.action_signInFragment2_to_notesFragment, bundle);
        navController.popBackStack(R.id.notesFragment, false);
    }
}