import java.util.Scanner;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Capitulo implements Serializable {
    private String nome;
    private String texto;
    private Personagem personagem;
    private int vida;
    private Personagem personagemConsequencia;
    private int vidaConsequencia;
    private ArrayList<Escolha> arrayEscolhas;
    private String finalCap;
    private transient Scanner scanner;

    public Capitulo(String nome, String texto, Personagem personagem, int vida, String finalCap,
            Scanner scanner) {
        this.nome = nome;
        this.texto = texto;
        this.arrayEscolhas = new ArrayList<Escolha>();
        this.personagem = personagem;
        this.vida = vida;
        this.finalCap = finalCap;
        this.scanner = scanner;
    }

    public Capitulo(HashMap<String, Personagem> personagens, Scanner scannerDeArquivo, Scanner scannerDoConsole) {
        this.lerCapitulo(personagens, scannerDeArquivo);
        this.scanner = scannerDoConsole;
    }

    // Getters
    public String getNome() {
        return this.nome;
    }

    public String getTexto() {
        return this.texto;
    }

    public ArrayList<Escolha> getArrayEscolhas() {
        return this.arrayEscolhas;
    }

    public Personagem getPersonagem() {
        return this.personagem;
    }

    public int getVida() {
        return this.vida;
    }

    public String getFinalCap() {
        return this.finalCap;
    }

    private Scanner getScanner() {
        return this.scanner;
    }

    public Personagem getPersonagemConsequencia() {
        return personagemConsequencia;
    }

    public int getVidaConsequencia() {
        return vidaConsequencia;
    }

    // Setters
    public void setArray(ArrayList<Escolha> escolhas) {
        this.arrayEscolhas = escolhas;
    }

    public void setFinalCap(String finalCap) {
        this.finalCap = finalCap;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPersonagem(Personagem personagem) {
        this.personagem = personagem;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    public void setArrayEscolhas(ArrayList<Escolha> arrayEscolhas) {
        this.arrayEscolhas = arrayEscolhas;
    }

    public void setPersonagemConsequencia(Personagem personagemConsequencia) {
        this.personagemConsequencia = personagemConsequencia;
    }

    public void setVidaConsequencia(int vidaConsequencia) {
        this.vidaConsequencia = vidaConsequencia;
    }

    // Métodos

    protected void mostrar() {
        System.out.println(getNome());
        getScanner().nextLine();
        System.out.println(getTexto());
    }

    private int escolher(Scanner continuar) {
        boolean escolhaValida = false;
        int escolhido = -1;
        if (getArrayEscolhas().get(0).getTexto() != null) {
            System.out.print("Digite aqui:");
            String digitado = continuar.nextLine();
            while (!escolhaValida) {

                for (int i = 0; i < getArrayEscolhas().size(); i++) {
                    if (digitado.equalsIgnoreCase(getArrayEscolhas().get(i).getTexto())) {
                        escolhido = i;
                        escolhaValida = true;
                        break;
                    }
                }
                if (!escolhaValida) {
                    System.out.println("Certifique-se se digitou corretamente.");
                    System.out.print("Digite novamente aqui:");
                    digitado = continuar.nextLine();
                }
            }
        }
        return escolhido;
    }

    public int executar(Scanner continuar, HashMap<String, Capitulo> capitulos) {
        ObterDadosDeArquivo.serializadorDeCapitulo(capitulos.get(getNome()));
        this.mostrar();
        /*
         * if (getConsequencia() != null) {
         * System.out.println(getConsequencia());
         * }
         */
        int escolhido;
        if (getFinalCap() != null) {
            escolhido = -1;
            System.out.println(getFinalCap());
            ObterDadosDeArquivo.deletarProgresso();
            System.exit(0);
        } else {
            escolhido = this.escolher(continuar);
            if (escolhido >= 0 && escolhido < getArrayEscolhas().size()) {
                Capitulo proximoCapitulo = getArrayEscolhas().get(escolhido).getProximo();
                if (proximoCapitulo.getArrayEscolhas().get(escolhido).getTexto() == null) {
                    Capitulo escolhaAutomatica = proximoCapitulo.getArrayEscolhas().get(0).getProximo();
                    proximoCapitulo.executar(continuar, capitulos);
                    escolhaAutomatica.mostrar();
                    if (escolhaAutomatica.arrayEscolhas.get(0).getProximo() == null) {
                        System.out.println(getPersonagem().getMensagemAtk(getPersonagem(), getVida()));
                        System.out.println(escolhaAutomatica.getFinalCap());
                        ObterDadosDeArquivo.deletarProgresso();
                    }
                } else {
                    proximoCapitulo.executar(continuar, capitulos);
                }
            }
        }
        return escolhido;
    }

    public void lerCapitulo(HashMap<String, Personagem> personagens,
            Scanner escaneadorDoArquivo) {
        String linhaLida = "";
        linhaLida = escaneadorDoArquivo.nextLine();
        if (!(linhaLida).equalsIgnoreCase("null")) {
            this.nome = linhaLida;
            linhaLida = escaneadorDoArquivo.nextLine(); // PULA TEXTO
        } else {
            this.setNome(null);
            linhaLida = escaneadorDoArquivo.nextLine(); // PULA TEXTO
        }
        linhaLida = escaneadorDoArquivo.nextLine();
        this.texto = linhaLida;
        linhaLida = escaneadorDoArquivo.nextLine(); // PULA PERSONAGEM
        linhaLida = escaneadorDoArquivo.nextLine();
        this.personagem = personagens.get(linhaLida);
        linhaLida = escaneadorDoArquivo.nextLine(); // PULA VIDA
        linhaLida = escaneadorDoArquivo.nextLine();
        this.vida = personagens.get(linhaLida).getVida();
        linhaLida = escaneadorDoArquivo.nextLine(); // PULA CONSEQUÊNCIA
        linhaLida = escaneadorDoArquivo.nextLine();
        if (!linhaLida.equalsIgnoreCase("null")) {
            this.personagemConsequencia = personagens.get(linhaLida);
            linhaLida = escaneadorDoArquivo.nextLine();
        } else {
            this.personagemConsequencia = null;
            linhaLida = escaneadorDoArquivo.nextLine();
        }
        if (!linhaLida.equalsIgnoreCase("null")) {
            this.vidaConsequencia = Integer.parseInt(linhaLida);
            linhaLida = escaneadorDoArquivo.nextLine();
        } else {
            this.vidaConsequencia = Integer.parseInt(linhaLida);
            linhaLida = escaneadorDoArquivo.nextLine();
        }
        linhaLida = escaneadorDoArquivo.nextLine(); // PULA FINAL DO CAPÍTULO
        if (!linhaLida.equalsIgnoreCase("null")) {
            this.finalCap = linhaLida;
        } else {
            this.setFinalCap(null);
        }
    }

    public String alterarVidaPersonagem(int moduloDoAtaque) {
        if (getVidaConsequencia() == 0) {
            return null;
        } else {
            return getPersonagemConsequencia().ataque(moduloDoAtaque);
        }
    }

}
