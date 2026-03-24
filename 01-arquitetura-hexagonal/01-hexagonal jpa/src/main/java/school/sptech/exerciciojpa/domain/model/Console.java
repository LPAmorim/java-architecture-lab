package school.sptech.exerciciojpa.domain.model;

/**
 * Entidade de domínio pura — sem dependência de framework.
 * Representa um Console no contexto do negócio.
 *
 * IMPORTANTE: Nenhuma anotação de JPA (@Entity, @Id) nem de validação (@NotBlank).
 * Isso garante que se trocar o banco ou framework, esta classe continua intacta.
 * O centro do hexágono não sabe que JPA, Spring ou HTTP existem.
 */
public class Console {

    private Integer id;
    private String nome;
    private String fabricante;
    private Integer geracao;
    private Double preco;

    public Console() {
    }

    public Console(Integer id, String nome, String fabricante, Integer geracao, Double preco) {
        this.id = id;
        this.nome = nome;
        this.fabricante = fabricante;
        this.geracao = geracao;
        this.preco = preco;
    }

    public Integer getId() {
        return id;
    }

    public Console setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getNome() {
        return nome;
    }

    public Console setNome(String nome) {
        this.nome = nome;
        return this;
    }

    public String getFabricante() {
        return fabricante;
    }

    public Console setFabricante(String fabricante) {
        this.fabricante = fabricante;
        return this;
    }

    public Integer getGeracao() {
        return geracao;
    }

    public Console setGeracao(Integer geracao) {
        this.geracao = geracao;
        return this;
    }

    public Double getPreco() {
        return preco;
    }

    public Console setPreco(Double preco) {
        this.preco = preco;
        return this;
    }

    @Override
    public String toString() {
        return "Console{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", fabricante='" + fabricante + '\'' +
                ", geracao=" + geracao +
                ", preco=" + preco +
                '}';
    }

}