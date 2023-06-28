package dev.web.futebol_mensalidade.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "pagamento")
public class Pagamento {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "ano")
  private Short ano;

  @Column(name = "mes")
  private Integer mes;

  @Column(name = "valor")
  private Float valor;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "jogador")
  private Jogador jogador;

  public Pagamento() {
  }

  public Pagamento(Short ano, Integer mes, Float valor, Jogador jogador) {
    this.ano = ano;
    this.mes = mes;
    this.valor = valor;
    this.jogador = jogador;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Short getAno() {
    return ano;
  }

  public void setAno(Short ano) {
    this.ano = ano;
  }

  public Integer getMes() {
    return mes;
  }

  public void setMes(Integer mes) {
    this.mes = mes;
  }

  public Float getValor() {
    return valor;
  }

  public void setValor(Float valor) {
    this.valor = valor;
  }

  public Jogador getJogador() {
    return jogador;
  }

  public void setJogador(Jogador jogador) {
    this.jogador = jogador;
  }
}
