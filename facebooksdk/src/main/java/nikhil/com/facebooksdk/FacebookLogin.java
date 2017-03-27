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

import nikhil.com.facebooksdk.app.Profile;

/**
 * Created by nikhil on 21/9/16.
 */
public class FacebookLogin {

    Context context;
    Activity activity;
    public static Profile profile;


    public static CallbackManager callbackManager;

    String jsonObjectValues,fbPersonId,fbPersonFullname,fbPersonEmail,fbPersonToken;

    /**
     * fb sdk initialise
     */







    public FacebookLogin()
    {
        callbackManager=CallbackManager.Factory.create();
    }

    public FacebookLogin(Context context)
    {
        FacebookSdk.sdkInitialize(context);
        callbackManager=CallbackManager.Factory.create();
    }


    public void setAppId(String appId)
    {
        FacebookSdk.setApplicationId(appId);
    }



    public Profile fbSdkIntialize(Context context,String packageName,Activity activity)
    {
        this.context=context;
        getFbKeyHash(context,packageName);
        profile=Fblogin(activity);

        return profile;
    }





    /**
     * this method is to get the kfbkeyHash
     * @param packageName
     * @param context
     */
    public void getFbKeyHash(Context context,String packageName) {

        try {

            PackageInfo info = context.getPackageManager().getPackageInfo(
                    packageName,
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                // Log.d("YourKeyHash :", Base64.encodeToString(md.digest(), Base64.DEFAULT));
                //System.out.println("YourKeyHash: " + Base64.encodeToString(md.digest(), Base64.DEFAULT));
                //Log.v("LoginActivity");
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }



    public Profile Fblogin(Activity activity)
    {
        // Set permissions

        try {
            Log.v("FacebookLogin","aaa FbLogin() ");
            LoginManager.getInstance().logInWithReadPermissions(activity, Arrays.asList("email", "user_photos", "public_profile"));
            Log.v("FacebookLogin","aaa FbLogin() 1 ");
            LoginManager.getInstance().registerCallback(callbackManager,
                    new FacebookCallback<LoginResult>() {
                        @Override
                        public void onSuccess(final LoginResult loginResult) {

                            Log.v("FacebookLogin","aaa FbLogin() onSuccess()");
                            System.out.println("Success");
                            GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                                    new GraphRequest.GraphJSONObjectCallback() {
                                        @Override
                                        public void onCompleted(JSONObject object, GraphResponse response) {

                                            if (response != null) {
                                                Log.v("FacebookLogin","aaa FbLogin() response!=null");
                                                try {

                                                    jsonObjectValues = object.toString();
                                                    fbPersonId = object.getString("id");
                                                    fbPersonFullname = object.getString("name");
                                                    Log.v("FacebookLogin","aaa name : "+fbPersonFullname);

                                                    fbPersonEmail = object.getString("email");
                                                    Log.v("FacebookLogin","aaa email : "+fbPersonEmail);
                                                    String profilePicUrl = object.getJSONObject("picture").getJSONObject("data").getString("url");

                                                    fbPersonToken = loginResult.getAccessToken().getToken();

                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }else
                                            {
                                                Log.v("FacebookLogin","aaa FbLogin() response=null");
                                            }
                                        }
                                    });
                            Bundle parameters = new Bundle();
                            parameters.putString("fields", "id,name,email,picture.width(100).height(100)");
                            request.setParameters(parameters);
                            request.executeAsync();

                        }

                        @Override
                        public void onCancel() {
                            //Log.d(TAG_CANCEL,"On cancel");
                            Log.v("FacebookLogin","aaa FbLogin() onCancel");
                        }

                        @Override
                        public void onError(FacebookException error) {
                            Log.d("UserConstants",error.toString());

                            Log.v("FacebookLogin","aaa FbLogin() onError() : "+error.toString());
                        }
                    });
        }

        catch (Exception e)
        {
            e.printStackTrace();
        }
        return new Profile(fbPersonFullname,fbPersonEmail,fbPersonId);
    }



}
