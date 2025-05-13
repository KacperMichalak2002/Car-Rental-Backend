package com.Gr3ID12A.car_rental.domain.dto.discount;

import com.Gr3ID12A.car_rental.domain.dto.CustomerDiscountsDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DiscountDto {
    private UUID id;
    private String code;
    private int percent_discount;
    private int const_value_discount;
    private Date date_of_beginning;
    private Date date_of_end;
    private String status;
    private String description;
    private Set<CustomerDiscountsDto> customerDiscounts;
}
