package com.sist.hr;

public class ParsingVO {
	private int category;// 1 = °ü±¤Áö, 2 = ¸ÀÁý
	private String img;
	private String title;
	private String point;
	private String describe;
	private String href;

	public ParsingVO() {

	}

	public ParsingVO(int category, String title, String href) {
		this.category = category;
		this.title = title;
		this.href = href;
	}

	public ParsingVO(int categoty, String img, String title, String describe, String href) {
		this.category = category;
		this.img = img;
		this.title = title;
		this.describe = describe;
		this.href = href;
	}

	public ParsingVO(String img, String title, String point, String describe, String href) {
		this.img = img;
		this.title = title;
		this.point = point;
		this.describe = describe;
		this.href = href;
	}

	public ParsingVO(int category, String img, String title, String point, String describe, String href) {
		super();
		this.category = category;
		this.img = img;
		this.title = title;
		this.point = point;
		this.describe = describe;
		this.href = href;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPoint() {
		return point;
	}

	public void setPoint(String point) {
		this.point = point;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

}
