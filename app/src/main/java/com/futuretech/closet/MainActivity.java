package com.futuretech.closet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.futuretech.closet.account.LoginActivity;
import com.futuretech.closet.ui.fragment.MainFragment;

import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

public class MainActivity extends SupportActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

        if (findFragment(MainFragment.class) == null) {
            loadRootFragment(R.id.fl_container, MainFragment.newInstance());
        }

    }

    @Override
    public void onBackPressedSupport() {
        // 对于 4个类别的主Fragment内的回退back逻辑,已经在其onBackPressedSupport里各自处理了
        super.onBackPressedSupport();
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        // 设置横向(和安卓4.x动画相同)
        //return new DefaultHorizontalAnimator();
        return super.onCreateFragmentAnimator();
    }
}
