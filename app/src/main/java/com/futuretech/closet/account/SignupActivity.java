package com.futuretech.closet.account;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.futuretech.closet.R;
import com.futuretech.closet.utils.StatusCode;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";
    private static final String OK = "1";
    @BindView(R.id.input_code)
    EditText _codeText;
    @BindView(R.id.toggle_psw)
    ToggleButton _pswToggle;
    @BindView(R.id.input_email) EditText _emailText;
    @BindView(R.id.input_password) EditText _passwordText;
    @BindView(R.id.btn_code)
    Button _codeButton;
    @BindView(R.id.btn_signup) Button _signupButton;
    @BindView(R.id.link_login) TextView _loginLink;
    private ProgressDialog progressDialog;
    private boolean isOpenEye = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                finish();
            }
        });

        _codeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCode();
            }
        });

        //填写验证码后才允许点击
        //_signupButton.setEnabled(false);
        //发送验证码后才允许填写
        //_codeText.setEnabled(false);

        _pswToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isOpenEye) {
                    _pswToggle.setSelected(true);
                    isOpenEye = true;
                    //密码可见
                    _passwordText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    _passwordText.setSelection(_passwordText.getText().length());
                    _pswToggle.setBackground(getResources().getDrawable(R.drawable.ic_eye_closed));

                } else {
                    _pswToggle.setSelected(false);
                    isOpenEye = false;
                    //密码不可见
                    _passwordText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    _passwordText.setSelection(_passwordText.getText().length());
                    _pswToggle.setBackground(getResources().getDrawable(R.drawable.ic_eye_open));
                }
            }
        });
    }

    //点击发送验证码按钮后的操作
    public void sendCode() {
        if (!validate()) {
            //onSignupFailed();
            Toast.makeText(getBaseContext(), "请输入有效的邮箱及密码", Toast.LENGTH_LONG).show();
            return;
        }

        progressDialog = new ProgressDialog(SignupActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("正在发送验证码");
        progressDialog.show();


        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();
        //TODO:发送邮箱密码 通知服务器发送验证码
        doSignup1(email, password);
    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validateCode()) {
            onSignupFailed();
            return;
        }

        _signupButton.setEnabled(false);

        progressDialog = new ProgressDialog(SignupActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        String email = _emailText.getText().toString();
        String code = _codeText.getText().toString();
        // TODO: 发送邮箱密码验证码
        doSignup2(email, code);


    }


    public void onSignupSuccess() {
        _signupButton.setEnabled(true);
        setResult(RESULT_OK, null);

        //保存输入的账号密码
        // 创建SharedPreferences对象用于储存帐号和密码,并将其私有化
        SharedPreferences sharedPreferences = getSharedPreferences("Login",
                Context.MODE_PRIVATE);
        // 获取编辑器来存储数据到sharedpreferences中
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Email", _emailText.getText().toString());
        editor.putString("Password",_passwordText.getText().toString());
        editor.putBoolean("LoginBool", true);
        // 将数据提交到sharedpreferences中
        editor.apply();

        //注册完跳转到登陆
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();

    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Signup failed", Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }

    public void onSendCodeSuccess() {
        _signupButton.setEnabled(true);
        _codeText.setEnabled(true);
        //不允许修改账号密码
        _emailText.setEnabled(false);
        _passwordText.setEnabled(false);
    }

    public void onSendCodeFailed() {
        Toast.makeText(getBaseContext(), "发送验证码失败", Toast.LENGTH_LONG).show();
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

    public boolean validateCode() {
        boolean valid = true;
        String code = _codeText.getText().toString();

        if (code.isEmpty() || code.length() != 4) {
            _codeText.setError("请输入四位有效验证码");
            valid = false;
        } else {
            _codeText.setError(null);
        }
        return valid;
    }

    public void doSignup1(String email, String psw) {
        //创建okHttpClient对象
        OkHttpClient mOkHttpClient = new OkHttpClient();
        //String url = "http://39.105.83.165/service/user/insertUser.action"+"?userid="+email+"&password="+psw;
        String url = "http://39.105.83.165/service/user/insertUser.action";
        //Log.d(TAG, "url="+url);

        FormBody formBody = new FormBody
                .Builder()
                .add("userid", email)
                .add("password", psw)
                .build();


        //创建一个Request
        Request request = new Request.Builder()
                .url(url)
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
                    onSendCodeFailed();
                    progressDialog.dismiss();
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                StatusCode statusCode = new StatusCode(response);
                Log.d(TAG, "doSignup1 网络请求返回码:" + response.code());
                //Log.d(TAG, "doSignup1 网络请求返回:" + response.body().string());
                if (OK.equals(statusCode.getStatus())) {
                    runOnUiThread(() -> {
                        onSendCodeSuccess();
                        progressDialog.dismiss();
                    });
                } else {
                    runOnUiThread(() -> {
                        onSendCodeFailed();
                        progressDialog.dismiss();
                    });
                }
            }
        });
    }

    public void doSignup2(String email, String code) {
        //创建okHttpClient对象
        OkHttpClient mOkHttpClient = new OkHttpClient();

        FormBody formBody = new FormBody
                .Builder()
                .add("userid", email)
                .add("checkNum", code)
                .build();

        //创建一个Request
        Request request = new Request.Builder()
                .url("http://39.105.83.165/service/user/avtivateUser.action")
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
                    onSignupFailed();
                    progressDialog.dismiss();
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                StatusCode statusCode = new StatusCode(response);
                Log.d(TAG, "doSignup2 网络请求返回码:" + response.code());
                //Log.d(TAG, "doSignup2 网络请求返回:" + response.body().string());
                if (OK.equals(statusCode.getStatus())) {
                    runOnUiThread(() -> {
                        onSignupSuccess();
                        progressDialog.dismiss();
                    });
                } else {
                    runOnUiThread(() -> {
                        onSignupFailed();
                        progressDialog.dismiss();
                    });
                }
            }
        });
    }
}