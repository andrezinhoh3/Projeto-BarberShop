package br.com.barbearia.api_barbearia.repository;

import br.com.barbearia.api_barbearia.model.Servico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicoRepository extends JpaRepository<Servico, Integer> {
}
