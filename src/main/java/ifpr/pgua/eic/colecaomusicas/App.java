package ifpr.pgua.eic.colecaomusicas;

import ifpr.pgua.eic.colecaomusicas.controllers.CadastroArtista;
import ifpr.pgua.eic.colecaomusicas.controllers.CadastroGenero;
import ifpr.pgua.eic.colecaomusicas.controllers.CadastroMusica;
import ifpr.pgua.eic.colecaomusicas.controllers.CadastroPlaylist;
import ifpr.pgua.eic.colecaomusicas.controllers.ListarArtistas;
import ifpr.pgua.eic.colecaomusicas.controllers.ListarGeneros;
import ifpr.pgua.eic.colecaomusicas.controllers.ListarMusicas;
import ifpr.pgua.eic.colecaomusicas.controllers.ListarPlaylists;
import ifpr.pgua.eic.colecaomusicas.controllers.Principal;
import ifpr.pgua.eic.colecaomusicas.daos.ArtistaDAO;
import ifpr.pgua.eic.colecaomusicas.daos.FabricaConexoes;
import ifpr.pgua.eic.colecaomusicas.daos.GeneroDAO;
import ifpr.pgua.eic.colecaomusicas.daos.JDBCArtistaDAO;
import ifpr.pgua.eic.colecaomusicas.daos.JDBCGeneroDAO;
import ifpr.pgua.eic.colecaomusicas.daos.JDBCMusicaDAO;
import ifpr.pgua.eic.colecaomusicas.daos.JDBCPlaylistDAO;
import ifpr.pgua.eic.colecaomusicas.daos.MusicaDAO;
import ifpr.pgua.eic.colecaomusicas.daos.PlaylistDAO;
import ifpr.pgua.eic.colecaomusicas.repositories.RepositorioArtistas;
import ifpr.pgua.eic.colecaomusicas.repositories.RepositorioGeneros;
import ifpr.pgua.eic.colecaomusicas.repositories.RepositorioMusicas;
import ifpr.pgua.eic.colecaomusicas.repositories.RepositorioPlaylist;
import io.github.hugoperlin.navigatorfx.BaseAppNavigator;
import io.github.hugoperlin.navigatorfx.ScreenRegistryFXML;

/**
 * JavaFX App
 */
public class App extends BaseAppNavigator {

    private ArtistaDAO artistaDAO = new JDBCArtistaDAO(FabricaConexoes.getInstance());
    private RepositorioArtistas repositorioArtistas = new RepositorioArtistas(artistaDAO);

    private GeneroDAO generoDAO = new JDBCGeneroDAO(FabricaConexoes.getInstance());
    private RepositorioGeneros repositorioGeneros = new RepositorioGeneros(generoDAO);

    private MusicaDAO musicaDAO = new JDBCMusicaDAO(FabricaConexoes.getInstance());
    private RepositorioMusicas repositorioMusicas = new RepositorioMusicas(musicaDAO, artistaDAO, generoDAO);

    private PlaylistDAO playlistDAO = new JDBCPlaylistDAO(FabricaConexoes.getInstance());
    private RepositorioPlaylist repositorioPlaylist = new RepositorioPlaylist(playlistDAO, musicaDAO, artistaDAO, generoDAO);

    public static void main(String[] args) {
        launch();
    }

    @Override
    public String getHome() {
        // TODO Auto-generated method stub
        return "PRINCIPAL";
    }

    @Override
    public String getAppTitle() {
        // TODO Auto-generated method stub
        return "Coleção de Músicas";
    }

    @Override
    public void registrarTelas() {
        registraTela("PRINCIPAL", new ScreenRegistryFXML(App.class, "principal.fxml", o -> new Principal()));
        registraTela("CADASTROGENERO",
                new ScreenRegistryFXML(App.class,
                        "cadastrar_genero.fxml",
                        o -> new CadastroGenero(repositorioGeneros)));
        registraTela("LISTARGENEROS",
                new ScreenRegistryFXML(App.class,
                        "listar_generos.fxml",
                        o -> new ListarGeneros(repositorioGeneros)));
        registraTela("CADASTROARTISTA",
                new ScreenRegistryFXML(App.class,
                        "cadastrar_artista.fxml",
                        o -> new CadastroArtista(repositorioArtistas)));
        registraTela("LISTARARTISTAS",
                new ScreenRegistryFXML(App.class,
                        "listar_artistas.fxml",
                        o -> new ListarArtistas(repositorioArtistas)));
        registraTela("CADASTRARMUSICA",
                new ScreenRegistryFXML(App.class,
                        "cadastrar_musica.fxml",
                        o -> new CadastroMusica(repositorioMusicas, repositorioGeneros, repositorioArtistas)));
        registraTela("LISTARMUSICAS",
                new ScreenRegistryFXML(App.class,
                        "listar_musicas.fxml",
                        o -> new ListarMusicas(repositorioMusicas)));

        registraTela("CADASTRARPLAYLIST",
                new ScreenRegistryFXML(App.class,
                        "cadastrar_playlist.fxml",
                        o -> new CadastroPlaylist(repositorioMusicas, repositorioPlaylist)));

        registraTela("LISTARPLAYLIST",
                new ScreenRegistryFXML(App.class,
                        "listar_playlist.fxml",
                        o -> new ListarPlaylists(repositorioPlaylist)));
    }

}