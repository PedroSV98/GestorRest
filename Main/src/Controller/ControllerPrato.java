package Controller;

import Model.Configuracoes;
import Model.Prato;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class ControllerPrato {

    private ConfiguracoesController configController;
    private String caminhoCompletoPratos;

    public ControllerPrato(ConfiguracoesController configController) {
        this.configController = configController;
        String basePath = configController.getModelo().getCaminhoFicheiros();
        this.caminhoCompletoPratos = basePath + "Pratos.txt";
    }

    /**
     * Lê completamente o ficheiro "Pratos.txt" (UTF-8) em duas passagens
     * e cria um array novo, sobrescrevendo tudo que estava em memória.
     * Formato: nome;categoria;PC;PV;tempPrep;tempCons;estado
     */
    public Prato[] carregarPratos() {
        String separador = configController.getModelo().getSeparador();

        int numLinhasValidas = 0;
        // 1) Contagem
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(caminhoCompletoPratos),
                        StandardCharsets.UTF_8
                )
        )) {
            String linha;
            while ((linha = br.readLine()) != null) {
                linha = linha.trim();
                if (!linha.isEmpty()) {
                    String[] partes = linha.split(separador);
                    if (partes.length >= 7) {
                        numLinhasValidas++;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o ficheiro de pratos (contagem): " + e.getMessage());
            return new Prato[0];
        }

        // 2) Criar array e preencher
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
                if (linha.isEmpty()) {
                    continue;
                }
                String[] dados = linha.split(separador);
                if (dados.length < 7) {
                    System.out.println("Linha inválida (esperava 7 colunas): " + linha);
                    continue;
                }

                String nome      = dados[0];
                String categoria = dados[1];
                double PC        = Double.parseDouble(dados[2]);
                double PV        = Double.parseDouble(dados[3]);
                int tempPrep     = Integer.parseInt(dados[4]);
                int tempCons     = Integer.parseInt(dados[5]);
                boolean estado   = Boolean.parseBoolean(dados[6]);

                Prato novoPrato = new Prato(nome, categoria, PC, PV, tempPrep, tempCons, estado);
                if (idx < pratos.length) {
                    pratos[idx] = novoPrato;
                    idx++;
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o ficheiro de pratos (preenchimento): " + e.getMessage());
            return new Prato[0];
        }

        return pratos;
    }

    /**
     * Faz uma mesclagem ("agrupar") entre o array emMemoria e o ficheiro "Pratos.txt" (UTF-8).
     * - Se o prato (pelo 'nome') já existe em emMemoria, atualiza apenas
     *   categoria, PC, PV, tempPrep, tempCons, mas MANTÉM o estado que estava em memória.
     * - Se o prato NÃO existe, cria um novo prato com os dados do ficheiro (incluindo estado do ficheiro).
     */
    public Prato[] AgruparComFicheiro(Prato[] emMemoria) {
        Prato[] pratosDoFicheiro = carregarPratos(); // Carrega do ficheiro
        if (pratosDoFicheiro.length == 0) {
            System.out.println("O ficheiro está vazio ou não foi encontrado.");
            return emMemoria; // Mantém o estado atual
        }

        for (Prato pratoFicheiro : pratosDoFicheiro) {
            boolean existe = false;

            // Verifica se o prato já existe em memória
            for (Prato pratoMemoria : emMemoria) {
                if (pratoMemoria.getNome().equalsIgnoreCase(pratoFicheiro.getNome())) {
                    existe = true;
                    break;
                }
            }

            // Se não existe, adiciona o prato do ficheiro à memória
            if (!existe) {
                emMemoria = criarPrato(
                        emMemoria,
                        pratoFicheiro.getNome(),
                        pratoFicheiro.getCategoria(),
                        pratoFicheiro.getPC(),
                        pratoFicheiro.getPV(),
                        pratoFicheiro.getTempPrep(),
                        pratoFicheiro.getTempCons(),
                        pratoFicheiro.isEstado()
                );
            }
        }
        return emMemoria;
    }

    /**
     * Lê todas as linhas de um ficheiro (UTF-8) e retorna num array de Strings (sem usar ArrayList).
     * Fazemos em duas passagens: contar e depois armazenar.
     */
    private String[] lerLinhasFicheiroUTF8(String caminho) {
        // 1) Contar
        int contagem = 0;
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(caminho),
                        StandardCharsets.UTF_8
                )
        )) {
            while (br.readLine() != null) {
                contagem++;
            }
        } catch (IOException e) {
            System.out.println("Erro ao contar linhas: " + e.getMessage());
            return null;
        }

        // 2) Ler e guardar
        String[] linhas = new String[contagem];
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(caminho),
                        StandardCharsets.UTF_8
                )
        )) {
            String linha;
            int idx = 0;
            while ((linha = br.readLine()) != null) {
                linhas[idx++] = linha;
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler linhas: " + e.getMessage());
            return null;
        }

        return linhas;
    }

    /**
     * Adiciona um novo prato a um array, criando um array maior em +1 posição.
     */
    private Prato[] adicionarPrato(Prato[] originais, Prato novo) {
        Prato[] maior = new Prato[originais.length + 1];
        for (int i = 0; i < originais.length; i++) {
            maior[i] = originais[i];
        }
        maior[maior.length - 1] = novo;
        return maior;
    }

    public void gravarPratos(Prato[] pratos) {
        String separador = configController.getModelo().getSeparador();

        try (BufferedWriter bw = new BufferedWriter(
                new OutputStreamWriter(
                        new FileOutputStream(caminhoCompletoPratos),
                        StandardCharsets.UTF_8
                )
        )) {
            for (Prato p : pratos) {
                String linha = p.getNome() + separador
                        + p.getCategoria() + separador
                        + p.getPC() + separador
                        + p.getPV() + separador
                        + p.getTempPrep() + separador
                        + p.getTempCons() + separador
                        + p.isEstado();

                bw.write(linha);
                bw.newLine();
            }
            System.out.println("Pratos guardados com sucesso em: " + caminhoCompletoPratos);
        } catch (IOException e) {
            System.out.println("Erro ao guardar o ficheiro de pratos: " + e.getMessage());
        }
    }

    public Prato[] criarPrato(Prato[] pratos,
                              String nome,
                              String categoria,
                              double PC,
                              double PV,
                              int tempPrep,
                              int tempCons,
                              boolean estado)
    {
        Prato[] novosPratos = new Prato[pratos.length + 1];
        for (int i = 0; i < pratos.length; i++) {
            novosPratos[i] = pratos[i];
        }

        Prato novoPrato = new Prato(nome, categoria, PC, PV, tempPrep, tempCons, estado);
        novosPratos[novosPratos.length - 1] = novoPrato;
        System.out.println("Prato '" + nome + "' criado em memória.");
        return novosPratos;
    }

    public void exibirPratos(Prato[] pratos) {
        if (pratos == null || pratos.length == 0) {
            System.out.println("Não há pratos registados.");
            return;
        }
        System.out.println("\n==== Lista de Pratos ====");
        for (Prato p : pratos) {
            if (p == null) continue;
            System.out.println("Nome: " + p.getNome()
                    + ", Categoria: " + p.getCategoria()
                    + ", Custo: " + p.getPC()
                    + ", Preço: " + p.getPV()
                    + ", TempoPrep: " + p.getTempPrep()
                    + ", TempoCons: " + p.getTempCons()
                    + ", Estado: " + (p.isEstado() ? "Disponível" : "Indisponível"));
        }
    }

    public void atualizarPrato(Prato[] pratos,
                               String nome,
                               String novaCategoria,
                               double novoPC,
                               double novoPV,
                               int novoTempPrep,
                               int novoTempCons,
                               boolean novoEstado)
    {
        Prato p = encontrarPratoPorNome(pratos, nome);
        if (p != null) {
            p.setCategoria(novaCategoria);
            p.setPC(novoPC);
            p.setPV(novoPV);
            p.setTempPrep(novoTempPrep);
            p.setTempCons(novoTempCons);
            p.setEstado(novoEstado);
            System.out.println("Prato '" + nome + "' foi atualizado em memória.");
        } else {
            System.out.println("Prato '" + nome + "' não encontrado.");
        }
    }

    public Prato[] eliminarPrato(Prato[] pratos, String nome) {
        int index = -1;
        for (int i = 0; i < pratos.length; i++) {
            if (pratos[i].getNome().equalsIgnoreCase(nome)) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            System.out.println("Prato '" + nome + "' não encontrado.");
            return pratos;
        }

        Prato[] atualizados = new Prato[pratos.length - 1];
        for (int i = 0, j = 0; i < pratos.length; i++) {
            if (i != index) {
                atualizados[j] = pratos[i];
                j++;
            }
        }
        System.out.println("Prato '" + nome + "' foi eliminado.");
        return atualizados;
    }

    public Prato encontrarPratoPorNome(Prato[] pratos, String nome) {
        for (Prato p : pratos) {
            if (p.getNome().equalsIgnoreCase(nome)) {
                return p;
            }
        }
        return null;
    }
}