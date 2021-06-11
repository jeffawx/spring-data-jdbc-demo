package com.airwallex.demo

import com.airwallex.db.converter.JsonField
import com.airwallex.db.fsm.*
import com.airwallex.db.model.Metadata
import com.airwallex.demo.OrderAction.*
import com.airwallex.demo.OrderStatus.*
import java.math.BigDecimal
import org.springframework.stereotype.Repository

// Although we define status and action in Enum, they can be of any type!
enum class OrderStatus { NEW, PAID, REFUNDED, CANCELLED }

enum class OrderAction { PAY, CANCEL, REFUND }

class Order(
    val amount: BigDecimal,
    @JsonField val metadata: Metadata = Metadata()
) : TransitionAwareEntity<Order, OrderStatus>(
    NEW, // initial state
    transitions { // valid state transitions
        +(NEW to PAID on PAY)
        +(NEW to CANCELLED on CANCEL)
        +(PAID to REFUNDED on REFUND onlyIf { // transition guard
            metadata["refundable"] == "true"
        })
    }
)

@Repository
interface OrderRepository : TransitionAwareRepository<Order, OrderStatus>
