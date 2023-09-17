package com.eat.gymgenius;


import static android.content.Context.MODE_PRIVATE;
import static androidx.core.content.ContextCompat.getMainExecutor;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.MenuItem;
import androidx.biometric.BiometricPrompt;
//androidx.biometric.BiometricPrompt possibly shown as not resolvable by the IDE, application still starts
//and biometrics still work.

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class Navigation {

    public static void loadNavigationBar(BottomNavigationView bottomNavigationView, Context context){
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.newWorkout){
                    Intent intent = new Intent(context, CreateActivity.class);
                    context.startActivity(intent);
                    return true;
                } else if(item.getItemId() == R.id.workouts){

                    SharedPreferences sharedPreferences = context.getSharedPreferences("MyPreferences", MODE_PRIVATE);
                    boolean locked = sharedPreferences.getBoolean("areWorkoutsLocked", false);

                    if(!locked){
                        Intent intent = new Intent(context, SavedWorkoutsActivity.class);
                        context.startActivity(intent);
                        return true;
                    }
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
                        BiometricPrompt.AuthenticationCallback authenticationCallback = new BiometricPrompt.AuthenticationCallback() {
                            @Override
                            public void onAuthenticationError(int errorCode, CharSequence errString) {
                                super.onAuthenticationError(errorCode, errString);
                                // Handle authentication errors, e.g., show error messages.
                            }

                            @Override
                            public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
                                super.onAuthenticationSucceeded(result);
                                // Biometric authentication succeeded. Launch your activity here.
                                Intent intent = new Intent(context, SavedWorkoutsActivity.class);
                                context.startActivity(intent);
                            }

                            @Override
                            public void onAuthenticationFailed() {
                                super.onAuthenticationFailed();
                                // Biometric authentication failed. Handle this, e.g., show an error message or try again.
                            }
                        };

                        BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                                .setTitle("Authenticate")
                                .setSubtitle("Fingerprint authentication required")
                                .setNegativeButtonText("Cancel")
                                .build();

                        BiometricPrompt biometricPrompt = null;
                        biometricPrompt = new BiometricPrompt((FragmentActivity) context, getMainExecutor(context), authenticationCallback);
                        biometricPrompt.authenticate(promptInfo);
                    }




                    return true;
                }

                return false;
            }
        });
    }


}
