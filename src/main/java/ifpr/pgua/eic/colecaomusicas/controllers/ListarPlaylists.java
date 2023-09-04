package ifpr.pgua.eic.colecaomusicas.controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.colecaomusicas.App;
import ifpr.pgua.eic.colecaomusicas.models.Musica;
import ifpr.pgua.eic.colecaomusicas.models.Playlist;
import ifpr.pgua.eic.colecaomusicas.repositories.RepositorioPlaylist;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;

public class ListarPlaylists  implements Initializable{
    @FXML
    private ListView<Musica> listaMusicas;

    @FXML
    private ListView<Playlist> listaPlaylists;

    private RepositorioPlaylist repositorio;

    public ListarPlaylists(RepositorioPlaylist repositorio){
        this.repositorio = repositorio;
    }

    @FXML
    void mostrarMusicas() {
        listaMusicas.getItems().clear();
        Playlist pl = listaPlaylists.getSelectionModel().getSelectedItem();
        listaMusicas.getItems().addAll(pl.getMusicas());
    }

    @FXML
    void voltar() {
        App.popScreen();
    }

    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        listaPlaylists.getItems().clear();
        Resultado r = repositorio.listarPlaylists();

        if(r.foiSucesso()){
            List<Playlist> lista = (List)r.comoSucesso().getObj();
            listaPlaylists.getItems().addAll(lista);
        }else{
            Alert alert = new Alert(AlertType.ERROR, r.getMsg());
            alert.showAndWait();
        }
    
    }
}
