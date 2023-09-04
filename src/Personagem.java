import java.io.Serializable;

public class Personagem implements Serializable {
    private String nome;
    private int vida;

    public Personagem(String nome, int vida) {
        this.nome = nome;
        this.vida = vida;
    }

    // Getters
    public String getNome() {
        return nome;
    }

    public int getVida() {
        return this.vida;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public String getMensagemAtk(Personagem personagem, int moduloDoAtaque) {
        personagem.setVida(personagem.getVida() - moduloDoAtaque);
        return personagem.getNome() + " perde " + moduloDoAtaque + " pontos de vida!\n" +
                personagem.getNome() + " agora possui " + personagem.getVida()
                + " pontos de vida.\n";

    }
}