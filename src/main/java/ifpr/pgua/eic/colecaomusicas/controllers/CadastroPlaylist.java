package ifpr.pgua.eic.colecaomusicas.controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.colecaomusicas.App;
import ifpr.pgua.eic.colecaomusicas.models.Musica;
import ifpr.pgua.eic.colecaomusicas.repositories.RepositorioMusicas;
import ifpr.pgua.eic.colecaomusicas.repositories.RepositorioPlaylist;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class CadastroPlaylist implements Initializable {

    @FXML
    private ListView<Musica> listaMusica;
    @FXML
    private TextField nomePlaylist;

    private RepositorioMusicas repositorioMusica;
    private RepositorioPlaylist repositorio;

    public CadastroPlaylist(RepositorioMusicas repositorioMusica, RepositorioPlaylist repositorio) {
        this.repositorioMusica = repositorioMusica;
        this.repositorio= repositorio;
    }

    @FXML
    void cadastrarPlaylist() {
        String nome = nomePlaylist.getText();
        List<Musica> musicas = listaMusica.getSelectionModel().getSelectedItems();
        Resultado rs = repositorio.cadastrarPlaylist(nome, musicas);
        
        String msg="";
        Alert alert;
        msg = rs.getMsg();
        if(rs.foiErro()){
            alert = new Alert(AlertType.ERROR,msg);
        }else{
            alert = new Alert(AlertType.INFORMATION,msg);
            
        }

        alert.showAndWait();
    }

    @FXML
    void voltar() {
        App.popScreen();
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        listaMusica.getItems().clear();
        listaMusica.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        Resultado r = repositorioMusica.listar();

        if (r.foiSucesso()) {
            List<Musica> lista = (List) r.comoSucesso().getObj();
            listaMusica.getItems().addAll(lista);
        } else {
            Alert alert = new Alert(AlertType.ERROR, r.getMsg());
            alert.showAndWait();
        }
    }

}
