package cn.univs.api.bean;

import java.io.Serializable;
import java.util.ArrayList;

/***
 * 组图bean
 * 
 * @author issuser
 * 
 */
public class PhotosItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1089911715996939339L;

	private boolean state;

	private PhotoData data;

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	public PhotoData getData() {
		return data;
	}

	public void setData(PhotoData data) {
		this.data = data;
	}

	public PhotosItem() {
		super();
	}

	public class PhotoData {
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
		public ArrayList<ImageData> images;

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

		public ArrayList<ImageData> getImages() {
			return images;
		}

		public void setImages(ArrayList<ImageData> images) {
			this.images = images;
		}

		public PhotoData() {
			super();
		}

	}

	public class ImageData {
		public String image;
		public String thumb;
		public String note;

		public String getImage() {
			return image;
		}

		public void setImage(String image) {
			this.image = image;
		}

		public String getThumb() {
			return thumb;
		}

		public void setThumb(String thumb) {
			this.thumb = thumb;
		}

		public String getNote() {
			return note;
		}

		public void setNote(String note) {
			this.note = note;
		}

		public ImageData() {
			super();
		}

	}
}
