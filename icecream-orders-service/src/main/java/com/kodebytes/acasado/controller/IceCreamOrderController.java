package com.kodebytes.acasado.controller;

import com.kodebytes.acasado.dto.IceCreamOrderDTO;
import com.kodebytes.acasado.service.IceCreamOrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/v1/ice_cream_orders")
public class IceCreamOrderController {
    private IceCreamOrderService iceCreamOrderService;

    public IceCreamOrderController(IceCreamOrderService iceCreamOrderService) {
        this.iceCreamOrderService = iceCreamOrderService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public IceCreamOrderDTO newOrder(@Valid @RequestBody IceCreamOrderDTO iceCreamOrderDTO) {
        return iceCreamOrderService.newOrder(iceCreamOrderDTO);
    }
}
