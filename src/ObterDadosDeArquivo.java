import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class ObterDadosDeArquivo {

    public static HashMap<String, Personagem> carregarPersonagem() {
        HashMap<String, Personagem> personagens = new HashMap<String, Personagem>();
        File arquivo = new File(
                "rsc/origin/personagens.txt");
        try {
            Scanner scanner = new Scanner(arquivo, "UTF-8");
            String algumaLinha = "";

            while (scanner.hasNextLine()) {
                while (!algumaLinha.equals("PERSONAGEM")) {
                    algumaLinha = scanner.nextLine();
                }
                Personagem algumPersonagem = new Personagem("", 0);
                algumaLinha = scanner.nextLine();
                algumPersonagem.setNome(scanner.nextLine());
                algumaLinha = scanner.nextLine();
                algumPersonagem.setVida(Integer.parseInt(scanner.nextLine()));
                personagens.put(algumPersonagem.getNome(), algumPersonagem);
            }
            scanner.close();
        } catch (Exception e) {
            System.out.println("Erro ao carregar algum personagem.");
        }
        return personagens;
    }

    public static HashMap<String, Capitulo> carregarCapitulo(HashMap<String, Personagem> personagens,
            Scanner escaneadorDeEscolhas) {
        HashMap<String, Capitulo> capitulos = new HashMap<String, Capitulo>();
        File arquivo = new File(
                "rsc/origin/capitulos.txt");
        try {
            Scanner scanner = new Scanner(arquivo, "UTF-8");
            String algumaLinha = "";

            while (scanner.hasNextLine()) {
                while (!algumaLinha.equalsIgnoreCase("CAPÍTULO") &&
                        !algumaLinha.equalsIgnoreCase("CAPÍTULO COM IMAGEM") && !algumaLinha.equalsIgnoreCase("ESCOLHA")) {
                    algumaLinha = scanner.nextLine();
                }
                if (algumaLinha.equalsIgnoreCase("CAPÍTULO") || algumaLinha.equalsIgnoreCase("CAPÍTULO COM IMAGEM")) {
                    if (algumaLinha.equalsIgnoreCase("CAPÍTULO")) {
                        algumaLinha = scanner.nextLine();
                        algumaLinha = scanner.nextLine();
                        String idCapitulo = algumaLinha;
                        algumaLinha = scanner.nextLine();
                        capitulos.put(idCapitulo, new Capitulo(personagens, scanner, escaneadorDeEscolhas));
                    } else if (algumaLinha.equalsIgnoreCase("CAPÍTULO COM IMAGEM")) {
                         algumaLinha = scanner.nextLine();
                algumaLinha = scanner.nextLine();
                String idCapitulo = algumaLinha;
                algumaLinha = scanner.nextLine();
                        capitulos.put(idCapitulo, new CapituloImagem(personagens, scanner, escaneadorDeEscolhas));
                    }
                } else if (algumaLinha.equalsIgnoreCase("ESCOLHA")) {
                    String capituloDecisivo = "";
                    String opcao1 = "";
                    String capituloOpcao1 = "";
                    String opcao2 = "";
                    String capituloOpcao2 = "";
                    algumaLinha = scanner.nextLine(); // PULA CAPÍTULO DECISIVO
                    algumaLinha = scanner.nextLine();
                    capituloDecisivo = algumaLinha;
                    algumaLinha = scanner.nextLine(); // PULA OPÇÃO 1
                    algumaLinha = scanner.nextLine();
                    if (!algumaLinha.equalsIgnoreCase("null")) {
                        opcao1 = algumaLinha;
                    } else {
                        opcao1 = null;
                    }
                    algumaLinha = scanner.nextLine(); // PULA CAPÍTULO PRÓXIMO OPÇÃO 1
                    algumaLinha = scanner.nextLine();
                    if (!algumaLinha.equalsIgnoreCase("null")) {
                        capituloOpcao1 = algumaLinha;
                    } else {
                        capituloOpcao1 = null;
                    }
                    algumaLinha = scanner.nextLine(); // PULA OPÇÃO 2
                    algumaLinha = scanner.nextLine();
                    if (!algumaLinha.equalsIgnoreCase("null")) {
                        opcao2 = algumaLinha;
                    } else {
                        opcao2 = null;
                    }
                    algumaLinha = scanner.nextLine(); // PULA CAPÍTULO PRÓXIMO OPÇÃO 2
                    algumaLinha = scanner.nextLine();
                    if (!algumaLinha.equalsIgnoreCase("null")) {
                        capituloOpcao2 = algumaLinha;
                    } else {
                        capituloOpcao2 = null;
                    }
                    capitulos.get(capituloDecisivo).setArray(new ArrayList<>(Arrays.asList(
                            new Escolha(opcao1, capitulos.get(capituloOpcao1)),
                            new Escolha(opcao2, capitulos.get(capituloOpcao2)))));
                }
            }
            scanner.close();
        } catch (Exception e) {
            System.out.println("Erro ao carregar algum capítulo.");
        }
        return capitulos;
    }

    public void salvarProgresso() {
        try {
            File arquivoSalvo = new File("rsc/saves/capituloSave.txt");
            FileOutputStream fos = new FileOutputStream(arquivoSalvo);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(oos);
            oos.close();
        } catch (Exception e) {
            System.out.println("Erro ao salvar progresso.");
        }
    }

    public void carregarProgresso() {
        try {
            File arquivoSalvo = new File("rsc/saves/save.txt");
            FileInputStream fis = new FileInputStream(arquivoSalvo);
            ObjectInputStream ois = new ObjectInputStream(fis);
            ois.readObject();
            ois.close();
        } catch (Exception e) {
            System.out.println("Erro ao salvar progresso.");
        }
    }

    public static boolean verificaSave() {
        boolean retorno = false;
        try {
            File pasta = new File("rsc/saves");
            if (pasta.isDirectory()) {
                String qtdArquivosNaPasta[] = pasta.list();
                if (qtdArquivosNaPasta.length > 0) {
                    retorno = true;
                } else {
                    retorno = false;
                }
            }
        } catch (Exception e) {
            System.out.println("Erro na verificação de progressos existentes.");
        }
        return retorno;
    }

    public static void serializadorDeCapitulo(Capitulo capitulo) {
        try {
            File arquivo = new File("rsc/saves/capituloSave.txt");
            FileOutputStream fos = new FileOutputStream(arquivo);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(capitulo);
            oos.close();
        } catch (Exception e) {
            System.out.println("Erro ao serializar o capítulo.");
        }
    }

    public static Capitulo desserializadorDeCapitulo(String nomeArquivo) {
        Capitulo capituloSave = new Capitulo(null, null, new Personagem("", 0), 0, null, null, null);
        try {
            File arquivo = new File(nomeArquivo);
            FileInputStream fis = new FileInputStream(arquivo);
            ObjectInputStream ois = new ObjectInputStream(fis);
            capituloSave = (Capitulo) ois.readObject();
            ois.close();
        } catch (Exception e) {
            System.out.println("Erro ao carregar o progresso.");
        }
        return capituloSave;
    }

    public static void deletarProgresso() {
        try {
            File arquivo = new File("rsc/saves/capituloSave.txt");
            arquivo.delete();
        } catch (Exception e) {
            System.out.println("Erro ao deletar o arquivo \"capituloSave.txt\".");
        }
    }
}