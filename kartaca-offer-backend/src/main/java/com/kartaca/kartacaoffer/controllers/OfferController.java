package com.kartaca.kartacaoffer.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kartaca.kartacaoffer.entities.Offer;
import com.kartaca.kartacaoffer.entities.Product;
import com.kartaca.kartacaoffer.entities.User;
import com.kartaca.kartacaoffer.repositories.OfferRepository;
import com.kartaca.kartacaoffer.repositories.ProductRepository;
import com.kartaca.kartacaoffer.repositories.UserRepository;
import com.kartaca.kartacaoffer.services.AuthService;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Optional;

@RestController
public class OfferController {
    private final SimpMessagingTemplate template;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OfferRepository offerRepository;
    private final ObjectMapper objectMapper;

    public OfferController(SimpMessagingTemplate template, UserRepository userRepository, ProductRepository productRepository, OfferRepository offerRepository, ObjectMapper objectMapper, AuthService authService) {
        this.template = template;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.offerRepository = offerRepository;
        this.objectMapper = objectMapper;
    }

    @MessageMapping("/ws/{productId}")
    public void addOffer(@DestinationVariable Long productId, Offer offer) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isEmpty()) {
            throw new RuntimeException("Product not found!");
        }
        Product product = optionalProduct.get();
        offer.setUser(offer.getUser());
        offer.setProduct(product);
        offer.setOfferPrice(offer.getOfferPrice());
        offerRepository.save(offer);
        Double offerPrice = offer.getOfferPrice();
        Double currentPrice = product.getPrice();
        Double newPrice = currentPrice + offerPrice;
        product.setPrice(newPrice);
        productRepository.save(product);
        String productJson;
        try {
            productJson = objectMapper.writeValueAsString(product);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting product to JSON");
        }
        String offerJson;
        try {
            offerJson = objectMapper.writeValueAsString(offer);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting offer to JSON");
        }
        // Send the new product price to all clients
        template.convertAndSend("/topic/offer/" + productId, offerJson);
    }

}
