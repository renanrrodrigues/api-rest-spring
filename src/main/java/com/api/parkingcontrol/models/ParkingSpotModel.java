package com.api.parkingcontrol.models;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

// ** todos os comentários são apenas para fins didáticos **

@Entity // This class is a table in the database
@Table(name = "TB_PARKING_SPOT") // Table name in the database
public class ParkingSpotModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L; // Serializable ID (Java) (1L -> Long) (1 -> Integer) (1.0 -> Double)...

    // Attributes
    @Id // entity's primary key
    @GeneratedValue(strategy = GenerationType.AUTO) // Auto Increment
    private UUID id; // tipo UUID (Universally Unique Identifier) (128 bits) (16 bytes) (32 hexadecimal characters) (8-4-4-4-12) identificadores distribuídos globalmente

    @Column(nullable = false, unique = true, length = 10) // Atributo não pode ser nulo (not null) (unique = true) (length = 10)
    private String parkingSpotNumber; // Nome do atributo na tabela do banco de dados


    @Column(nullable = false, unique = true, length = 7)
    private String licensePlateCar;
    @Column(nullable = false, length = 70)
    private String brandCar;
    @Column(nullable = false, length = 70)
    private String modelCar;
    @Column(nullable = false, length = 70)
    private String colorCar;
    @Column(nullable = false)
    private LocalDateTime registrationDate;
    @Column(nullable = false, length = 130)
    private String responsibleName;
    @Column(nullable = false, length = 30)
    private String apartment;
    @Column(nullable = false, length = 30)
    private String block;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getParkingSpotNumber() {
        return parkingSpotNumber;
    }

    public void setParkingSpotNumber(String parkingSpotNumber) {
        this.parkingSpotNumber = parkingSpotNumber;
    }

    public String getLicensePlateCar() {
        return licensePlateCar;
    }

    public void setLicensePlateCar(String licensePlate) {
        this.licensePlateCar = licensePlate;
    }

    public String getBrandCar() {
        return brandCar;
    }

    public void setBrandCar(String brandCar) {
        this.brandCar = brandCar;
    }

    public String getModelCar() {
        return modelCar;
    }

    public void setModelCar(String modelCar) {
        this.modelCar = modelCar;
    }

    public String getColorCar() {
        return colorCar;
    }

    public void setColorCar(String colorCar) {
        this.colorCar = colorCar;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getResponsibleName() {
        return responsibleName;
    }

    public void setResponsibleName(String responsibleName) {
        this.responsibleName = responsibleName;
    }

    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }
}
