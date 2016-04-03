package cn.univs.api.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class Splash implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8415172330611884673L;
	private boolean state;
	private SData data;

	public Splash() {
		super();
	}

	public class SData implements Serializable {
		private static final long serialVersionUID = 1L;
		private String image;
		private String category_version;
		private String square_version;
		private String weather_version;
		private App app;
		private Config config;
		private Style style;
		private String screen_duration;

		public String getImage() {
			return image;
		}

		public void setImage(String image) {
			this.image = image;
		}

		public String getCategory_version() {
			return category_version;
		}

		public void setCategory_version(String category_version) {
			this.category_version = category_version;
		}

		public String getSquare_version() {
			return square_version;
		}

		public void setSquare_version(String square_version) {
			this.square_version = square_version;
		}

		public String getWeather_version() {
			return weather_version;
		}

		public void setWeather_version(String weather_version) {
			this.weather_version = weather_version;
		}

		public App getApp() {
			return app;
		}

		public void setApp(App app) {
			this.app = app;
		}

		public Config getConfig() {
			return config;
		}

		public void setConfig(Config config) {
			this.config = config;
		}

		public Style getStyle() {
			return style;
		}

		public void setStyle(Style style) {
			this.style = style;
		}

		public String getScreen_duration() {
			return screen_duration;
		}

		public void setScreen_duration(String screen_duration) {
			this.screen_duration = screen_duration;
		}

		public SData() {
			super();
		}

	}

	public class App implements Serializable {
		private static final long serialVersionUID = 1L;
		private String version;
		private String lowestid;
		private String description;
		private String url;

		public App() {
			super();
		}

		public String getVersion() {
			return version;
		}

		public void setVersion(String version) {
			this.version = version;
		}

		public String getLowestid() {
			return lowestid;
		}

		public void setLowestid(String lowestid) {
			this.lowestid = lowestid;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

	}

	class Config implements Serializable {
		private static final long serialVersionUID = 1L;
		private Display display;
		private Comment comment;
		private BaoLiao baoliao;
		private Weibo weibao;
		private ArrayList<ApiItme> api;

		public Config() {
			super();
		}

		public Display getDisplay() {
			return display;
		}

		public void setDisplay(Display display) {
			this.display = display;
		}

		public Comment getComment() {
			return comment;
		}

		public void setComment(Comment comment) {
			this.comment = comment;
		}

		public BaoLiao getBaoliao() {
			return baoliao;
		}

		public void setBaoliao(BaoLiao baoliao) {
			this.baoliao = baoliao;
		}

		public Weibo getWeibao() {
			return weibao;
		}

		public void setWeibao(Weibo weibao) {
			this.weibao = weibao;
		}

		public ArrayList<ApiItme> getApi() {
			return api;
		}

		public void setApi(ArrayList<ApiItme> api) {
			this.api = api;
		}

	}

	class Display implements Serializable {
		private static final long serialVersionUID = 1L;
		private String thumb_align;
		private HeadLine headline;
		private ArrayList<MenuItme> menu;

		public Display() {
			super();
		}

		public String getThumb_align() {
			return thumb_align;
		}

		public void setThumb_align(String thumb_align) {
			this.thumb_align = thumb_align;
		}

		public HeadLine getHeadline() {
			return headline;
		}

		public void setHeadline(HeadLine headline) {
			this.headline = headline;
		}

		public ArrayList<MenuItme> getMenu() {
			return menu;
		}

		public void setMenu(ArrayList<MenuItme> menu) {
			this.menu = menu;
		}

	}

	class HeadLine implements Serializable {
		private static final long serialVersionUID = 1L;
		private String text;
		private String iconurl;

		public HeadLine() {
			super();
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}

		public String getIconurl() {
			return iconurl;
		}

		public void setIconurl(String iconurl) {
			this.iconurl = iconurl;
		}

	}

	class MenuItme implements Serializable {
		private static final long serialVersionUID = 1L;
		private String text;
		private String url;

		public MenuItme() {
			super();
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

	}

	class Comment implements Serializable {
		private static final long serialVersionUID = 1L;
		private int isopen;
		private int islogin;

		public Comment() {
			super();
		}

		public int getIsopen() {
			return isopen;
		}

		public void setIsopen(int isopen) {
			this.isopen = isopen;
		}

		public int getIslogin() {
			return islogin;
		}

		public void setIslogin(int islogin) {
			this.islogin = islogin;
		}

	}

	class BaoLiao implements Serializable {
		private static final long serialVersionUID = 1L;
		private int islogin;
		private int max_picnum;
		private int max_picsize;
		private int max_videosize;

		public BaoLiao() {
			super();
		}

		public int getIslogin() {
			return islogin;
		}

		public void setIslogin(int islogin) {
			this.islogin = islogin;
		}

		public int getMax_picnum() {
			return max_picnum;
		}

		public void setMax_picnum(int max_picnum) {
			this.max_picnum = max_picnum;
		}

		public int getMax_picsize() {
			return max_picsize;
		}

		public void setMax_picsize(int max_picsize) {
			this.max_picsize = max_picsize;
		}

		public int getMax_videosize() {
			return max_videosize;
		}

		public void setMax_videosize(int max_videosize) {
			this.max_videosize = max_videosize;
		}

	}

	class Weibo implements Serializable {
		private static final long serialVersionUID = 1L;
		private int enabled;
		private String platform;
		private String username;
		private String nickname;

		public Weibo() {
			super();
		}

		public int getEnabled() {
			return enabled;
		}

		public void setEnabled(int enabled) {
			this.enabled = enabled;
		}

		public String getPlatform() {
			return platform;
		}

		public void setPlatform(String platform) {
			this.platform = platform;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getNickname() {
			return nickname;
		}

		public void setNickname(String nickname) {
			this.nickname = nickname;
		}

	}

	class ApiItme implements Serializable {
		private static final long serialVersionUID = 1L;
		private int index;
		private String text;
		private String alias;
		private String appid;
		private String appkey;
		private String appsecret;
		private String redirect_uri;

		public ApiItme() {
			super();
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}

		public String getAlias() {
			return alias;
		}

		public void setAlias(String alias) {
			this.alias = alias;
		}

		public String getAppid() {
			return appid;
		}

		public void setAppid(String appid) {
			this.appid = appid;
		}

		public String getAppkey() {
			return appkey;
		}

		public void setAppkey(String appkey) {
			this.appkey = appkey;
		}

		public String getAppsecret() {
			return appsecret;
		}

		public void setAppsecret(String appsecret) {
			this.appsecret = appsecret;
		}

		public String getRedirect_uri() {
			return redirect_uri;
		}

		public void setRedirect_uri(String redirect_uri) {
			this.redirect_uri = redirect_uri;
		}

	}

	class Style implements Serializable {
		private static final long serialVersionUID = 1L;
		private String nav;
		private String button0;
		private String button1;
		private String background;

		public Style() {
			super();
		}

		public String getNav() {
			return nav;
		}

		public void setNav(String nav) {
			this.nav = nav;
		}

		public String getButton0() {
			return button0;
		}

		public void setButton0(String button0) {
			this.button0 = button0;
		}

		public String getButton1() {
			return button1;
		}

		public void setButton1(String button1) {
			this.button1 = button1;
		}

		public String getBackground() {
			return background;
		}

		public void setBackground(String background) {
			this.background = background;
		}

	}

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	public SData getData() {
		return data;
	}

	public void setData(SData data) {
		this.data = data;
	}

}
