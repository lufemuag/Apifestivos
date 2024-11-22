package apidiafestivo.apidiafestivo.aplicacion.seguridad;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import apidiafestivo.apidiafestivo.dominio.Usuario;

public class UsuarioDetalles implements UserDetails {

    private final String nombreUsuario;
    private final String clave;
    private final List<GrantedAuthority> permisos;

    public UsuarioDetalles(Usuario usuario) {
        this.nombreUsuario = usuario.getUsuario();
        this.clave = usuario.getClave();
        this.permisos = (usuario.getRoles() != null && !usuario.getRoles().isEmpty())
                ? Arrays.stream(usuario.getRoles().split(","))
                        .map(String::trim) // Eliminamos espacios en blanco adicionales.
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList())
                : Collections.emptyList(); // Evitar que permisos sea `null`.
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return permisos;
    }

    @Override
    public String getPassword() {
        return clave;
    }

    @Override
    public String getUsername() {
        return nombreUsuario;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

