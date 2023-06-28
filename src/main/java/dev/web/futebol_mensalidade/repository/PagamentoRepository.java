package dev.web.futebol_mensalidade.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.web.futebol_mensalidade.model.Pagamento;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
  List<Pagamento> findByAno(Short ano);
  List<Pagamento> findByMes(Integer  mes);
  List<Pagamento> findByValor(Float valor);
  List<Pagamento> findByJogadorId(Long jogadorId);
}
