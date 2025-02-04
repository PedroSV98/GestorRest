package Controller;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Um OutputStream que duplica a escrita para dois streams:
 * por exemplo, para o terminal e para um ficheiro de log.
 */
public class OutputStreamDuplicador extends OutputStream {
    private final OutputStream stream1;
    private final OutputStream stream2;

    public OutputStreamDuplicador(OutputStream stream1, OutputStream stream2) {
        this.stream1 = stream1;
        this.stream2 = stream2;
    }

    @Override
    public void write(int b) throws IOException {
        stream1.write(b);
        stream2.write(b);
    }

    @Override
    public void flush() throws IOException {
        stream1.flush();
        stream2.flush();
    }

    @Override
    public void close() throws IOException {
        stream1.close();
        stream2.close();
    }
}