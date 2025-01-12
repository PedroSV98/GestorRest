package Controller;

import Model.Mesa;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ControllerMesa {

    private ConfiguracoesController configController;
    private String caminhoCompletoMesas; // Ex.: "C:\\...\\data\\Mesas.txt"

    public ControllerMesa(ConfiguracoesController configController) {
        // Guarda a referência do configController
        this.configController = configController;

        // Ir buscar o caminho base (ex.: "C:\\...\\data\\")
        String basePath = configController.getModelo().getCaminhoFicheiros();

        // Concatena o nome do ficheiro específico (ex.: "Mesas.txt")
        this.caminhoCompletoMesas = basePath + "Mesas.txt";
    }

    /**
     * Ler as mesas do ficheiro “Mesas.txt” que só tem 'id;lugares'.
     * O boolean “ocupada” fica apenas em memória, não é carregado do ficheiro.
     */
    public Mesa[] carregarMesas() {
        // 1) Primeiro pass: contar só linhas válidas
        int numLinhasValidas = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(caminhoCompletoMesas))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                linha = linha.trim();
                if (linha.isEmpty()) {
                    // Linha vazia, ignorar
                    continue;
                }

                // Separa
                String[] partes = linha.split(";");
                if (partes.length >= 2) {  // só conta como válida se tiver pelo menos 2 colunas
                    numLinhasValidas++;
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o ficheiro (contagem): " + e.getMessage());
            return new Mesa[0];
        }

        // 2) Criar array do tamanho certo
        Mesa[] mesas = new Mesa[numLinhasValidas];

        // 3) Segundo pass: preencher
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoCompletoMesas))) {
            String linha;
            int idx = 0;
            while ((linha = br.readLine()) != null) {
                linha = linha.trim();
                if (linha.isEmpty()) {
                    continue;
                }
                String[] partes = linha.split(";");
                if (partes.length < 2) {
                    // linha inválida, ignora
                    System.out.println("Linha inválida (esperava 2 colunas: 'id;lugares'): " + linha);
                    continue;
                }

                int id = Integer.parseInt(partes[0]);
                int lugares = Integer.parseInt(partes[1]);
                boolean ocupada = false; // valor por defeito

                // só adicionas se ainda houver espaço no array
                if (idx < mesas.length) {
                    mesas[idx] = new Mesa(id, ocupada, lugares);
                    idx++;
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o ficheiro (preenchimento): " + e.getMessage());
            return new Mesa[0];
        }

        return mesas;
    }

    /**
     * Grava as mesas em disco, só com 'id;lugares'.
     * A ocupação (boolean) NÃO é guardada no ficheiro.
     */
    public void gravarMesas(Mesa[] mesas) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminhoCompletoMesas))) {
            for (Mesa mesa : mesas) {
                // Formato "id;lugares"
                String linha = mesa.getId() + ";" + mesa.getLugares();
                bw.write(linha);
                bw.newLine();
            }
            System.out.println("Mesas gravadas com sucesso em: " + caminhoCompletoMesas);
        } catch (IOException e) {
            System.out.println("Erro ao gravar no ficheiro de mesas: " + e.getMessage());
        }
    }

    /**
     * Cria uma nova mesa e devolve um array com +1 elemento.
     */
    public Mesa[] criarMesa(Mesa[] mesas, int id, int lugares, boolean ocupada) {
        Mesa[] novasMesas = new Mesa[mesas.length + 1];
        // copiar manualmente
        for (int i = 0; i < mesas.length; i++) {
            novasMesas[i] = mesas[i];
        }
        // colocar a nova mesa na última posição
        novasMesas[novasMesas.length - 1] = new Mesa(id, ocupada, lugares);

        System.out.println("Mesa " + id + " criada em memória.");
        return novasMesas;
    }

    /**
     * Lista no ecrã as mesas do array.
     */
    public void exibirMesas(Mesa[] mesas) {
        if (mesas == null || mesas.length == 0) {
            System.out.println("Não há mesas registadas.");
            return;
        }
        System.out.println("\n==== Lista de Mesas ====");
        for (Mesa m : mesas) {
            System.out.println("ID: " + m.getId()
                    + ", Lugares: " + m.getLugares()
                    + ", Ocupada: " + (m.isOcupada() ? "Sim" : "Não"));
        }
    }

    /**
     * Atualiza (edita) a mesa correspondente a 'idMesa', caso seja encontrada.
     */
    public void atualizarMesa(Mesa[] mesas, int idMesa, int novosLugares, boolean novaOcupacao) {
        Mesa mesa = encontrarMesaPorId(mesas, idMesa);
        if (mesa != null) {
            mesa.setLugares(novosLugares);
            mesa.setOcupada(novaOcupacao);
            System.out.println("Mesa " + idMesa + " atualizada em memória.");
        } else {
            System.out.println("Mesa " + idMesa + " não encontrada.");
        }
    }

    /**
     * Remove (elimina) a mesa com 'idMesa' do array, devolvendo um novo array com -1 elemento.
     */
    public Mesa[] eliminarMesa(Mesa[] mesas, int idMesa) {
        int index = -1;
        for (int i = 0; i < mesas.length; i++) {
            if (mesas[i].getId() == idMesa) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            System.out.println("Mesa " + idMesa + " não encontrada.");
            return mesas; // não modifica o array
        }

        Mesa[] mesasAtualizadas = new Mesa[mesas.length - 1];
        // Copiar a parte antes do index
        for (int i = 0; i < index; i++) {
            mesasAtualizadas[i] = mesas[i];
        }
        // Copiar a parte depois do index
        for (int i = index + 1; i < mesas.length; i++) {
            mesasAtualizadas[i - 1] = mesas[i];
        }
        System.out.println("Mesa " + idMesa + " eliminada em memória.");
        return mesasAtualizadas;
    }

    /**
     * Método auxiliar para encontrar mesa por ID.
     */
    private Mesa encontrarMesaPorId(Mesa[] mesas, int idMesa) {
        for (Mesa m : mesas) {
            if (m.getId() == idMesa) {
                return m;
            }
        }
        return null;
    }
}
