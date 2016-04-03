package cn.univs.api.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class SpecialItme implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5982286909589038691L;
	/**
	 * 
	 */
	private String catname;
	private ArrayList<ArticleItme> data;

	public String getCatname() {
		return catname;
	}

	public void setCatname(String catname) {
		this.catname = catname;
	}

	public ArrayList<ArticleItme> getData() {
		return data;
	}

	public void setData(ArrayList<ArticleItme> data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "SpecialItme [catname=" + catname + ", data=" + data + "]";
	}

	public SpecialItme(String catname, ArrayList<ArticleItme> data) {
		super();
		this.catname = catname;
		this.data = data;
	}

	public SpecialItme() {
		super();
	}

}
