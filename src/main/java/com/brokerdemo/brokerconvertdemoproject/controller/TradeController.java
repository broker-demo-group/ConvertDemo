package com.brokerdemo.brokerconvertdemoproject.controller;

import com.brokerdemo.brokerconvertdemoproject.response.BrokerResponse;
import com.brokerdemo.brokerconvertdemoproject.service.TradeService;
import org.okxbrokerdemo.handler.trade.PlaceOrderReq;
import org.okxbrokerdemo.handler.trade.PlaceOrderRes;
import org.okxbrokerdemo.handler.trade.QueryOrderDetailReq;
import org.okxbrokerdemo.handler.trade.QueryOrderDetailRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;
import java.util.List;

@RestController
@RequestMapping("/trade")
public class TradeController {

    @Autowired
    private TradeService tradeService;

    @GetMapping("/orders")
    public BrokerResponse<List<QueryOrderDetailRes>> getOrder(@RequestBody QueryOrderDetailReq req, @ApiIgnore Authentication authentication) {
        return BrokerResponse.success(tradeService.getOrder(req, authentication.getName()));
    }

    @PostMapping("/order")
    public BrokerResponse<List<PlaceOrderRes>> placeOrder(@RequestBody PlaceOrderReq req, @ApiIgnore Authentication authentication) {
        return BrokerResponse.success(tradeService.placeOrder(req, authentication.getName()));
    }
}
