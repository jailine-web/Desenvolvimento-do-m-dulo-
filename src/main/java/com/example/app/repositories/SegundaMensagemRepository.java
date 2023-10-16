package com.example.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.app.model.entities.MensagemEntrevista;

public interface SegundaMensagemRepository extends JpaRepository<MensagemEntrevista, Integer>{

}
