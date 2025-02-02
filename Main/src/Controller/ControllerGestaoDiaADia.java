package Controller;

import Model.Financeiro;
import Model.Configuracoes;
import Model.Mesa;
import Model.Pedido;
import Model.Prato;
import Model.Reserva;

import java.util.Scanner;

public class ControllerGestaoDiaADia {
    private final ControllerPedido controllerPedido;
    private final ControllerMesa controllerMesa;
    private final ControllerPrato controllerPrato;
    private final ConfiguracoesController configuracoesController;
    private final Financeiro financeiro;
    private final Reserva[] reservas;
    private int unidadesTempoAtual;
    private final int unidadesTempoMaximo;
    private int tempoEspera;
    private double custoNaoAtendido;

    public ControllerGestaoDiaADia(ControllerPedido controllerPedido,
                                   ControllerMesa controllerMesa,
                                   ControllerPrato controllerPrato,
                                   int unidadesTempoMaximo,
                                   Reserva[] reservas) {
        this.controllerPedido = controllerPedido;
        this.controllerMesa = controllerMesa;
        this.controllerPrato = controllerPrato;
        this.configuracoesController = ConfiguracoesController.getInstancia();
        this.unidadesTempoAtual = 1;
        this.unidadesTempoMaximo = unidadesTempoMaximo;
        this.reservas = reservas;

        Configuracoes configuracoes = configuracoesController.getModelo();
        this.tempoEspera = configuracoes.getTempoEsperaAcao();
        this.custoNaoAtendido = configuracoes.getCustoClienteNaoAtendido();

        // Inicializa o financeiro com valores iniciais
        this.financeiro = new Financeiro(0.0, 0.0, 0, 0, 0.0);
    }

    public int getUnidadesTempoAtual() {
        return unidadesTempoAtual;
    }

    public void avancarTempo() {
        if (unidadesTempoAtual < unidadesTempoMaximo) {
            unidadesTempoAtual++;
            System.out.println("\nTempo avançado para unidade: " + unidadesTempoAtual);

            atualizarConsumos();
            verificarClientesNaoAtendidos();
        } else {
            System.out.println("O dia foi concluído.");
        }
    }

    public Pedido[] getPedidosQuePodemEscolherPratos() {
        int count = 0;

        //  Contar apenas os pedidos no estado "ESCOLHER_PRATOS"
        for (Pedido pedido : controllerPedido.getPedidos()) {
            if (pedido.getEstado().equals("ESCOLHER_PRATOS")) {
                count++;
            }
        }

        //  Criar um array com esses pedidos
        Pedido[] pedidosSelecionaveis = new Pedido[count];
        int index = 0;
        for (Pedido pedido : controllerPedido.getPedidos()) {
            if (pedido.getEstado().equals("ESCOLHER_PRATOS")) {
                pedidosSelecionaveis[index++] = pedido;
            }
        }

        return pedidosSelecionaveis;
    }


    private boolean foiEncaminhado(String nomeCliente) {
        for (Pedido pedido : controllerPedido.getPedidos()) {
            if (pedido.getCliente().equalsIgnoreCase(nomeCliente)) {
                return true; // Cliente já tem um pedido, logo foi encaminhado
            }
        }
        return false; // Cliente ainda não foi encaminhado
    }
    public Reserva[] getReservasNoTempoAtual() {
        int count = 0;

        // Contar apenas as reservas válidas
        for (Reserva reserva : reservas) {
            int tempoEntrada = reserva.getTempoEntrada();
            int tempoDesdeChegada = unidadesTempoAtual - tempoEntrada;

            // A reserva só aparece se:
            // - O cliente já chegou (tempoEntrada <= unidadesTempoAtual)
            // - O cliente ainda não foi encaminhado
            // - O tempo de espera ainda não foi ultrapassado
            if (tempoEntrada <= unidadesTempoAtual && !foiEncaminhado(reserva.getNomeReserva()) && tempoDesdeChegada <= tempoEspera) {
                count++;
            }
        }

        // Criar um array do tamanho correto
        Reserva[] reservasAtuais = new Reserva[count];
        int index = 0;

        // Preencher o array apenas com as reservas válidas
        for (Reserva reserva : reservas) {
            int tempoEntrada = reserva.getTempoEntrada();
            int tempoDesdeChegada = unidadesTempoAtual - tempoEntrada;

            if (tempoEntrada <= unidadesTempoAtual && !foiEncaminhado(reserva.getNomeReserva()) && tempoDesdeChegada <= tempoEspera) {
                reservasAtuais[index++] = reserva;
            }
        }

        return reservasAtuais; // Retorna apenas as reservas que ainda podem ser atendidas
    }


