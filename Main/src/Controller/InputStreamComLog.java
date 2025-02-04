package Controller;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

/**
 * Um FilterInputStream que regista, à medida que se lê da entrada,
 * os dados num ficheiro (por intermédio de um PrintStream).
 */
public class InputStreamComLog extends FilterInputStream {
    private final PrintStream logStream;

    public InputStreamComLog(InputStream in, PrintStream logStream) {
        super(in);
        this.logStream = logStream;
    }

    @Override
    public int read() throws IOException {
        int b = super.read();
        if (b != -1) {
            logStream.print((char) b);  // Regista o carácter lido
            logStream.flush();
        }
        return b;
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        int count = super.read(b, off, len);
        if (count > 0) {
            String s = new String(b, off, count);
            logStream.print(s);  // Regista o bloco de caracteres lido
            logStream.flush();
        }
        return count;
    }
}