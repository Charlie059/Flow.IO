package org.flowio.tenant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.flowio.tenant.entity.AccessToken;
import org.flowio.tenant.entity.User;

public interface AccessTokenService extends IService<AccessToken> {
    /**
     * Create a new {@link AccessToken} for the given user
     *
     * @param user The user to create token for
     * @return created {@link AccessToken}
     */
    AccessToken createToken(User user);

    /**
     * Find {@link AccessToken} by token string
     * @param token The token string
     * @return The {@link AccessToken}
     */
    AccessToken getByToken(String token);

    /**
     * Check if the {@link AccessToken} is valid
     * @param token The {@link AccessToken} to check
     * @return Whether the {@link AccessToken} is valid
     */
    boolean isTokenValid(AccessToken token);
}
