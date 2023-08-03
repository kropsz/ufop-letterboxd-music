package model.entities;

public class Musica {
    private Long id;
    private String titulo;
    private String artista;
    private String estilo;
    private Integer anoLanc;
    private String duração;

    public Musica() {
    }

    public Musica(Long id, String titulo, String artista, String estilo, Integer anoLanc, String duração) {
        this.id = id;
        this.titulo = titulo;
        this.artista = artista;
        this.estilo = estilo;
        this.anoLanc = anoLanc;
        this.duração = duração;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getArtista() {
        return artista;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }

    public String getEstilo() {
        return estilo;
    }

    public void setEstilo(String estilo) {
        this.estilo = estilo;
    }

    public Integer getAnoLanc() {
        return anoLanc;
    }

    public void setAnoLanc(Integer anoLanc) {
        this.anoLanc = anoLanc;
    }

    public String getDuração() {
        return duração;
    }

    public void setDuração(String duração) {
        this.duração = duração;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Musica other = (Musica) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}