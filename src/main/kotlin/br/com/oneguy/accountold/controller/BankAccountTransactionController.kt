package br.com.oneguy.accountold.controller

import br.com.oneguy.accountold.model.dto.BankAccountTransaction
import br.com.oneguy.accountold.service.BankAccountTransactionService
import org.springframework.web.bind.annotation.*

@RestController
class BankAccountTransactionController(
    private val service: BankAccountTransactionService
) {

    @GetMapping("/transactions")
    fun find(
        @RequestParam(name = "transactionId", required = false) transactionId: Long? = null,
        @RequestParam(name = "accountId", required = false) accountId: Long? = null,
        @RequestParam(name = "customerId", required = false) customerId: String? = null
    ) = service.find(
        transactionId = transactionId,
        accountId = accountId,
        customerId = customerId
    )

    @PostMapping("/transactions")
    @PutMapping("/transactions")
    fun save(value: BankAccountTransaction) = service.save(value)

    @DeleteMapping("/transactions/{transactionId}")
    fun remove(
        @PathVariable("transactionId", required = true) transactionId: Long
    ) = service.remove(transactionId)
}