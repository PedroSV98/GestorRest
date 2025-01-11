package Controller;

import Model.Prato;
import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class ControllerPrato {

    // Método para carregar pratos do ficheiro
    public Prato[] carregarPratosDoficheiro(String ficheiro) {
        try (BufferedReader br = new BufferedReader(new FileReader(ficheiro))) {
            String linha;
            Prato[] pratos = new Prato[0];

            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");
                String nome = dados[0];
                String categoria = dados[1];
                double PC = Double.parseDouble(dados[2]);
                double PV = Double.parseDouble(dados[3]);
                int tempPrep = Integer.parseInt(dados[4]);
                int tempCons = Integer.parseInt(dados[5]);
                boolean estado = Boolean.parseBoolean(dados[6]);

                // Cria um novo prato a partir dos dados
                Prato novoPrato = new Prato(nome, categoria, PC, PV, tempPrep, tempCons, estado);

                // Expande o array de pratos
                pratos = Arrays.copyOf(pratos, pratos.length + 1);
                pratos[pratos.length - 1] = novoPrato;
            }

            return pratos;
        } catch (IOException e) {
            System.out.println("Erro ao ler o ficheiro: " + e.getMessage());
            return new Prato[0]; // Retorna um array vazio se não conseguir ler o ficheiro
        }
    }

    // Método para gravar os pratos no ficheiro
    public void gravarPratosNoficheiro(Prato[] pratos, String ficheiro) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ficheiro))) {
            for (Prato prato : pratos) {
                String linha = prato.getNome() + ";" + prato.getCategoria() + ";" + prato.getPC() + ";" + prato.getPV() + ";" + prato.getTempPrep() + ";" + prato.getTempCons() + ";" + prato.isEstado();
                bw.write(linha);
                bw.newLine();
            }
            System.out.println("Pratos gravados com sucesso no ficheiro.");
        } catch (IOException e) {
            System.out.println("Erro ao gravar no ficheiro: " + e.getMessage());
        }
    }

    // Criar um novo prato
    public Prato[] criarPrato(Prato[] pratos, String nome, String categoria, double PC, double PV, int tempPrep, int tempCons, boolean estado) {
        Prato novoPrato = new Prato(nome, categoria, PC, PV, tempPrep, tempCons, estado);
        Prato[] novosPratos = Arrays.copyOf(pratos, pratos.length + 1);
        novosPratos[novosPratos.length - 1] = novoPrato;
        System.out.println("Prato " + nome + " criado.");
        return novosPratos;
    }

    // Exibir todos os pratos
    public void exibirPratos(Prato[] pratos) {
        for (Prato prato : pratos) {
            System.out.println("Nome: " + prato.getNome() + ", Categoria: " + prato.getCategoria() + ", Custo: " + prato.getPC() + ", Preço: " + prato.getPV() + ", Preparação: " + prato.getTempPrep() + " unidades, Consumo: " + prato.getTempCons() + " unidades, Estado: " + (prato.isEstado() ? "Disponível" : "Indisponível"));
        }
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

    // Método para encontrar um prato pelo nome
    private Prato encontrarPratoPorNome(Prato[] pratos, String nome) {
        for (Prato prato : pratos) {
            if (prato.getNome().equals(nome)) {
                return prato;
            }
        }
        return null;
    }

}
