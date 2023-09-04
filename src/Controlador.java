import java.util.HashMap;
import java.util.Optional;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Controlador {

    @FXML
    private Button botaoClique;
    @FXML
    private Button botaoNovoJogo;

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
    private void acaoCarregar(ActionEvent event) {
        if (ObterDadosDeArquivo.verificaSave()) {
            Scanner continuar = new Scanner(System.in);
            HashMap<String, Personagem> personagens = ObterDadosDeArquivo.carregarPersonagem();
            HashMap<String, Capitulo> capitulos = ObterDadosDeArquivo.carregarCapitulo(personagens, continuar);
            if (ObterDadosDeArquivo.verificaSave()) {
                Capitulo capituloSave = ObterDadosDeArquivo.desserializadorDeCapitulo("rsc/saves/capituloSave.txt");
                mostrarCapitulo(capitulos.get(capituloSave.getNome()));
            } else {
                Capitulo raiz = capitulos.get("CAPÍTULO 1 - O INÍCIO");
                mostrarCapitulo(raiz);
            }
        } else {
            Alert janelaAviso = new Alert(Alert.AlertType.ERROR);
            janelaAviso.setTitle("Carregar Progresso");
            janelaAviso.setHeaderText("Erro");
            janelaAviso.setContentText("Não há nenhum progresso para carregar. Por favor, inicie um novo jogo.");
            janelaAviso.show();
        }
    }

    private void mostrarCapitulo(Capitulo capitulo) {
        ObterDadosDeArquivo.serializadorDeCapitulo(capitulo);
        textoCapitulo.clear();
        textoConsequencia.clear();
        imagemCapituloImagem.setText("");

        try {
            CapituloImagem capituloImagem = (CapituloImagem) capitulo;
            imagemCapituloImagem.setText(capituloImagem.getImagem());
        } catch (Exception e) {
        }

        tituloCapitulo.setText(capitulo.getNome());
        if (capitulo.getFinalCap() != null) {
            textoCapitulo.setText(capitulo.getTexto() + "\n \n" + capitulo.getFinalCap());
        } else {
            textoCapitulo.setText(capitulo.getTexto() + "\n");
        }
        textoConsequencia.setText(capitulo.alterarVidaPersonagem(capitulo.getVidaConsequencia()));

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
        if (capitulo.getArrayEscolhas().get(0).getProximo() == null) {
            ObterDadosDeArquivo.deletarProgresso();
        }
    }

    @FXML
    private void criarNovoJogo(ActionEvent event) {
        if (!ObterDadosDeArquivo.verificaSave()) {
            Scanner continuar = new Scanner(System.in);
            HashMap<String, Personagem> personagens = ObterDadosDeArquivo.carregarPersonagem();
            HashMap<String, Capitulo> capitulos = ObterDadosDeArquivo.carregarCapitulo(personagens, continuar);
            Capitulo raiz = capitulos.get("CAPÍTULO 1 - O INÍCIO");
            mostrarCapitulo(raiz);
        } else {
            Alert janelaConfirmacao = new Alert(Alert.AlertType.CONFIRMATION);
            janelaConfirmacao.setTitle("Iniciar Novo Jogo");
            janelaConfirmacao.setHeaderText("Aviso");
            janelaConfirmacao.setContentText("Criar um novo jogo irá deletar qualquer progresso existente."
                    + " Deseja continuar?");
            Optional<ButtonType> resultado = janelaConfirmacao.showAndWait();

            if (resultado.isPresent()) {
                if (resultado.get() == ButtonType.OK) {
                    Scanner continuar = new Scanner(System.in);
                    HashMap<String, Personagem> personagens = ObterDadosDeArquivo.carregarPersonagem();
                    HashMap<String, Capitulo> capitulos = ObterDadosDeArquivo.carregarCapitulo(personagens, continuar);
                    Capitulo raiz = capitulos.get("CAPÍTULO 1 - O INÍCIO");
                    mostrarCapitulo(raiz);
                }
            }
        }
    }
}
