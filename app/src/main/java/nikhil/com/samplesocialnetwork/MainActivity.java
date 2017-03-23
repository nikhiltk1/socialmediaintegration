package nikhil.com.samplesocialnetwork;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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

import nikhil.com.facebooksdk.FacebookLogin;

public class MainActivity extends AppCompatActivity {
    Context context;
    String packageName;
    Button buttonFacebooklogin;

    FacebookLogin facebookLogin;





    public CallbackManager callbackManager;

    String jsonObjectValues,fbPersonId,fbPersonFullname,fbPersonEmail,fbPersonToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        context=getApplicationContext();

        FacebookSdk.sdkInitialize(context);
        callbackManager = CallbackManager.Factory.create();
        Log.v("MainActivity","callbackManager : "+callbackManager);


        setContentView(R.layout.activity_main);




        buttonFacebooklogin=(Button)findViewById(R.id.button_facebooklogin);



        buttonFacebooklogin.setOnClickListener(facebookClickListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
         callbackManager.onActivityResult(requestCode,resultCode,data);
    }

    View.OnClickListener facebookClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            fbSdkIntialize(context,"nikhil.com.samplesocialnetwork");


        }
    };











    public void fbSdkIntialize(Context context,String packageName)
    {

        this.context=context;
        getFbKeyHash(context,packageName);

        Fblogin(MainActivity.this);
    }

    public void setAppId(String appId)
    {
        FacebookSdk.setApplicationId(appId);
    }
    public void Fblogin(Activity activity)
    {
        // Set permissions

        try {
            LoginManager.getInstance().logInWithReadPermissions(activity, Arrays.asList("email", "user_photos", "public_profile"));
            LoginManager.getInstance().registerCallback(callbackManager,
                    new FacebookCallback<LoginResult>() {
                        @Override
                        public void onSuccess(final LoginResult loginResult) {

                            System.out.println("Success");
                            GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                                    new GraphRequest.GraphJSONObjectCallback() {
                                        @Override
                                        public void onCompleted(JSONObject object, GraphResponse response) {
                                            if (response != null) {
                                                try {

                                                    jsonObjectValues = object.toString();
                                                    fbPersonId = object.getString("id");
                                                    fbPersonFullname = object.getString("name");
                                                    fbPersonEmail = object.getString("email");
                                                    String profilePicUrl = object.getJSONObject("picture").getJSONObject("data").getString("url");

                                                    fbPersonToken = loginResult.getAccessToken().getToken();

                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
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
                        }

                        @Override
                        public void onError(FacebookException error) {
                            Log.d("UserConstants",error.toString());

                        }
                    });
        }

        catch (Exception e)
        {
            e.printStackTrace();
        }
    }




    /**
     * this method is to get the kfbkeyHash
     * @param packageName
     * @param context
     */
    public void getFbKeyHash(Context context,String packageName) {

        try {

            PackageInfo info = getPackageManager().getPackageInfo(
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


}
