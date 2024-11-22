package apidiafestivo.apidiafestivo.aplicacion;

import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Service;
import apidiafestivo.apidiafestivo.core.interfaces.repositorios.IFestivoRepositorio;
import apidiafestivo.apidiafestivo.core.interfaces.servicios.IFestivoServicio;
import apidiafestivo.apidiafestivo.dominio.Festivo;


@Service
public class FestivoServicio implements IFestivoServicio {

    private static final int DIAS_EN_MES = 31;
    private final IFestivoRepositorio repositorio;

    public FestivoServicio(IFestivoRepositorio repositorio) {
        this.repositorio = repositorio;
    }


    @Override
    public Date getDomingo(Integer año) {
        // Lógica para calcular Domingo de Ramos
        int a = año % 19;
        int b = año % 4;
        int c = año % 7;
        int d = (19 * a + 24) % 30;
        int dias = d + (2 * b + 4 * c + 6 * d + 5) % 7;
        int dia = 15 + dias;
        int mes = 3;

        if (dia > DIAS_EN_MES) {
            dia -= DIAS_EN_MES;
            mes = 4;
        }
        return java.sql.Date.valueOf(java.time.LocalDate.of(año, mes, dia));  // Cambié a Date
    }



    @Override
    public Date getDomingoRamos(Integer año) {
        return getDomingo(año); 
    }

    @Override
    public Date incrementarDias(Date fecha, Integer dias) {
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.add(java.util.Calendar.DAY_OF_MONTH, dias);
        return calendar.getTime();  
    }

    @Override
    public Date siguienteLunes(Date fecha) {
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.setTime(fecha);
        int daysUntilNextMonday = java.util.Calendar.MONDAY - calendar.get(java.util.Calendar.DAY_OF_WEEK);
        if (daysUntilNextMonday <= 0) {
            daysUntilNextMonday += 7;  // Si ya es lunes, añade 7 días
        }
        calendar.add(java.util.Calendar.DAY_OF_MONTH, daysUntilNextMonday);
        return calendar.getTime();  // Cambié a Date
    }

    @Override
    public String verificar(int año, int mes, int dia) {
        try {
            java.util.Date fechas = java.sql.Date.valueOf(java.time.LocalDate.of(año, mes, dia)); 
        } catch (Exception e) {
            return "Fecha No válida";
        }

        List<Festivo> listaFestivos = repositorio.findAll();
        Date domingoRamos = getDomingo(año);
        Date domingoPascua = incrementarDias(domingoRamos, 7);

        for (Festivo festivo : listaFestivos) {
            Date fechaFestivo = java.sql.Date.valueOf(java.time.LocalDate.of(año, festivo.getMes(), festivo.getDia())); 
            boolean esFestivo = false;

            switch (festivo.getTipo().getId()) {
                case 1:  // Tipo 1
                    esFestivo = fechaFestivo.equals(java.sql.Date.valueOf(java.time.LocalDate.of(año, mes, dia)));
                    break;
                case 2:  // Tipo 2 (Lunes de Pascua)
                    esFestivo = siguienteLunes(fechaFestivo).equals(java.sql.Date.valueOf(java.time.LocalDate.of(año, mes, dia)));  // Cambié a Date
                    break;
                case 3:  // Tipo 3
                    esFestivo = incrementarDias(domingoPascua, festivo.getDiasPascua()).equals(java.sql.Date.valueOf(java.time.LocalDate.of(año, mes, dia)));  // Cambié a Date
                    break;
                case 4:  // Tipo 4
                    esFestivo = siguienteLunes(incrementarDias(domingoPascua, festivo.getDiasPascua()))
                            .equals(java.sql.Date.valueOf(java.time.LocalDate.of(año, mes, dia)));  // Cambié a Date
                    break;
            }

            if (esFestivo) {
                return "Es Festivo";
            }
        }

        return "No es Festivo";
    }

    // Métodos no implementados, lanzan UnsupportedOperationException
    @Override
    public List<Festivo> listar() {
        throw new UnsupportedOperationException("Método 'listar' no implementado");
    }

    @Override
    public Festivo obtener(Integer id) {
        throw new UnsupportedOperationException("Método 'obtener' no implementado");
    }

    @Override
    public List<Festivo> buscar(String nombre) {
        throw new UnsupportedOperationException("Método 'buscar' no implementado");
    }

    @Override
    public Festivo agregar(Festivo tipo) {
        throw new UnsupportedOperationException("Método 'agregar' no implementado");
    }

    @Override
    public Festivo modificar(Festivo tipo) {
        throw new UnsupportedOperationException("Método 'modificar' no implementado");
    }

    @Override
    public boolean eliminar(Integer id) {
        throw new UnsupportedOperationException("Método 'eliminar' no implementado");
    }
}
