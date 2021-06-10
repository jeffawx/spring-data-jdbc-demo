package com.airwallex.demo

import com.airwallex.db.repo.BaseRepositoryTest
import com.airwallex.demo.OrderAction.PAY
import com.airwallex.demo.OrderAction.REFUND
import com.airwallex.demo.OrderStatus.PAID
import com.airwallex.demo.OrderStatus.REFUNDED
import java.math.BigDecimal
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class OrderRepositoryTest : BaseRepositoryTest<OrderRepository>() {

    @Test
    fun test() {
        val order = repo.save(Order(BigDecimal.TEN))
        val orderId = order.id!!

        assertThat(order.nextTransitions()).hasSize(2) // PAY & CANCEL
        assertTrue(order.canExecute(PAY))

        val paidOrder = repo.execute(orderId, PAY)
        assertThat(paidOrder.status).isEqualTo(PAID)
        assertFalse(paidOrder.canExecute(REFUND)) // non-refundable by default

        paidOrder.metadata.add("refundable", "true")
        val refundableOrder = repo.save(paidOrder)
        assertTrue(refundableOrder.canExecute(REFUND)) // refundable now
        assertThat(repo.execute(orderId, REFUND).status).isEqualTo(REFUNDED)
    }
}
