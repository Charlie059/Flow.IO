package org.flowio.tenant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.flowio.tenant.entity.RefreshToken;
import org.flowio.tenant.entity.User;

public interface RefreshTokenService extends IService<RefreshToken> {
    /**
     * Create a new {@link RefreshToken} for the given user
     * @param user The user to create token for
     * @return created {@link RefreshToken}
     */
    RefreshToken createToken(User user);

    /**
     * Find {@link RefreshToken} by token string
     * @param token The token string
     * @return The {@link RefreshToken}
     */
    RefreshToken getByToken(String token);

    /**
     * Check if the {@link RefreshToken} is valid
     * @param token The {@link RefreshToken} to check
     * @return Whether the {@link RefreshToken} is valid
     */
    boolean isTokenValid(RefreshToken token);
}
