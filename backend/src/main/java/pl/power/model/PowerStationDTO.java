package pl.power.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PowerStationDTO implements DTOInterface, Serializable {

    private Long id;

    @NotBlank
    @Size(max = 1000)
    private String name;

    @NotNull
    private BigDecimal power;

}
