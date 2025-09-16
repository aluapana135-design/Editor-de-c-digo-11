import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

import fi.iki.elonen.NanoHTTPD;

public class CodeEditorServer extends NanoHTTPD {

    private String filePathToServe;

    // Construtor do servidor, define a porta
    public CodeEditorServer(int port) {
        super(port);
    }

    // Método principal que lida com as requisições HTTP
    @Override
    public Response serve(IHTTPSession session) {
        // Pega o caminho do arquivo solicitado na URL
        String uri = session.getUri();

        // Se a requisição for para o caminho principal ("/")
        if (uri.equals("/")) {
            // A resposta será um HTML simples
            return newFixedLengthResponse("<html><body><h1>Servidor Local Rodando!</h1><p>Seu servidor HTTP em Java está funcionando.</p></body></html>");
        }

        // Se a requisição for para um arquivo específico
        if (filePathToServe != null && new File(filePathToServe).exists()) {
            try {
                // Lê o arquivo para servir
                FileInputStream fis = new FileInputStream(new File(filePathToServe));
                return newFixedLengthResponse(Response.Status.OK, "text/plain", fis, fis.available());
            } catch (IOException e) {
                // Em caso de erro, retorna uma resposta de erro
                return newFixedLengthResponse(Response.Status.INTERNAL_ERROR, MIME_PLAINTEXT, "Erro ao ler o arquivo: " + e.getMessage());
            }
        }

        // Se o recurso não for encontrado
        return newFixedLengthResponse(Response.Status.NOT_FOUND, MIME_PLAINTEXT, "404 - Arquivo não encontrado.");
    }

    // Método para definir qual arquivo o servidor deve servir
    public void setFilePathToServe(String path) {
        this.filePathToServe = path;
    }
}