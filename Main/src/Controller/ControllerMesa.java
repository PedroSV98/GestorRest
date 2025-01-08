package Controller;


import Model.Mesa;
import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

    public class ControllerMesa {

        // Método para carregar mesas do ficheiro
        public Mesa[] carregarMesasDoFicheiro(String Ficheiro) {
            try (BufferedReader br = new BufferedReader(new FileReader(Ficheiro))) {
                String linha;
                Mesa[] mesas = new Mesa[0];

                while ((linha = br.readLine()) != null) {
                    String[] dados = linha.split(";");
                    int id = Integer.parseInt(dados[0]);
                    int lugares = Integer.parseInt(dados[1]);
                    boolean ocupada = Boolean.parseBoolean(dados[2]);

                    // Cria uma nova mesa a partir dos dados
                    Mesa novaMesa = new Mesa(id, ocupada, lugares);

                    // Expande o array de mesas
                    mesas = Arrays.copyOf(mesas, mesas.length + 1);
                    mesas[mesas.length - 1] = novaMesa;
                }

                return mesas;
            } catch (IOException e) {
                System.out.println("Erro ao ler o Ficheiro: " + e.getMessage());
                return new Mesa[0]; // Retorna um array vazio se não conseguir ler o Ficheiro
            }
        }

        // Método para gravar as mesas no Ficheiro
        public void gravarMesasNoFicheiro(Mesa[] mesas, String Ficheiro) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(Ficheiro))) {
                for (Mesa mesa : mesas) {
                    String linha = mesa.getId() + ";" + mesa.getLugares() + ";" + mesa.isOcupada();
                    bw.write(linha);
                    bw.newLine();
                }
                System.out.println("Mesas gravadas com sucesso no Ficheiro.");
            } catch (IOException e) {
                System.out.println("Erro ao gravar no Ficheiro: " + e.getMessage());
            }
        }

        // Criar uma nova mesa
        public Mesa[] criarMesa(Mesa[] mesas, int id, int lugares, boolean ocupada) {
            Mesa novaMesa = new Mesa(id, ocupada, lugares);
            Mesa[] novasMesas = Arrays.copyOf(mesas, mesas.length + 1);
            novasMesas[novasMesas.length - 1] = novaMesa;
            System.out.println("Mesa " + id + " criada.");
            return novasMesas;
        }

        // Exibir todas as mesas
        public void exibirMesas(Mesa[] mesas) {
            for (Mesa mesa : mesas) {
                System.out.println("Mesa ID: " + mesa.getId() + ", Lugares: " + mesa.getLugares() + ", Ocupada: " + (mesa.isOcupada() ? "Sim" : "Não"));
            }
        }

        // Atualizar (editar) os dados de uma mesa
        public void atualizarMesa(Mesa[] mesas, int idMesa, int lugares, boolean ocupada) {
            Mesa mesa = encontrarMesaPorId(mesas, idMesa);
            if (mesa != null) {
                mesa.setLugares(lugares);
                mesa.setOcupada(ocupada);
                System.out.println("Mesa " + idMesa + " atualizada.");
            } else {
                System.out.println("Mesa " + idMesa + " não encontrada.");
            }
        }

        // Eliminar (remover) uma mesa pelo ID
        public Mesa[] eliminarMesa(Mesa[] mesas, int idMesa) {
            int index = -1;

            for (int i = 0; i < mesas.length; i++) {
                if (mesas[i].getId() == idMesa) {
                    index = i;
                    break;
                }
            }

            if (index != -1) {
                Mesa[] mesasAtualizadas = new Mesa[mesas.length - 1];
                System.arraycopy(mesas, 0, mesasAtualizadas, 0, index);
                System.arraycopy(mesas, index + 1, mesasAtualizadas, index, mesas.length - index - 1);
                System.out.println("Mesa " + idMesa + " deletada.");
                return mesasAtualizadas;
            } else {
                System.out.println("Mesa " + idMesa + " não encontrada.");
                return mesas;
            }
        }

        // Método para encontrar uma mesa pelo ID
        private Mesa encontrarMesaPorId(Mesa[] mesas, int idMesa) {
            for (Mesa mesa : mesas) {
                if (mesa.getId() == idMesa) {
                    return mesa;
                }
            }
            return null;
        }

    }



