package br.com.oneguy.accountold.model.dto

import java.math.BigDecimal
import java.time.LocalDateTime

class BankAccountTransaction(
    val customerId: String,
    val accountId: Long,
    val transactionId: Long?=null,
    val transactionType: BankAccountTransactionEnum,
    val transactionDate: LocalDateTime= LocalDateTime.now(),
    val value: BigDecimal
) : Comparable<BankAccountTransaction> {

    fun computeValue(): BigDecimal {
        return when (transactionType) {
            BankAccountTransactionEnum.DEPOSIT -> value.abs()
            BankAccountTransactionEnum.WITHDRAWN -> value.abs().negate()
        }
    }

    override fun equals(other: Any?): Boolean {
        return other != null &&
                other is BankAccountTransaction &&
                transactionId == other.transactionId &&
                accountId == other.accountId &&
                customerId == other.customerId
    }

    override fun hashCode() = transactionId.hashCode() or accountId.hashCode() or customerId.hashCode()

    override fun toString(): String {
        return """
            {
                "transactionId": $transactionId,
                "accountId": $accountId,
                "customerId" : $customerId,
                "transactionType": "$transactionType",
                "transactionDate": "$transactionDate",
                "value": $value
            }""".trimMargin()
    }

    override fun compareTo(other: BankAccountTransaction): Int {
        val compares = listOf(
            customerId.compareTo(other.customerId),
            accountId.compareTo(other.accountId),
            transactionDate.compareTo(other.transactionDate),
            transactionType.compareTo(transactionType)
        )
        var comp = 0

        for(c in compares) {
            comp = c
            if(comp != 0) {
                break
            }
        }

        return comp
    }
}
