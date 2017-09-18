package com.sxbwstxpay.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.sxbwstxpay.constant.Constant;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.pay_result);

        api = WXAPIFactory.createWXAPI(this, Constant.WXAPPID);

        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
        Log.e("WXPayEntryActivity", "WXPayEntryActivity--onReq--" + req.toString());
    }


    private void sendPayResult(int errCode) {
        Log.e("WXPayEntryActivity", "WXPayEntryActivity--sendPayResult--" + errCode);
        Intent intent = new Intent(Constant.BROADCASTCODE.PAY_RECEIVER);
        intent.putExtra("error", errCode);
        sendBroadcast(intent);
    }

    @Override
    public void onResp(BaseResp resp) {
        sendPayResult(resp.errCode);
        finish();
        // if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
        // }
    }

//	private void sendLoginCode() {
//		Intent intent = new Intent(Constant.BROADCASTCODE.WX_LOGIN);
//		intent.putExtra("error", errCode);
//		sendBroadcast(intent);
//	}
}