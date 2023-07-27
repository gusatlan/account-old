package br.com.oneguy.accountold.model.dto

import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

class BankAccount(
    val customerId: String,
    val accountId: Long? = null,
    val since: LocalDateTime = LocalDateTime.now(),
    val expiredAt: LocalDateTime? = null,
    val transactions: Collection<BankAccountTransaction> = emptySet()
) {

    private fun computeBalance(): Optional<BigDecimal> {
        return transactions
            .stream()
            .sorted()
            .map(BankAccountTransaction::computeValue)
            .reduce { a, b ->
                a.add(b)
            }
    }

    val balance = computeBalance().orElse(BigDecimal.ZERO)


}