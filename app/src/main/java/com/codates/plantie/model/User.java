package com.codates.plantie.model;

import android.content.Context;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;

public class User {
    private GoogleApiClient googleApiClient;
    private GoogleSignInOptions gso;
    Context context;

    public static GoogleSignInResult setOptionalPendingResult(GoogleApiClient googleApiClient){
        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(googleApiClient);
        if (opr.isDone()){
            GoogleSignInResult result =opr.get();
            return result;
        }else{
            return null;
        }
    }

    public static GoogleSignInAccount handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()){
            GoogleSignInAccount account = result.getSignInAccount();
            return account;
        }else{
            return null;
        }
    }
}
