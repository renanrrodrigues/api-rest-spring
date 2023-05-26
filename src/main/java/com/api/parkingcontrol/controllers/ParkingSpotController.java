package com.api.parkingcontrol.controllers;

import com.api.parkingcontrol.dtos.ParkingSpotDto;
import com.api.parkingcontrol.models.ParkingSpotModel;
import com.api.parkingcontrol.services.ParkingSpotService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;


// ** todos os comentários são apenas para fins didáticos **
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/parking-spot") // mapeia a rota, ou seja, o endpoint
public class ParkingSpotController {

    final ParkingSpotService parkingSpotService; // ponto de injeção de dependência

    // ponto de injeção de dependência via construtor
    public ParkingSpotController(ParkingSpotService parkingSpotService) {
        this.parkingSpotService = parkingSpotService;
    }

    @PostMapping
    public ResponseEntity<Object> saveParkingSpot(@RequestBody @Valid ParkingSpotDto parkingSpotDto) {
        // @RequestBody indica que o corpo da requisição será convertido para o objeto ParkingSpotDto
        // @Valid indica que o objeto ParkingSpotDto será validado

        if(parkingSpotService.existsByLicensePlateCar(parkingSpotDto.getLicensePlateCar())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Já existe um veículo com essa placa cadastrado!");
        } // verifica se já existe um veículo com essa placa cadastrado
        if(parkingSpotService.existsByParkingSpotNumber(parkingSpotDto.getParkingSpotNumber())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Já existe uma vaga com esse número cadastrado!");
        } // verifica se já existe uma vaga com esse número cadastrado
        if(parkingSpotService.existsByApartamentAndBlock(parkingSpotDto.getApartment(), parkingSpotDto.getBlock())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Já existe um veículo cadastrado para esse apartamento e bloco!");
        } // verifica se já existe um veículo cadastrado para esse apartamento e bloco

        var parkingSpotModel = new ParkingSpotModel();
        BeanUtils.copyProperties(parkingSpotDto, parkingSpotModel); // copia as propriedades do DTO para o Model (converte DTO para Model)
        parkingSpotModel.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC"))); // seta a data de registro
        return ResponseEntity.status(HttpStatus.CREATED).body(parkingSpotService.save(parkingSpotModel)); // retorna o objeto salvo

    }
    @GetMapping
    public ResponseEntity<List<ParkingSpotModel>> getAllParkingSpots() {
        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotService.findAll());
    }
}
