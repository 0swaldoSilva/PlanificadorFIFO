package sample;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import sample.UI.Fifo;
import sample.UI.RR;

public class Main extends Application {

    private Scene escena;
    private HBox hBox;
    private Button btnFifo, btnRR;

    @Override
    public void start(Stage primaryStage) throws Exception{
        CrearUI();

        primaryStage.setTitle("Selecciona el planificador");
        primaryStage.setScene(escena);
        primaryStage.show();
    }

    private void CrearUI() {
        hBox = new HBox();
        btnFifo = new Button("INICIAR PLANIFICADOR FIFO");
        btnFifo.setOnAction(event -> opcion(1));
        btnRR = new Button("RR+FIFO");
        btnRR.setOnAction(event -> opcion(2));
        btnRR.setDisable(true);
        hBox.getChildren().addAll(btnFifo);
        hBox.setSpacing(10);
        hBox.setAlignment(Pos.CENTER);
        escena = new Scene(hBox, 400, 400);
    }

    private void opcion(int opc) {
        switch (opc){
            case 1:
                new Fifo();
                break;
            case 2:
                new RR();
                break;
        }
    }


    public static void main(String[] args) {launch(args);}
}
