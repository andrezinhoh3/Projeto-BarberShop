package br.com.barbearia.api_barbearia.repository;

import br.com.barbearia.api_barbearia.model.Agendamento;
import br.com.barbearia.api_barbearia.status.StatusAgendamento;
import br.com.barbearia.api_barbearia.status.StatusPagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Integer> {
    // Buscar todos os agendamentos por status
    List<Agendamento> findByStatus(StatusAgendamento status);

    // Buscar todos pendentes
    default List<Agendamento> findPendentes() {
        return findByStatus(StatusAgendamento.PENDENTE);
    }

}
