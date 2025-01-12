package Controller;

import Model.Mesa;
import java.io.*;

public class ControllerMesa {

    private ConfiguracoesController configController;
    private String caminhoCompletoMesas;

    public ControllerMesa(ConfiguracoesController configController) {
        this.configController = configController;
        // Caminho base + "Mesas.txt"
        String basePath = configController.getModelo().getCaminhoFicheiros();
        this.caminhoCompletoMesas = basePath + "Mesas.txt";
    }

    /**
     * Lê o ficheiro e devolve um array NOVO, ignorando o campo 'ocupada'.
     * Cada mesa terá 'ocupada = false'. (Modo antigo)
     */
    public Mesa[] carregarMesas() {
        // Contar as linhas
        int numLinhasValidas = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoCompletoMesas))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                linha = linha.trim();
                if (!linha.isEmpty()) {
                    String[] partes = linha.split(";");
                    if (partes.length >= 2) {
                        numLinhasValidas++;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o ficheiro (contagem): " + e.getMessage());
            return new Mesa[0];
        }

        // Preencher o array
        Mesa[] mesas = new Mesa[numLinhasValidas];
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
                    System.out.println("Linha inválida: " + linha);
                    continue;
                }

                int id = Integer.parseInt(partes[0]);
                int lugares = Integer.parseInt(partes[1]);
                // ocupada = false, pois o ficheiro não guarda essa info
                mesas[idx] = new Mesa(id, false, lugares);
                idx++;
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o ficheiro (preenchimento): " + e.getMessage());
            return new Mesa[0];
        }

        return mesas;
    }

    /**
     * Mescla o ficheiro com o array emMemoria.
     * - Se a mesa (ID) existe em 'emMemoria', mantém 'ocupada' e atualiza apenas 'lugares'.
     * - Se a mesa não existe em 'emMemoria', cria uma nova mesa (ocupada = false).
     */
    public Mesa[] agruparComFicheiro(Mesa[] emMemoria) {
        String[] linhas = lerLinhasFicheiro(caminhoCompletoMesas);
        if (linhas == null || linhas.length == 0) {
            System.out.println("Ficheiro vazio ou não encontrado. Mantêm-se as mesas em memória.");
            return emMemoria;
        }

        for (String linha : linhas) {
            if (linha == null) continue;
            linha = linha.trim();
            if (linha.isEmpty()) continue;

            String[] partes = linha.split(";");
            if (partes.length < 2) {
                System.out.println("Linha inválida (esperava 'id;lugares'): " + linha);
                continue;
            }

            int id = Integer.parseInt(partes[0]);
            int lugares = Integer.parseInt(partes[1]);

            // Verificar se já existe no array emMemoria
            Mesa existente = encontrarMesaPorId(emMemoria, id);
            if (existente != null) {
                // Mantemos 'ocupada', só atualizamos 'lugares'
                existente.setLugares(lugares);
            } else {
                // Nova mesa (ocupada = false)
                Mesa nova = new Mesa(id, false, lugares);
                emMemoria = adicionarMesa(emMemoria, nova);
            }
        }

        return emMemoria;
    }

    /**
     * Lê todas as linhas do ficheiro (sem ArrayList).
     */
    private String[] lerLinhasFicheiro(String caminho) {
        int contagem = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            while (br.readLine() != null) {
                contagem++;
            }
        } catch (IOException e) {
            System.out.println("Erro a contar linhas: " + e.getMessage());
            return null;
        }

        String[] linhas = new String[contagem];
        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            String linha;
            int idx = 0;
            while ((linha = br.readLine()) != null) {
                linhas[idx] = linha;
                idx++;
            }
        } catch (IOException e) {
            System.out.println("Erro a ler linhas: " + e.getMessage());
            return null;
        }
        return linhas;
    }

    /**
     * Adiciona uma nova mesa a um array, criando um array maior em +1 posição.
     */
    private Mesa[] adicionarMesa(Mesa[] originais, Mesa nova) {
        Mesa[] maior = new Mesa[originais.length + 1];
        for (int i = 0; i < originais.length; i++) {
            maior[i] = originais[i];
        }
        maior[maior.length - 1] = nova;
        return maior;
    }

    /**
     * Grava (id;lugares) no ficheiro. Não grava 'ocupada'.
     */
    public void gravarMesas(Mesa[] mesas) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminhoCompletoMesas))) {
            for (Mesa mesa : mesas) {
                String linha = mesa.getId() + ";" + mesa.getLugares();
                bw.write(linha);
                bw.newLine();
            }
            System.out.println("Mesas guardadas com sucesso em: " + caminhoCompletoMesas);
        } catch (IOException e) {
            System.out.println("Erro ao guardar o ficheiro de mesas: " + e.getMessage());
        }
    }

    /**
     * Cria uma nova mesa em memória e devolve um array com mais 1 elemento.
     */
    public Mesa[] criarMesa(Mesa[] mesas, int id, int lugares, boolean ocupada) {
        Mesa[] novas = new Mesa[mesas.length + 1];
        for (int i = 0; i < mesas.length; i++) {
            novas[i] = mesas[i];
        }
        novas[novas.length - 1] = new Mesa(id, ocupada, lugares);
        System.out.println("Mesa " + id + " criada em memória.");
        return novas;
    }

    /**
     * Lista no ecrã as mesas (mostrando ID, lugares e se está ocupada).
     */
    public void exibirMesas(Mesa[] mesas) {
        if (mesas == null || mesas.length == 0) {
            System.out.println("Não há mesas registadas em memória.");
            return;
        }
        System.out.println("\n==== Lista de Mesas ====");
        for (Mesa m : mesas) {
            if (m == null) continue;
            System.out.println("ID: " + m.getId()
                    + ", Lugares: " + m.getLugares()
                    + ", Ocupada: " + (m.isOcupada() ? "Sim" : "Não"));
        }
    }

    /**
     * Atualiza (em memória) a mesa que corresponda ao ID, se encontrada.
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
     * Elimina a mesa do array (se encontrada) e devolve um array menor.
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
            return mesas;
        }
        Mesa[] atualizadas = new Mesa[mesas.length - 1];
        for (int i = 0, j = 0; i < mesas.length; i++) {
            if (i != index) {
                atualizadas[j] = mesas[i];
                j++;
            }
        }
        System.out.println("Mesa " + idMesa + " eliminada em memória.");
        return atualizadas;
    }

    /**
     * Procura uma mesa no array, pelo ID.
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
