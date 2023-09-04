import java.io.Serializable;

public class Escolha implements Serializable{
    private String texto;
    private Capitulo proximo;

    public Escolha(String texto, Capitulo proximo) {
        this.texto = texto;
        this.proximo = proximo;
    }

    // Getters
    public String getTexto() {
        return this.texto;
    }

    public Capitulo getProximo() {
        return this.proximo;
    }
    public void setProximo(Capitulo proximo) {
        this.proximo = proximo;
    }
    public void setTexto(String texto) {
        this.texto = texto;
    }
}
