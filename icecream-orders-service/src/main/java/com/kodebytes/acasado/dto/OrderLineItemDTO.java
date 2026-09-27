package com.kodebytes.acasado.dto;

import com.kodebytes.acasado.domain.generated.Flavor;
import com.kodebytes.acasado.domain.generated.Recipient;
import com.kodebytes.acasado.domain.generated.Size;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderLineItemDTO {

    @NotNull(message = "iceCreamOrder.orderLineItem.recipient is mandatory")
    private Recipient recipient;

    @NotNull(message = "iceCreamOrder.orderLineItem.flavor is mandatory")
    private Flavor flavor;

    @NotNull(message = "iceCreamOrder.orderLineItem.size is mandatory")
    private Size size;

    @NotNull(message = "iceCreamOrder.orderLineItem.quantity is mandatory")
    private Integer quantity;

    @NotNull
    private BigDecimal cost;

}
