package SistemaContaBancaria.Class;

public class Titular {
    private static Integer contagemTitular = 1;
    private String nome;
    private String cpf;
    private String endereco;

    public Titular(String nome, String cpf, String endereco) {
        this.nome = nome;
        this.cpf = cpf;
        this.endereco = endereco;
        contagemTitular += 1;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return this.cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEndereco() {
        return this.endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    @Override
    public String toString() {
        return "\nNome: " + this.nome + "\nCPF: " + this.cpf + "\nEndereço: " + this.endereco;
    }

}