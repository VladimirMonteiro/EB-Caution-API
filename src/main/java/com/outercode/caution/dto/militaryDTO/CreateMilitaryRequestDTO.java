package com.outercode.caution.dto.militaryDTO;

import com.outercode.caution.entities.enums.MilitaryStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;

public record CreateMilitaryRequestDTO(
        @NotBlank(message = "O nome de guerra é obrigatório.")
        String warName,
        @CPF(message = "CPF inválido.")
        String cpf,
        @Email(message = "E-mail inválido.")
        String email,
        @NotBlank(message = "O telefone é obrigatório.")
        String phone,
        @NotBlank(message = "A companhia é obrigatório.")
        String cia,
        @NotBlank(message = "O pelotão/seção é obrigatório.")
        String pel,
        @NotBlank(message = "A graduação é obrigatório.")
        String grad,
        @NotNull(message = "O status do militar é obrigatório.")
        MilitaryStatus status) {
}