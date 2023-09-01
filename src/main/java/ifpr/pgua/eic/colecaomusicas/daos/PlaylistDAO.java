package ifpr.pgua.eic.colecaomusicas.daos;


import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.colecaomusicas.models.Playlist;

public interface PlaylistDAO {
    Resultado criar(Playlist pl);
    Resultado listar();
    Resultado atualizar(int id, Playlist pl);
    Resultado deletar(int id);
}
