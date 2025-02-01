package Controller;

import Model.Mesa;
import Model.Pedido;
import Model.Prato;
import Model.Reserva;

import java.util.Scanner;

public class ControllerGestaoDiaADia {
    private final ControllerPedido controllerPedido;
    private final ControllerMesa controllerMesa;
    private final ControllerPrato controllerPrato;
    private final Reserva[] reservas; // Adicionar reservas como membro
    private int unidadesTempoAtual;
    private final int unidadesTempoMaximo;

    public ControllerGestaoDiaADia(ControllerPedido controllerPedido, ControllerMesa controllerMesa, ControllerPrato controllerPrato, int unidadesTempoMaximo, Reserva[] reservas) {
        this.controllerPedido = controllerPedido;
        this.controllerMesa = controllerMesa;
        this.controllerPrato = controllerPrato;
        this.unidadesTempoAtual = 1;
        this.unidadesTempoMaximo = unidadesTempoMaximo;
        this.reservas = reservas; // Inicializa reservas

    }

    public int getUnidadesTempoAtual() {
        return unidadesTempoAtual;
    }

    public void avancarTempo() {
        if (unidadesTempoAtual < unidadesTempoMaximo) {
            unidadesTempoAtual++;
            System.out.println("\nTempo avançado para unidade: " + unidadesTempoAtual);
            atualizarConsumos();
        } else {
            System.out.println("O dia foi concluído.");
        }
    }

    public Reserva[] getReservasNoTempoAtual() {
        int count = 0;

        // Contar quantas reservas correspondem ao tempo atual
        for (Reserva reserva : reservas) {
            if (reserva.getTempoEntrada() == unidadesTempoAtual) {
                count++;
            }
        }

        // Criar um array para armazenar as reservas do tempo atual
        Reserva[] reservasAtuais = new Reserva[count];
        int index = 0;
        for (Reserva reserva : reservas) {
            if (reserva.getTempoEntrada() == unidadesTempoAtual) {
                reservasAtuais[index++] = reserva;
            }
        }

        return reservasAtuais;
    }

