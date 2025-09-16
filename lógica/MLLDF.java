public class MainActivity extends AppCompatActivity {

    private CodeEditorServer server;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Define a porta do servidor
        int port = 8080; 

        // Cria e inicia a instância do servidor em uma thread separada para não travar a UI
        try {
            server = new CodeEditorServer(port);
            server.start();
            Toast.makeText(this, "Servidor iniciado na porta " + port, Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Erro ao iniciar o servidor.", Toast.LENGTH_LONG).show();
        }

        // Lembre-se de parar o servidor quando o aplicativo for fechado
        // para liberar a porta.
        // Adicione este código no método onDestory() da sua Activity
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (server != null) {
            server.stop();
        }
    }
}