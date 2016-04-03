package cn.univs.api;

import org.apache.http.message.BasicHeader;

import android.content.Context;
import android.util.Log;
import cn.univs.app.UnivsApplication;
import cn.univs.app.util.LogUtil;
import cn.univs.app.util.ScreenSizeUtil;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * @author mdj 请求接口控制类
 * 
 */
@SuppressWarnings("unused")
public class MyHttpAPIControl {
	public static final String MY_HTTP_HOME = "http://mapi.univs.cn/mobile/index.php";
	private static MyHttpAPIControl mInstance = null;
	public static AsyncHttpClient client = new AsyncHttpClient();

	private static final UnivsApplication getApplication() {
		return UnivsApplication.getInstance();
	}

	private MyHttpAPIControl() {
	}

	public static MyHttpAPIControl newInstance() {
		if (mInstance == null) {
			mInstance = new MyHttpAPIControl();
		}
		return mInstance;
	}

	/**
	 * @return 请求头
	 */
	private static BasicHeader[] getBaseHeader() {
		BasicHeader[] header = new BasicHeader[1];
		// header[0] = new BasicHeader("APP_KEY", Constant.API_APP_KEY);
		return header;
	}

	/**
	 * 所有接口 请求通用参数
	 */
	private static RequestParams getBaseParams() {
		RequestParams params = new RequestParams();
		params.put("app", "mobile");
		params.put("type", "mobile");
		return params;
	}

	/**
	 * 基础 get
	 * 
	 * @param context
	 * @param url
	 * @param params
	 * @param handler
	 */
	private void get(String url, RequestParams params,
			AsyncHttpResponseHandler handler) {
		String urlWithQueryString = AsyncHttpClient.getUrlWithQueryString(url,
				params);
		LogUtil.d(this.getClass(), urlWithQueryString, 81);
		client.get(getApplication(), url, null, params, handler);
	}

	/**
	 * 基础 post
	 * 
	 * @param context
	 * @param url
	 * @param params
	 * @param handler
	 */
	private void post(String url, RequestParams params,
			AsyncHttpResponseHandler handler) {
		client.post(getApplication(), url, null, params,
				"application/x-www-form-urlencoded", handler);
	}

	/**
	 * 基础 put
	 * 
	 * @param context
	 * @param url
	 * @param params
	 * @param handler
	 */
	private void put(String url, RequestParams params,
			AsyncHttpResponseHandler handler) {
		LogUtil.d(this.getClass(), url, 81);
		client.put(getApplication(), url, null, null,
				"application/x-www-form-urlencoded", handler);
	}

	/**
	 * 基础 delete
	 * 
	 * @param context
	 * @param url
	 * @param params
	 * @param handler
	 */
	private void delete(String url, RequestParams params,
			AsyncHttpResponseHandler handler) {
		client.delete(getApplication(), url, getBaseHeader(), params, handler);
	}

	/**
	 * 2.1 启动
	 * 
	 * @param handler
	 */
	public void getSplashInfo(Context context, AsyncHttpResponseHandler handler) {
		RequestParams baseParams = getBaseParams();
		baseParams.add("system_name", "android");
		baseParams.add("screen_width", ScreenSizeUtil.getScreenWidth(context)
				+ "");
		baseParams.add("screen_height", ScreenSizeUtil.getScreenHeight(context)
				+ "");
		baseParams.add("controller", "index");
		baseParams.add("action", "splash");
		get(MY_HTTP_HOME, baseParams, handler);
	}

	/**
	 * 2.3.1 频道列表
	 * 
	 * @param handler
	 */
	public void getNewsCategory(AsyncHttpResponseHandler handler) {
		RequestParams baseParams = getBaseParams();
		baseParams.add("controller", "content");
		baseParams.add("action", "category");
		get(MY_HTTP_HOME, baseParams, handler);
	}

	/**
	 * 2.3.2 新闻列表
	 * 
	 * @param handler
	 */
	public void getNewsList(String catid, int page, long time,
			AsyncHttpResponseHandler handler) {
		RequestParams baseParams = getBaseParams();
		baseParams.add("controller", "content");
		baseParams.add("action", "index");
		baseParams.add("catid", catid);
		baseParams.add("page", page + "");
		baseParams.add("time", time + "");
		get(MY_HTTP_HOME, baseParams, handler);
	}

	/**
	 * 2.3.3 新闻列表幻灯片
	 * 
	 * @param handler
	 */
	public void getNewsListSlide(String catid, long time,
			AsyncHttpResponseHandler handler) {
		RequestParams baseParams = getBaseParams();
		baseParams.add("controller", "content");
		baseParams.add("action", "slide");
		baseParams.add("catid", catid);
		baseParams.add("time", time + "");
		get(MY_HTTP_HOME, baseParams, handler);
	}

	/**
	 * 2.4.1 文章内容
	 * 
	 * @param handler
	 */
	public void getArticle(String contentid, AsyncHttpResponseHandler handler) {
		RequestParams baseParams = getBaseParams();
		baseParams.add("controller", "article");
		baseParams.add("action", "content");
		baseParams.add("contentid", contentid);
		get(MY_HTTP_HOME, baseParams, handler);
	}

	/**
	 * 2.5.1 组图内容
	 * 
	 * @param handler
	 */
	public void getPicture(String contentid, AsyncHttpResponseHandler handler) {
		RequestParams baseParams = getBaseParams();
		baseParams.add("controller", "picture");
		baseParams.add("action", "content");
		baseParams.add("contentid", contentid);
		get(MY_HTTP_HOME, baseParams, handler);
	}

	/**
	 * 2.6.1 专题内容
	 * 
	 * @param handler
	 */
	public void getSpecial(String contentid, AsyncHttpResponseHandler handler) {
		RequestParams baseParams = getBaseParams();
		baseParams.add("controller", "special");
		baseParams.add("action", "content");
		baseParams.add("contentid", contentid);
		get(MY_HTTP_HOME, baseParams, handler);
	}

	/**
	 * 2.7.1内容统计
	 * 
	 * @param handler
	 */
	public void getTongji(String contentid, AsyncHttpResponseHandler handler) {
		RequestParams baseParams = getBaseParams();
		baseParams.add("controller", "tongji");
		baseParams.add("action", "content");
		baseParams.add("contentid", contentid);
		get(MY_HTTP_HOME, baseParams, handler);
	}

}
