package ec.edu.ups.icc.fundamentos01.security.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ec.edu.ups.icc.fundamentos01.users.entities.UserEntity;
import ec.edu.ups.icc.fundamentos01.users.repositories.UserRepository;

/**
 * UserDetailsServiceImpl: Carga usuarios desde la base de datos
 */
@Service // Componente de Spring (se inyecta automáticamente)
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        /**
         * 1. Buscar usuario por email en la base de datos
         * 
         * Nota: Los roles se cargan automáticamente por FetchType.EAGER
         */
        UserEntity user = userRepository.findByEmailAndDeletedFalse(email)
                /**
                 * .orElseThrow(): Si Optional está vacío, lanza excepción
                 */
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Usuario no encontrado con email: " + email));

        /**
         * 2. Convertir UserEntity → UserDetailsImpl
         * 
         * UserDetailsImpl.build(user):
         * - Factory method que convierte nuestro UserEntity
         * - Extrae roles y los convierte a authorities
         * - Retorna objeto compatible con Spring Security
         * 
         */
        return UserDetailsImpl.build(user);
    }
}