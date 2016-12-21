package zhangfanfan.baway.com.mysrvice;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import zhangfanfan.baway.com.mysrvice.service.MusicService;

public class MainActivity extends Activity implements View.OnClickListener {

    // 音乐播放服务
    private MusicService musicService;

    /**
     * 通过SerciceConnection 对象 的相关 方法可以得到 更多的Service对象
     */
    private ServiceConnection conn = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
            // 当启动源跟 Service 的 连接 以外的丢失的时候，会调用这个方法：比如Service被杀死了

        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // 当启动源跟 Service 成功 连接之后会 自动调用这个方法
            musicService = ((MusicService.mBind) service).getService();
        }

    };

    private TextView textview1;
    private Button button1, button2, button3, button4, button5, button6;

    private SharedPreferences shared;
    private final String PLAYMUSIC = "音乐状态：音乐播放中。。。。。。。。";
    private final String STOPMUSIC = "音乐状态：播放停止";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 使用SharedPreferences 保存 音乐的状态
        shared = this.getSharedPreferences("music", MODE_PRIVATE);

        // 显示 当前 音乐状态
        textview1 = (TextView) findViewById(R.id.textView1);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);

        // 加载状态 文件 配置
        String mState = shared.getString("mState", null);
        int mColor = shared.getInt("mColor", 0);
        if (mState != null) {
            if (mColor > 0) {
                textview1.setTextColor(Color.RED);
            } else {
                textview1.setTextColor(Color.BLUE);
            }
            textview1.setText(mState);
        }

    }

    /**
     * 按钮点击事件
     */
    @Override
    public void onClick(View v) {
        // 根据id 点击
        // 根据Intent 来开启 Service
        Intent intent = new Intent(MainActivity.this, MusicService.class);
        switch (v.getId()) {
            case R.id.button1:
                // onStart 方式 开启 音乐服务：播放音乐
                startService(intent);

                // 音乐状态
                playMusicState();
                break;
            case R.id.button2:
                // onStart 方式 关闭 音乐服务：关闭音乐播放
                stopService(intent);

                // 音乐状态
                stopMusicState();
                break;
        }

    }

    /**
     * 存入本地，状态配置
     *
     * @param content
     * @param color
     */
    private void EditMusicState(String content, int color) {
        SharedPreferences.Editor editor = shared.edit();
        // 状态
        editor.putString("mState", content);
        // 文字颜色 0 蓝色 ； 1 红色；
        editor.putInt("mColor", color);
        // 提交
        editor.commit();
    }

    /**
     * 音乐播放状态
     */
    private void playMusicState() {
        textview1.setTextColor(Color.BLUE);
        textview1.setText(PLAYMUSIC);
        EditMusicState(PLAYMUSIC, 0);
    }

    /**
     * 音乐停止状态
     */
    private void stopMusicState() {
        textview1.setTextColor(Color.RED);
        textview1.setText(STOPMUSIC);
        EditMusicState(STOPMUSIC, 1);
    }

}
