package com.cooperativa.persistence;

import com.cooperativa.exceptions.PersistenciaException;
import com.cooperativa.model.Asociado;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public final class AsociadoRepositoryJdbc {
    private final DataSource dataSource;

    public AsociadoRepositoryJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void guardar(Asociado asociado) {
        String sql = "INSERT INTO asociado(id, nombre) VALUES (?, ?)";
        try (Connection c = dataSource.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, asociado.getId());
            ps.setString(2, asociado.getNombre());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenciaException("Error guardando asociado", e);
        }
    }

    public Optional<Asociado> buscarPorId(String id) {
        String sql = "SELECT id, nombre FROM asociado WHERE id = ?";
        try (Connection c = dataSource.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Asociado(rs.getString("id"), rs.getString("nombre")));
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Error consultando asociado", e);
        }
    }
}
