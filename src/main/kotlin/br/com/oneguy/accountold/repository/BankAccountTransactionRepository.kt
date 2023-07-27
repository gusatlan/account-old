package br.com.oneguy.accountold.repository

import br.com.oneguy.accountold.model.persist.BankAccountTransactionPU
import org.springframework.data.jpa.repository.JpaRepository

interface BankAccountTransactionRepository: JpaRepository<BankAccountTransactionPU, Long> {

    fun findByTransactionId(transactionId: Long) : Collection<BankAccountTransactionPU>

    fun findByAccountAccountId(accountId: Long) : Collection<BankAccountTransactionPU>

    fun findByAccountCustomerId(customerId: String) : Collection<BankAccountTransactionPU>

    fun findByAccountCustomerIdAndAccountAccountId(customerId: String, accountId: Long) : Collection<BankAccountTransactionPU>
    fun findByAccountCustomerIdAndAccountAccountIdAndTransactionId(customerId: String, accountId: Long, transactionId:Long) : Collection<BankAccountTransactionPU>
}