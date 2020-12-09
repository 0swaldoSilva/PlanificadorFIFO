package sample.UI;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.Tables.TablaLlegadas;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class RR extends Stage {

    private Scene escena;
    private TableView<TablaLlegadas> tbvLlegadas, tbvProcesos;
    private Label lblProcesos, lblTiempo, lblProcesador, lblOcio;
    private Button btnIniciar;
    private ComboBox<Integer> cbxProcesos;
    private HBox hBox, hBoxDatos, hBoxCbx;
    private VBox vBox;
    ObservableList<TablaLlegadas> lista1 = FXCollections.observableArrayList();
    ObservableList<TablaLlegadas> lista2 = FXCollections.observableArrayList();

    String[] arPid;
    Integer[] arLlegada, arDuracion;

    private String texto1, texto2, texto3;
    private int numProcesos, duracionTotal, segundero, inicio = 0, cont = 0, ocio = 0;

    private Timer timer = new Timer(1500, new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            Platform.runLater(new Runnable() {
                public void run() {
                    btnIniciar.setDisable(true);
                    segundero++;

                    if (inicio == 1){
                        try {
                            if (arDuracion[cont] > 0) {
                                arDuracion[cont]--;
                                tbvProcesos.refresh();
                                if (arDuracion[cont] == 0) {
                                    cont++;
                                    if (arLlegada[cont] > segundero) {
                                        texto2 = "Procesador: ";
                                    }
                                    inicio = 0;
                                }
                            }
                        }catch (Exception e){
                            texto2 = "Procesador: ";
                            inicio = 0;
                        }
                    }

                    llenarTablaProceso();
                    actuLabel();

                    if(inicio == 0){
                        ocio++;
                        texto3 = "Tiempo de ocio: " + ocio;
                    }

                    if(arDuracion[numProcesos-1] == 0){
                        timer.stop();
                    }
                }
            });
        }
    });

    public RR(){
        CrearUI();

        this.setTitle("FIFO");
        this.setScene(escena);
        this.setMaximized(true);
        this.show();
    }

    private void CrearUI() {
        tbvLlegadas = new TableView<TablaLlegadas>();
        tbvProcesos = new TableView<TablaLlegadas>();

        lblProcesos   = new Label("Procesos: ");
        lblTiempo     = new Label("Tiempo:");
        lblProcesador = new Label("Procesador: ");
        lblOcio       = new Label(texto3 = "Tiempo de ocio: ");
        btnIniciar    = new Button("Iniciar");
        cbxProcesos   = new ComboBox();

        btnIniciar.setOnAction(event -> iniciar());
        cbxProcesos.getItems().addAll(10, 15, 20, 25, 30);

        hBox      = new HBox();
        hBoxCbx   = new HBox();
        hBoxDatos = new HBox();

        hBox.setSpacing(20);
        hBox.setAlignment(Pos.CENTER);
        hBoxDatos.setSpacing(20);
        hBoxDatos.setAlignment(Pos.CENTER);

        vBox = new VBox();
        vBox.setSpacing(20);

        hBoxCbx.getChildren().addAll(lblProcesos, cbxProcesos);
        hBoxDatos.getChildren().addAll(hBoxCbx, lblTiempo, lblProcesador, lblOcio, btnIniciar);
        hBox.getChildren().addAll(tbvLlegadas, tbvProcesos);
        vBox.getChildren().addAll(hBoxDatos, hBox);

        escena = new Scene(vBox, 800, 400);
    }

    public void CrearTabla(){
        TableColumn<TablaLlegadas, String> tbcPID = new TableColumn<>("PID");
        tbcPID.setCellValueFactory(new PropertyValueFactory<>("pid"));

        TableColumn<TablaLlegadas, Integer> tbcLlegada = new TableColumn<>("Llegadas");
        tbcLlegada.setCellValueFactory(new PropertyValueFactory<>("llegada"));

        TableColumn<TablaLlegadas, Integer> tbcDuracion = new TableColumn<>("Duración");
        tbcDuracion.setCellValueFactory(new PropertyValueFactory<>("duracion"));

        tbvLlegadas.getColumns().addAll(tbcPID, tbcLlegada, tbcDuracion);
        tbvLlegadas.setItems(lista1);
        tbvLlegadas.setPrefSize(182,580);
    }

    public void llenarTabla(){
        numProcesos = cbxProcesos.getValue();
        arPid = new String[numProcesos];
        arLlegada = new Integer[numProcesos];
        arDuracion = new Integer[numProcesos];

        for (int i = 0; i < numProcesos; i++) {
            arPid[i] = "PD" + (i + 1);
            arLlegada[i] = (int) (Math.random() * 30) + 1;
            arDuracion[i] = (int) (Math.random() * 5) + 1;
            duracionTotal += arDuracion[i];
        }

        Arrays.sort(arLlegada);

        duracionTotal += arLlegada[0];

        for (int i = 0; i < numProcesos; i++) {
            lista1.add(new TablaLlegadas(arPid[i], arLlegada[i], arDuracion[i], null, null));
        }
    }

    public void CrearTablaProceso(){
        TableColumn<TablaLlegadas, String> tbcPID = new TableColumn<>("PID");
        tbcPID.setCellValueFactory(new PropertyValueFactory<>("pid"));

        TableColumn<TablaLlegadas, Integer> tbcDuracion = new TableColumn<>("Duración");
        tbcDuracion.setCellValueFactory(new PropertyValueFactory<>("duracion"));

        TableColumn<TablaLlegadas, String> tbcEstado = new TableColumn<>("Estado");
        tbcEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));

        TableColumn<TablaLlegadas, String> tbcUbicacion = new TableColumn<>("Ubicación");
        tbcUbicacion.setCellValueFactory(new PropertyValueFactory<>("ubicacion"));

        tbvProcesos.getColumns().addAll(tbcPID, tbcDuracion, tbcEstado, tbcUbicacion);
        tbvProcesos.setItems(lista2);
        tbvProcesos.setPrefSize(322,580);
    }

    public void llenarTablaProceso(){

        for (int i = 0; i < numProcesos; i++) {
            if (arLlegada[i] == segundero) {
                inicio = 1;
                lista2.add(new TablaLlegadas(arPid[i], 0, arDuracion[i], "W", "M"));
            }
            if(cont >= 0 && inicio == 1){
                texto2 = "Procesador: " + arPid[cont];
                lista2.set(cont, new TablaLlegadas(arPid[cont], 0, arDuracion[cont], "X", "CPU"));
            }
            if (cont > 0){
                lista2.set(cont-1, new TablaLlegadas(arPid[cont-1], 0, arDuracion[cont-1], "F", "S"));
            }
        }
    }

    private void actuLabel() {
        texto1 = "Tiempo: " + segundero;
        lblTiempo.setText(texto1);

        lblProcesador.setText(texto2);

        lblOcio.setText(texto3);
    }

    private void iniciar() {
        if (cbxProcesos.getValue() != null){
            segundero = 0;
            CrearTabla();
            llenarTabla();

            CrearTablaProceso();
            timer.start();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR: 404");
            alert.setHeaderText("No has ingresado un valor");
            alert.setContentText("Debes seleccionar un valor FORZOSAMENTE");
            alert.showAndWait();
        }
    }

}