    public void verificarClientesNaoAtendidos() {
        for (Reserva reserva : reservas) {
            if (!foiEncaminhado(reserva.getNomeReserva()) &&
                    (unidadesTempoAtual - reserva.getTempoEntrada() > tempoEspera)) {
                financeiro.setPedidosNaoAtendidos(financeiro.getPedidosNaoAtendidos() + 1);
                financeiro.setSaldoRestaurante(financeiro.getSaldoRestaurante() - custoNaoAtendido);
                System.out.println("Cliente " + reserva.getNomeReserva() + " não foi atendido a tempo. Custo de " + custoNaoAtendido + "€ aplicado.");
            }
        }
    }

    public void encaminharPedido() {
        Scanner scanner = new Scanner(System.in);
        Reserva[] reservasAtuais = getReservasNoTempoAtual();

        System.out.println("\n=== Escolha o Tipo de Cliente ===");
        System.out.println("1. Cliente com reserva");
        System.out.println("2. Cliente espontâneo");
        System.out.print("Selecione uma opção: ");
        int tipoCliente = scanner.nextInt();
        scanner.nextLine();

        String cliente;
        int quantidadePessoas;
        int tempoEncaminhamento = unidadesTempoAtual; // O tempo real de encaminhamento é aqui

        if (tipoCliente == 1 && reservasAtuais.length > 0) {
            System.out.println("\n=== Clientes com Reserva Elegíveis ===");
            for (int i = 0; i < reservasAtuais.length; i++) {
                System.out.println((i + 1) + ". " + reservasAtuais[i].getNomeReserva() +
                        " - " + reservasAtuais[i].getQtdPessoas() + " pessoas (Chegada: Tempo " +
                        reservasAtuais[i].getTempoEntrada() + ")");
            }

            System.out.print("Selecione um cliente pelo número: ");
            int escolhaCliente = scanner.nextInt();
            scanner.nextLine();

            if (escolhaCliente < 1 || escolhaCliente > reservasAtuais.length) {
                System.out.println("Opção inválida.");
                return;
            }

            Reserva reservaEscolhida = reservasAtuais[escolhaCliente - 1];
            cliente = reservaEscolhida.getNomeReserva();
            quantidadePessoas = reservaEscolhida.getQtdPessoas();
        } else if (tipoCliente == 2) {
            System.out.print("Digite o nome do cliente: ");
            cliente = scanner.nextLine();

            System.out.print("Digite o número de pessoas: ");
            quantidadePessoas = scanner.nextInt();
            scanner.nextLine();
        } else {
            System.out.println("Opção inválida.");
            return;
        }

        System.out.println("\n=== Mesas Disponíveis ===");
        Mesa[] mesas = controllerMesa.getMesas();
        int[] mesasDisponiveis = new int[mesas.length];
        int count = 0;

        for (Mesa mesa : mesas) {
            if (!mesa.isOcupada() && mesa.getLugares() >= quantidadePessoas) {
                mesasDisponiveis[count] = mesa.getId();
                System.out.println((count + 1) + ". Mesa " + mesa.getId() +
                        " - Capacidade: " + mesa.getLugares() +
                        " - Estado: Disponível");
                count++;
            }
        }

        if (count == 0) {
            System.out.println("Nenhuma mesa disponível para acomodar " + quantidadePessoas + " pessoas.");
            return;
        }

        System.out.print("Selecione uma mesa pelo número: ");
        int escolhaMesa = scanner.nextInt();
        scanner.nextLine();

        if (escolhaMesa < 1 || escolhaMesa > count) {
            System.out.println("Opção inválida.");
            return;
        }

        Mesa mesaEscolhida = controllerMesa.encontrarMesaPorId(mesasDisponiveis[escolhaMesa - 1]);

        Pedido novoPedido = new Pedido(cliente, quantidadePessoas, tempoEncaminhamento); // 🔹 Salva o tempo real de encaminhamento
        novoPedido.setMesaId(mesaEscolhida.getId());
        controllerPedido.adicionarPedido(novoPedido);
        mesaEscolhida.setOcupada(true);

        System.out.println("Cliente " + cliente + " foi encaminhado para a mesa " + mesaEscolhida.getId());
    }

