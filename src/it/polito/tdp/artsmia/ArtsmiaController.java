/**
 * Sample Skeleton for 'Artsmia.fxml' Controller Class
 */

package it.polito.tdp.artsmia;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import it.polito.tdp.artsmia.model.ArtObject;
import it.polito.tdp.artsmia.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ArtsmiaController {
	Model model;
	ArtObject source;
	Integer lunMax;

	@FXML // ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;

	@FXML // URL location of the FXML file that was given to the FXMLLoader
	private URL location;

	@FXML // fx:id="boxLUN"
    private TextField boxLUN;

	@FXML // fx:id="btnCalcolaComponenteConnessa"
	private Button btnCalcolaComponenteConnessa; // Value injected by FXMLLoader

	@FXML // fx:id="btnCercaOggetti"
	private Button btnCercaOggetti; // Value injected by FXMLLoader

	@FXML // fx:id="btnAnalizzaOggetti"
	private Button btnAnalizzaOggetti; // Value injected by FXMLLoader

	@FXML // fx:id="txtObjectId"
	private TextField txtObjectId; // Value injected by FXMLLoader

	@FXML // fx:id="txtResult"
	private TextArea txtResult; // Value injected by FXMLLoader

	@FXML
	void doAnalizzaOggetti(ActionEvent event) {
		model.creaGrafo();
		txtResult.setText("Grafo creato: "+model.getVertexSize()+" vertici e "+model.getEdgeSize()+" archi.");
		btnCalcolaComponenteConnessa.setDisable(false);
		txtObjectId.setDisable(false);
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
		btnCalcolaComponenteConnessa.setDisable(true);
		btnCercaOggetti.setDisable(true);
		txtObjectId.setDisable(true);
		boxLUN.setDisable(true);
	}

	@FXML
	void doCalcolaComponenteConnessa(ActionEvent event) {
		Integer id;
		try {
			id = Integer.parseInt(txtObjectId.getText());
		} catch (NumberFormatException e) {
			txtResult.clear();
			txtResult.appendText("Devi inserire un numero!\n");
			return;
		}
		source = this.model.getObject(id);
		if (source == null) {
			txtResult.clear();
			txtResult.appendText("Devi inserire un ID valido\n");
			return;
		}
			
		List<ArtObject> listCorrelati = this.model.listCorrelati(source);
		txtResult.clear();
		lunMax = listCorrelati.size();
//		for (int i = 2; i<= listCorrelati.size(); i++) {
//			boxLUN.getItems().add(i-2, i);
//		}
		txtResult.appendText("Componente connessa formata da :"+listCorrelati.size()+" vertici");
		btnCercaOggetti.setDisable(false);
		boxLUN.setDisable(false);

	}

	@FXML
	void doCercaOggetti(ActionEvent event) {
		if (lunMax < 3) {
			txtResult.clear();
			txtResult.appendText("Questo oggetto non e' collegato a niente!\n");
			return;
		}
		Integer lun;
		try {
			lun = Integer.parseInt(boxLUN.getText());
			if (lun < 2 || lun > lunMax) {
				txtResult.clear();
				txtResult.appendText("Devi inserire un numero tra 2 e "+lunMax+"!\n");
				return;
			}
		} catch (NumberFormatException e) {
			txtResult.clear();
			txtResult.appendText("Devi inserire un numero!\n");
			return;
		}
		txtResult.clear();

		List<ArtObject> walk = this.model.findWalk(source, lun);
		txtResult.appendText("Percorso trovato. Peso totale: "+this.model.getTotalWeight(walk)+"\n");

		for (ArtObject a : walk) {
			txtResult.appendText(a.toString()+"\n");
		}
		
//		this.model.cercaOggetti(int lun);
	}

	@FXML // This method is called by the FXMLLoader when initialization is complete
	void initialize() {
        assert boxLUN != null : "fx:id=\"boxLUN\" was not injected: check your FXML file 'Artsmia.fxml'.";
		assert btnCalcolaComponenteConnessa != null : "fx:id=\"btnCalcolaComponenteConnessa\" was not injected: check your FXML file 'Artsmia.fxml'.";
		assert btnCercaOggetti != null : "fx:id=\"btnCercaOggetti\" was not injected: check your FXML file 'Artsmia.fxml'.";
		assert btnAnalizzaOggetti != null : "fx:id=\"btnAnalizzaOggetti\" was not injected: check your FXML file 'Artsmia.fxml'.";
		assert txtObjectId != null : "fx:id=\"txtObjectId\" was not injected: check your FXML file 'Artsmia.fxml'.";
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Artsmia.fxml'.";

	}
}
