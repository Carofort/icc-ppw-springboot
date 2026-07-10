package ec.edu.ups.icc.fundamentos01.users.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ec.edu.ups.icc.fundamentos01.users.entities.UserEntity;

/*
 * Repositorio encargado de gestionar la persistencia
 * de usuarios usando Spring Data JPA.
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByIdAndDeletedFalse(Long id);

    Optional<UserEntity> findByIdAndDeleted(Long id, boolean deleted);

    Optional<UserEntity> findByNameAndId(String name, Long id);

    boolean existsByIdAndDeletedFalse(Long id);

    Optional<UserEntity> findById(Long id);

    Optional<UserEntity> findByEmailAndDeletedFalse(String email);

    // Verificar si email ya está registrado (usado en registro)
    boolean existsByEmail(String email);

}