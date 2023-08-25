package com.proje.salad_App.payload.request;

import com.proje.salad_App.entity.concretes.UserRole;
import com.proje.salad_App.payload.request.abstracts.BaseUserRequest;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class UserRequest extends BaseUserRequest {


}
