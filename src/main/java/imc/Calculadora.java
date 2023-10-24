package imc;

import javafx.application.Application;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

public class Calculadora extends Application {


    //model

    private DoubleProperty peso = new SimpleDoubleProperty();
    private DoubleProperty altura = new SimpleDoubleProperty();
    private DoubleProperty imc = new SimpleDoubleProperty();
    private StringProperty indice = new SimpleStringProperty();


    //view

    private TextField pesoText;
    private TextField alturaText;
    private Label imcLabel;
    private Label indiceLabel;


    @Override
    public void start(Stage stage) throws Exception {


        pesoText = new TextField();
        pesoText.setPromptText("Introduce el peso");
        pesoText.setPrefColumnCount(4);

        alturaText = new TextField();
        alturaText.setPromptText("Introduce la altura");
        alturaText.setPrefColumnCount(4);

        imcLabel = new Label();
        indiceLabel = new Label("Bajo peso | Normal | Sobrepeso | Obeso");

        HBox pesoHbox = new HBox(5, new Label("Peso"), pesoText, new Label("kg"));
        pesoHbox.setAlignment(Pos.CENTER);

        HBox alturaHbox = new HBox(5, new Label("Altura: "), alturaText, new Label("cm"));
        alturaHbox.setAlignment(Pos.CENTER);

        HBox imcHbox = new HBox(5, new Label("Imc: "), imcLabel);
        imcHbox.setAlignment(Pos.CENTER);

        VBox root = new VBox(5, pesoHbox, alturaHbox, imcHbox, indiceLabel);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 400, 300);

        stage.setTitle("IMC");
        stage.setScene(scene);
        stage.show();


        //bindings

        imc.bind(peso.divide(altura.multiply(altura).divide(10000)));
        imcLabel.textProperty().bind(Bindings
                .when(imc.isEqualTo(0))
                .then("IMC: ")
                .otherwise(Bindings.format("IMC: %.2f", imc))
        );


        pesoText.textProperty().bindBidirectional(peso, new NumberStringConverter());

        alturaText.textProperty().bindBidirectional(altura, new NumberStringConverter());

        indiceLabel.textProperty().bind(indice);
        indice.set("Bajo peso | Normal | Sobrepeso | Obeso");

        imc.addListener(this::onCalcularindiceAction);

    }


    private void onCalcularindiceAction(Observable observable, Number ov, Number nv) {

        double imcValue = imc.get();

        if (imcValue > 0) {
            if (imcValue < 18.5) {
                indice.set("Bajo peso");
            } else if (imcValue < 25) {
                indice.set("Normal");
            } else if (imcValue < 30) {
                indice.set("Sobrepeso");
            } else {
                indice.set("Obeso");
            }
        } else {
            indice.set("Bajo peso | Normal | Sobrepeso | Obeso");
        }
    }
}
