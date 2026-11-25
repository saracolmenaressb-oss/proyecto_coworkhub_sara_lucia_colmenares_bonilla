package com.campuslands.proyectocoworkhub.busisness.impl;

import com.campuslands.proyectocoworkhub.busisness.service.MemberService;
import com.campuslands.proyectocoworkhub.persistence.entity.Member;
import com.campuslands.proyectocoworkhub.persistence.entity.MemberStatus;
import com.campuslands.proyectocoworkhub.persistence.entity.MembershipPlan;
import com.campuslands.proyectocoworkhub.persistence.repository.BookingRepository;
import com.campuslands.proyectocoworkhub.persistence.repository.MemberRepository;
import com.campuslands.proyectocoworkhub.persistence.repository.MembershipPlanRepository;
import com.campuslands.proyectocoworkhub.presentation.dto.MemberRequestDTO;
import com.campuslands.proyectocoworkhub.presentation.dto.MemberResponseDTO;
import com.campuslands.proyectocoworkhub.presentation.exception.ResourceInUseException;
import com.campuslands.proyectocoworkhub.presentation.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final MembershipPlanRepository membershipPlanRepository;
    private final MemberMapper memberMapper;
    private final BookingRepository bookingRepository;

    // Registrar un miembro
    @Override
    public MemberResponseDTO registrar(MemberRequestDTO dto) {

        if (memberRepository.findByDocumentNumber(dto.documentNumber()).isPresent()) {
            throw new IllegalArgumentException("El documento ya está registrado");
        }

        if (memberRepository.findByEmail(dto.email()).isPresent()) {
            throw new IllegalArgumentException("El correo ya está registrado");
        }

        MembershipPlan membershipPlan = membershipPlanRepository.findById(dto.membershipPlanId())
                .orElseThrow(() -> new IllegalArgumentException("El plan de membresía no existe"));

        Member member = memberMapper.toEntity(dto);

        member.setMembershipPlan(membershipPlan);

        Member savedMember = memberRepository.save(member);

        return memberMapper.toResponseDTO(savedMember);
    }


    // Buscar miembro por ID
    @Override
    public MemberResponseDTO buscarPorId(Long id) {

        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("El miembro no existe"));

        return memberMapper.toResponseDTO(member);
    }


    // Buscar por documento
    @Override
    public MemberResponseDTO buscarPorDocumento(String documentNumber) {

        Member member = memberRepository.findByDocumentNumber(documentNumber)
                .orElseThrow(() -> new IllegalArgumentException("No existe un miembro con ese documento"));

        return memberMapper.toResponseDTO(member);
    }


    // Buscar por correo
    @Override
    public MemberResponseDTO buscarPorCorreo(String email) {

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("No existe un miembro con ese correo"));

        return memberMapper.toResponseDTO(member);
    }


    // Buscar por nombre
    @Override
    public List<MemberResponseDTO> buscarPorNombre(String fullName) {

        return memberRepository.findByFullNameContainingIgnoreCase(fullName)
                .stream()
                .map(memberMapper::toResponseDTO)
                .toList();
    }


    // Buscar por estado
    @Override
    public List<MemberResponseDTO> buscarPorEstado(MemberStatus status) {

        return memberRepository.findByStatus(status)
                .stream()
                .map(memberMapper::toResponseDTO)
                .toList();
    }


    // Buscar por nombre y estado
    @Override
    public List<MemberResponseDTO> buscarPorNombreYEstado(
            String fullName,
            MemberStatus status
    ) {

        return memberRepository.findByFullNameContainingIgnoreCaseAndStatus(
                        fullName,
                        status
                )
                .stream()
                .map(memberMapper::toResponseDTO)
                .toList();
    }


    // Actualizar miembro
    @Override
    public MemberResponseDTO actualizar(Long id, MemberRequestDTO dto) {

        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("El miembro no existe"));

        memberRepository.findByDocumentNumber(dto.documentNumber())
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(existing -> {
                    throw new IllegalArgumentException(
                            "El documento ya pertenece a otro miembro"
                    );
                });

        memberRepository.findByEmail(dto.email())
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(existing -> {
                    throw new IllegalArgumentException(
                            "El correo ya pertenece a otro miembro"
                    );
                });

        MembershipPlan membershipPlan = membershipPlanRepository.findById(dto.membershipPlanId())
                .orElseThrow(() -> new IllegalArgumentException("El plan de membresía no existe"));

        member.setDocumentNumber(dto.documentNumber());
        member.setFullName(dto.fullName());
        member.setEmail(dto.email());
        member.setStatus(dto.status());
        member.setMembershipPlan(membershipPlan);

        Member updatedMember = memberRepository.save(member);

        return memberMapper.toResponseDTO(updatedMember);
    }
    @Override
    public void deleteMember(Long id) {

        Member member = memberRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("El miembro no existe")
                );

        boolean hasBookings = bookingRepository.existsByMember_Id(id);

        if (hasBookings) {
            throw new ResourceInUseException(
                    "No se puede eliminar el miembro porque tiene reservas asociadas"
            );
        }

        memberRepository.delete(member);
    }
}
