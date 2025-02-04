package Controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Model.Configuracoes;
import Controller.ConfiguracoesController;  // Certifique-se que esta classe existe e fornece o modelo

/**
 * O LogController inicializa a monitorização dos fluxos padrão de entrada e saída,
 * cria um ficheiro unificado de log num diretório configurado (ex: data/Log's/),
 * numerar o ficheiro (ex: log_unificado1.txt, log_unificado2.txt, etc.).
 */
public class LogController {
    public static void iniciar() {
        try {
            // Obter as configurações através do ConfiguracoesController
            Configuracoes config = ConfiguracoesController.getInstancia().getModelo();

            // Obter o caminho base dos ficheiros (por exemplo, "data/")
            String basePath = config.getCaminhoFicheiros();

            // Definir a pasta de logs: basePath + "Log's" + separador de ficheiros
            String logsDirPath = basePath + "Log's" + File.separator;
            File logsDir = new File(logsDirPath);
            if (!logsDir.exists()) {
                logsDir.mkdirs(); // Cria a pasta se não existir
            }

            // Procurar ficheiros existentes com o padrão "log_unificadoX.txt" para determinar o próximo número
            int maxNumber = 0;
            File[] files = logsDir.listFiles();
            if (files != null) {
                Pattern pattern = Pattern.compile("log(\\d+)\\.txt");
                for (File f : files) {
                    Matcher matcher = pattern.matcher(f.getName());
                    if (matcher.matches()) {
                        int num = Integer.parseInt(matcher.group(1));
                        if (num > maxNumber) {
                            maxNumber = num;
                        }
                    }
                }
            }
            int nextNumber = maxNumber + 1;
            String logFileName = logsDirPath + "log" + nextNumber + ".txt";

            // Guarda o fluxo de saída original (terminal)
            PrintStream originalOut = System.out;

            // Cria o ficheiro de log unificado (modo append)
            FileOutputStream fosUnified = new FileOutputStream(logFileName, true);
            PrintStream unifiedLog = new PrintStream(fosUnified, true, "UTF-8");

            // Cria um OutputStream que duplica a saída para o terminal e para o ficheiro unificado
            OutputStreamDuplicador duplicadorOut = new OutputStreamDuplicador(originalOut, unifiedLog);
            PrintStream novoOut = new PrintStream(duplicadorOut, true, "UTF-8");
            System.setOut(novoOut);

            // Guarda o fluxo de entrada original (teclado)
            InputStream originalIn = System.in;

            // Cria um InputStream que regista a entrada do utilizador no mesmo ficheiro unificado
            InputStreamComLog inputComLog = new InputStreamComLog(originalIn, unifiedLog);
            System.setIn(inputComLog);

            System.out.println("LogController iniciado. A monitorizar entrada e saída (log unificado em " + logFileName + ").");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}