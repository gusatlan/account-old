package br.com.oneguy.accountold.model.persist

import br.com.oneguy.accountold.model.dto.BankAccountTransactionEnum
import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "account_transaction")
class BankAccountTransactionPU(
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.AUTO)
    val transactionId: Long?=null,
    @field:Column(name = "transaction_type") val type: BankAccountTransactionEnum,
    @field:Column(name = "transaction_date")
    @field:Temporal(TemporalType.TIMESTAMP)
    val date: LocalDateTime,
    @field:Column(name = "transaction_value") val value: BigDecimal,
    @field:ManyToOne()
    @field:JoinColumn(name = "account_id", referencedColumnName = "id")
    val account: BankAccountPU
) : Comparable<BankAccountTransactionPU> {

    private var createdAt = LocalDateTime.now()
        @Column(name = "created_at", nullable = false)
        get

    private var updatedAt = LocalDateTime.now()
        @Column(name = "updated_at", nullable = false)
        get

    @PreUpdate
    private fun updateTrigger() {
        updatedAt = LocalDateTime.now()
    }

    @PrePersist
    private fun createTrigger() {
        createdAt = LocalDateTime.now()
        updatedAt = LocalDateTime.now()
    }

    fun computeValue(): BigDecimal {
        return when (type) {
            BankAccountTransactionEnum.DEPOSIT -> value.abs()
            BankAccountTransactionEnum.WITHDRAWN -> value.abs().negate()
        }
    }

    override fun equals(other: Any?) =
        other != null && other is BankAccountTransactionPU && transactionId == other.transactionId

    override fun hashCode() = transactionId.hashCode()

    override fun toString() = """{"transactionId": $transactionId, "type": "$type", "date": "$date", "value": $value}"""

    override fun compareTo(other: BankAccountTransactionPU): Int {
        val compares = listOf(
            date.compareTo(other.date),
            type.compareTo(other.type)
        )
        var comp = 0

        for (c in compares) {
            comp = c
            if (comp != 0) {
                break
            }
        }

        return comp
    }

}