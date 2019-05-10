package com.futuretech.closet.account;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.futuretech.closet.R;
import com.futuretech.closet.utils.JsonUtils;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final String LOGINOK = "1";
    private static final int REQUEST_SIGNUP = 0;

    private SharedPreferences sharedPreferences;

    private ProgressDialog progressDialog;

    @BindView(R.id.input_email) EditText _emailText;
    @BindView(R.id.input_password) EditText _passwordText;
    @BindView(R.id.btn_login) Button _loginButton;
    @BindView(R.id.link_signup) TextView _signupLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        //sharedPreferences = this.getSharedPreferences("Login", Context.MODE_PRIVATE);

        // 获取sharedpreferences对象
        sharedPreferences = getSharedPreferences("Login",
                Context.MODE_PRIVATE);
        _emailText.setText(sharedPreferences.getString("Email", ""));
        _passwordText.setText(sharedPreferences.getString("Password", ""));

        //自动登录
        if(sharedPreferences.getBoolean("LoginBool",false)){
            login();
        }

        _loginButton.setOnClickListener(v -> {
            login();
        });

        _signupLink.setOnClickListener(v -> {
            // Start the Signup activity
            Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
            startActivityForResult(intent, REQUEST_SIGNUP);
        });
    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        // 连接服务器验证账号密码
        doLogin(email, password);

        // 创建SharedPreferences对象用于储存帐号和密码,并将其私有化
        sharedPreferences = getSharedPreferences("Login",
                Context.MODE_PRIVATE);
        // 获取编辑器来存储数据到sharedpreferences中
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Email", email);
        editor.putString("Password",password);
        editor.putBoolean("LoginBool", true);
        // 将数据提交到sharedpreferences中
        editor.apply();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }

    public void doLogin(String email, String psw) {
        //创建okHttpClient对象
        OkHttpClient mOkHttpClient = new OkHttpClient();

        FormBody formBody = new FormBody
                .Builder()
                .add("userid", email)
                .add("password", psw)
                .build();

        //创建一个Request
        Request request = new Request.Builder()
                .url("http://39.105.83.165/service/user/userLogin.action")
                .post(formBody)
                .build();
        //new call
        Call call = mOkHttpClient.newCall(request);
        //请求加入调度
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "网络请求失败");
                e.printStackTrace();
                runOnUiThread(() -> {
                    onLoginFailed();
                    progressDialog.dismiss();
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String resp = response.body().string();
                Log.d(TAG, "doLogin 网络请求返回码:" + response.code());
                //Log.d(TAG, "doLogin 网络请求返回:" + response.body().string());
                if (LOGINOK.equals(JsonUtils.getStatusCode(resp))) {
                    runOnUiThread(() -> {
                        onLoginSuccess();
                        progressDialog.dismiss();
                    });
                } else {
                    runOnUiThread(() -> {
                        onLoginFailed();
                        progressDialog.dismiss();
                    });
                }
            }
        });
    }
}
