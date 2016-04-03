package cn.univs.api.bean;

import java.util.List;

/***
 * 首页实体bean
 * 
 * @author issuser
 * 
 */
public class GoBean {
	public Boolean state;

	public Data1 data;

	public class Data1 {
		public String image;

		public int category_version;

		public int square_version;

		public App app;

		public Config config;

		public int screen_duration;

		public Style style;

		public int weather_version;

		public class App {
			public String description;
			public String lowestid;
			public String url;
			public String version;
		}

		public class Config {
			public List<Api> api;
			public Baoliao baoliao;
			public Comment comment;
			public Display display;
			public Weibo weibo;

			public class Weibo {
				public int enabled;
				public String nickname;
				public String platform;
				public String userid;
				public String username;
			}

			public class Display {
				public Headline headline;
				public String thumb_align;
				public List<Menu> menu;

				public class Menu {

				}

				public class Headline {
					public String iconurl;
					public String text;
				}
			}

			public class Comment {
				public int islogin;
				public int isopen;
			}

			public class Baoliao {
				public int islogin;
				public int max_picnum;
				public int max_picsize;
				public int max_videosize;
			}

			public class Api {
				public String alias;
				public String appid;
				public String appkey;
				public String appsecret;
				public int index;
				public String redirect_uri;
				public String text;

			}
		}

		public class Style {
			public String background;
			public String button0;
			public String button1;
			public String nav;

		}
	}
}
