package com.android.ekishan.chat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.ekishan.R;
import com.android.ekishan.vendor.VendorMainActivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class UserListActivity extends AppCompatActivity {

    private RelativeLayout rlStartChat;
    ImageView iv_back;
    TextView tvHeaderName;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_chat_list_layout);
        getSupportActionBar().hide();
        rlStartChat=(RelativeLayout)findViewById(R.id.rlStartChat);
        iv_back=(ImageView)findViewById(R.id.iv_back);
        tvHeaderName=(TextView)findViewById(R.id.tvHeaderName);
        tvHeaderName.setText("User List");
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        rlStartChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserListActivity.this, SingleChatActivity.class));
            }
        });
    }
}
