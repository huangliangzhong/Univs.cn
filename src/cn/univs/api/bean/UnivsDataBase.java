package cn.univs.api.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class UnivsDataBase<T> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4536510344094949545L;
	private boolean state;
	private String message;
	private long time;
	private ArrayList<T> data;

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

	public ArrayList<T> getData() {
		return data;
	}

	public void setData(ArrayList<T> data) {
		this.data = data;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public UnivsDataBase(boolean state, String message, long time,
			ArrayList<T> data) {
		super();
		this.state = state;
		this.message = message;
		this.time = time;
		this.data = data;
	}

	public UnivsDataBase() {
		super();
	}

	@Override
	public String toString() {
		return "UnivsDataBase [state=" + state + ", message=" + message
				+ ", time=" + time + ", data=" + data + "]";
	}
}
