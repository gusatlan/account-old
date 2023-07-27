package br.com.oneguy.accountold.repository

import br.com.oneguy.accountold.model.persist.BankAccountPU
import org.springframework.data.jpa.repository.JpaRepository

interface BankAccountRepository : JpaRepository<BankAccountPU, Long> {


    fun findByCustomerId(customerId: String): Collection<BankAccountPU>
    fun findByAccountId(accountId: Long): Collection<BankAccountPU>

    fun findByCustomerIdAndAccountId(customerId: String, accountId:Long): Collection<BankAccountPU>

}