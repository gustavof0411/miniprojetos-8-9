import java.util.HashMap;
import java.util.Scanner;

public class CapituloImagem extends Capitulo {
    String imagem;

    public CapituloImagem(HashMap<String, Personagem> personagens, Scanner scannerDeArquivo, Scanner scannerDoConsole) {
        super(personagens, scannerDeArquivo, scannerDoConsole);
        String parteDaImagem = "";
        parteDaImagem = scannerDeArquivo.nextLine(); // IMAGEM
        parteDaImagem = scannerDeArquivo.nextLine();
        while (!parteDaImagem.equals("FIM DA IMAGEM")) {
            this.imagem = imagem + "\n" + parteDaImagem;
            parteDaImagem = scannerDeArquivo.nextLine();
        }
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    protected void mostrar() {
        System.out.println(getImagem());
        System.out.println();
        super.mostrar();
    }
}
