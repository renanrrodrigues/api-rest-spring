package com.api.parkingcontrol.controllers;

import com.api.parkingcontrol.dtos.ParkingSpotDto;
import com.api.parkingcontrol.models.ParkingSpotModel;
import com.api.parkingcontrol.services.ParkingSpotService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


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

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneParkingSpot(@PathVariable(value = "id") UUID id){
        Optional<ParkingSpotModel> parkingSpotModelOptional = parkingSpotService.findById(id);
        if(!parkingSpotModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não foi encontrado nenhum veículo com esse ID!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotModelOptional.get());
    }

    // implementação do método delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteParkingSpot(@PathVariable(value = "id") UUID id){
        Optional<ParkingSpotModel> parkingSpotModelOptional = parkingSpotService.findById(id);
        if(!parkingSpotModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não foi encontrado nenhum veículo com esse ID!");
        }
        parkingSpotService.delete(parkingSpotModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Veículo deletado com sucesso!");
    }

    // implementação do método put
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateParkingSpot(@PathVariable(value = "id") UUID id,
                                                    @RequestBody @Valid ParkingSpotDto parkingSpotDto){
        Optional<ParkingSpotModel> parkingSpotModelOptional = parkingSpotService.findById(id);
        if (!parkingSpotModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não foi encontrado nenhum veículo com esse ID!");
        }
        var parkingSpotModel = parkingSpotModelOptional.get(); // recebe um novo objeto ParkingSpotModel

        parkingSpotModel.setParkingSpotNumber(parkingSpotDto.getParkingSpotNumber());
        parkingSpotModel.setLicensePlateCar(parkingSpotDto.getLicensePlateCar());
        parkingSpotModel.setModelCar(parkingSpotDto.getModelCar());
        parkingSpotModel.setBrandCar(parkingSpotDto.getBrandCar());
        parkingSpotModel.setColorCar(parkingSpotDto.getColorCar());
        parkingSpotModel.setResponsibleName(parkingSpotDto.getResponsibleName());
        parkingSpotModel.setApartment(parkingSpotDto.getApartment());
        parkingSpotModel.setBlock(parkingSpotDto.getBlock());

        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotService.save(parkingSpotModel)); // retorna o objeto salvo

    }

    @PutMapping("/v2/{id}")
    public ResponseEntity<Object> updateParkingSpotv2(@PathVariable(value = "id") UUID id,
                                                    @RequestBody @Valid ParkingSpotDto parkingSpotDto){
        Optional<ParkingSpotModel> parkingSpotModelOptional = parkingSpotService.findById(id);
        if (!parkingSpotModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não foi encontrado nenhum veículo com esse ID!");
        }
        var parkingSpotModel = new ParkingSpotModel(); // recebe um novo objeto ParkingSpotModel
        BeanUtils.copyProperties(parkingSpotDto, parkingSpotModel); // copia as propriedades do DTO para o Model (converte DTO para Model)
        parkingSpotModel.setId(parkingSpotModelOptional.get().getId()); // seta o id do objeto
        parkingSpotModel.setRegistrationDate(parkingSpotModelOptional.get().getRegistrationDate()); // seta a data de registro


        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotService.save(parkingSpotModel)); // retorna o objeto salvo

    }




}
