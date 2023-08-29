package ifpr.pgua.eic.colecaomusicas.controllers;

import ifpr.pgua.eic.colecaomusicas.App;
import ifpr.pgua.eic.colecaomusicas.models.Musica;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class CadastroPlaylist {

    @FXML
    private TableView<Musica> listaMusicas;

    @FXML
    private TextField nomePlaylist;

    @FXML
    void cadastrarPlaylist() {

    }

    @FXML
    void voltar() {
        App.popScreen();
    }

}
