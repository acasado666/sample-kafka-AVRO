package com.kodebytes.acasado.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShopDTO {
    @NotNull(message = "iceCreamOrder.shop.shopId is mandatory")
    private Integer shopId;

    @NotNull(message = "iceCreamOrder.shop.shopName is mandatory")
    private String shopName;

    @Valid
    @NotNull(message = "iceCreamOrder.shop.address is mandatory")
    private AddressDTO address;
}
