package gui;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;

import model.entities.Musica;
import model.entities.Usuario;

public class PlaylistViewController {
    @FXML
    private Label txtPlaylistName;
    @FXML
    private TextArea txtDesc;
    @FXML
    private Label txtDescLabel;
    @FXML
    private Button buttonAddSong;
    @FXML
    private Button buttonChangeDesc;
    @FXML
    private TableView<Musica> tabelaMusica;
    @FXML
    private TableColumn<Musica, String> columnTitulo;
    @FXML
    private TableColumn<Musica, String> columnArtista;
    @FXML
    private TableColumn<Musica, String> columnEstilo;
    @FXML
    private TableColumn<Musica, Integer> columnAnoLanc;

    private Usuario usuario;
    
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void changeDescription() {
        String desc = txtDesc.getText();
        txtDesc.clear();
        txtDescLabel.setText(desc);
    }

    public void addSong() {
        abreTelaMusica();
    }

    public void abreTelaMusica() {
    }
}