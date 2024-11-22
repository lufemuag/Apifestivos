package apidiafestivo.apidiafestivo.core.interfaces.repositorios;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import apidiafestivo.apidiafestivo.dominio.Tipo;

public interface ITipoRepositorio extends JpaRepository<Tipo, Integer> {

    List<Tipo> findByNombreContaining(String nombre);  // Método para búsqueda parcial por nombre
}