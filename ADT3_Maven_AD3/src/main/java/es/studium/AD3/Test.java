package es.studium.AD3;

public class Test {
    public static void main(String[] args) {
        // Agregar un nuevo cliente
        int idCliente1 = HotelManager.agregarUsuario(
                "Alvaro", "Moreno", "Caballero", "27689834Z", "testclave123");

        // Mostrar los datos del cliente creado
        System.out.println("Cliente creado:");
        System.out.println("ID: " + idCliente1);
        System.out.println("Nombre: " + HotelManager.consultarDatoCliente(idCliente1, "nombre"));
        System.out.println("Apellidos: " + HotelManager.consultarDatoCliente(idCliente1, "apellidos"));
        System.out.println("Email: " + HotelManager.consultarDatoCliente(idCliente1, "email"));
        System.out.println("DNI: " + HotelManager.consultarDatoCliente(idCliente1, "dni"));
        System.out.println("Clave: " + HotelManager.consultarDatoCliente(idCliente1, "clave"));

        // Actualizar el email del cliente
        System.out.println("Actualizando el email del cliente...");
        HotelManager.modificarAtributoCliente(idCliente1, "email", "ruizalejandro@gmail.com");

        // Mostrar los datos actualizados del cliente
        System.out.println("Datos del cliente actualizados:");
        System.out.println("ID: " + idCliente1);
        System.out.println("Email: " + HotelManager.consultarDatoCliente(idCliente1, "email"));

        // Eliminar al cliente
        System.out.println("Eliminando al cliente...");
        boolean eliminado = HotelManager.borrarRegistroCliente(idCliente1);

        if (eliminado) {
            System.out.println("El cliente fue eliminado correctamente.");
        } else {
            System.out.println("No se pudo eliminar al cliente.");
        }
    }
}