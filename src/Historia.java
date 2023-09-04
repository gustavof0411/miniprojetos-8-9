
//import java.util.Scanner;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
//import java.util.HashMap;

public class Historia extends Application {
        public static void main(String[] args) throws Exception {
                launch(args);
                /*
                 * Scanner continuar = new Scanner(System.in);
                 * HashMap<String, Personagem> personagens =
                 * ObterDadosDeArquivo.carregarPersonagem();
                 * HashMap<String, Capitulo> capitulos =
                 * ObterDadosDeArquivo.carregarCapitulo(personagens, continuar);
                 * Capitulo inicial;
                 * 
                 * if (ObterDadosDeArquivo.verificaSave()) {
                 * System.out.
                 * println("Foi detectado um progresso já existente. Deseja carregar o progresso?"
                 * );
                 * System.out.println("CARREGAR - DIGITE \"s\"");
                 * System.out.println("INICIAR NOVO JOGO - DIGITE \"n\"");
                 * System.out.print("Digite aqui:");
                 * String querCarregar = continuar.nextLine();
                 * while (!querCarregar.equalsIgnoreCase("s") &&
                 * !querCarregar.equalsIgnoreCase("n")) {
                 * System.out.println("Certifique-se se digitou corretamente.");
                 * System.out.print("Digite novamente aqui:");
                 * querCarregar = continuar.nextLine();
                 * }
                 * if (querCarregar.equalsIgnoreCase("s")) {
                 * Capitulo capituloSave = ObterDadosDeArquivo
                 * .desserializadorDeCapitulo("rsc/saves/capituloSave.txt");
                 * inicial = capitulos.get(capituloSave.getNome());
                 * inicial.executar(continuar, capitulos);
                 * } else {
                 * inicial = capitulos.get("[CAPÍTULO 1 - O INÍCIO]");
                 * inicial.executar(continuar, capitulos);
                 * }
                 * } else {
                 * inicial = capitulos.get("[CAPÍTULO 1 - O INÍCIO]");
                 * inicial.executar(continuar, capitulos);
                 * 
                 * }
                 */
        }

        @Override
        public void start(Stage primaryStage) throws Exception {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("InterfaceGrafica.fxml"));
                Parent root = fxmlLoader.load();
                Scene tela = new Scene(root);

                primaryStage.setTitle("Miniprojeto - Programação 2");
                primaryStage.setScene(tela);
                primaryStage.show();

        }
}