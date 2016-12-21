package zhangfanfan.baway.com.mysrvice.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import zhangfanfan.baway.com.mysrvice.R;

/**
 * Created by ZhangFanfan on 2016/12/21.
 */
public class MusicService extends Service {


    private String TAG="MusicService";

    private MediaPlayer player;

    @Override
    public void onCreate() {
        // 服务创建：可以做初始化 操作

        Log.i(TAG, "onCreate");

        //初始化 音乐播放
        player=MediaPlayer.create(getApplicationContext(), R.raw.czg);
        //设置 无限循环
        player.setLooping(true);

        super.onCreate();
    }
    @Override
    public void onStart(Intent intent, int startId) {
        // 开启服务
        Log.i(TAG, "onStart");

        //播放
        player.start();
        super.onStart(intent, startId);
    }




    @Override
    public IBinder onBind(Intent intent) {
        //开启服务
        //其他对象通过bindService 方法通知该Service时该方法被调用
        Log.i(TAG, "onBind");

        //返回 对象
        return new mBind();
    }

    @Override
    public void onDestroy() {
        //销毁时
        Log.i(TAG, "onDestroy");
        //停止
        player.stop();
        super.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        //其它对象通过unbindService方法通知该Service时该方法被调用
        //解绑定操作

        Log.i(TAG, "onUnbind");
        //停止
        return super.onUnbind(intent);
    }


    //用来返回 musicservice 对象
    //start() 是没办法 返回 服务对象的
    //binder 可以返回 bind对象

    public class mBind extends Binder {

        public MusicService getService(){
            return MusicService.this;
        }
    }

    //binder 播放音乐
    public void playMusic(){
        player.start();
    }


    //binder 停止音乐
    public void stopMusic(){
        player.stop();
    }

    //当然 还可以添加 其余操作
}