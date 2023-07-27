package br.com.oneguy.accountold.mapper

import br.com.oneguy.accountold.model.dto.BankAccount
import br.com.oneguy.accountold.model.dto.BankAccountTransaction
import br.com.oneguy.accountold.model.persist.BankAccountPU
import br.com.oneguy.accountold.model.persist.BankAccountTransactionPU

fun BankAccountPU.transform(): BankAccount {
    val items = try {
        transactions.stream().map(BankAccountTransactionPU::transform)?.toList()
    } catch (e: Exception) {
        emptyList()
    }

    return BankAccount(
        customerId = customerId,
        accountId = accountId!!,
        since = since,
        expiredAt = expiredAt,
        transactions = items!!
    )
}

fun BankAccount.transform(): BankAccountPU {
    val value = BankAccountPU(
        customerId = customerId,
        accountId = accountId,
        since = since,
        expiredAt = expiredAt
    )

    val items = transactions.stream().map { it.transform(value) }.toList()

    return value.applyTransactions(items)
}

fun BankAccountPU.applyTransactions(items: Collection<BankAccountTransactionPU>) : BankAccountPU {
    return BankAccountPU(
        customerId = customerId,
        accountId = accountId,
        since = since,
        expiredAt = expiredAt,
        transactions = items
    )
}

fun BankAccountTransactionPU.transform(): BankAccountTransaction {
    return BankAccountTransaction(
        customerId = account.customerId,
        accountId = account.accountId!!,
        transactionId = transactionId,
        transactionType = type,
        transactionDate = date,
        value = value
    )
}

fun BankAccountTransaction.transform(account: BankAccountPU): BankAccountTransactionPU {
    return BankAccountTransactionPU(
        transactionId = transactionId,
        type = transactionType,
        date = transactionDate,
        value = value,
        account = account
    )
}
