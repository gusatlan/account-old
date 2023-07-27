package br.com.oneguy.accountold.service

import br.com.oneguy.accountold.mapper.transform
import br.com.oneguy.accountold.model.dto.BankAccountTransaction
import br.com.oneguy.accountold.model.persist.BankAccountTransactionPU
import br.com.oneguy.accountold.repository.BankAccountRepository
import br.com.oneguy.accountold.repository.BankAccountTransactionRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toFlux
import reactor.kotlin.core.publisher.toMono

@Service
class BankAccountTransactionService(
    private val repository: BankAccountTransactionRepository,
    private val bankAccountRepository: BankAccountRepository
) {

    fun find(
        transactionId: Long? = null,
        accountId: Long? = null,
        customerId: String? = null
    ): Flux<BankAccountTransaction> {
        val items: Collection<BankAccountTransactionPU> =
            if (transactionId != null && accountId != null && customerId != null) {
                repository.findByAccountCustomerIdAndAccountAccountIdAndTransactionId(
                    customerId = customerId,
                    accountId = accountId,
                    transactionId = transactionId
                )
            } else if (accountId != null && customerId != null) {
                repository.findByAccountCustomerIdAndAccountAccountId(
                    customerId = customerId,
                    accountId = accountId
                )
            } else if (accountId != null) {
                repository.findByAccountAccountId(accountId)
            } else if (customerId != null) {
                repository.findByAccountCustomerId(customerId)
            } else if (transactionId != null) {
                repository.findByTransactionId(transactionId)
            } else {
                repository.findAll()
            }

        return items.parallelStream().map(BankAccountTransactionPU::transform).toFlux()
    }

    @Transactional
    fun save(value: BankAccountTransaction): Mono<BankAccountTransaction> {
        return repository.save(value.transform(bankAccountRepository.findById(value.accountId).get())).transform().toMono()
    }

    @Transactional
    fun remove(transactionId: Long): Mono<Void> {
        repository.deleteById(transactionId)
        return Mono.empty()
    }
}