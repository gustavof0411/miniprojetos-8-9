import java.util.HashMap;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
        Scanner continuar = new Scanner(System.in);
        HashMap<String, Personagem> personagens = ObterDadosDeArquivo.carregarPersonagem();
        HashMap<String, Capitulo> capitulos = ObterDadosDeArquivo.carregarCapitulo(personagens, continuar);
        Capitulo raiz = capitulos.get("CAPÍTULO 1 - O INÍCIO");
        mostrarCapitulo(raiz);
    }

    private void mostrarCapitulo(Capitulo capitulo) {
        textoCapitulo.clear();
        textoConsequencia.clear();
        imagemCapituloImagem.setText("");

        try {
            CapituloImagem capituloImagem = (CapituloImagem) capitulo;
            imagemCapituloImagem.setText(capituloImagem.getImagem());
        } catch (Exception e) {
            System.out.println("Erro no processamento da imagem do capítulo.");
        }

        tituloCapitulo.setText(capitulo.getNome());
        if (capitulo.getFinalCap() != null) {
            textoCapitulo.setText(capitulo.getTexto() + "\n" + capitulo.getFinalCap());
        } else {
            textoCapitulo.setText(capitulo.getTexto() + "\n");
        }
        textoConsequencia.setText(capitulo.alterarVidaPersonagem(40));

        if (capitulo.getArrayEscolhas().size() != 0) {
            hboxAreaBotoes.getChildren().clear();
            boolean botaoProsseguirCriado = false;
            for (Escolha algumaEscolha : capitulo.getArrayEscolhas()) {
                if (algumaEscolha.getTexto() != null && !algumaEscolha.getTexto().isEmpty()) {
                    Button botao = new Button(algumaEscolha.getTexto());
                    botao.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            mostrarCapitulo(algumaEscolha.getProximo());
                        }
                    });
                    hboxAreaBotoes.getChildren().add(botao);
                } else if (!botaoProsseguirCriado && algumaEscolha.getProximo() != null) {
                    Button botao = new Button("Prosseguir >>");
                    botao.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            mostrarCapitulo(algumaEscolha.getProximo());
                        }
                    });
                    hboxAreaBotoes.getChildren().add(botao);
                    botaoProsseguirCriado = true;
                }
            }
        }
    }

}
