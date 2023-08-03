package model.entities;

public class Reviews {
    private Long id;
    private Musica musica;
    private Usuario user;
    private String comentario;
    private Integer classificao;

    public Reviews() {
    }

    public Reviews(Long id, Musica musica, Usuario user, String comentario, Integer classificao) {
        this.id = id;
        this.musica = musica;
        this.user = user;
        this.comentario = comentario;
        this.classificao = classificao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Musica getMusica() {
        return musica;
    }

    public void setMusica(Musica musica) {
        this.musica = musica;
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Integer getClassificao() {
        return classificao;
    }

    public void setClassificao(Integer classificao) {
        this.classificao = classificao;
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
        Reviews other = (Reviews) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}