    private void atualizarConsumos() {
        for (Pedido pedido : controllerPedido.getPedidos()) {
            int tempoEncaminhamento = pedido.getTempoEntrada(); // 🔹 Momento real do encaminhamento
            int tempoEscolha = tempoEncaminhamento + 1; // Cliente pode escolher pratos na unidade seguinte
            int tempoInicioPreparacao = tempoEscolha + 1;
            int tempoFimPreparacao = tempoInicioPreparacao + pedido.getMaiorTempoPreparacao() - 1;
            int tempoInicioConsumo = tempoFimPreparacao + 1;
            int tempoFimConsumo = tempoInicioConsumo + pedido.getMaiorTempoConsumo() - 1;
            int tempoPagamento = tempoFimConsumo + 1;
            int tempoAtual = unidadesTempoAtual;

            // 🔹 Cliente pode escolher pratos se já tiver sido encaminhado
            if (pedido.getEstado().equals("ENCAMINHADO") && tempoAtual == tempoEscolha) {
                System.out.println("Agora o cliente " + pedido.getCliente() + " pode escolher os pratos.");
                pedido.setEstado("ESCOLHER_PRATOS");
            }

            // 🔹 Início da preparação
            if (pedido.getEstado().equals("ESCOLHER_PRATOS") && tempoAtual == tempoInicioPreparacao) {
                System.out.println("Os pratos do cliente " + pedido.getCliente() + " começaram a ser preparados.");
                pedido.setEstado("PREPARAR");
            }

            // 🔹 Fim da preparação
            if (pedido.getEstado().equals("PREPARAR") && tempoAtual == tempoFimPreparacao) {
                System.out.println("Os pratos do cliente " + pedido.getCliente() + " terminaram de ser preparados.");
                pedido.setEstado("PREPARADO");
            }

            // 🔹 Cliente começa a consumir
            if (pedido.getEstado().equals("PREPARADO") && tempoAtual == tempoInicioConsumo) {
                System.out.println("Cliente " + pedido.getCliente() + " começou a consumir.");
                pedido.setEstado("CONSUMIR");
            }

            // 🔹 Cliente termina o consumo
            if (pedido.getEstado().equals("CONSUMIR") && tempoAtual == tempoFimConsumo) {
                System.out.println("Cliente " + pedido.getCliente() + " terminou o consumo.");
                pedido.setEstado("FINALIZADO");
            }

            // 🔹 Cliente pode pagar
            if (pedido.getEstado().equals("FINALIZADO") && tempoAtual == tempoPagamento) {
                System.out.println("Cliente " + pedido.getCliente() + " poderá pagar na unidade de tempo " + tempoPagamento + ".");
            }
        }
    }

