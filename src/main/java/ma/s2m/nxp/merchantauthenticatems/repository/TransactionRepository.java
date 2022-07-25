package ma.s2m.nxp.merchantauthenticatems.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ma.s2m.nxp.merchantauthenticatems.domain.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}
