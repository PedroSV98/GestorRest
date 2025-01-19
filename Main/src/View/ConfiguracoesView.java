package View;

import Controller.ConfiguracoesController;
import Controller.LoginController;
import Model.Configuracoes;
import Model.LoginModel;

import java.io.File;
import java.util.Scanner;


public class ConfiguracoesView {

    private ConfiguracoesController controller;

    private LoginController loginController;

    public ConfiguracoesView(ConfiguracoesController controller, LoginController loginController) {

        this.controller = controller;
        this.loginController = loginController;
    }

    public void exibirMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean sair = false;

        while (!sair) {
            System.out.println("\n=== Configurações ===");
            System.out.println("1. Ver Configurações Atuais");
            System.out.println("2. Atualizar Configuração");
            System.out.println("3. Guardar Configurações");
            System.out.println("4. Voltar ao Menu Principal");
            System.out.println("5. Identificar Separador do Ficheiro");


            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha

            switch (opcao) {
                case 1:
                    mostrarConfiguracoes();
                    break;
                case 2:
                    atualizarConfiguracao(scanner);
                    break;
                case 3:
                    controller.guardar();
                    break;
                case 4:
                    sair = true;
                    break;
                case 5:
                    boolean voltar = false;
                    while (!voltar) {
                        System.out.println("\nEscolha uma das opções para o caminho do ficheiro:");
                        System.out.println("0. Voltar");
                        System.out.println("1. Usar o caminho já inserido (" + controller.getModelo().getCaminhoFicheiros() + ")");
                        System.out.println("2. Inserir novo caminho");

                        System.out.print("Opção: ");
                        int opcaoCaminho = scanner.nextInt();
                        scanner.nextLine(); // Consumir a quebra de linha

                        switch (opcaoCaminho) {
                            case 0:
                                voltar = true;
                                break;
                            case 1:
                                String caminhoAtual = controller.getModelo().getCaminhoFicheiros();
                                if (caminhoAtual != null && !caminhoAtual.isEmpty()) {

                                    // Solicitar ao usuário para escolher um arquivo
                                    System.out.println("Escolha o número do arquivo para identificar o separador:");
                                    File diretorioAtual = new File(caminhoAtual);
                                    String[] ficheiros = diretorioAtual.list();
                                    if (ficheiros != null && ficheiros.length > 0) {
                                        for (int i = 0; i < ficheiros.length; i++) {
                                            System.out.println((i + 1) + ". " + ficheiros[i]);  // Listando os arquivos com números
                                        }

                                        System.out.print("Número do arquivo: ");
                                        int numeroArquivo = scanner.nextInt();
                                        scanner.nextLine(); // Consumir a quebra de linha

                                        // Verificar se a escolha é válida
                                        if (numeroArquivo > 0 && numeroArquivo <= ficheiros.length) {
                                            String arquivoEscolhido = caminhoAtual + "\\" + ficheiros[numeroArquivo - 1];
                                            File arquivoSelecionado = new File(arquivoEscolhido);

                                            if (arquivoSelecionado.isFile()) {
                                                String separadorIdentificado1 = controller.identificarSeparador(arquivoSelecionado.getPath());
                                                if (separadorIdentificado1 != null) {
                                                    System.out.println("Separador identificado: " + separadorIdentificado1);
                                                    controller.atualizarSeparador(separadorIdentificado1); // Atualiza o separador no modelo
                                                } else {
                                                    System.out.println("Não foi possível identificar o separador no caminho atual.");
                                                }
                                            } else {
                                                System.out.println("O arquivo selecionado não é válido.");
                                            }
                                        } else {
                                            System.out.println("Número inválido. Tente novamente.");
                                        }
                                    } else {
                                        System.out.println("O diretório está vazio.");
                                    }
                                } else {
                                    System.out.println("Nenhum caminho foi configurado anteriormente.");
                                }
                                voltar = true;
                                break;

                            case 2:
                                System.out.print("Insira o novo caminho do ficheiro: ");
                                String novoCaminho = scanner.nextLine();
                                File novoCaminhoArquivo = new File(novoCaminho);
                                if (novoCaminhoArquivo.isDirectory()) {

                                    // Solicitar ao usuário para escolher um arquivo
                                    System.out.println("Escolha o número do arquivo para identificar o separador:");
                                    String[] ficheiros = novoCaminhoArquivo.list();
                                    if (ficheiros != null && ficheiros.length > 0) {
                                        for (int i = 0; i < ficheiros.length; i++) {
                                            System.out.println((i + 1) + ". " + ficheiros[i]);  // Listando os arquivos com números
                                        }

                                        System.out.print("Número do arquivo: ");
                                        int numeroArquivo = scanner.nextInt();
                                        scanner.nextLine(); // Consumir a quebra de linha

                                        // Verificar se a escolha é válida
                                        if (numeroArquivo > 0 && numeroArquivo <= ficheiros.length) {
                                            String arquivoEscolhido = novoCaminho + "\\" + ficheiros[numeroArquivo - 1];
                                            File arquivoSelecionado = new File(arquivoEscolhido);

                                            if (arquivoSelecionado.isFile()) {
                                                String separadorIdentificado2 = controller.identificarSeparador(arquivoSelecionado.getPath());
                                                if (separadorIdentificado2 != null) {
                                                    System.out.println("Separador identificado: " + separadorIdentificado2);
                                                    controller.atualizarSeparador(separadorIdentificado2);
                                                    controller.atualizarCaminhoFicheiros(novoCaminho); // Atualiza o caminho no modelo
                                                } else {
                                                    System.out.println("Não foi possível identificar o separador no novo caminho.");
                                                }
                                            } else {
                                                System.out.println("O arquivo selecionado não é válido.");
                                            }
                                        } else {
                                            System.out.println("Número inválido. Tente novamente.");
                                        }
                                    } else {
                                        System.out.println("O diretório está vazio.");
                                    }
                                } else if (novoCaminhoArquivo.isFile()) {
                                    String separadorIdentificado2 = controller.identificarSeparador(novoCaminho);
                                    if (separadorIdentificado2 != null) {
                                        System.out.println("Separador identificado: " + separadorIdentificado2);
                                        controller.atualizarSeparador(separadorIdentificado2);
                                        controller.atualizarCaminhoFicheiros(novoCaminho); // Atualiza o caminho no modelo
                                    } else {
                                        System.out.println("Não foi possível identificar o separador no novo caminho.");
                                    }
                                } else {
                                    System.out.println("O caminho especificado não é válido.");
                                }
                                voltar = true;
                                break;


                            default:
                                System.out.println("Opção inválida. Tente novamente.");
                        }
                    }
                    break;


                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private void mostrarConfiguracoes() {
        Configuracoes conf = controller.getModelo();
        System.out.println("\n=== Configurações Atuais ===");
        System.out.println("CaminhoFicheiros: " + conf.getCaminhoFicheiros());
        System.out.println("Separador: " + conf.getSeparador());
        System.out.println("UnidadesTempoDia: " + conf.getUnidadesTempoDia());
        System.out.println("TempoEsperaAcao: " + conf.getTempoEsperaAcao());
        System.out.println("CustoClienteNaoAtendido: " + conf.getCustoClienteNaoAtendido());
        System.out.println("Password: ********");
    }

    private void atualizarConfiguracao(Scanner scanner) {
        Configuracoes conf = controller.getModelo();
        System.out.println("\nCampos disponíveis para atualizar:");
        System.out.println("1. CaminhoFicheiros");
        System.out.println("2. Separador");
        System.out.println("3. UnidadesTempoDia");
        System.out.println("4. TempoEsperaAcao");
        System.out.println("5. CustoClienteNaoAtendido");
        System.out.println("6. Password");
        System.out.println("0. Voltar");

        System.out.print("\nQual o campo que deseja alterar? ");
        String campo = scanner.nextLine();

        switch (campo) {
            case "1":
                System.out.println("Caminho do Ficheiro Atual: " + conf.getCaminhoFicheiro());
                System.out.print("Novo Caminho: ");
                controller.atualizarCaminhoFicheiros(scanner.nextLine());
                break;
            case "2":
                System.out.println("Separador Atual: " + conf.getSeparador());
                System.out.print("Novo Separador: ");
                controller.atualizarSeparador(scanner.nextLine());
                break;
            case "3":
                System.out.println("Unidades de Tempo Atuais: " + conf.getUnidadesTempoDia());
                System.out.print("Novo valor (int): ");
                controller.atualizarUnidadesTempoDia(scanner.nextInt());
                scanner.nextLine();
                break;
            case "4":
                System.out.println("Unidades de Tempo Ação Atuais: " + conf.getTempoEsperaAcao());
                System.out.print("Novo valor (int): ");
                controller.atualizarTempoEsperaAcao(scanner.nextInt());
                scanner.nextLine();
                break;
            case "5":
                System.out.println("Custo Cliente Não Atendido Atual: " + conf.getCustoClienteNaoAtendido());
                System.out.print("Novo valor (double): ");
                controller.atualizarCustoClienteNaoAtendido(scanner.nextDouble());
                scanner.nextLine();
                break;
            case "6":
                LoginModel loginModel = new LoginModel(controller.getModelo()); // Passando o modelo corretamente
                LoginView loginView = new LoginView(); // Criando a view de login
                LoginController loginController = new LoginController(loginModel, loginView);
                loginController.alterarSenha(scanner);
                break;
            case "0":
                System.out.println("A Voltar ao Menu Configurações...");
                exibirMenu();
                break;

            default:
                System.out.println("Campo inválido. Verifique a lista acima.");
                break;
        }
    }

    private void listarFicheirosNoDiretorio(String caminhoDiretorio) {
        File diretorio = new File(caminhoDiretorio);

        if (diretorio.exists() && diretorio.isDirectory()) {
            System.out.println("\nFicheiros no diretório: " + caminhoDiretorio);
            String[] ficheiros = diretorio.list();
            if (ficheiros != null && ficheiros.length > 0) {
                for (String ficheiro : ficheiros) {
                    System.out.println("- " + ficheiro);
                }
            } else {
                System.out.println("O diretório está vazio.");
            }
        } else if (!diretorio.exists()) {
            System.out.println("O caminho especificado não existe.");
        } else if (!diretorio.canRead()) {
            System.out.println("Não há permissões para acessar este diretório.");
        } else {
            System.out.println("O caminho especificado não é um diretório válido.");
        }
    }


}

