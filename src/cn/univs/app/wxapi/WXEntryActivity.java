
package cn.univs.app.wxapi;

import android.os.Bundle;
import android.widget.Toast;
import cn.univs.app.R;

import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.umeng.socialize.weixin.view.WXCallbackActivity;

public class WXEntryActivity extends WXCallbackActivity {
	 private IWXAPI api;
	 
	    @Override
	    protected void onCreate(Bundle savedInstanceState)
	    {
	        super.onCreate(savedInstanceState);
	 
	        api = WXAPIFactory.createWXAPI(this, "wx8b63be803dc131b2", false);
	        api.registerApp("wx8b63be803dc131b2");
	        api.handleIntent(getIntent(), this);
	    }
	 
	    @Override
	    public void onReq(BaseReq req)
	    {
	    }
	 
	    @Override
	    public void onResp(BaseResp resp)
	    {
	        int result = 0;
	 
	        switch (resp.errCode)
	        {
	        case BaseResp.ErrCode.ERR_OK:
	            result = R.string.errcode_success;
	            break;
	        case BaseResp.ErrCode.ERR_USER_CANCEL:
	            result = R.string.errcode_cancel;
	            break;
	        case BaseResp.ErrCode.ERR_AUTH_DENIED:
	            result = R.string.errcode_deny;
	            break;
	        default:
	            result = R.string.errcode_unknown;
	            break;
	        }
	 
	        Toast.makeText(this, result, Toast.LENGTH_LONG).show();
	        finish();
//	        overridePendingTransition(R.anim.change_in, R.anim.change_out);
	    }
}
