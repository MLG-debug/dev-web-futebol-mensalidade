package dev.web.futebol_mensalidade.control;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.web.futebol_mensalidade.model.Jogador;
import dev.web.futebol_mensalidade.repository.JogadorRepository;

@RestController
@RequestMapping("/api")
public class JogadorController {

  @Autowired
  JogadorRepository jogadorRepository;

  /*
   * GET /api/jogadores : listar todos os jogadores
   * ?nome=Joao
   * &email=@gmail.com
   */
  @GetMapping("/jogadores")
  public ResponseEntity<List<Jogador>> getAllJogadores(@RequestParam(required = false) String nome, String email) {
    try {
      List<Jogador> jogadores = new ArrayList<Jogador>();

      if (nome != null) {
        jogadorRepository.findByNomeContaining(nome).forEach(jogadores::add);
      } else if (email != null) {
        jogadorRepository.findByEmailContaining(email).forEach(jogadores::add);
      } else {
        jogadorRepository.findAll().forEach(jogadores::add);
      }

      if (jogadores.isEmpty()) {
        return new ResponseEntity<List<Jogador>>(HttpStatus.NO_CONTENT);
      }

      return new ResponseEntity<List<Jogador>>(jogadores, HttpStatus.OK);
    } catch (

    Exception e) {
      System.out.println("Erro: " + e);
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /*
   * GET /api/jogadores/{id} : buscar um jogador
   */
  @GetMapping("/jogadores/{id}")
  public ResponseEntity<Jogador> getOneJogador(@PathVariable("id") Long id) {
    try {
      Optional<Jogador> jogadorOptional = jogadorRepository.findById(id);

      if (jogadorOptional.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }

      Jogador jogador = jogadorOptional.get();
      return new ResponseEntity<>(jogador, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /*
   * POST /api/jogadores : criar jogador
   */
  @PostMapping("/jogadores")
  public ResponseEntity<Jogador> createJogador(@RequestBody Jogador jogador_dados) {
    try {

      String email = jogador_dados.getEmail();
      if (jogadorRepository.findByEmail(email) != null) {
        return new ResponseEntity<>(null, HttpStatus.CONFLICT);
      }

      Jogador _jogador = jogadorRepository.save(
          new Jogador(jogador_dados.getNome(), jogador_dados.getEmail(), jogador_dados.getData_nascimento()));
      return new ResponseEntity<>(_jogador, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /*
   * PUT /api/jogadores/{id} : editar um jogador
   */
  @PutMapping("/jogadores/{id}")
  public ResponseEntity<Jogador> updateJogador(@PathVariable("id") Long id, @RequestBody Jogador jogador_dados) {
    try {
      Optional<Jogador> jogadorOptional = jogadorRepository.findById(id);

      if (jogadorOptional.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }

      if (jogadorOptional.isPresent()) {
        Jogador j = jogadorOptional.get();
        j.setNome(jogador_dados.getNome());
        j.setEmail(jogador_dados.getEmail());
        j.setData_nascimento(jogador_dados.getData_nascimento());

        return new ResponseEntity<>(jogadorRepository.save(j), HttpStatus.OK);
      } else
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /*
   * DELETE /api/jogadores/{id} : deletar um jogador
   */
  @DeleteMapping("/jogadores/{id}")
  public ResponseEntity<HttpStatus> deleteJogador(@PathVariable("id") Long id) {
    try {
      Optional<Jogador> jogadorOptional = jogadorRepository.findById(id);

      if (jogadorOptional.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }

      jogadorRepository.deleteById(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /*
   * DELETE /api/jogadores : deletar todos os jogadores
   */
  @DeleteMapping("/jogadores")
  public ResponseEntity<HttpStatus> deleteAllJogadores() {
    try {
      jogadorRepository.deleteAll();
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

}
