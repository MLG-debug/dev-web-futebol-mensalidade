package dev.web.futebol_mensalidade.model;

import java.util.Date;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "jogador")
public class Jogador {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 60, name = "nome")
  private String nome;

  @Column(length = 60, name = "email")
  private String email;

  @Column(name = "data_nascimento")
  private Date data_nascimento;

  @OneToMany(mappedBy = "jogador", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Pagamento> pagamentos;

  public Jogador() {
  }

  public Jogador(String nome, String email, Date data_nascimento) {
    this.nome = nome;
    this.email = email;
    this.data_nascimento = data_nascimento;
  }

  public Long getid() {
    return id;
  }

  public void setid(Long id) {
    this.id = id;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Date getData_nascimento() {
    return data_nascimento;
  }

  public void setData_nascimento(Date data_nascimento) {
    this.data_nascimento = data_nascimento;
  }

}
