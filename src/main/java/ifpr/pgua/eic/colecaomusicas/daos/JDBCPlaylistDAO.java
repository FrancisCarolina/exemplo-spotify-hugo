package ifpr.pgua.eic.colecaomusicas.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.colecaomusicas.models.Musica;
import ifpr.pgua.eic.colecaomusicas.models.Playlist;

public class JDBCPlaylistDAO implements PlaylistDAO{
    private static final String INSERTPL = "INSERT INTO playlist(nome) VALUES (?)";
    private static final String INSERTPLMSC = "INSERT INTO playlist_musicas(musicaId, playlistId) VALUES (?,?)";
    private static final String SELECTSQLPL = "SELECT * FROM playlist";

    private FabricaConexoes fabrica;

    public JDBCPlaylistDAO(FabricaConexoes fabrica) {
        this.fabrica = fabrica;
    }

    private boolean cadastrarMusicasSql(int id, List<Musica>musicas){
        try (Connection con = fabrica.getConnection()) {
            int count =0;
            PreparedStatement pstm = con.prepareStatement(INSERTPLMSC, Statement.RETURN_GENERATED_KEYS);

            for (Musica musica : musicas) {
                pstm.setInt(1, musica.getId());
                pstm.setInt(2, id);

                int ret = pstm.executeUpdate();

                if(ret == 1){
                    ResultSet rs = pstm.getGeneratedKeys();
                    rs.next();
                    count++;
                }else{
                    return false;
                }
            }
            if(count==musicas.size()){
                return true;
            }
            return false;
            


        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public Resultado criar(Playlist pl) {
        try (Connection con = fabrica.getConnection()) {

            PreparedStatement pstm = con.prepareStatement(INSERTPL, Statement.RETURN_GENERATED_KEYS);
            
            pstm.setString(1, pl.getNome());

            int ret = pstm.executeUpdate();

            if(ret == 1){
                ResultSet rs = pstm.getGeneratedKeys();
                rs.next();
                int id = rs.getInt(1);

                pl.setId(id);

                boolean sucesso=cadastrarMusicasSql(pl.getId(),pl.getMusicas());
                if(sucesso){
                    return Resultado.sucesso("Playlist cadastrada", pl);
                }
            }
            return Resultado.erro("Erro desconhecido!");


        } catch (SQLException e) {
            return Resultado.erro(e.getMessage());
        }
    }

    @Override
    public Resultado listar() {
        try {
            Connection con = fabrica.getConnection();

            PreparedStatement pstm = con.prepareStatement(SELECTSQLPL);

            ResultSet rs = pstm.executeQuery();

            ArrayList<Playlist> lista = new ArrayList<>();

            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");

                Playlist pl = new Playlist(id, nome,null);
                lista.add(pl);
            }
            rs.close();
            pstm.close();
            con.close();

            return Resultado.sucesso("Playlist listada!", lista);
        } catch (SQLException e) {
            return Resultado.erro(e.getMessage());
        }
    }

    @Override
    public Resultado atualizar(int id,Playlist pl) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'atualizar'");
    }

    @Override
    public Resultado deletar(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deletar'");
    }

    

}
