package ec.edu.ups.icc.fundamentos01.security.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ec.edu.ups.icc.fundamentos01.security.entities.RefreshTokenEntity;

import java.util.List;
import java.util.Optional;
/*
 * Repositorio encargado de gestionar refresh tokens.
 */
@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {

    /*
     * Busca un refresh token activo por su valor.
     *
     * Se usa durante /auth/refresh y /auth/logout.
     */
    Optional<RefreshTokenEntity> findByTokenAndRevokedFalse(String token);

    /*
     * Busca todos los refresh tokens activos de un usuario.
     *
     * Se usa para revocar tokens anteriores cuando el usuario inicia sesión.
     */
    List<RefreshTokenEntity> findByUserIdAndRevokedFalse(Long userId);
}