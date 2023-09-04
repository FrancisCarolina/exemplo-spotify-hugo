package ifpr.pgua.eic.colecaomusicas.repositories;

import java.util.ArrayList;
import java.util.List;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.colecaomusicas.daos.ArtistaDAO;
import ifpr.pgua.eic.colecaomusicas.daos.GeneroDAO;
import ifpr.pgua.eic.colecaomusicas.daos.MusicaDAO;
import ifpr.pgua.eic.colecaomusicas.daos.PlaylistDAO;
import ifpr.pgua.eic.colecaomusicas.models.Artista;
import ifpr.pgua.eic.colecaomusicas.models.Genero;
import ifpr.pgua.eic.colecaomusicas.models.Musica;
import ifpr.pgua.eic.colecaomusicas.models.Playlist;

public class RepositorioPlaylist {
    private PlaylistDAO dao;
    private MusicaDAO musicaDAO;
    private ArtistaDAO artistaDAO;
    private GeneroDAO generoDAO;

    public RepositorioPlaylist(PlaylistDAO dao,MusicaDAO musicaDAO,  ArtistaDAO artistaDAO, GeneroDAO generoDAO) {
        this.dao = dao;
        this.musicaDAO = musicaDAO;
        this.artistaDAO = artistaDAO;
        this.generoDAO = generoDAO;
    }
    public Resultado cadastrarPlaylist(String nome, List<Musica> musicas){
        if(nome.isBlank() || nome.isEmpty()){
            return Resultado.erro("Nome inválido!");
        }

        Playlist pl = new Playlist(nome, musicas);

        return dao.criar(pl);
    }

    public Resultado listarPlaylists(){
        Resultado resultado = dao.listar();
        if(resultado.foiSucesso()){
            List<Playlist> lista = (List<Playlist>)resultado.comoSucesso().getObj();
            
            for(Playlist playlist:lista){
                Resultado r1 = musicaDAO.buscarMusicasPlaylist(playlist.getId());
                if(r1.foiErro()){
                    return r1;
                }
                ArrayList<Musica> musicas = (ArrayList<Musica>)r1.comoSucesso().getObj();
                for(Musica musica:musicas){
                    //buscar o artista da musica, para isso iremos utilizar o ArtistaDAO
                    Resultado r2 = artistaDAO.buscarArtistaMusica(musica.getId());
                    if(r2.foiErro()){
                        return r2;
                    }
                    Artista artista = (Artista)r2.comoSucesso().getObj();
                    musica.setArtista(artista);
    
                    //buscar o genero da musica, faremos o mesmo no GeneroDAO
                    Resultado r3 = generoDAO.buscarGeneroMusica(musica.getId());
                    if(r3.foiErro()){
                        return r3;
                    }
                    Genero genero = (Genero)r3.comoSucesso().getObj();
                    musica.setGenero(genero);
                }
                playlist.setMusicas(musicas);

            }

        }

        return resultado;
    }

}
