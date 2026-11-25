package com.campuslands.proyectocoworkhub.presentation.mapper;

import com.campuslands.proyectocoworkhub.persistence.entity.MembershipPlan;
import com.campuslands.proyectocoworkhub.presentation.dto.MemberShipPlanRequestDTO;
import com.campuslands.proyectocoworkhub.presentation.dto.MemberShipPlanResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class MembershipPlanMapper {
    public MembershipPlan toEntity(MemberShipPlanRequestDTO dto){
        return new MembershipPlan(
                dto.name().trim(),
                dto.includedHoursPerMonth(),
                dto.overageDiscount(),
                dto.maxActiveBookings()
        );
    }
    public MemberShipPlanResponseDTO toResponseDTO(MembershipPlan membershipPlan){
        return new MemberShipPlanResponseDTO(
          membershipPlan.getId(),
          membershipPlan.getName(),
          membershipPlan.getIncludedHourPerMonth(),
          membershipPlan.getOverageDiscount(),
          membershipPlan.getMaxActiveBooking()
        );
    }
}
