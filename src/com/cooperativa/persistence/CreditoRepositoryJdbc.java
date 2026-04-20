package com.cooperativa.persistence;

import com.cooperativa.exceptions.PersistenciaException;
import com.cooperativa.model.Credito;
import com.cooperativa.model.enums.EstadoCredito;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public final class CreditoRepositoryJdbc {
    private final DataSource dataSource;

    public CreditoRepositoryJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void guardar(Credito credito) {
        String sql = "INSERT INTO credito(id, asociado_id, monto, estado) VALUES (?, ?, ?, ?)";
        try (Connection c = dataSource.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, credito.getId());
            ps.setString(2, credito.getAsociadoId());
            ps.setBigDecimal(3, credito.getMonto());
            ps.setString(4, credito.getEstado().name());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenciaException("Error guardando crédito", e);
        }
    }

    public void actualizarEstado(String creditoId, EstadoCredito estado) {
        String sql = "UPDATE credito SET estado = ? WHERE id = ?";
        try (Connection c = dataSource.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, estado.name());
            ps.setString(2, creditoId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenciaException("Error actualizando estado de crédito", e);
        }
    }
}
