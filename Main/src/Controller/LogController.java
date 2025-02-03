package Controller;

import Model.Configuracoes;

import java.io.*;
import java.util.*;

public class LogController {
    private final Configuracoes configuracoes;
    private PrintStream fileStream;

    public LogController(Configuracoes configuracoes) {
        this.configuracoes = configuracoes;
    }

    public void inicializarLog() {
        try {
            String caminhoLogs = configuracoes.getCaminhoFicheiros();
            File pastaLogs = new File(caminhoLogs + File.separator + "Log's");

            if (!pastaLogs.exists()) {
                if (pastaLogs.mkdirs()) {
                    System.out.println("Pasta de logs 'Log's' criada com sucesso.");
                } else {
                    System.out.println("Falha ao criar a pasta de logs 'Log's'.");
                }
            }

            int[] numerosDeTeste = new int[0];
            File[] listaArquivos = pastaLogs.listFiles((dir, name) -> name.matches("teste\\d+\\.txt"));

            if (listaArquivos != null) {
                int count = 0;
                for (File arquivo : listaArquivos) {
                    String nome = arquivo.getName();
                    int numero = Integer.parseInt(nome.replaceAll("\\D+", ""));
                    numerosDeTeste = Arrays.copyOf(numerosDeTeste, numerosDeTeste.length + 1); // Expande o array
                    numerosDeTeste[count] = numero;
                    count++;
                }
            }

            int proximoNumero = numerosDeTeste.length == 0 ? 1 : Arrays.stream(numerosDeTeste).max().getAsInt() + 1;
            String nomeArquivo = "teste" + proximoNumero + ".txt";
            File arquivoLog = new File(pastaLogs, nomeArquivo);

            if (!arquivoLog.exists()) {
                arquivoLog.createNewFile();
                System.out.println("📝 Arquivo de log " + nomeArquivo + " criado com sucesso.");
            }

            fileStream = new PrintStream(new FileOutputStream(arquivoLog, true), true);
            PrintStream consoleStream = System.out;

            PrintStream multiStream = new PrintStream(new OutputStream() {
                @Override
                public void write(int b) throws IOException {
                    consoleStream.write(b);
                    fileStream.write(b);
                    fileStream.flush();
                }

                @Override
                public void flush() throws IOException {
                    fileStream.flush();
                    consoleStream.flush();
                }

                @Override
                public void close() throws IOException {
                    fileStream.close();
                    consoleStream.flush();
                }
            }, true);

            System.setOut(multiStream);
            System.out.println("Log inicializado com sucesso. Guardado em: " + nomeArquivo);
            System.out.flush();
        } catch (IOException e) {
            System.err.println("Erro ao inicializar o log: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void  registarEscolhaDoUtilizador(String mensagem) {
        fileStream.println(mensagem);
        System.out.flush();
    }

    public class ScannerLog {
        private final Scanner scanner;
        private final LogController logController;

        public ScannerLog(Scanner scanner, LogController logController) {
            this.scanner = scanner;
            this.logController = logController;
        }

        public int nextInt() {
            int escolha = scanner.nextInt();
            logController. registarEscolhaDoUtilizador(String.valueOf(escolha));
            return escolha;
        }

        public String nextLine() {
            String escolha = scanner.nextLine();
            logController. registarEscolhaDoUtilizador(escolha);
            return escolha;
        }

        public double nextDouble() {
            double escolha = scanner.nextDouble();
            logController. registarEscolhaDoUtilizador(String.valueOf(escolha));
            return escolha;
        }

        public void close() {
            scanner.close();
        }

        public boolean nextBoolean() {
            boolean escolha = scanner.nextBoolean();
            logController. registarEscolhaDoUtilizador(String.valueOf(escolha));
            return escolha;
        }

        public String  registarEscolhaDoUtilizador(String s) {
            String escolha = scanner.nextLine();
            logController. registarEscolhaDoUtilizador(escolha);
            return escolha;
        }

        public boolean hasNextInt() {
            return scanner.hasNextInt();
        }
    }


    public void listarEExibirArquivos() {
        try {
            String caminhoLogs = configuracoes.getCaminhoFicheiros();
            File pastaLogs = new File(caminhoLogs + File.separator + "Log's");

            if (!pastaLogs.exists()) {
                System.out.println("A pasta 'Log's' não existe.");
                return;
            }

            File[] arquivosTxt = pastaLogs.listFiles((dir, name) -> name.endsWith(".txt"));

            if (arquivosTxt == null || arquivosTxt.length == 0) {
                System.out.println("Não há arquivos .txt na pasta.");
                return;
            }

            System.out.println("Arquivos encontrados:");
            for (int i = 0; i < arquivosTxt.length; i++) {
                System.out.println((i + 1) + ". " + arquivosTxt[i].getName());
            }

            Scanner scanner = new Scanner(System.in);
            System.out.print("Digite o número do arquivo que deseja visualizar: ");
            int escolha = scanner.nextInt();

            if (escolha < 1 || escolha > arquivosTxt.length) {
                System.out.println("Opção inválida.");
                return;
            }

            File arquivoEscolhido = arquivosTxt[escolha - 1];

            System.out.println("\nExibir conteúdo de: " + arquivoEscolhido.getName());
            try (BufferedReader reader = new BufferedReader(new FileReader(arquivoEscolhido))) {
                String linha;
                while ((linha = reader.readLine()) != null) {
                    System.out.println(linha);
                }
            }
            System.out.println("FIM DE VISUALIZAÇÃO: " +arquivoEscolhido.getName());

        } catch (IOException e) {
            System.err.println("Erro ao listar ou exibir arquivos: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
