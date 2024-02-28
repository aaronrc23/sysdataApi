package com.example.service;
import com.example.entity.UnidadMedida;
import java.util.Collection;
import java.util.List;

public interface UnidadMedidaService {
    public abstract UnidadMedida insert(UnidadMedida und);
    public abstract void update(UnidadMedida und);
    public abstract void delete(Long umd_id);
    public abstract UnidadMedida findById(Long umd_id);
    public abstract Collection<UnidadMedida> findAll();

    List<UnidadMedida> findAllDesactivadas();
    List<UnidadMedida> findAllActivos();
    Long countByNombre(String nombre);
}
