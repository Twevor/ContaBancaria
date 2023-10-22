package SistemaContaBancaria;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import SistemaContaBancaria.Class.ContaBancaria;
import SistemaContaBancaria.Class.Titular;
import SistemaContaBancaria.Class.Utils;

public class App {
    static Scanner scanner = new Scanner(System.in);
    static List<ContaBancaria> contasBancarias = new ArrayList<>();
    static Utils utils = new Utils();
    static ContaBancaria contaSelecionada;

    public static void main(String[] args) {
        janelaAbertura();
    }

    private static void janelaAbertura() {
        utils.limpar();
        System.out.println("-----------------------------------------------");
        System.out.println("Olá, bem vindo ao Sistema de Gerenciamento de Contas Bancárias.");
        System.out.println("Você já possui uma conta cadastrada?");
        Integer possuiContaCadastrada = utils.getUserInteger(scanner, "1. Sim  |  2. Não  | 3. Sair  | ");
        scanner.nextLine();
        switch (possuiContaCadastrada) {
            case 1:
                telaLogin();
                break;
            case 2:
                criarConta();
                break;
            case 3:
                System.out.println("Obrigado por utilizar nossos serviços!");
                confirmaFecharInicio();
            default:
                System.out.println("Digite uma opção válida!");
                janelaAbertura();
                break;
        }

        scanner.close();
    }

    private static void janelaPrincipal() {
        utils.limpar();
        System.out.println("Número da Conta: " + contaSelecionada.getNumeroConta());
        System.out.println("------------O que gostaria de ver?-------------");
        System.out.println("1. Saldo");
        System.out.println("2. Depositar");
        System.out.println("3. Sacar");
        System.out.println("4. Transferir");
        System.out.println("5. Histórico de transações");
        System.out.println("6. Verificar dados da conta e titular");
        System.out.println("7. Alterar senha");
        System.out.println("8. Sair da conta");
        System.out.println("9. Fechar aplicativo");
        Integer escolhaJanelaPrincipal = utils.getUserInteger(scanner, "");
        scanner.nextLine();
        switch (escolhaJanelaPrincipal) {
            case 1:
                utils.limpar();
                verSaldo();
                break;
            case 2:
                depositar();
                break;
            case 3:
                sacar();
                break;
            case 4:
                transferir();
                break;
            case 5:
                historicoTransacoes();
                break;
            case 6:
                verDadosTitular();
                break;
            case 7:
                janelaAlterarSenha();
                break;
            case 8:
                confirmaSair();
                break;
            case 9:
                confirmaFechar();
                ;
                break;
            default:
                break;
        }
    }

    private static void criarConta() {
        utils.limpar();
        System.out.println("---------------Criação de Conta----------------");
        System.out.print("Informe o nome do(a) titular: ");
        String nomeTitular = scanner.nextLine();

        System.out.print("Informe o CPF do(a) titular: ");
        String cpfTitular = scanner.nextLine();

        System.out.print("Informe o endereço do(a) titualr: ");
        String enderecoTitular = scanner.nextLine();

        Titular novoTitular = new Titular(nomeTitular, cpfTitular, enderecoTitular);
        ContaBancaria conta = new ContaBancaria(novoTitular);
        contasBancarias.add(conta);
        System.out.println("-----------------------------------------------");
        System.out.println("----------Cadastrado(a) com sucesso!----------");
        System.out.println("-----------------------------------------------");
        System.out.println("----------------Dados da conta----------------");
        System.out.println("Número da conta: " + conta.getNumeroConta());
        System.out.println("Senha: " + conta.getSenhaConta());
        System.out.println("-----------------------------------------------");
        utils.pressToContinue();
        janelaAbertura();
    }

    private static void telaLogin() {
        utils.limpar();
        System.out.println("-----------------------------------------------");
        Integer numeroConta = utils.getUserInteger(scanner, "Informe o número da conta: ");
        Integer senhaConta = utils.getUserInteger(scanner, "Informe a senha da conta: ");
        scanner.nextLine();
        Boolean verificaConta = verificaConta(numeroConta, senhaConta);
        if (!verificaConta) {
            System.out.println("Conta não cadastrada!");
            System.out.println("-----------------------------------------------");
            utils.pressToContinue();
            janelaAbertura();
        } else {
            janelaPrincipal();
        }

    }

    private static Boolean verificaConta(Integer numeroConta, Integer senhaConta) {
        if (contasBancarias.isEmpty()) {
            return false;
        }

        for (ContaBancaria conta : contasBancarias) {
            if (conta.getNumeroConta() == numeroConta && conta.getSenhaConta().equals(senhaConta)) {
                contaSelecionada = conta;
                return true;
            }
        }
        return false;
    }

    private static void confirmaFecharInicio() {
        utils.limpar();
        System.out.println("Tem certeza que gostaria de sair?");
        Integer confirmaFecharInicio = utils.getUserInteger(scanner, "1. Sim  |  2. Não  |  ");
        switch (confirmaFecharInicio) {
            case 1:
                utils.limpar();
                contaSelecionada = null;
                System.out.println("Obrigado por utilizar nossos serviços!");
                System.exit(0);
                break;
            default:
                janelaAbertura();
                break;
        }
    }

