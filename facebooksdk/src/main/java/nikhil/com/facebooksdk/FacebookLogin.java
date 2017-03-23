package nikhil.com.facebooksdk;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * Created by nikhil on 21/9/16.
 */
public class FacebookLogin {

    Context context;
    Activity activity;


    //public CallbackManager callbackManager;

    String jsonObjectValues,fbPersonId,fbPersonFullname,fbPersonEmail,fbPersonToken;

    /**
     * fb sdk initialise
     */

}
