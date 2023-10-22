package SistemaContaBancaria.Class;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

public class ContaBancaria extends Titular {
    Scanner scanner = new Scanner(System.in);

    private Titular titular;
    private long numeroConta = 0;
    private Integer senhaConta;
    private double saldo = 0;
    private List<String> historicoTransacoes = new ArrayList<>();
    private static long numeroTotalContas = 1;
    private Integer totalAlteracaoSenha = 1;

    public ContaBancaria(Titular titular) {
        super(titular.getNome(), titular.getCpf(), titular.getEndereco());
        this.titular = titular;
        this.numeroConta = setNumeroConta();
        generateRandomPassword();
        numeroTotalContas++;
    }

    public Integer getSenhaConta() {
        return this.senhaConta;
    }

    public void setSenhaConta(Integer senhaConta) {
        this.senhaConta = senhaConta;
    }

    public Titular getTitular() {
        return this.titular;
    }

    public void setTitular(Titular titular) {
        this.titular = titular;
    }

    public long getNumeroConta() {
        return this.numeroConta;
    }

    private long setNumeroConta() {
        this.numeroConta = numeroTotalContas;
        return this.numeroConta;
    }

    public double getSaldo() {
        return this.saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public int getTotalAlteracaoSenha() {
        return this.totalAlteracaoSenha;
    }

    public void setTotalAlteracaoSenha() {
        this.totalAlteracaoSenha -= 1;
    }

    public List<String> getHistoricoTransacoes() {
        return this.historicoTransacoes;
    }

    public void imprimirHistoricoTransacoes() {
        if (getHistoricoTransacoes().isEmpty()) {
            System.out.println("Não há transações registradas.");
            return;
        }
        for (String transacao : getHistoricoTransacoes()) {
            System.out.println(transacao);
        }
    }

    public void depositar(double valorTransacao) {
        Calendar hoje = Calendar.getInstance();
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT);

        if (valorTransacao <= 0) {
            System.out.println("Não é possível realizar depósito.");
            return;

        }

        if (valorTransacao + this.saldo < 0) {
            System.out.println("Não é possível realizar depósito.");
            return;
        }

        double saldoAnterior = getSaldo();
        setSaldo(getSaldo() + valorTransacao);

        String deposito = String.format(
                "Depósito: R$ %.2f | Saldo anterior: R$ %.2f | Saldo posterior: R$ %.2f | Realizado em: %s \n-----------------------------------------------",
                valorTransacao, saldoAnterior, getSaldo(), dateFormat.format(hoje.getTime()));
        historicoTransacoes.add(deposito);
        System.out.println("Operação realizada com sucesso!");
    }

    public void sacar(double valorTransacao) {
        Calendar hoje = Calendar.getInstance();
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT);

        if (valorTransacao <= 0) {
            System.out.println("Não é possível realizar saque.");
            return;

        }

        if (this.saldo - valorTransacao < 0) {
            System.out.println("Saldo insuficiente.");
            return;
        }

        double saldoAnterior = this.saldo;
        this.saldo -= valorTransacao;

        String sacar = String.format(
                "Saque: R$ %.2f | Saldo anterior: R$ %.2f | Saldo posterior: R$ %.2f | Realizado em: %s \n-----------------------------------------------",
                valorTransacao, saldoAnterior, getSaldo(), dateFormat.format(hoje.getTime()));
        historicoTransacoes.add(sacar);
        System.out.println("Operação realizada com sucesso!");
    }

    public void transferirEntreContas(double valorTransacao, ContaBancaria contaReceptora) {
        if (validarTransferirEntreContas(valorTransacao, contaReceptora) == false) {
            return;
        }
        Calendar hoje = Calendar.getInstance();
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT);

        Double saldoAnterior = getSaldo();
        Double saldoAnteriorReceptor = contaReceptora.getSaldo();
        setSaldo(getSaldo() - valorTransacao);
        contaReceptora.setSaldo(contaReceptora.getSaldo() + valorTransacao);

        String transacao = String.format(
                "Transferência para: %s  |  Valor: R$ %.2f  |  Saldo anterior: R$ %.2f  |  Saldo posterior: R$ %.2f  |  Realizado em %s \n-----------------------------------------------",
                contaReceptora.getNome(), valorTransacao, saldoAnterior, getSaldo(), dateFormat.format(hoje.getTime()));
        getHistoricoTransacoes().add(transacao);

        String transacaoReceptora = String.format(
                "Transferência de: %s  |  Valor: R$ %.2f  |  Saldo anterior: R$ %.2f  |  Saldo posterior: R$ %.2f  |  Realizado em %s \n-----------------------------------------------",
                getNome(), valorTransacao, saldoAnteriorReceptor, contaReceptora.getSaldo(),
                dateFormat.format(hoje.getTime()));
        contaReceptora.getHistoricoTransacoes().add(transacaoReceptora);

        System.out.println("Transação realizada com sucesso!");
    }

    private boolean validarTransferirEntreContas(double valorTransacao, ContaBancaria contaReceptora) {
        if (contaReceptora.getNumeroConta() == getNumeroConta()) {
            System.out.println("Não é possível realizar transferência para a própria conta");
            return false;
        }

        if (valorTransacao == 0) {
            System.out.println("Não existe valor para ser transferido");
            return false;
        }

        if (valorTransacao < 0) {
            System.out.println("Saldo insuficiente.");
            return false;
        }

        if (getSaldo() - valorTransacao < 0) {
            System.out.println("Saldo insuficiente.");
            return false;
        }

        return true;
    }

    private int generateRandomPassword() {
        int numeroMaximoCaracteres = 6;
        String senha = "";

        int primeiroDigito = 1 + (int) (Math.random() * 9);
        senha += primeiroDigito;

        for (int i = 1; i < numeroMaximoCaracteres; i++) {
            int numero = (int) (Math.random() * 10);
            senha += numero;
        }

        setSenhaConta(Integer.parseInt(senha));
        return getSenhaConta();
    }

    @Override
    public String toString() {
        return "----------Dados do Titular----------" + super.toString()
                + "\n----------Dados da Conta----------\nNúmero da conta: " + this.numeroConta
                + "\nTrocas de senha disponíveis: " + getTotalAlteracaoSenha();
    }

    public Boolean verificaTotalAlteracaoSenha() {
        if (getTotalAlteracaoSenha() <= 0) {
            return false;
        }
        return true;
    }
}