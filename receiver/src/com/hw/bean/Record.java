package com.hw.bean;

import java.util.Date;

public class Record {

	private int id;
	private String method;
	private String header;
	private int takeType;
	private int dataType;
	private String dataJSON;
	private byte[] dataStream;
	private String ext;
	private Date time;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getHeader() {
		return header;
	}
	public void setHeader(String header) {
		this.header = header;
	}
	public int getTakeType() {
		return takeType;
	}
	public void setTakeType(int takeType) {
		this.takeType = takeType;
	}
	public int getDataType() {
		return dataType;
	}
	public void setDataType(int dataType) {
		this.dataType = dataType;
	}
	public String getDataJSON() {
		return dataJSON;
	}
	public void setDataJSON(String dataJSON) {
		this.dataJSON = dataJSON;
	}
	public byte[] getDataStream() {
		return dataStream;
	}
	public void setDataStream(byte[] dataStream) {
		this.dataStream = dataStream;
	}
	public String getExt() {
		return ext;
	}
	public void setExt(String ext) {
		this.ext = ext;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
}
