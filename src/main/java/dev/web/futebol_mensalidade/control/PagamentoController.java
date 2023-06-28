package dev.web.futebol_mensalidade.control;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
import dev.web.futebol_mensalidade.model.Pagamento;
import dev.web.futebol_mensalidade.repository.JogadorRepository;
import dev.web.futebol_mensalidade.repository.PagamentoRepository;

@RestController
@RequestMapping("/api")
public class PagamentoController {

	@Autowired
	PagamentoRepository pagamentoRepository;
	@Autowired
	JogadorRepository jogadorRepository;

	/*
	 * GET /api/pagamentos : listar todos os pagamentos
	 * ?ano=2021
	 * &mes=1
	 * &id_jogador=1
	 */
	@GetMapping("/pagamentos")
	public ResponseEntity<List<Pagamento>> getAllPagamentos(@RequestParam(required = false) Short ano, Integer mes,
			Long id_jogador) {
		try {
			List<Pagamento> pagamentos = new ArrayList<Pagamento>();

			if (ano != null) {
				pagamentoRepository.findByAno(ano).forEach(pagamentos::add);
			} else if (mes != null) {
				pagamentoRepository.findByMes(mes).forEach(pagamentos::add);
			} else if (id_jogador != null) {
				pagamentoRepository.findByJogadorId(id_jogador).forEach(pagamentos::add);
			} else {
				pagamentoRepository.findAll().forEach(pagamentos::add);
			}

			if (pagamentos.isEmpty())
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);

			return new ResponseEntity<>(pagamentos, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/*
	 * GET /api/pagamentos/{id} : buscar um pagamento
	 */
	@GetMapping("/pagamentos/{id}")
	public ResponseEntity<Pagamento> getOnePagamento(@PathVariable("id") long id) {
		try {
			Optional<Pagamento> pagamentoOptional = pagamentoRepository.findById(id);

			if (pagamentoOptional.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}

			Pagamento pagamento = pagamentoOptional.get();
			return new ResponseEntity<>(pagamento, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/*
	 * POST /api/pagamentos : criar um pagamento
	 */
@PostMapping("/pagamentos")
public ResponseEntity<Pagamento> createPagamento(@RequestBody Map<String, Object> pagamento_dados) {
    try {
        Short ano = Short.valueOf(pagamento_dados.get("ano").toString());
        Integer mes = Integer.valueOf(pagamento_dados.get("mes").toString());
        Float valor = Float.valueOf(pagamento_dados.get("valor").toString());
        Long jogadorId = Long.valueOf(pagamento_dados.get("jogador_id").toString());

        Optional<Jogador> jogador = jogadorRepository.findById(jogadorId);

        if (jogador.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        Pagamento pagamento = new Pagamento(ano, mes, valor, jogador.get());

        Pagamento _pagamento = pagamentoRepository.save(pagamento);
        return new ResponseEntity<>(_pagamento, HttpStatus.CREATED);
    } catch (Exception e) {
				System.out.println(e);
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

	/*
	 * EDIT /api/pagamentos/{id} : editar um pagamento
	 */
	@PutMapping("/pagamentos/{id}")
	public ResponseEntity<Pagamento> updatePagamento(@PathVariable("id") long id,
			@RequestBody Pagamento pagamento_dados) {
		try {
			Optional<Pagamento> pagamentoOptional = pagamentoRepository.findById(id);

			if (pagamentoOptional.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}

			Pagamento pagamento = pagamentoOptional.get();
			pagamento.setAno(pagamento_dados.getAno());
			pagamento.setMes(pagamento_dados.getMes());
			pagamento.setValor(pagamento_dados.getValor());

			return new ResponseEntity<>(pagamentoRepository.save(pagamento), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/*
	 * DELETE /api/pagamentos/{id} : deletar um pagamento
	 */
	@DeleteMapping("/pagamentos/{id}")
	public ResponseEntity<Pagamento> deletePagamento(@PathVariable("id") long id) {
		try {
      Optional<Pagamento> pagamentoOptional = pagamentoRepository.findById(id);

			if (pagamentoOptional.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}

			pagamentoRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/*
	 * DELETE /api/pagamentos : deletar todos os pagamentos
	 */
	@DeleteMapping("/pagamentos")
	public ResponseEntity<HttpStatus> deleteAllPagamentos() {
		try {
			pagamentoRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
