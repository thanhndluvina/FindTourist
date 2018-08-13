package com.luvinat3h;

public class File_Image {
	public int id_image;
	public byte[] data;
	
	public File_Image() {
		id_image = 0;
	}

	public int getId_image() {
		return id_image;
	}

	public void setId_image(int id_image) {
		this.id_image = id_image;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}
}