    private static void confirmaFechar() {
        utils.limpar();
        System.out.println("Tem certeza que gostaria de sair?");
        Integer confirmaFechar = utils.getUserInteger(scanner, "1. Sim  |  2. Não  |  ");
        switch (confirmaFechar) {
            case 1:
                utils.limpar();
                contaSelecionada = null;
                System.out.println("Obrigado por utilizar nossos serviços!");
                System.exit(0);
                break;
            default:
                janelaPrincipal();
                break;
        }
    }

    private static void confirmaSair() {
        utils.limpar();
        System.out.println("Tem certeza que gostaria de sair da conta?");
        Integer confirmaSair = utils.getUserInteger(scanner, "1. Sim  |  2. Não  |  ");
        switch (confirmaSair) {
            case 1:
                contaSelecionada = null;
                janelaAbertura();
                break;
            case 2:
                janelaPrincipal();
                break;
            default:
                System.out.println("Opção inválida");
                utils.pressToContinue();
                confirmaSair();
                break;
        }
    }

    private static void verSaldo() {
        System.out.printf("Saldo: R$ %.2f\n", contaSelecionada.getSaldo());
        utils.pressToContinue();

        janelaPrincipal();
    }

    private static void depositar() {
        utils.limpar();
        Double deposito = utils.getUserDouble(scanner, "Informe o valor do depósito: ");
        contaSelecionada.depositar(deposito);
        verSaldo();
    }

    private static void sacar() {
        utils.limpar();
        if (contaSelecionada.getSaldo() <= 0) {
            System.out.println("Saldo insuficiente! ");
            verSaldo();

        }
        System.out.printf("Saldo: %.2f\n", contaSelecionada.getSaldo());
        Double saque = utils.getUserDouble(scanner, "Informe o valor do saque: ");
        utils.limpar();
        contaSelecionada.sacar(saque);
        verSaldo();
    }

    private static void transferir() {
        utils.limpar();
        ContaBancaria contaReceptora = null;
        System.out.printf("Número da conta: %d |  Saldo: %.2f\n", contaSelecionada.getNumeroConta(),
                contaSelecionada.getSaldo());
        Integer receptora = utils.getUserInteger(scanner,
                "Informe o número da conta que irá receber a transferência: ");

        for (ContaBancaria contaBancaria : contasBancarias) {
            if (contaBancaria.getNumeroConta() == receptora) {
                contaReceptora = contaBancaria;
                break;
            }
        }

        if (contaReceptora == null) {
            System.out.println("Conta não localizada!");
            utils.pressToContinue();
            janelaPrincipal();
        }

        Double tranferencia = utils.getUserDouble(scanner, "Informe o valor da transferência: ");
        utils.limpar();
        contaSelecionada.transferirEntreContas(tranferencia, contaReceptora);
        verSaldo();
        utils.pressToContinue();
        janelaPrincipal();
    }

    private static void historicoTransacoes() {
        utils.limpar();
        contaSelecionada.imprimirHistoricoTransacoes();
        utils.pressToContinue();
        janelaPrincipal();
    }

    private static void verDadosTitular() {
        utils.limpar();
        System.out.println(contaSelecionada.toString());
        utils.pressToContinue();
        janelaPrincipal();
    }

    private static void janelaAlterarSenha() {
        utils.limpar();

        if (!contaSelecionada.verificaTotalAlteracaoSenha()) {
            System.out.println("Limite máximo de alteração de senha atingido!");
            System.out.println("Para realizar esse procedimento, visite sua agência");
            utils.pressToContinue();
            janelaPrincipal();
        }

        Integer confirmaSenha = utils.getUserInteger(scanner, "Confirme sua senha: ");
        if (!contaSelecionada.getSenhaConta().equals(confirmaSenha)) {
            System.out.println("Senha incorreta!");
            utils.pressToContinue();
            janelaPrincipal();
        }

        utils.limpar();

        System.out.println("Atenção!");
        System.out.println("A senha que você estará alterando também servirá como base para entrar no aplicativo.");
        System.out.println("A alteraçao da mesma só pode ser feita apenas UMA vez via aplicativo.");
        System.out.println("Deseja continuar?");
        Integer alterarSenha = utils.getUserInteger(scanner, "1. Sim  |  2. Não  |  ");
        switch (alterarSenha) {
            case 1:
                alterarSenha();
                break;
            case 2:
                janelaPrincipal();
                break;
            default:
                System.out.println("Opção inválida");
                janelaAlterarSenha();
                break;
        }

        utils.pressToContinue();
        janelaPrincipal();
    }

    private static void alterarSenha() {
        utils.limpar();
        System.out.println("A senha deverá ser composta apenas por números.");
        System.out.println("A senha deverá possuir um total de 6 dígitos.");

        Integer novaSenha = utils.getUserInteger(scanner, "Digite a nova senha: ");

        String verificaSenha = Integer.toString(novaSenha);
        if (verificaSenha.length() > 6) {
            verificaSenha = "";
            System.out.println("Senha ultrapassa limite máximo de números. Tente novamente!");
            utils.pressToContinue();
            alterarSenha();
        }

        if (verificaSenha.length() < 6) {
            verificaSenha = "";
            System.out.println("Senha muito curta. Tente novamente!");
            utils.pressToContinue();
            alterarSenha();
        }

        contaSelecionada.setSenhaConta(novaSenha);
        contaSelecionada.setTotalAlteracaoSenha();
        System.out.println("Senha alterada com sucesso!");
        utils.pressToContinue();
        janelaPrincipal();
    }
}