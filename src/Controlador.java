import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Controlador {

    @FXML
    private Button botaoClique;

    @FXML
    private HBox hboxAreaBotoes;

    @FXML
    private Label imagemCapituloImagem;

    @FXML
    private TextArea textoCapitulo;

    @FXML
    private TextArea textoConsequencia;

    @FXML
    private Label tituloCapitulo;

    @FXML
    private VBox vboxImagemCapituloImagem;

    @FXML
    private VBox vboxTextoCapitulo;

    @FXML
    private VBox vboxTextoConsequencia;

    @FXML
    void acaoCarregar(ActionEvent event) {

    }

}
