package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.dao.DaoFactory;
import model.dao.interfaces.PlaylistDAO;
import model.entities.Playlist;
import model.entities.Usuario;

public class SelecaoPlaylistController implements Initializable{
    
    @FXML
    private TableView<Playlist> tablePlaylist;
    @FXML
    private TableColumn<Playlist, String> columnNome;
    @FXML
    private TableColumn<Playlist, String> columnDesc;
    @FXML
    private Button buttonSelect;

    private Usuario usuario;
    
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    private PlaylistDAO playlistDAO = DaoFactory.createPlaylistDAO();

        @Override
    public void initialize(URL location, ResourceBundle resources) {
        configureTableColumns();
    }


    public void configureTableColumns() {
        columnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        columnDesc.setCellValueFactory(new PropertyValueFactory<>("desc"));
        if(usuario != null){        
            List<Playlist> playlist = playlistDAO.findAllByUsername(usuario);
            tablePlaylist.getItems().addAll(playlist);
        }
    }

    @FXML
    private void onSelecionarButtonClick() {
        Playlist playlistSelecionada = tablePlaylist.getSelectionModel().getSelectedItem();
        if (playlistSelecionada != null) {
            abrirCenaExibirPlaylist(playlistSelecionada);
        }
    }

    private void abrirCenaExibirPlaylist(Playlist playlist) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/PlaylistView.fxml"));
        Parent root = loader.load();
        PlaylistViewController controller = loader.getController();
        controller.setPlaylist(playlist);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
}




    
}