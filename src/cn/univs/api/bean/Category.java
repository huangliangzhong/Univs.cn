package cn.univs.api.bean;

import java.io.Serializable;

public class Category implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7799050779029004162L;
	/*
	 * "catid": "3", "catname": "96058bfb", "iconurl":
	 * "http://upload.univs.cn/2014/0901/thumb_360_270_1409533092441.jpg",
	 * "sort": "3", "name": "96058bfb", "url": "http://m.univs.cn/category/3"
	 * 
	 * 
	 * "contentid": 193, "modelid": 1, "title":
	 * "519b8bad670d5e9f726952297528 65f65c1a5feb95ea", "thumb":
	 * "http://upload.univs.cn/2014/1022/thumb_160_120_1413992041971.jpg",
	 * "description":
	 * "1067081765e5ff0c57286d596c5f4f205a925b66966268504e616821533aff0c4e2d53481270b96574ff0c968f77404e0096358282594f5f3a70c8768497f34e5058f054cd8d77ff0c4e007fa451456ee1751f673a6d3b529b768459275b66751f51fa73b057285b666821751f6d3b533a95e853e373a98d774e8665f65c1a5feb95eaff0c8fd94e9b5b66751f7a7f7684201c65f65c1a201d670d88c5662f4ed64eec5728519b8bad65f6768465e7519b8bad670d88c575318be568218bbe8ba1827a672f"
	 * , "comments": 0, "sorttime": 1414135295
	 */
	private String catid;
	private String catname;
	private String iconurl;
	private String sort;
	private String name;
	private String url;
	private boolean isSelect = false;

	public boolean isSelect() {
		return isSelect;
	}

	public void setSelect(boolean isSelect) {
		this.isSelect = isSelect;
	}

	public String getCatid() {
		return catid;
	}

	public void setCatid(String catid) {
		this.catid = catid;
	}

	public String getCatname() {
		return catname;
	}

	public void setCatname(String catname) {
		this.catname = catname;
	}

	public String getIconurl() {
		return iconurl;
	}

	public void setIconurl(String iconurl) {
		this.iconurl = iconurl;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "Category [catid=" + catid + ", catname=" + catname
				+ ", iconurl=" + iconurl + ", sort=" + sort + ", name=" + name
				+ ", url=" + url + "]";
	}

	public Category(String catid, String catname, String iconurl, String sort,
			String name, String url) {
		super();
		this.catid = catid;
		this.catname = catname;
		this.iconurl = iconurl;
		this.sort = sort;
		this.name = name;
		this.url = url;
	}

	public Category() {
		super();
	}

}
