import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Historia extends Application {
        public static void main(String[] args) throws Exception {
                launch(args);
        }

        @Override
        public void start(Stage primaryStage) throws Exception {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("InterfaceGrafica.fxml"));
                Parent root = fxmlLoader.load();
                Scene tela = new Scene(root);

                primaryStage.setTitle("Miniprojeto - Programação 2");
                primaryStage.setScene(tela);
                primaryStage.setResizable(false);
                primaryStage.show();

        }
}