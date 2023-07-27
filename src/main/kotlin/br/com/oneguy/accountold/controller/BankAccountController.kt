package br.com.oneguy.accountold.controller

import br.com.oneguy.accountold.model.dto.BankAccount
import br.com.oneguy.accountold.service.BankAccountService
import org.springframework.web.bind.annotation.*

@RestController
class BankAccountController(
    private val service: BankAccountService
) {

    @GetMapping("/accounts")
    fun find(
        @RequestParam(name = "customerId", required = false) customerId: String? = null,
        @RequestParam(name = "accountId", required = false) accountId: Long? = null
    ) = service.find(customerId, accountId)

    @PostMapping("/accounts")
    @PutMapping("/accounts")
    fun save(@RequestBody value: BankAccount) = service.save(value)

    @DeleteMapping("accounts/{accountId}")
    fun remove(@PathVariable("accountId", required = true) accountId: Long) = service.remove(accountId)
}