package school.sptech.exerciciojpa.adapter.out.persistence.entity;

import jakarta.persistence.*;

/**
 * Entidade JPA — mapeamento para a tabela do banco.
 * Separada do modelo de domínio (domain/model/Console.java) para desacoplar persistência do negócio.
 *
 * Só esta classe tem @Entity e @Id. O model de domínio é POJO puro.
 * Se trocar JPA por MongoDB, só muda esta classe e o SpringDataConsoleRepository.
 */
@Entity
@Table(name = "console")
public class ConsoleJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private String nome;
    private String fabricante;
    private Integer geracao;
    private Double preco;

    public ConsoleJpaEntity() {
    }

    public  ConsoleJpaEntity(Integer id, String nome, String fabricante, Integer geracao, Double preco) {
        this.id = id;
        this.nome = nome;
        this.fabricante = fabricante;
        this.geracao = geracao;
        this.preco = preco;
    }

    public Integer getId() {
        return id;
    }

    public ConsoleJpaEntity setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getNome() {
        return nome;
    }

    public ConsoleJpaEntity setNome(String nome) {
        this.nome = nome;
        return this;
    }

    public String getFabricante() {
        return fabricante;
    }

    public ConsoleJpaEntity setFabricante(String fabricante) {
        this.fabricante = fabricante;
        return this;
    }

    public Integer getGeracao() {
        return geracao;
    }

    public ConsoleJpaEntity setGeracao(Integer geracao) {
        this.geracao = geracao;
        return this;
    }

    public Double getPreco() {
        return preco;
    }

    public ConsoleJpaEntity setPreco(Double preco) {
        this.preco = preco;
        return this;
    }

    @Override
    public String toString() {
        return "ConsoleJpaEntity{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", fabricante='" + fabricante + '\'' +
                ", geracao=" + geracao +
                ", preco=" + preco +
                '}';
    }

}
