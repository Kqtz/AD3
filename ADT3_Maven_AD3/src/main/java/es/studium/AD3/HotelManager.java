package es.studium.AD3;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class HotelManager {

    private static SessionFactory fabricaDeSesiones;

    // Obtiene la instancia de SessionFactory (implementación de singleton)
    private static SessionFactory inicializarFabrica() {
        if (fabricaDeSesiones == null) {
            fabricaDeSesiones = new Configuration()
                    .addAnnotatedClass(Cliente.class) // Clase Cliente debe estar anotada con @Entity
                    .configure()
                    .buildSessionFactory();
        }
        return fabricaDeSesiones;
    }

    // Crear un nuevo cliente en la base de datos
    public static int agregarUsuario(String nombre, String apellido, String correo, String identificacion, String contrasena) {
        int identificador = 0;
        Session sesion = inicializarFabrica().openSession();
        Transaction transaccion = null;

        try {
            transaccion = sesion.beginTransaction();
            Cliente nuevoCliente = new Cliente(nombre, apellido, correo, identificacion, contrasena);
            sesion.persist(nuevoCliente);
            transaccion.commit();
            identificador = nuevoCliente.getIdCliente(); // Recuperar el ID generado
            System.out.println("Se ha creado un nuevo cliente con ID: " + identificador);
        } catch (Exception excepcion) {
            if (transaccion != null) {
                transaccion.rollback();
            }
            excepcion.printStackTrace();
        } finally {
            sesion.close();
        }
        return identificador;
    }

    // Leer un atributo de un cliente dado su ID
    public static String consultarDatoCliente(int id, String atributo) {
        String resultado = null;
        Session sesion = inicializarFabrica().openSession();

        try {
            Cliente cliente = sesion.get(Cliente.class, id);
            if (cliente != null) {
                if ("nombre".equals(atributo)) {
                    resultado = cliente.getNombre();
                } else if ("apellidos".equals(atributo)) {
                    resultado = cliente.getApellidos();
                } else if ("email".equals(atributo)) {
                    resultado = cliente.getEmail();
                } else if ("dni".equals(atributo)) {
                    resultado = cliente.getDni();
                } else if ("clave".equals(atributo)) {
                    resultado = cliente.getClave();
                } else {
                    System.out.println("El atributo solicitado no es válido.");
                }
            } else {
                System.out.println("No se encontró al cliente con el ID especificado.");
            }
        } catch (Exception error) {
            error.printStackTrace();
        } finally {
            sesion.close();
        }
        return resultado;
    }

    // Eliminar un cliente de la base de datos
    public static boolean borrarRegistroCliente(int identificadorCliente) {
        boolean fueEliminado = false;
        Session sesion = inicializarFabrica().openSession();
        Transaction transaccion = null;

        try {
            transaccion = sesion.beginTransaction();
            Cliente cliente = sesion.get(Cliente.class, identificadorCliente);
            if (cliente != null) {
                sesion.remove(cliente);
                transaccion.commit();
                fueEliminado = true;
                System.out.println("El cliente fue eliminado exitosamente.");
            } else {
                System.out.println("El cliente no existe en la base de datos.");
            }
        } catch (Exception excepcion) {
            if (transaccion != null) {
                transaccion.rollback();
            }
            excepcion.printStackTrace();
        } finally {
            sesion.close();
        }
        return fueEliminado;
    }

    // Actualizar un campo de un cliente existente
    public static boolean modificarAtributoCliente(int id, String campo, String nuevoValor) {
        boolean seActualizo = false;
        Session sesion = inicializarFabrica().openSession();
        Transaction transaccion = null;

        try {
            transaccion = sesion.beginTransaction();
            Cliente cliente = sesion.get(Cliente.class, id);

            if (cliente != null) {
                switch (campo) {
                    case "nombre":
                        cliente.setNombre(nuevoValor);
                        break;
                    case "apellidos":
                        cliente.setApellidos(nuevoValor);
                        break;
                    case "email":
                        cliente.setEmail(nuevoValor);
                        break;
                    case "dni":
                        cliente.setDni(nuevoValor);
                        break;
                    case "clave":
                        cliente.setClave(nuevoValor);
                        break;
                    default:
                        System.out.println("Campo proporcionado no es válido.");
                        return false;
                }
                transaccion.commit();
                seActualizo = true;
                System.out.println("Campo actualizado correctamente.");
            } else {
                System.out.println("Cliente no encontrado en la base de datos.");
            }
        } catch (Exception excepcion) {
            if (transaccion != null) {
                transaccion.rollback();
            }
            excepcion.printStackTrace();
        } finally {
            sesion.close();
        }
        return seActualizo;
    }
}