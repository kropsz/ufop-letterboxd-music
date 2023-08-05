package gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.dao.DaoFactory;
import model.dao.interfaces.MusicaDAO;
import model.entities.Musica;

public class MusicaViewController implements Initializable {

    @FXML
    private TextField txtPesquisar;
    @FXML
    private Button buttonPesquisar;
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

    private MusicaDAO musicaDAO = DaoFactory.createMusicaDAO();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        columnTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        columnArtista.setCellValueFactory(new PropertyValueFactory<>("artista"));
        columnEstilo.setCellValueFactory(new PropertyValueFactory<>("estilo"));
        columnAnoLanc.setCellValueFactory(new PropertyValueFactory<>("anoLanc"));

        List<Musica> musicas = musicaDAO.findAll();

        tabelaMusica.getItems().addAll(musicas);

    }

    @FXML
    public void searchButton() {
        String nomeMusica = txtPesquisar.getText();
        
        List<Musica> musicasEncontradas = musicaDAO.findByMusica(nomeMusica);
        exibirMusicasEncontradas(musicasEncontradas);
    }

    private void exibirMusicasEncontradas(List<Musica> musicas) {
        // Limpar a tabela de músicas
        tabelaMusica.getItems().clear();

        // Adicionar as músicas encontradas à tabela
        tabelaMusica.getItems().addAll(musicas);
    }
}