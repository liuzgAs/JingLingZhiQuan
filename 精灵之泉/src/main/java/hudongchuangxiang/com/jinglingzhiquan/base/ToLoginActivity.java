package hudongchuangxiang.com.jinglingzhiquan.base;

import android.content.Context;
import android.content.Intent;
import android.os.Process;

import hudongchuangxiang.com.jinglingzhiquan.activity.DengLuActivity;


/**
 * Created by Administrator on 2017/8/27.
 */
public class ToLoginActivity {

    /**
     * des： 登录
     * author： ZhangJieBo
     * date： 2017/7/6 0006 下午 2:14
     */
    public static void toLoginActivity(Context context) {
        Intent intent = new Intent(context, DengLuActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
        // 杀掉进程
        Process.killProcess(Process.myPid());
        System.exit(0);
    }
}
