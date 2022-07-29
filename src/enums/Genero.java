package enums;

/**
 * Created by Amanda on 30/11/2016.
 */
public enum Genero {
    MASCULINO("Masculino", "M"), FEMININO("Feminino", "F");

    private String descricao;
    private String letra;

    Genero(String descricao, String letra) {
        this.descricao = descricao;
        this.letra = letra;
    }

    public static Genero getGenero(String letra){
        if(letra.equals("M"))
            return MASCULINO;
        else
            return FEMININO;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getLetra() {
        return letra;
    }

    @Override
    public String toString() {
        return this.descricao;
    }
}
