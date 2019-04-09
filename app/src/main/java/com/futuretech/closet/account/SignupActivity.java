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

import butterknife.BindView;
import butterknife.ButterKnife;


public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";

    private SharedPreferences sharedPreferences;

    @BindView(R.id.input_code)
    EditText _codeText;


    @BindView(R.id.input_email) EditText _emailText;
    @BindView(R.id.input_password) EditText _passwordText;
    @BindView(R.id.toggle_psw)
    ToggleButton _pswToggle;
    @BindView(R.id.btn_signup) Button _signupButton;
    @BindView(R.id.link_login) TextView _loginLink;
    @BindView(R.id.btn_code)
    Button _codeButton;
    private boolean isOpenEye = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

        sharedPreferences = this.getSharedPreferences("Login", Context.MODE_PRIVATE);

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
        _signupButton.setEnabled(false);

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
            onSignupFailed();
            return;
        }
        //TODO:发送邮箱密码 通知服务器发送验证码

        _signupButton.setEnabled(true);
    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validateCode()) {
            onSignupFailed();
            return;
        }

        _signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();


        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();
        String code = _codeText.getText().toString();

        // TODO: 发送邮箱密码验证码

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        onSignupSuccess();
                        // onSignupFailed();
                        progressDialog.dismiss();
                    }
                }, 1000);
    }


    public void onSignupSuccess() {
        _signupButton.setEnabled(true);
        setResult(RESULT_OK, null);

        //保存输入的账号密码
        // 创建SharedPreferences对象用于储存帐号和密码,并将其私有化
        SharedPreferences share = getSharedPreferences("Login",
                Context.MODE_PRIVATE);
        // 获取编辑器来存储数据到sharedpreferences中
        SharedPreferences.Editor editor = share.edit();
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
}