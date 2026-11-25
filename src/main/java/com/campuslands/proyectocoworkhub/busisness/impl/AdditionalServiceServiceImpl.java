package com.campuslands.proyectocoworkhub.busisness.impl;

import com.campuslands.proyectocoworkhub.busisness.service.AdditionalServiceService;
import com.campuslands.proyectocoworkhub.persistence.entity.AdditionalService;
import com.campuslands.proyectocoworkhub.persistence.entity.Booking;
import com.campuslands.proyectocoworkhub.persistence.repository.AdditionalServiceRepository;
import com.campuslands.proyectocoworkhub.presentation.dto.AdditionalServiceRequestDTO;
import com.campuslands.proyectocoworkhub.presentation.dto.AdditionalServiceResponseDTO;
import com.campuslands.proyectocoworkhub.presentation.mapper.AdditionalServiceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdditionalServiceServiceImpl implements AdditionalServiceService {

    private final AdditionalServiceMapper additionalServiceMapper;
    private final AdditionalServiceRepository additionalServiceRepository;
    @Override
    public AdditionalServiceResponseDTO createAdditionalService(
            AdditionalServiceRequestDTO dto) {

        // 1. Normalizar el nombre
        String name = dto.name().trim();

        // 2. Comprobar si ya existe
        if (additionalServiceRepository.existsByNameIgnoreCase(name)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Ya existe un servicio adicional con ese nombre"
            );
        }

        // 3. Crear un DTO con el nombre normalizado
        AdditionalServiceRequestDTO normalizedDTO =
                new AdditionalServiceRequestDTO(
                        name,
                        dto.unitPrice()
                );

        // 4. Convertir el DTO en entidad
        AdditionalService additionalService =
                additionalServiceMapper.toEntity(normalizedDTO);

        // 5. Guardar la entidad
        AdditionalService savedAdditionalService =
                additionalServiceRepository.save(additionalService);

        // 6. Convertir la entidad guardada en DTO
        return additionalServiceMapper.toResponseDTO(
                savedAdditionalService
        );
    }
    @Override
    public AdditionalServiceResponseDTO getAdditionalServiceById(Long id) {

        AdditionalService additionalService =
                additionalServiceRepository.findById(id)
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "No se encontró el servicio adicional con ID: " + id
                        ));

        return additionalServiceMapper.toResponseDTO(additionalService);
    }

    @Override
    public List<AdditionalServiceResponseDTO> getAllAdditionalServices() {
        return additionalServiceRepository.findAll()
                .stream()
                .map(additionalServiceMapper::toResponseDTO)
                .toList();
    }

    @Override
    public List<AdditionalServiceResponseDTO> getAdditionalServicesByBooking(
            Long bookingId) {
        return additionalServiceRepository
                .findAdditionalServicesByBookingId(bookingId)
                .stream()
                .map(additionalServiceMapper::toResponseDTO)
                .toList();

    }
    @Override
    public AdditionalServiceResponseDTO updateAdditionalService(
            Long id,
            AdditionalServiceRequestDTO dto) {

        // 1. Buscar el servicio que queremos actualizar
        AdditionalService additionalService =
                additionalServiceRepository.findById(id)
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "No se encontró el servicio adicional con ID: " + id
                        ));

        // 2. Normalizar el nombre
        String name = dto.name().trim();

        // 3. Comprobar si el nombre pertenece a otro servicio
        additionalServiceRepository.findByNameIgnoreCase(name)
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(existing -> {
                    throw new ResponseStatusException(
                            HttpStatus.CONFLICT,
                            "Ya existe otro servicio adicional con ese nombre"
                    );
                });

        // 4. Actualizar los atributos
        additionalService.setName(name);
        additionalService.setUnitPrice(dto.unitPrice());

        // 5. Guardar los cambios
        AdditionalService updatedAdditionalService =
                additionalServiceRepository.save(additionalService);

        // 6. Devolver la respuesta
        return additionalServiceMapper.toResponseDTO(
                updatedAdditionalService
        );
    }
    @Override
    public void deleteAdditionalService(Long id) {

        // 1. Verificar que el servicio exista
        AdditionalService additionalService =
                additionalServiceRepository.findById(id)
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "No se encontró el servicio adicional con ID: " + id
                        ));

        // 2. Verificar si está siendo utilizado
        if (additionalServiceRepository.isServiceInUse(id)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "No se puede eliminar el servicio adicional porque está siendo utilizado en una reserva"
            );
        }

        // 3. Eliminar el servicio
        additionalServiceRepository.delete(additionalService);
    }
}
