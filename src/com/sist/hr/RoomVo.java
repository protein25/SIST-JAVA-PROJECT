package com.sist.hr;

import java.util.Vector;
/**
 * RoomVo Value Object
 * @Class Name : RoomVo.java
 * @Description : RoomVo Class
 * @Modification Information
 * @ @ 수정일 수정자 수정내용 @ --------- --------- ------------------------------- @
 *   2018.03.02 최초생성
 *
 * @author SIST jamesol@paran.com
 * @since 2018.03.02
 * @version 0.3
 * @see
 *
 *   Copyright (C) by KnJ All right reserved.
 */
public class RoomVo {

	Vector uservc=new Vector();
	String title;
	String zzang;
	int    inwon;
	
	
	public RoomVo() {

	}
	
	public RoomVo(String title,String zzang,int inwon) {
		this.title = title;
		this.zzang = zzang;
		this.inwon = inwon;
	}	

}
