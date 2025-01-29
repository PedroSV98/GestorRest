package Controller;

import Model.Configuracoes;
import Model.Prato;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class ControllerPrato {

    private ConfiguracoesController configController;
    private String caminhoCompletoPratos;

    public ControllerPrato(ConfiguracoesController configController) {
        this.configController = configController;
        String basePath = configController.getModelo().getCaminhoFicheiros();
        this.caminhoCompletoPratos = basePath + "Pratos.txt";
    }

    // Carrega todos os pratos do ficheiro
    public Prato[] carregarPratos() {
        String separador = configController.getModelo().getSeparador();
        int numLinhasValidas = 0;
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(caminhoCompletoPratos),
                        StandardCharsets.UTF_8
                )
        )) {
            String linha;
            while ((linha = br.readLine()) != null) {
                linha = linha.trim();
                if (!linha.isEmpty() && linha.split(separador).length >= 7) {
                    numLinhasValidas++;
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o ficheiro de pratos (contagem): " + e.getMessage());
            return new Prato[0];
        }

        Prato[] pratos = new Prato[numLinhasValidas];
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(caminhoCompletoPratos),
                        StandardCharsets.UTF_8
                )
        )) {
            String linha;
            int idx = 0;
            while ((linha = br.readLine()) != null) {
                linha = linha.trim();
                if (linha.isEmpty()) continue;

                String[] dados = linha.split(separador);
                if (dados.length < 7) continue;

                String nome = dados[0];
                String categoria = dados[1];
                double PC = Double.parseDouble(dados[2]);
                double PV = Double.parseDouble(dados[3]);
                int tempPrep = Integer.parseInt(dados[4]);
                int tempCons = Integer.parseInt(dados[5]);
                boolean estado = Boolean.parseBoolean(dados[6]);

                pratos[idx++] = new Prato(nome, categoria, PC, PV, tempPrep, tempCons, estado);
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o ficheiro de pratos (preenchimento): " + e.getMessage());
            return new Prato[0];
        }

        return pratos;
    }

    // Criar um novo prato
    public Prato[] criarPrato(Prato[] pratos, String nome, String categoria, double PC, double PV, int tempPrep, int tempCons, boolean estado) {
        Prato novoPrato = new Prato(nome, categoria, PC, PV, tempPrep, tempCons, estado);
        Prato[] novosPratos = Arrays.copyOf(pratos, pratos.length + 1);
        novosPratos[novosPratos.length - 1] = novoPrato;
        System.out.println("Prato " + nome + " criado.");
        return novosPratos;
    }

    // Atualizar (editar) os dados de um prato
    public void atualizarPrato(Prato[] pratos, String nome, String categoria, double PC, double PV, int tempPrep, int tempCons, boolean estado) {
        Prato prato = encontrarPratoPorNome(pratos, nome);
        if (prato != null) {
            prato.setCategoria(categoria);
            prato.setPC(PC);
            prato.setPV(PV);
            prato.setTempPrep(tempPrep);
            prato.setTempCons(tempCons);
            prato.setEstado(estado);
            System.out.println("Prato " + nome + " atualizado.");
        } else {
            System.out.println("Prato " + nome + " não encontrado.");
        }
    }


    // Eliminar (remover) um prato pelo nome
    public Prato[] eliminarPrato(Prato[] pratos, String nome) {
        int index = -1;

        for (int i = 0; i < pratos.length; i++) {
            if (pratos[i].getNome().equals(nome)) {
                index = i;
                break;
            }
        }

        if (index != -1) {
            Prato[] pratosAtualizados = new Prato[pratos.length - 1];
            System.arraycopy(pratos, 0, pratosAtualizados, 0, index);
            System.arraycopy(pratos, index + 1, pratosAtualizados, index, pratos.length - index - 1);
            System.out.println("Prato " + nome + " eliminado.");
            return pratosAtualizados;
        } else {
            System.out.println("Prato " + nome + " não encontrado.");
            return pratos;
        }
    }


    // Lista pratos disponíveis por categoria
    public void listarPratosPorCategoria(Prato[] pratos, String categoria) {
        System.out.println("\n==== Pratos Disponíveis na Categoria: " + categoria + " ====");
        boolean encontrou = false;
        for (Prato prato : pratos) {
            if (prato.getCategoria().equalsIgnoreCase(categoria) && prato.isEstado()) {
                System.out.println("Nome: " + prato.getNome()
                        + ", Preço: " + prato.getPV()
                        + ", Tempo de Preparação: " + prato.getTempPrep()
                        + ", Tempo de Consumo: " + prato.getTempCons());
                encontrou = true;
            }
        }
        if (!encontrou) {
            System.out.println("Não há pratos disponíveis nesta categoria.");
        }
    }

    // Valida um prato selecionado pelo cliente
    public Prato validarPrato(String nome, String categoria, Prato[] pratos) {
        for (Prato prato : pratos) {
            if (prato.getNome().equalsIgnoreCase(nome)
                    && prato.getCategoria().equalsIgnoreCase(categoria)
                    && prato.isEstado()) {
                return prato;
            }
        }
        System.out.println("Prato inválido ou indisponível.");
        return null;
    }

    // Calcula o tempo total para os pratos selecionados
    public int calcularTempoTotal(Prato entrada, Prato principal, Prato sobremesa) {
        int tempoTotal = 0;

        if (entrada != null) {
            tempoTotal = Math.max(tempoTotal, entrada.getTempPrep() + entrada.getTempCons());
        }
        if (principal != null) {
            tempoTotal = Math.max(tempoTotal, principal.getTempPrep() + principal.getTempCons());
        }
        if (sobremesa != null) {
            tempoTotal = Math.max(tempoTotal, sobremesa.getTempPrep() + sobremesa.getTempCons());
        }

        return tempoTotal;
    }

    // Calcula o custo total dos pratos selecionados
    public double calcularCustoTotal(Prato entrada, Prato principal, Prato sobremesa) {
        double custoTotal = 0.0;

        if (entrada != null) {
            custoTotal += entrada.getPC();
        }
        if (principal != null) {
            custoTotal += principal.getPC();
        }
        if (sobremesa != null) {
            custoTotal += sobremesa.getPC();
        }

        return custoTotal;
    }

    // Calcula o preço total dos pratos selecionados
    public double calcularPrecoTotal(Prato entrada, Prato principal, Prato sobremesa) {
        double precoTotal = 0.0;

        if (entrada != null) {
            precoTotal += entrada.getPV();
        }
        if (principal != null) {
            precoTotal += principal.getPV();
        }
        if (sobremesa != null) {
            precoTotal += sobremesa.getPV();
        }

        return precoTotal;
    }

    // Exibe todos os pratos
    public void exibirPratos(Prato[] pratos) {
        System.out.println("\n==== Lista Completa de Pratos ====");
        for (Prato prato : pratos) {
            System.out.println("Nome: " + prato.getNome()
                    + ", Categoria: " + prato.getCategoria()
                    + ", Preço Venda: " + prato.getPV()
                    + ", Estado: " + (prato.isEstado() ? "Disponível" : "Indisponível"));
        }
    }
    //Método para encontrar um prato pelo nome
    public Prato encontrarPratoPorNome(Prato[] pratos, String nome) {
        for (Prato prato : pratos) {
            if (prato.getNome().equals(nome)) {
                return prato;
            }
        }
        return null;
    }



    // Grava pratos no ficheiro
    public void gravarPratos(Prato[] pratos) {
        String separador = configController.getModelo().getSeparador();
        try (BufferedWriter bw = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(caminhoCompletoPratos), StandardCharsets.UTF_8))) {
            for (Prato prato : pratos) {
                bw.write(prato.getNome() + separador + prato.getCategoria() + separador + prato.getPC() + separador +
                        prato.getPV() + separador + prato.getTempPrep() + separador + prato.getTempCons() + separador +
                        prato.isEstado());
                bw.newLine();
            }
            System.out.println("Pratos salvos com sucesso.");
        } catch (IOException e) {
            System.out.println("Erro ao salvar pratos no ficheiro: " + e.getMessage());
        }
    }
}