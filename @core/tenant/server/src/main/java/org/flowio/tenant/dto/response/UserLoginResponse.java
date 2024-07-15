package org.flowio.tenant.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.flowio.tenant.dto.TokenDto;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginResponse {
    private Long id;
    private Long tenantId;
    private TokenDto accessToken;
    private TokenDto refreshToken;
}
