package com.sist.hr;

import java.io.File;
import java.io.IOException;

import javax.imageio.stream.FileCacheImageInputStream;

public class CommonVO {
	private String id = null;
	private String keyword = null;
	private String dirPath = null;
	private String filePathSchedule;
	private String filePathCost;
	private String filePathFavs;
	private String filePathTCL;

	public CommonVO() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
//		this.dirPath = "D://" + this.id;
		this.dirPath = (System.getProperty("user.dir")) + File.separator + this.id;
		File dir = new File(dirPath);
		dir.mkdirs();
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
		File dirs = new File(dirPath);
		if (dirs.exists()) {
			this.filePathCost = dirPath + "//Cost_" + this.keyword + ".csv";
			this.filePathFavs = dirPath + "//Favs_" + this.keyword + ".csv";
			this.filePathSchedule = dirPath + "//Schedule_" + this.keyword + ".csv";
			this.filePathTCL = dirPath + "//TCL_" + this.keyword + ".csv";

			File c = new File(filePathCost);
			File f = new File(filePathFavs);
			File s = new File(filePathSchedule);
			File t = new File(filePathTCL);
			try {
				c.createNewFile();
				f.createNewFile();
				s.createNewFile();
				t.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public String getFilePathSchedule() {
		return filePathSchedule;
	}

	public String getFilePathCost() {

		return filePathCost;
	}

	public String getFilePathFavs() {
		return filePathFavs;
	}

	public String getFilePathTCL() {
		return filePathTCL;
	}

	public String getDirPath() {
		return dirPath;
	}

}
