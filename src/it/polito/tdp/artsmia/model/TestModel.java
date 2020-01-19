package it.polito.tdp.artsmia.model;

import java.util.List;

public class TestModel {
	
	public void run() {
		Model model = new Model();
		model.creaGrafo();
		ArtObject ao  = model.getObject(1160);
		model.listCorrelati(ao);
		System.out.println(ao);
		List<ArtObject> walk = model.findWalk(ao, 5);
		System.out.println(walk);
	}
	
	public static void main(String[] args) {
		TestModel main = new TestModel();
		main.run();
	}
}