package com.airwallex.demo

import com.airwallex.db.converter.JsonField
import com.airwallex.db.model.BaseEntity
import com.airwallex.db.repo.BaseRepository
import java.math.BigDecimal
import java.util.*
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jdbc.repository.query.Modifying
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.relational.core.mapping.Embedded
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository

enum class PaymentStatus { NEW, COMPLETED }
data class CurrencyAmount(val ccy: String, val amount: BigDecimal)
data class Payer(val name: String, val phone: String)
data class BankAccount(val number: String, val accountHolderName: String)

class Payment(
    @Embedded.Empty val ccyAmount: CurrencyAmount,
    val reference: String,
    @JsonField val payer: Payer?,
    @JsonField val bankAccount: BankAccount?,
    val accountId: UUID
) : BaseEntity<Payment, PaymentStatus>(PaymentStatus.NEW)

@Repository
interface PaymentRepository : BaseRepository<Payment, PaymentStatus>, MyOperations {

    fun findByReference(reference: String): List<Payment>

    fun findByAccountIdAndStatus(accountId: UUID, status: PaymentStatus, pageable: Pageable): Page<Payment>

    @Query("select * from payment where payer->>'name' = :name")
    fun findByPayerName(name: String): List<Payment>

    @Modifying
    @Query("delete from payment where payer->>'name' = :name")
    fun deleteByPayerName(name: String): Int
}

interface MyOperations {
    fun complexQuery(): List<Payment>
}

class MyOperationsImpl(private val jdbcTemplate: JdbcTemplate) : MyOperations {
    override fun complexQuery(): List<Payment> {
        // TODO: any complex query here
        return emptyList()
    }
}
