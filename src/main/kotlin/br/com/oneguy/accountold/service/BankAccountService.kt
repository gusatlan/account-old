package br.com.oneguy.accountold.service

import br.com.oneguy.accountold.mapper.transform
import br.com.oneguy.accountold.model.dto.BankAccount
import br.com.oneguy.accountold.model.persist.BankAccountPU
import br.com.oneguy.accountold.repository.BankAccountRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toFlux
import reactor.kotlin.core.publisher.toMono

@Service
class BankAccountService(
    private val repository: BankAccountRepository
) {

    fun find(customerId: String? = null, accountId: Long? = null): Flux<BankAccount> {
        val items: Collection<BankAccountPU> = if (customerId != null && accountId != null) {
            repository.findByCustomerIdAndAccountId(customerId, accountId)
        } else if (customerId != null) {
            repository.findByCustomerId(customerId)
        } else if (accountId != null) {
            repository.findByAccountId(accountId)
        } else {
            repository.findAll()
        }

        return items.parallelStream().map(BankAccountPU::transform).toFlux()
    }

    @Transactional
    fun save(value: BankAccount): Mono<BankAccount> = repository.save(value.transform()).transform().toMono()

    @Transactional
    fun remove(accountId: Long): Mono<Void> {
        repository.deleteById(accountId)
        return Mono.empty()
    }
}
