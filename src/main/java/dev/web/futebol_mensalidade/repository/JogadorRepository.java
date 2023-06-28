package dev.web.futebol_mensalidade.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.web.futebol_mensalidade.model.Jogador;

@Repository
public interface JogadorRepository extends JpaRepository<Jogador, Long> {
	// Optional<Jogador> findById(Long id);

	List<Jogador> findByNomeContaining(String nome);

	List<Jogador> findByEmailContaining(String nome);

	Jogador findByEmail(String email);

	// Jogador create(Jogador jogador);

	// void deleteById(Jogador jogador);
}
