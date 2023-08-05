package br.com.oneguy.accountold.model.persist

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*
import java.util.stream.Stream

@Entity
@Table(name = "account")
class BankAccountPU(
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.AUTO)
    @field:Column(name = "id")
    val accountId: Long? = null,
    @field:Column(name = "customer_id", length = 14, nullable = false) val customerId: String,
    @field:Column(name = "since", nullable = false) val since: LocalDateTime,
    @field:Column(name = "expired", nullable = true) val expiredAt: LocalDateTime? = null,
    @field:OneToMany(
        mappedBy = "account",
        fetch = FetchType.EAGER
    ) val transactions: Collection<BankAccountTransactionPU> = emptySet()
) {

    private var createdAt = LocalDateTime.now()
        @Column(name = "created_at")
        get

    private var updatedAt = LocalDateTime.now()
        @Column(name = "updated_at")
        get

    @PreUpdate
    private fun updateTrigger() {
        updatedAt = LocalDateTime.now()
    }

    @PrePersist
    private fun createTrigger() {
        createdAt = LocalDateTime.now()
    }


    private fun sortTransactions(): Stream<BankAccountTransactionPU> {
        return transactions
            .stream()
            .filter { it.value >= BigDecimal.ZERO }
            .sorted()
    }

    private fun computeTransactions(): Optional<BigDecimal> {
        return sortTransactions()
            .map(BankAccountTransactionPU::computeValue)
            .reduce { a, b -> a.add(b) }
    }

    override fun equals(other: Any?) = other != null && other is BankAccountPU && accountId == other.accountId
    override fun hashCode() = accountId.hashCode()
    override fun toString() =
        """{"accountId": $accountId, "customerId": "$customerId", "since": "$since", "expiredAt" : "$expiredAt"}"""
}