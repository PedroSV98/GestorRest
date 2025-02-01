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


    public void encaminharPedido(String cliente, int quantidadePessoas) {
        Reserva[] reservasAtuais = getReservasNoTempoAtual(); // Busca as reservas no tempo atual
        boolean clienteEncontrado = false;

        // Verifica se o cliente está nas reservas
        for (Reserva reserva : reservasAtuais) {
            if (reserva.getNomeReserva().trim().equalsIgnoreCase(cliente.trim())) {
                clienteEncontrado = true;

                // Exibir mesas disponíveis para o usuário escolher
                System.out.println("\n=== Mesas Disponíveis ===");
                for (Mesa mesa : controllerMesa.getMesas()) {
                    System.out.println("Mesa ID: " + mesa.getId() +
                            ", Capacidade: " + mesa.getLugares() +
                            ", Ocupada: " + (mesa.isOcupada() ? "Sim" : "Não"));
                }

                // Solicitar ID da mesa
                System.out.print("Digite o ID da mesa para o cliente " + cliente + ": ");
                Scanner scanner = new Scanner(System.in);
                int mesaId = scanner.nextInt();
                scanner.nextLine(); // Consumir quebra de linha

                // Validar se a mesa está disponível
                Mesa mesaEscolhida = controllerMesa.encontrarMesaPorId(mesaId);
                if (mesaEscolhida == null) {
                    System.out.println("ID de mesa inválido.");
                    return;
                }

                if (mesaEscolhida.isOcupada()) {
                    System.out.println("A mesa " + mesaId + " já está ocupada.");
                    return;
                }

                if (mesaEscolhida.getLugares() < quantidadePessoas) {
                    System.out.println("A mesa " + mesaId + " não tem capacidade suficiente.");
                    return;
                }

                // Criar e alocar o pedido
                Pedido novoPedido = new Pedido(
                        reserva.getNomeReserva(),
                        reserva.getQtdPessoas(),
                        getUnidadesTempoAtual()
                );
                novoPedido.setMesaId(mesaEscolhida.getId());
                controllerPedido.adicionarPedido(novoPedido);
                mesaEscolhida.setOcupada(true); // Marca a mesa como ocupada

                System.out.println("Cliente " + cliente + " foi encaminhado para a mesa " + mesaEscolhida.getId());
                return;
            }
        }

        // Lidar com clientes espontâneos
        if (!clienteEncontrado) {
            System.out.println("Cliente não encontrado nas reservas. É um cliente espontâneo? (S/N): ");
            Scanner scanner = new Scanner(System.in);
            String resposta = scanner.nextLine();

            if (resposta.equalsIgnoreCase("S")) {
                // Exibir mesas disponíveis para escolha manual
                System.out.println("\n=== Mesas Disponíveis ===");
                for (Mesa mesa : controllerMesa.getMesas()) {
                    System.out.println("Mesa ID: " + mesa.getId() +
                            ", Capacidade: " + mesa.getLugares() +
                            ", Ocupada: " + (mesa.isOcupada() ? "Sim" : "Não"));
                }

                // Solicitar ID da mesa
                System.out.print("Digite o ID da mesa para o cliente " + cliente + ": ");
                int mesaId = scanner.nextInt();
                scanner.nextLine(); // Consumir quebra de linha

                // Validar a mesa
                Mesa mesaEscolhida = controllerMesa.encontrarMesaPorId(mesaId);
                if (mesaEscolhida == null) {
                    System.out.println("ID de mesa inválido.");
                    return;
                }

                if (mesaEscolhida.isOcupada()) {
                    System.out.println("A mesa " + mesaId + " já está ocupada.");
                    return;
                }

                if (mesaEscolhida.getLugares() < quantidadePessoas) {
                    System.out.println("A mesa " + mesaId + " não tem capacidade suficiente.");
                    return;
                }

                // Criar e alocar o pedido
                Pedido novoPedido = new Pedido(cliente, quantidadePessoas, getUnidadesTempoAtual());
                novoPedido.setMesaId(mesaEscolhida.getId());
                controllerPedido.adicionarPedido(novoPedido);
                mesaEscolhida.setOcupada(true); // Marca a mesa como ocupada

                System.out.println("Cliente espontâneo " + cliente + " foi encaminhado para a mesa " + mesaEscolhida.getId());
            } else {
                System.out.println("Encaminhamento cancelado.");
            }
        }
    }


    private void atualizarConsumos() {
        for (Pedido pedido : controllerPedido.getPedidos()) {
            int tempoEncaminhamento = pedido.getTempoInicioPreparacao(); // Tempo em que o cliente foi encaminhado
            int tempoEscolha = tempoEncaminhamento + 1; // Cliente escolhe pratos na próxima unidade de tempo
            int tempoInicioPreparacao = tempoEscolha + 1 ; // Preparação inicia após a escolha
            int tempoFimPreparacao = tempoInicioPreparacao + pedido.getMaiorTempoPreparacao(); // Termina após preparação
            int tempoInicioConsumo = tempoFimPreparacao ; // Consumo inicia na unidade seguinte
            int tempoFimConsumo = tempoInicioConsumo + pedido.getMaiorTempoConsumo(); // Termina após consumo
            int tempoPagamento = tempoFimConsumo; // Cliente pode pagar após o fim do consumo

            int tempoAtual = unidadesTempoAtual - tempoEncaminhamento; // Calcula o tempo decorrido desde o encaminhamento

            // Cliente escolhe pratos dinamicamente na unidade seguinte ao encaminhamento
            if (pedido.isEncaminhado() && unidadesTempoAtual == tempoEscolha) {
                System.out.println("Agora o cliente " + pedido.getCliente() + " pode escolher os pratos.");
                escolherPratos(pedido);
            }

            // Notifica início da preparação
            if (unidadesTempoAtual == tempoInicioPreparacao) {
                System.out.println("Os pratos do cliente " + pedido.getCliente() + " começaram a ser preparados.");
                pedido.setEstado("PREPARAR");
            }

            // Cliente começa a consumir
            if (unidadesTempoAtual == tempoInicioConsumo) {
                pedido.setEstado("CONSUMIR");
                System.out.println("Cliente " + pedido.getCliente() + " começou a consumir.");
            }

            // Cliente termina o consumo e pode pagar
            if (unidadesTempoAtual == tempoFimConsumo) {
                pedido.setEstado("FINALIZADO");
                System.out.println("Cliente " + pedido.getCliente() + " terminou o consumo e pode pagar.");
            }
        }
    }





    public void escolherPratos(Pedido pedido) {
        Scanner scanner = new Scanner(System.in);
        Prato[] pratos = controllerPrato.carregarPratos();

        pedido.setEstado("PREPARAR");

        // Entrada
        System.out.println("\n=== Pratos de Entrada Disponíveis ===");
        controllerPrato.listarPratosPorCategoria(pratos, "Entrada");
        System.out.print("Deseja adicionar um prato de entrada? (S/N): ");
        if (scanner.nextLine().equalsIgnoreCase("S")) {
            System.out.print("Digite o nome do prato de entrada: ");
            String entrada = scanner.nextLine();
            Prato pratoEntrada = controllerPrato.validarPrato(entrada, "Entrada", pratos);
            if (pratoEntrada != null) {
                System.out.print("Quantidade: ");
                int qtEntrada = scanner.nextInt();
                scanner.nextLine();
                pedido.setEntrada(pratoEntrada, qtEntrada);
            }
        }

        // Principal
        System.out.println("\n=== Pratos Principais Disponíveis ===");
        controllerPrato.listarPratosPorCategoria(pratos, "Principal");
        System.out.print("Deseja adicionar um prato principal? (S/N): ");
        if (scanner.nextLine().equalsIgnoreCase("S")) {
            System.out.print("Digite o nome do prato principal: ");
            String principal = scanner.nextLine();
            Prato pratoPrincipal = controllerPrato.validarPrato(principal, "Principal", pratos);
            if (pratoPrincipal != null) {
                System.out.print("Quantidade: ");
                int qtPrincipal = scanner.nextInt();
                scanner.nextLine();
                pedido.setPrincipal(pratoPrincipal, qtPrincipal);
            }
        }

        // Sobremesa
        System.out.println("\n=== Sobremesas Disponíveis ===");
        controllerPrato.listarPratosPorCategoria(pratos, "Sobremesa");
        System.out.print("Deseja adicionar uma sobremesa? (S/N): ");
        if (scanner.nextLine().equalsIgnoreCase("S")) {
            System.out.print("Digite o nome da sobremesa: ");
            String sobremesa = scanner.nextLine();
            Prato pratoSobremesa = controllerPrato.validarPrato(sobremesa, "Sobremesa", pratos);
            if (pratoSobremesa != null) {
                System.out.print("Quantidade: ");
                int qtSobremesa = scanner.nextInt();
                scanner.nextLine();
                pedido.setSobremesa(pratoSobremesa, qtSobremesa);
            }
        }
    }

    public Pedido encontrarPedidoPorNome(String cliente) {
        return controllerPedido.encontrarPedidoPorNome(cliente);
    }

    public void processarPagamentos() {
        Scanner scanner = new Scanner(System.in);

        for (Pedido pedido : controllerPedido.getPedidos()) {
            // Apenas clientes que já finalizaram o consumo podem pagar
            if (pedido.isFinalizado()) {
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
                    // Cliente pagou, então podemos liberar a mesa
                    pedido.setEstado("PAGO");
                    Mesa mesa = controllerMesa.encontrarMesaPorId(pedido.getMesaId());

                    if (mesa != null) {
                        mesa.setOcupada(false);
                        System.out.println("Mesa " + mesa.getId() + " agora está disponível.");
                    }

                    // Registrar pagamento no log
                    controllerPedido.gerarLog("Pagamento confirmado: Cliente=" + pedido.getCliente() + ", Total=" + total);
                    System.out.println("Pagamento realizado com sucesso!");
                } else {
                    System.out.println("Pagamento cancelado. O cliente ainda precisa pagar.");
                }
            }
        }
    }
}