    private Prato[] obterPratosDisponiveis(Prato[] pratos, String categoria) {
        int count = 0;

        // Contar apenas pratos disponíveis
        for (Prato prato : pratos) {
            if (prato.getCategoria().equalsIgnoreCase(categoria) && prato.estaDisponivel()) {
                count++;
            }
        }

        // Criar um novo array apenas com pratos disponíveis
        Prato[] pratosDisponiveis = new Prato[count];
        int index = 0;
        for (Prato prato : pratos) {
            if (prato.getCategoria().equalsIgnoreCase(categoria) && prato.estaDisponivel()) {
                pratosDisponiveis[index++] = prato;
            }
        }

        return pratosDisponiveis;
    }

    public Pedido[] getPedidosEncaminhados() {
        int count = 0;

        // Contar quantos pedidos estão no estado correto para escolher pratos
        for (Pedido pedido : controllerPedido.getPedidos()) {
            if (pedido.getEstado().equalsIgnoreCase("ESCOLHER_PRATOS")) {
                count++;
            }
        }

        Pedido[] pedidosEncaminhados = new Pedido[count];
        int index = 0;

        // Preencher o array com os pedidos corretos
        for (Pedido pedido : controllerPedido.getPedidos()) {
            if (pedido.getEstado().equalsIgnoreCase("ESCOLHER_PRATOS")) {
                pedidosEncaminhados[index++] = pedido;
            }
        }

        return pedidosEncaminhados;
    }
    public void escolherPratos() {
        Pedido[] pedidosSelecionaveis = getPedidosQuePodemEscolherPratos();

        if (pedidosSelecionaveis.length == 0) {
            System.out.println("Nenhum cliente pode escolher pratos neste momento.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("\n=== Clientes que podem escolher pratos ===");

        for (int i = 0; i < pedidosSelecionaveis.length; i++) {
            System.out.println((i + 1) + ". " + pedidosSelecionaveis[i].getCliente() + " - " + pedidosSelecionaveis[i].getQtdPessoas() + " pessoas");
        }

        System.out.print("Selecione um cliente pelo número: ");
        int escolha = scanner.nextInt();
        scanner.nextLine();

        if (escolha < 1 || escolha > pedidosSelecionaveis.length) {
            System.out.println("Opção inválida.");
            return;
        }

        Pedido pedidoSelecionado = pedidosSelecionaveis[escolha - 1];
        Scanner input = new Scanner(System.in);

        Prato[] pratos = controllerPrato.carregarPratos();

        // **Escolher Entrada**
        Prato[] entradas = obterPratosDisponiveis(pratos, "Entrada");
        if (entradas.length > 0) {
            System.out.println("\n=== Escolha um prato de entrada ===");
            for (int i = 0; i < entradas.length; i++) {
                System.out.println((i + 1) + ". " + entradas[i].getNome() + " | Tempo de preparo: " + entradas[i].getTempPrep() + " min");
            }
            System.out.print("Selecione uma entrada pelo número (0 para nenhum): ");
            int opcao = input.nextInt();
            if (opcao > 0 && opcao <= entradas.length) {
                System.out.print("Quantidade: ");
                int qt = input.nextInt();
                pedidoSelecionado.setEntrada(entradas[opcao - 1], qt);
            }
        }

        // **Escolher Prato Principal**
        Prato[] principais = obterPratosDisponiveis(pratos, "Principal");
        if (principais.length > 0) {
            System.out.println("\n=== Escolha um prato principal ===");
            for (int i = 0; i < principais.length; i++) {
                System.out.println((i + 1) + ". " + principais[i].getNome() + " | Tempo de preparo: " + principais[i].getTempPrep() + " min");
            }
            System.out.print("Selecione um prato principal pelo número (0 para nenhum): ");
            int opcao = input.nextInt();
            if (opcao > 0 && opcao <= principais.length) {
                System.out.print("Quantidade: ");
                int qt = input.nextInt();
                pedidoSelecionado.setPrincipal(principais[opcao - 1], qt);
            }
        }

        // **Escolher Sobremesa**
        Prato[] sobremesas = obterPratosDisponiveis(pratos, "Sobremesa");
        if (sobremesas.length > 0) {
            System.out.println("\n=== Escolha uma sobremesa ===");
            for (int i = 0; i < sobremesas.length; i++) {
                System.out.println((i + 1) + ". " + sobremesas[i].getNome() + " | Tempo de preparo: " + sobremesas[i].getTempPrep() + " min");
            }
            System.out.print("Selecione uma sobremesa pelo número (0 para nenhuma): ");
            int opcao = input.nextInt();
            if (opcao > 0 && opcao <= sobremesas.length) {
                System.out.print("Quantidade: ");
                int qt = input.nextInt();
                pedidoSelecionado.setSobremesa(sobremesas[opcao - 1], qt);
            }
        }

        // **Atualiza Estado do Pedido**
        pedidoSelecionado.setEstado("PREPARAR");
        System.out.println("Pedido atualizado! O cliente " + pedidoSelecionado.getCliente() + " terá os pratos preparados.");
    }


    /*Ppublic Pedido encontrarPedidoPorNome(String cliente) {
        return controllerPedido.encontrarPedidoPorNome(cliente);
    }*/

    public void processarPagamentos() {
        Scanner scanner = new Scanner(System.in);
        boolean encontrouPagamentos = false;

        for (Pedido pedido : controllerPedido.getPedidos()) {
            int tempoFimConsumo = pedido.getTempoEntrada() + pedido.getMaiorTempoPreparacao() + pedido.getMaiorTempoConsumo() - 1;
            int tempoPagamento = tempoFimConsumo + 1;

            if (pedido.getEstado().equals("FINALIZADO") && unidadesTempoAtual >= tempoPagamento) {
                encontrouPagamentos = true;
                double custo = pedido.calcularPrecoCusto();
                double total = pedido.calcularPrecoTotal();
                double lucro = total - custo;

                financeiro.setTotalFaturado(financeiro.getTotalFaturado() + total);
                financeiro.setTotalGastos(financeiro.getTotalGastos() + custo);
                financeiro.setPedidosAtendidos(financeiro.getPedidosAtendidos() + 1);
                financeiro.setSaldoRestaurante(financeiro.getSaldoRestaurante() + lucro);

                System.out.println("\n=== Pagamento para " + pedido.getCliente() + " ===");
                System.out.println("Preço de Custo: " + custo);
                System.out.println("Preço Total: " + total);
                System.out.println("Lucro: " + lucro);
                System.out.print("Confirmar pagamento? (S/N): ");

                String resposta = scanner.nextLine().trim();
                if (resposta.equalsIgnoreCase("S")) {
                    pedido.setEstado("PAGO");
                    Mesa mesa = controllerMesa.encontrarMesaPorId(pedido.getMesaId());

                    if (mesa != null) {
                        mesa.setOcupada(false);
                        System.out.println("Mesa " + mesa.getId() + " agora está disponível.");
                    }

                    controllerPedido.gerarLog("Pagamento confirmado: Cliente=" + pedido.getCliente() + ", Total=" + total);
                    System.out.println("Pagamento realizado com sucesso!");
                } else {
                    System.out.println("Pagamento cancelado. O cliente ainda precisa pagar.");
                }
            }
        }

        if (!encontrouPagamentos) {
            System.out.println("Nenhum cliente está pronto para pagar no momento.");
        }
    }

    public void exibirDesempenhoFinanceiro() {
        System.out.println("\n=== Relatório Financeiro ===");
        System.out.println("Total faturado: " + financeiro.getTotalFaturado() + "€");
        System.out.println("Total de gastos: " + financeiro.getTotalGastos() + "€");
        System.out.println("Lucro: " + (financeiro.getTotalFaturado() - financeiro.getTotalGastos()) + "€");
        System.out.println("Pedidos atendidos: " + financeiro.getPedidosAtendidos());
        System.out.println("Pedidos não atendidos: " + financeiro.getPedidosNaoAtendidos());
        System.out.println("Saldo do Restaurante: " + financeiro.getSaldoRestaurante() + "€");
    }
}

