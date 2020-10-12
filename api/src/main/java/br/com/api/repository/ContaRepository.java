package br.com.api.repository;

import br.com.api.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface  ContaRepository  extends JpaRepository<Conta, Long> {
    public Optional<Conta> findByHash(String hash);
}
