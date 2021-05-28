package com.airwallex.demo

import com.airwallex.db.repo.BaseRepository.Companion.DEFAULT_SORT
import com.airwallex.db.repo.BaseRepositoryTest
import java.math.BigDecimal
import java.util.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.data.domain.PageRequest

class PaymentRepositoryTest : BaseRepositoryTest<PaymentRepository>() {

    @Test
    fun test1() {
        assertThat(repo.count()).isEqualTo(0)
    }

    @Test
    fun test2() {
        assertThat(repo.findAll()).isEmpty()
    }

    @Test
    fun test3() {
        val accountId = UUID.randomUUID()
        repeat(5) {
            repo.save(
                Payment(
                    ccyAmount = CurrencyAmount("USD", BigDecimal.TEN),
                    reference = "13",
                    payer = Payer("jeff", "123"),
                    bankAccount = BankAccount("234", "jeff"),
                    accountId = accountId
                )
            )
        }

        val page = repo.findByAccountIdAndStatus(
            accountId, PaymentStatus.NEW,
            PageRequest.of(1, 2, DEFAULT_SORT)
        )

        assertThat(page.totalPages).isEqualTo(3)
        assertThat(page.totalElements).isEqualTo(5)
        assertThat(page.numberOfElements).isEqualTo(2)
    }

    @Test
    fun test4() {
        assertThat(repo.findByReference("111")).isEmpty()
        assertThat(repo.findByPayerName("jeff")).isEmpty()
        assertThat(repo.deleteByPayerName("jeff")).isEqualTo(0)
    }

    @Test
    fun test5() {
        assertThat(repo.complexQuery()).isEmpty()
    }

    @Test
    fun test6() {
        val payment = repo.save(
            Payment(
                ccyAmount = CurrencyAmount("USD", BigDecimal.TEN),
                reference = "13",
                payer = Payer("jeff", "123"),
                bankAccount = BankAccount("234", "jeff"),
                accountId = UUID.randomUUID()
            )
        )

        val updated = repo.updateById(payment.id!!) {
            changeStatus(PaymentStatus.COMPLETED)
        }
        assertThat(updated!!.getStatus()).isEqualTo(PaymentStatus.COMPLETED)
    }
}