    public Pedido[] getPedidosQuePodemEscolherPratos() {
        int count = 0;

        // ✅ **Contar pedidos que podem escolher pratos**
        for (Pedido pedido : controllerPedido.getPedidos()) {
            if (pedido.getEstado().equals("ESCOLHER_PRATOS")) {
                count++;
            }
        }

        // ✅ **Criar um array apenas com esses pedidos**
        Pedido[] pedidosSelecionaveis = new Pedido[count];
        int index = 0;
        for (Pedido pedido : controllerPedido.getPedidos()) {
            if (pedido.getEstado().equals("ESCOLHER_PRATOS")) {
                pedidosSelecionaveis[index++] = pedido;
            }
        }

        return pedidosSelecionaveis;
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
        int tempoEntrada = unidadesTempoAtual; // Tempo de chegada sempre na unidade de tempo atual

        if (tipoCliente == 1 && reservasAtuais.length > 0) {
            // Cliente com reserva
            System.out.println("\n=== Clientes com Reserva no Tempo Atual ===");
            for (int i = 0; i < reservasAtuais.length; i++) {
                System.out.println((i + 1) + ". " + reservasAtuais[i].getNomeReserva() +
                        " - " + reservasAtuais[i].getQtdPessoas() + " pessoas");
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
            tempoEntrada = reservaEscolhida.getTempoEntrada(); // Pegamos o tempo de chegada da reserva
        } else if (tipoCliente == 2) {
            // Cliente espontâneo
            System.out.print("Digite o nome do cliente: ");
            cliente = scanner.nextLine();

            System.out.print("Digite o número de pessoas: ");
            quantidadePessoas = scanner.nextInt();
            scanner.nextLine();
        } else {
            System.out.println("Opção inválida.");
            return;
        }

        // Exibir mesas disponíveis
        System.out.println("\n=== Mesas Disponíveis ===");
        Mesa[] mesas = controllerMesa.getMesas();
        int[] mesasDisponiveis = new int[mesas.length];
        int count = 0;

        for (Mesa mesa : mesas) {
            if (!mesa.isOcupada() && mesa.getLugares() >= quantidadePessoas) { // Verifica se a mesa tem capacidade suficiente
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

        // Escolher mesa pelo número
        System.out.print("Selecione uma mesa pelo número: ");
        int escolhaMesa = scanner.nextInt();
        scanner.nextLine();

        if (escolhaMesa < 1 || escolhaMesa > count) {
            System.out.println("Opção inválida.");
            return;
        }

        Mesa mesaEscolhida = controllerMesa.encontrarMesaPorId(mesasDisponiveis[escolhaMesa - 1]);

        // Criar e alocar pedido com a quantidade de pessoas correta
        Pedido novoPedido = new Pedido(cliente, quantidadePessoas, tempoEntrada);
        novoPedido.setMesaId(mesaEscolhida.getId());
        controllerPedido.adicionarPedido(novoPedido);
        mesaEscolhida.setOcupada(true);

        System.out.println("Cliente " + cliente + " foi encaminhado para a mesa " + mesaEscolhida.getId());
    }


    private void atualizarConsumos() {
        for (Pedido pedido : controllerPedido.getPedidos()) {
            int tempoEntrada = pedido.getTempoEntrada(); // Momento em que o cliente chegou
            int tempoEscolha = tempoEntrada + 1; // Cliente escolhe pratos na unidade seguinte
            int tempoInicioPreparacao = tempoEscolha + 1; // Preparação começa após escolha
            int tempoFimPreparacao = tempoInicioPreparacao + pedido.getMaiorTempoPreparacao() - 1; // Quando a preparação termina
            int tempoInicioConsumo = tempoFimPreparacao + 1; // Cliente começa a consumir logo após a preparação
            int tempoFimConsumo = tempoInicioConsumo + pedido.getMaiorTempoConsumo() - 1; // Quando o cliente termina de consumir
            int tempoPagamento = tempoFimConsumo + 1; // Cliente pode pagar no tempo seguinte ao consumo

            int tempoAtual = unidadesTempoAtual;

            // Cliente pode escolher pratos no tempo seguinte ao encaminhamento
            if (pedido.getEstado().equals("ENCAMINHADO") && tempoAtual == tempoEscolha) {
                System.out.println("Agora o cliente " + pedido.getCliente() + " pode escolher os pratos.");
                pedido.setEstado("ESCOLHER_PRATOS");
            }

            // Início da preparação dos pratos
            if (pedido.getEstado().equals("ESCOLHER_PRATOS") && tempoAtual == tempoInicioPreparacao) {
                System.out.println("Os pratos do cliente " + pedido.getCliente() + " começaram a ser preparados.");
                pedido.setEstado("PREPARAR");
            }

            // Fim da preparação dos pratos
            if (pedido.getEstado().equals("PREPARAR") && tempoAtual == tempoFimPreparacao) {
                System.out.println("Os pratos do cliente " + pedido.getCliente() + " terminaram de ser preparados.");
                pedido.setEstado("PREPARADO");
            }

            // Cliente começa a consumir na unidade seguinte ao fim da preparação
            if (pedido.getEstado().equals("PREPARADO") && tempoAtual == tempoInicioConsumo) {
                System.out.println("Cliente " + pedido.getCliente() + " começou a consumir.");
                pedido.setEstado("CONSUMIR");
            }

            // Cliente termina o consumo no tempo correto
            if (pedido.getEstado().equals("CONSUMIR") && tempoAtual == tempoFimConsumo) {
                System.out.println("Cliente " + pedido.getCliente() + " terminou o consumo.");
                pedido.setEstado("FINALIZADO");
            }

            // Cliente pode pagar na unidade seguinte ao fim do consumo
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


    public void escolherPratos(Pedido pedido) {
        Scanner scanner = new Scanner(System.in);
        Prato[] pratos = controllerPrato.carregarPratos();

        pedido.setEstado("PREPARAR");

        //  **Entrada**
        Prato[] entradas = obterPratosDisponiveis(pratos, "Entrada");
        if (entradas.length > 0) {
            System.out.println("\n=== Escolha um prato de entrada ===");
            for (int i = 0; i < entradas.length; i++) {
                System.out.println((i + 1) + ". " + entradas[i].getNome() +
                        " | Custo: " + entradas[i].getPC() + "€" +
                        " | Venda: " + entradas[i].getPV() + "€" +
                        " | Prep: " + entradas[i].getTempPrep() + " min" +
                        " | Consumo: " + entradas[i].getTempCons() + " min");
            }
            System.out.print("Selecione um prato de entrada pelo número (0 para nenhum): ");
            int escolha = scanner.nextInt();
            scanner.nextLine();
            if (escolha > 0 && escolha <= entradas.length) {
                System.out.print("Quantidade: ");
                int qt = scanner.nextInt();
                scanner.nextLine();
                pedido.setEntrada(entradas[escolha - 1], qt);
            }
        } else {
            System.out.println("Nenhum prato de entrada disponível.");
        }

        // **Prato Principal**
        Prato[] principais = obterPratosDisponiveis(pratos, "Principal");
        if (principais.length > 0) {
            System.out.println("\n=== Escolha um prato principal ===");
            for (int i = 0; i < principais.length; i++) {
                System.out.println((i + 1) + ". " + principais[i].getNome() +
                        " | Custo: " + principais[i].getPC() + "€" +
                        " | Venda: " + principais[i].getPV() + "€" +
                        " | Prep: " + principais[i].getTempPrep() + " min" +
                        " | Consumo: " + principais[i].getTempCons() + " min");
            }
            System.out.print("Selecione um prato principal pelo número (0 para nenhum): ");
            int escolha = scanner.nextInt();
            scanner.nextLine();
            if (escolha > 0 && escolha <= principais.length) {
                System.out.print("Quantidade: ");
                int qt = scanner.nextInt();
                scanner.nextLine();
                pedido.setPrincipal(principais[escolha - 1], qt);
            }
        } else {
            System.out.println("Nenhum prato principal disponível.");
        }

        // **Sobremesa**
        Prato[] sobremesas = obterPratosDisponiveis(pratos, "Sobremesa");
        if (sobremesas.length > 0) {
            System.out.println("\n=== Escolha uma sobremesa ===");
            for (int i = 0; i < sobremesas.length; i++) {
                System.out.println((i + 1) + ". " + sobremesas[i].getNome() +
                        " | Custo: " + sobremesas[i].getPC() + "€" +
                        " | Venda: " + sobremesas[i].getPV() + "€" +
                        " | Prep: " + sobremesas[i].getTempPrep() + " min" +
                        " | Consumo: " + sobremesas[i].getTempCons() + " min");
            }
            System.out.print("Selecione uma sobremesa pelo número (0 para nenhuma): ");
            int escolha = scanner.nextInt();
            scanner.nextLine();
            if (escolha > 0 && escolha <= sobremesas.length) {
                System.out.print("Quantidade: ");
                int qt = scanner.nextInt();
                scanner.nextLine();
                pedido.setSobremesa(sobremesas[escolha - 1], qt);
            }
        } else {
            System.out.println("Nenhuma sobremesa disponível.");
        }
    }


    /*Ppublic Pedido encontrarPedidoPorNome(String cliente) {
        return controllerPedido.encontrarPedidoPorNome(cliente);
    }*/

    public void processarPagamentos() {
        Scanner scanner = new Scanner(System.in);

        for (Pedido pedido : controllerPedido.getPedidos()) {
            int tempoEntrada = pedido.getTempoEntrada(); // Quando o cliente chegou
            int tempoEscolha = tempoEntrada + 1; // Cliente escolhe pratos na unidade seguinte
            int tempoInicioPreparacao = tempoEscolha + 1; // Preparação começa após escolha
            int tempoFimPreparacao = tempoInicioPreparacao + pedido.getMaiorTempoPreparacao() - 1; // Quando a preparação termina
            int tempoInicioConsumo = tempoFimPreparacao + 1; // Cliente começa a consumir
            int tempoFimConsumo = tempoInicioConsumo + pedido.getMaiorTempoConsumo() - 1; // Cliente termina o consumo
            int tempoPagamento = tempoFimConsumo + 1; // Cliente pode pagar **na unidade seguinte ao consumo**

            if (pedido.isFinalizado() && unidadesTempoAtual == tempoPagamento) {
                double custo = pedido.calcularPrecoCusto();
                double total = pedido.calcularPrecoTotal();
                double lucro = total - custo;

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
            } else if (pedido.isFinalizado() && unidadesTempoAtual < tempoPagamento) {
                System.out.println("O cliente " + pedido.getCliente() + " ainda não pode pagar. Ele só poderá pagar na unidade de tempo " + tempoPagamento + ".");
            }
        }
    }
}