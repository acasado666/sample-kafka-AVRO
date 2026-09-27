package com.kodebytes.acasado.service;

import com.kodebytes.acasado.domain.generated.*;
import com.kodebytes.acasado.dto.IceCreamOrderDTO;
import com.kodebytes.acasado.dto.IceCreamOrderUpdateDTO;
import com.kodebytes.acasado.producer.IceCreamOrderProducer;
import com.kodebytes.acasado.producer.IceCreamOrderUpdateProducer;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class IceCreamOrderService {

    IceCreamOrderProducer iceCreamOrderProducer;
    IceCreamOrderUpdateProducer iceCreamOrderUpdateProducer;

    public IceCreamOrderService(IceCreamOrderProducer iceCreamOrderProducer, IceCreamOrderUpdateProducer iceCreamOrderUpdateProducer) {
        this.iceCreamOrderProducer = iceCreamOrderProducer;
        this.iceCreamOrderUpdateProducer = iceCreamOrderUpdateProducer;
    }


    public IceCreamOrderDTO newOrder(IceCreamOrderDTO iceCreamOrderDTO) {
        IceCreamOrder iceCreamOrderAvro = mapToIceCreamOrder(iceCreamOrderDTO);
        iceCreamOrderDTO.setId(iceCreamOrderAvro.getId().toString());
        //DB saving this order
        iceCreamOrderProducer.sendMessage(iceCreamOrderAvro);
        return iceCreamOrderDTO;
    }

    private IceCreamOrder mapToIceCreamOrder(IceCreamOrderDTO iceCreamOrderDTO) {

        Shop shop = getShop(iceCreamOrderDTO);

        var orderLineItems = buildOrderLineItems(iceCreamOrderDTO);

        return IceCreamOrder.newBuilder()
                .setId(UUID.randomUUID())
                .setName(iceCreamOrderDTO.getName())
                .setNickName(iceCreamOrderDTO.getNickName() != null ? iceCreamOrderDTO.getNickName() : "")
                .setShop(shop)
                .setOrderLineItems(orderLineItems)
                .setStatus(iceCreamOrderDTO.getStatus() != null ? iceCreamOrderDTO.getStatus() : "NEW")
                .setOrderedTime(Instant.now())
                .setPickUp(iceCreamOrderDTO.getPickUp())
                .build();

    }

    private List<OrderLineItem> buildOrderLineItems(IceCreamOrderDTO iceCreamOrderDTO) {

        return iceCreamOrderDTO.getOrderLineItems()
                .stream().map(orderLineItem ->
                        new OrderLineItem(
                                orderLineItem.getRecipient(),
                                orderLineItem.getFlavor(),
                                orderLineItem.getSize(),
                                orderLineItem.getQuantity(),
                                orderLineItem.getCost()
                        )
                )
                .collect(Collectors.toList());
    }

    private Shop getShop(IceCreamOrderDTO iceCreamOrderDTO) {
        var shopDTO = iceCreamOrderDTO.getShop();

        Address address = new Address(shopDTO.getAddress().getAddressLine1(),
                shopDTO.getAddress().getCity(),
                shopDTO.getAddress().getCountry(),
                shopDTO.getAddress().getZip()
        );
        var shop = new Shop(shopDTO.getShopId(), shopDTO.getShopName(), address);
        return shop;
    }

    public IceCreamOrderUpdateDTO updateOrder(String orderId, IceCreamOrderUpdateDTO iceCreamOrderUpdateDTO) {
        var iceCreamOrderUpdateAvro = mapToIceCreamOrderUpdate(orderId, iceCreamOrderUpdateDTO);
        iceCreamOrderUpdateProducer.sendUpdateMessage(orderId, iceCreamOrderUpdateAvro);
        return iceCreamOrderUpdateDTO;
    }

    private IceCreamUpdateEvent mapToIceCreamOrderUpdate(String orderId, IceCreamOrderUpdateDTO iceCreamOrderUpdateDTO) {

        return IceCreamUpdateEvent
                .newBuilder()
                .setId(UUID.fromString(orderId))
                .setStatus(iceCreamOrderUpdateDTO.getOrderStatus())
                .build();
    }
}
