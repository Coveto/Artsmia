package it.polito.tdp.artsmia.db;

import it.polito.tdp.artsmia.model.ArtObject;
import it.polito.tdp.artsmia.model.Model;

public class TestModel {
	
	public void run() {
		Model model = new Model();
		model.creaGrafo();
		ArtObject ao  = model.getObjects().get(0);
		model.listCorrelati(ao);
	}
	
	public static void main(String[] args) {
		TestModel main = new TestModel();
		main.run();
	}
}