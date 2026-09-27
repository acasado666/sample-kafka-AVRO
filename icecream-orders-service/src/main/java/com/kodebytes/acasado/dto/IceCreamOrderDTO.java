package com.kodebytes.acasado.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kodebytes.acasado.domain.generated.PickUp;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IceCreamOrderDTO {

    private String id;

    @NotBlank(message = "iceCreamOrder.name is mandatory")
    private String name;
    private String nickName;

    @NotNull(message = "iceCreamOrder.shop is mandatory")
    @Valid
    private ShopDTO shop;

    @NotNull(message = "iceCreamOrder.orderLineItems is mandatory")
    @JsonProperty("orderLineItems")
    private List<@Valid OrderLineItemDTO> orderLineItems;

    @NotNull(message = "iceCreamOrder.pickUp is mandatory")
    @JsonProperty("pickUp")
    private PickUp pickUp;

    private String status;

}
