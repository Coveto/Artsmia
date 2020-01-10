package it.polito.tdp.artsmia.model;

public class Link {
	private int id_o1;
	private int id_02;
	private int cnt;
	public int getId_o1() {
		return id_o1;
	}
	public void setId_o1(int id_o1) {
		this.id_o1 = id_o1;
	}
	public int getId_02() {
		return id_02;
	}
	public void setId_02(int id_02) {
		this.id_02 = id_02;
	}
	public int getCnt() {
		return cnt;
	}
	public void setCnt(int cnt) {
		this.cnt = cnt;
	}
	public Link(int id_o1, int id_02, int cnt) {
		super();
		this.id_o1 = id_o1;
		this.id_02 = id_02;
		this.cnt = cnt;
	}
	

}
