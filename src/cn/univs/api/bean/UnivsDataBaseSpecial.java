package cn.univs.api.bean;

import java.io.Serializable;

public class UnivsDataBaseSpecial implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4536510344094949545L;
	private boolean state;
	private String message;
	private long time;
	private ArticleItme data;

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public ArticleItme getData() {
		return data;
	}

	public void setData(ArticleItme data) {
		this.data = data;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public UnivsDataBaseSpecial(boolean state, String message, long time,
			ArticleItme data) {
		super();
		this.state = state;
		this.message = message;
		this.time = time;
		this.data = data;
	}

	public UnivsDataBaseSpecial() {
		super();
	}

	@Override
	public String toString() {
		return "UnivsDataBase [state=" + state + ", message=" + message
				+ ", time=" + time + ", data=" + data + "]";
	}
}
