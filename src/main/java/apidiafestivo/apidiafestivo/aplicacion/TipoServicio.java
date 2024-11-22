package apidiafestivo.apidiafestivo.aplicacion;

import java.util.List;

import org.springframework.stereotype.Service;

import apidiafestivo.apidiafestivo.core.interfaces.repositorios.ITipoRepositorio;
import apidiafestivo.apidiafestivo.core.interfaces.servicios.ITipoServicio;
import apidiafestivo.apidiafestivo.dominio.Tipo;

@Service
public class TipoServicio implements ITipoServicio {

    private ITipoRepositorio repositorio;

    public TipoServicio(ITipoRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public List<Tipo> listar() {
        // Usamos el repositorio para obtener todos los tipos
        return repositorio.findAll();
    }

    @Override
    public Tipo obtener(Integer id) {
        // Usamos el repositorio para buscar el tipo por id
        return repositorio.findById(id).orElse(null);  // Devuelve null si no se encuentra
    }

    @Override
    public List<Tipo> buscar(String nombre) {
        // Usamos el repositorio para buscar tipos por nombre (parcial)
        return repositorio.findByNombreContaining(nombre);
    }

    @Override
    public Tipo agregar(Tipo tipo) {
        // Usamos el repositorio para guardar el nuevo tipo
        return repositorio.save(tipo);
    }

    @Override
    public Tipo modificar(Tipo tipo) {
        // Usamos el repositorio para guardar o actualizar el tipo
        return repositorio.save(tipo);
    }

    @Override
    public boolean eliminar(Integer id) {
        // Usamos el repositorio para eliminar el tipo si existe
        if (repositorio.existsById(id)) {
            repositorio.deleteById(id);
            return true;
        }
        return false;
    }
}

