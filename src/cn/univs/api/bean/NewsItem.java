package cn.univs.api.bean;

import java.io.Serializable;

/****
 * 新闻bean
 * 
 * @author issuser
 * 
 */
public class NewsItem implements Serializable {

	private boolean state;
	private NewData data;

	public NewData getData() {
		return data;
	}

	public void setData(NewData data) {
		this.data = data;
	}

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	public NewsItem() {
		super();
	}

	public class NewData {
		private String contentid;
		private String modelid;
		private String topicid;
		private String allowcomment;
		private String comments;
		private String published;
		private String title;
		private String description;
		private String thumb;
		private String source;
		private String shareurl;
		private long sorttime;
		private String content;

		public NewData() {
			super();
		}

		public String getContentid() {
			return contentid;
		}

		public void setContentid(String contentid) {
			this.contentid = contentid;
		}

		public String getModelid() {
			return modelid;
		}

		public void setModelid(String modelid) {
			this.modelid = modelid;
		}

		public String getTopicid() {
			return topicid;
		}

		public void setTopicid(String topicid) {
			this.topicid = topicid;
		}

		public String getAllowcomment() {
			return allowcomment;
		}

		public void setAllowcomment(String allowcomment) {
			this.allowcomment = allowcomment;
		}

		public String getComments() {
			return comments;
		}

		public void setComments(String comments) {
			this.comments = comments;
		}

		public String getPublished() {
			return published;
		}

		public void setPublished(String published) {
			this.published = published;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getThumb() {
			return thumb;
		}

		public void setThumb(String thumb) {
			this.thumb = thumb;
		}

		public String getSource() {
			return source;
		}

		public void setSource(String source) {
			this.source = source;
		}

		public String getShareurl() {
			return shareurl;
		}

		public void setShareurl(String shareurl) {
			this.shareurl = shareurl;
		}

		public long getSorttime() {
			return sorttime;
		}

		public void setSorttime(long sorttime) {
			this.sorttime = sorttime;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}
	}

}
