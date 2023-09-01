package ifpr.pgua.eic.colecaomusicas.repositories;

import java.util.List;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.colecaomusicas.daos.PlaylistDAO;
import ifpr.pgua.eic.colecaomusicas.models.Musica;
import ifpr.pgua.eic.colecaomusicas.models.Playlist;

public class RepositorioPlaylist {
    private PlaylistDAO dao;

    public RepositorioPlaylist(PlaylistDAO dao) {
        this.dao = dao;
    }
    public Resultado cadastrarPlaylist(String nome, List<Musica> musicas){
        if(nome.isBlank() || nome.isEmpty()){
            return Resultado.erro("Nome inválido!");
        }

        Playlist pl = new Playlist(nome, musicas);

        return dao.criar(pl);
    }

    public Resultado listarPlaylists(){
        return dao.listar();
    }

}
