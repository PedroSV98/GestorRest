package Model;

import java.io.*;
import java.util.Properties;

public class Configuracoes {
    private Properties propriedades;
    private static final String CONFIG_FILE = "./data/config.txt";

    public Configuracoes() {
        propriedades = new Properties();
        carregar();
    }

    public void carregar() {
        try (FileReader reader = new FileReader(CONFIG_FILE)) {
            propriedades.load(reader);
        } catch (IOException e) {
            System.out.println("Erro ao carregar configurações: " + e.getMessage());
        }
    }

    public void salvar() {
        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            propriedades.store(writer, "Configurações do Restaurante");
        } catch (IOException e) {
            System.out.println("Erro ao salvar configurações: " + e.getMessage());
        }
    }

    public String get(String chave) {
        return propriedades.getProperty(chave);
    }

    public void set(String chave, String valor) {
        propriedades.setProperty(chave, valor);
    }

    public Properties getPropriedades() {
        return propriedades;
    }
}
