package com.example.admin.hackuapp;

/**
 * Created by admin on 2016/08/02.
 */

import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * HTTP通信のPOSTタスク完了時に，通信の成否に応じて，受信した通信内容をUI上で取り扱うための抽象クラス。
 *
 */
public abstract class HttpPostHandler extends Handler {

    // このメソッドは隠ぺいし，Messageなどの低レベルオブジェクトを
    // 直接扱わないでもよいようにさせる
    public void handleMessage(Message msg)
    {
        boolean isPostSuccess = msg.getData().getBoolean("http_post_success");
        String http_response = msg.getData().get("http_response").toString();
        if( isPostSuccess )
        {
            String http_type=msg.getData().getString("http_type").toString();
            Log.d("http_type",http_type);
            if(http_type.equals("identification")){
                onIdentCompleted(http_response);
            }else if(http_type.equals("enroll")){
                onPostCompleted( http_response );
                
            }else if(http_type.equals("new")){
                onNewCompleted(http_response);
            }
        }
        else
        {
            onPostFailed( http_response );
        }
    }


    // 下記をoverrideさせずに抽象化した理由は，本クラス指定時に
    // 「実装されていないメソッドの追加」でメソッドスタブを楽に自動生成させるため。
    // また，異常系の処理フローも真剣にコーディングさせるため。


    // 通信成功時の処理を記述させる。
    // 名前をonPostSuccessではなくonPostCompletedにした理由は，
    // メソッド自動生成時に正常系が先頭に来るようにするため。
    public abstract void onPostCompleted( String response );
    public abstract void onIdentCompleted( String response );
    public abstract void onNewCompleted( String response );

    // 通信失敗時の処理を記述させる
    public abstract void onPostFailed( String response );

}
