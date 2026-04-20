package com.cooperativa.infrastructure;

import java.io.*;

public final class SessionSerializer {
    public <T extends Serializable> void serializar(T sessionObject, File destino) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(destino))) {
            oos.writeObject(sessionObject);
        } catch (IOException e) {
            throw new IllegalStateException("No fue posible serializar la sesión", e);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T deserializar(File origen, Class<T> tipo) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(origen))) {
            Object data = ois.readObject();
            return tipo.cast(data);
        } catch (IOException | ClassNotFoundException e) {
            throw new IllegalStateException("No fue posible deserializar la sesión", e);
        }
    }
}
