package nikhil.com.samplesocialnetwork;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import nikhil.com.facebooksdk.FacebookLogin;
import nikhil.com.facebooksdk.app.Profile;

public class MainActivity extends AppCompatActivity {
    Context context;
    String packageName;
    Button buttonFacebooklogin;
    FacebookLogin facebookLogin;
    String TAG="MainActivity";
    Profile profile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=getApplicationContext();

        packageName=getPackageName();
        Log.v("MainActivity","aaa packageName : "+packageName);
        facebookLogin=new FacebookLogin(context);
        facebookLogin.setAppId(getString(R.string.app_id));
        setContentView(R.layout.activity_main);
        buttonFacebooklogin=(Button)findViewById(R.id.button_facebooklogin);
        buttonFacebooklogin.setOnClickListener(facebookClickListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.v("MainActivity","aaa"+requestCode+", " +resultCode+", "+data);
         FacebookLogin.callbackManager.onActivityResult(requestCode,resultCode,data);
    }

    View.OnClickListener facebookClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            profile= facebookLogin.fbSdkIntialize(context,packageName,MainActivity.this);
            Log.v(TAG,"aaa name : "+profile.getName());
            Log.v(TAG,"aaa email : "+profile.getEmail());
            Log.v(TAG,"aaa FbId : "+profile.getFbId());

        }
    };


}
