package com.kartaca.kartacaoffer.repositories;

import com.kartaca.kartacaoffer.entities.Offer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfferRepository extends JpaRepository<Offer, Long> {
}