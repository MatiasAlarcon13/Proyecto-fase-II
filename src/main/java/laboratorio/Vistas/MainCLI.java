package laboratorio.Vistas;

import laboratorio.Controladores.*;
import laboratorio.Modelos.*;
import laboratorio.Persistencia.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class MainCLI {
    private static final Scanner scanner = new Scanner(System.in);
    private static Usuario usuarioActual = null;
    
    // Controladores
    private static final UsuarioController usuarioController = new UsuarioController();
    private static final SolicitudController solicitudController = new SolicitudController();
    private static final MaquinaController maquinaController = new MaquinaController();
    private static final ImpresoraController impresoraController = new ImpresoraController();
    private static final RegistroController registroController = new RegistroController();
    private static final ModelosImpresionController modelosController = new ModelosImpresionController();
    private static final CortadoraLaserDAO cortadoraLaserDAO = new CortadoraLaserDAO();
    private static final BobinaDAO bobinaDAO = new BobinaDAO();
    private static final SolicitudImpresionDAO solicitudImpresionDAO = new SolicitudImpresionDAO();

    private static void asegurarModelosIniciales() {
        if (modelosController.listarTodos().isEmpty()) {
            ModelosImpresion m1 = new ModelosImpresion(); m1.seleccionarModelo("puente"); modelosController.guardarModelo(m1);
            ModelosImpresion m2 = new ModelosImpresion(); m2.seleccionarModelo("casa"); modelosController.guardarModelo(m2);
            ModelosImpresion m3 = new ModelosImpresion(); m3.seleccionarModelo("pelota"); modelosController.guardarModelo(m3);
        }
        
        PlanchaDAO planchaDAO = new PlanchaDAO();
        if (planchaDAO.listarTodas().isEmpty()) {
            planchaDAO.guardar(new Plancha("MDF", 100));
            planchaDAO.guardar(new Plancha("Acrilico", 100));
        }
    }
    
    public static void main(String[] args) {
        System.out.println("=== Sistema de Gestión de Laboratorio Maker (CLI) ===");
        asegurarModelosIniciales();
        
        Runtime.getRuntime().addShutdownHook(new Thread(MainCLI::limpiarSolicitudesAlSalir));
        
        while (true) {
            usuarioActual = null;
            while (usuarioActual == null) {
                mostrarMenuInicial();
            }
            mostrarMenuPrincipal();
        }
    }

    private static void limpiarSolicitudesAlSalir() {
        System.out.println("\nLimpiando solicitudes pendientes...");
        List<Solicitud> pendientes = solicitudController.listarTodasLasSolicitudesPendientes();
        for (Solicitud s : pendientes) {
            s.setEstado(Solicitud.EstadoSolicitud.FINALIZADA); 
            if (s instanceof SolicitudImpresion) solicitudImpresionDAO.actualizar((SolicitudImpresion) s);
            else if (s instanceof SolicitudCorte) new SolicitudCorteDAO().actualizar((SolicitudCorte) s);
        }
        System.out.println("Solicitudes procesadas.");
    }
    
    private static void mostrarMenuInicial() {
        System.out.println("\n1. Iniciar sesión");
        System.out.println("2. Agregar usuario");
        System.out.print("Seleccione una opción: ");
        int opcion = scanner.nextInt();
        scanner.nextLine();
        
        switch (opcion) {
            case 1 -> login();
            case 2 -> registrarUsuario();
            default -> System.out.println("Opción inválida.");
        }
    }
    
    private static void login() {
        System.out.print("DNI: ");
        int dni = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Contraseña: ");
        String password = scanner.nextLine();
        
        usuarioActual = UsuarioDAO.verificarCredenciales(String.valueOf(dni), password);
        if (usuarioActual == null) {
            System.out.println("Credenciales incorrectas.");
        }
    }

    private static void registrarUsuario() {
        System.out.println("Seleccione tipo de usuario:");
        System.out.println("1. Alumno");
        System.out.println("2. Profesor");
        int tipo = scanner.nextInt(); scanner.nextLine();

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("DNI: ");
        int dni = scanner.nextInt(); scanner.nextLine();
        System.out.print("Correo: ");
        String correo = scanner.nextLine();

        Object creado = null;
        switch (tipo) {
            case 1 -> creado = usuarioController.crearAlumno(nombre, dni, correo, 500.0);
            case 2 -> creado = usuarioController.crearProfesor(nombre, dni, correo, 0); 
            default -> System.out.println("Tipo inválido.");
        }

        System.out.println(creado != null ? "\n====Usuario creado exitosamente.====" : "====Error al crear usuario.====");
    }
    
    private static void mostrarMenuPrincipal() {
        String rol = usuarioActual.getRol();
        System.out.println("\n============¡Hola! "+usuarioActual.getNombre()+" ============");
        boolean sesionActiva = true;
        while (sesionActiva) {
            System.out.println("\n--- Menú Principal (" + rol + ") ---");
            if (rol.equalsIgnoreCase("ADMIN")) sesionActiva = menuAdmin();
            else if (rol.equalsIgnoreCase("docente")) sesionActiva = menuDocente();
            else sesionActiva = menuEstudiante();
        }
    }
    
    private static boolean menuAdmin() {
        System.out.println("1. Listar máquinas");
        System.out.println("2. Crear impresora");
        System.out.println("3. Crear cortadora láser");
        System.out.println("4. Crear modelo de impresión");
        System.out.println("5. Ver auditoría");
        System.out.println("6. Gestionar inventario (Bobinas/Planchas)");
        System.out.println("7. Cerrar sesión");
        System.out.println("8. Salir");
        System.out.print("Opción: ");
        int opcion = scanner.nextInt(); scanner.nextLine();
        
        switch (opcion) {
            case 1 -> {
                List<Impresora> impresoras = new ImpresoraDAO().listarTodas();
                List<CortadoraLaser> cortadoras = new CortadoraLaserDAO().listarTodas();
                if (impresoras.isEmpty() && cortadoras.isEmpty()) System.out.println("No existen máquinas.");
                else {
                    System.out.println("--- Impresoras ---");
                    impresoras.forEach(i -> System.out.println("ID: " + i.getIdMaquina() + " | Estado: " + i.getEstado()));
                    System.out.println("--- Cortadoras ---");
                    cortadoras.forEach(c -> System.out.println("ID: " + c.getIdMaquina() + " | Estado: " + c.getEstado()));
                }
            }
            case 2 -> maquinaController.crearImpresora();
            case 3 -> maquinaController.crearCortadora();
            case 4 -> {
                System.out.print("Nombre modelo: ");
                String nombre = scanner.nextLine();
                System.out.print("Altura capa: ");
                int altura = scanner.nextInt();
                System.out.print("Total capas: ");
                int capas = scanner.nextInt();
                System.out.print("Gramos requeridos: ");
                double gramos = scanner.nextDouble(); scanner.nextLine();
                
                ModelosImpresion m = new ModelosImpresion();
                m.setNombreModelo(nombre);
                m.setAlturaCapa(altura);
                m.setTotalCapas(capas);
                m.setVelocidadImpresion(40.0);
                m.setGramosRequeridos(gramos);
                m.setTiempoEstimado((double)(altura * capas) / 40.0);
                
                modelosController.guardarModelo(m);
                System.out.println("Modelo creado.");
            }
            case 5 -> registroController.getRegistros().forEach(System.out::println);
            case 6 -> gestionarInventario();
            case 7 -> { return false; }
            case 8 -> System.exit(0);
        }
        return true;
    }

    private static void gestionarInventario() {
        System.out.println("1. Crear Bobina | 2. Actualizar Bobina | 3. Crear Plancha | 4. Actualizar Plancha");
        int op = scanner.nextInt(); scanner.nextLine();
        switch (op) {
            case 1 -> {
                System.out.print("Material: "); String mat = scanner.nextLine();
                System.out.print("Gramos: "); double cant = scanner.nextDouble(); scanner.nextLine();
                bobinaDAO.guardar(new Bobina(mat, cant));
                System.out.println("Bobina creada.");
            }
            case 2 -> {
                System.out.print("ID Bobina: ");
                int id = scanner.nextInt();
                System.out.print("Nueva cantidad (gramos): ");
                double cant = scanner.nextDouble(); scanner.nextLine();
                Bobina b = bobinaDAO.buscarPorId(id);
                if (b != null) {
                    b.setGramos(cant);
                    bobinaDAO.actualizar(b);
                    System.out.println("Bobina actualizada.");
                } else System.out.println("Bobina no encontrada.");
            }
            case 3 -> {
                System.out.print("Tipo (MDF/Acrilico): "); String tipo = scanner.nextLine();
                System.out.print("Cantidad inicial: ");
                int cant = scanner.nextInt(); scanner.nextLine();
                new PlanchaDAO().guardar(new Plancha(tipo, cant));
                System.out.println("Plancha creada.");
            }
            case 4 -> {
                System.out.print("Tipo (MDF/Acrilico): "); String tipo = scanner.nextLine();
                System.out.print("Cantidad adicional: ");
                int cant = scanner.nextInt(); scanner.nextLine();
                Plancha p = new PlanchaDAO().buscarPorTipo(tipo);
                if (p != null) {
                    p.setCantidadDisponible(p.getCantidadDisponible() + cant);
                    new PlanchaDAO().actualizar(p);
                    System.out.println("Inventario de planchas actualizado.");
                } else System.out.println("Material no encontrado.");
            }
        }
    }
    
    private static boolean menuDocente() {
        System.out.println("1. Gestionar solicitudes pendientes (Impresión/Corte)");
        System.out.println("2. Cerrar sesión");
        System.out.println("3. Salir");
        System.out.print("Opción: ");
        int opcion = scanner.nextInt(); scanner.nextLine();
        
        if (opcion == 1) {
            List<Solicitud> pendientes = solicitudController.listarTodasLasSolicitudesPendientes();
            if (pendientes.isEmpty()) {
                System.out.println("No hay solicitudes pendientes.");
                return true;
            }
            
            System.out.println("--- Solicitudes Pendientes ---");
            for (int i = 0; i < pendientes.size(); i++) {
                Solicitud s = pendientes.get(i);
                String tipo = (s instanceof SolicitudImpresion) ? "Impresión" : "Corte";
                System.out.println((i + 1) + ". (" + s.getTitularSolicitud() + ", " + tipo + ", " + s.getNombreArchivo() + ")");
            }
            
            System.out.print("Seleccione número de solicitud: ");
            int sel = scanner.nextInt() - 1; scanner.nextLine();
            if (sel < 0 || sel >= pendientes.size()) return true;
            
            Solicitud sol = pendientes.get(sel);
            System.out.print("Escriba '1' para Aprobar o '0' para Rechazar: ");
            int accion = scanner.nextInt(); scanner.nextLine();
            
            if (sol instanceof SolicitudImpresion) {
                if (accion == 1) {
                    Bobina b = bobinaDAO.buscarBobinaConMaterial();
                    if (b != null) {
                        SolicitudController.ResultadoSolicitud<SolicitudImpresion> res = solicitudController.aprobarSolicitud((SolicitudImpresion) sol, b);
                        if (res.exito()) System.out.println("Aprobada con bobina: " + b.getmaterialBobina());
                        else System.out.println("Error: " + res.getError());
                    } else {
                        System.out.println("No hay bobinas con material suficiente.");
                    }
                } else {
                    sol.setEstado(Solicitud.EstadoSolicitud.CANCELADA);
                    solicitudImpresionDAO.actualizar((SolicitudImpresion) sol);
                    System.out.println("Solicitud rechazada.");
                }
            } else if (sol instanceof SolicitudCorte solCorte) {
                if (accion == 1) {
                    System.out.print("ID Cortadora: ");
                    String idC = scanner.nextLine();
                    CortadoraLaser c = cortadoraLaserDAO.buscarPorId(idC);
                    Plancha p = new PlanchaDAO().buscarPorTipo(solCorte.getTipoPlancha());
                    
                    if (c != null && p != null) {
                        c.iniciarCorte(solCorte, p);
                        solCorte.setEstado(Solicitud.EstadoSolicitud.EN_PROCESO);
                        new SolicitudCorteDAO().actualizar(solCorte);
                        new CortadoraLaserDAO().actualizar(c);
                        
                        // Registrar en auditoría
                        registroController.guardar(Registro.generarRegistroCorte(solCorte, c, solCorte.getUsuario(), "Corte aprobado e iniciado"));
                        
                        System.out.println("Corte aprobado e iniciado.");
                    } else {
                        System.out.println("Cortadora o material no encontrados.");
                    }
                } else {
                    sol.setEstado(Solicitud.EstadoSolicitud.CANCELADA);
                    new SolicitudCorteDAO().actualizar(solCorte);
                    System.out.println("Corte rechazado.");
                }
            }
        } else if (opcion == 2) {
            return false;
        } else if (opcion == 3) {
            System.exit(0);
        }
        return true;
    }
    
    private static boolean menuEstudiante() {
        System.out.println("Cuota disponible: " + ((Alumno)usuarioActual).getCuotaDisponible() + "g");
        System.out.println("1. Crear solicitud de impresión");
        System.out.println("2. Crear solicitud de corte");
        System.out.println("3. Ver mis solicitudes");
        System.out.println("4. Cerrar sesión");
        System.out.println("5. Salir");
        System.out.print("Opción: ");
        int opcion = scanner.nextInt(); scanner.nextLine();
        
        switch (opcion) {
            case 1 -> {
                List<ModelosImpresion> modelos = modelosController.listarTodos();
                if (modelos.isEmpty()) {
                    System.out.println("No hay modelos disponibles.");
                    return true;
                }
                System.out.println("--- Modelos disponibles ---");
                for (int i = 0; i < modelos.size(); i++) {
                    ModelosImpresion m = modelos.get(i);
                    System.out.println((i + 1) + ". " + m.getNombreModelo() + " | Requerido: " + m.getGramosRequeridos() + "g");
                }
                System.out.print("Seleccione modelo: ");
                int sel = scanner.nextInt() - 1; scanner.nextLine();
                if (sel < 0 || sel >= modelos.size()) {
                    System.out.println("Selección inválida.");
                    return true;
                }
                String nombreModelo = modelos.get(sel).getNombreModelo();
                
                SolicitudController.ResultadoSolicitud<SolicitudImpresion> res = solicitudController.crearSolicitud(nombreModelo, usuarioActual);
                if (res.exito()) {
                    SolicitudImpresion s = res.getSolicitud();
                    System.out.println("\nSolicitud creada con éxito.");
                    System.out.println("ID: " + s.getIdSolicitud() + " | Modelo: " + s.getModelo() + " | Tiempo estimado: " + s.getTiempoEstimado() + "min");
                    System.out.println("Cuota disponible restante: " + ((Alumno)usuarioActual).getCuotaDisponible() + "g");
                } else {
                    System.out.println("Error: " + res.getError());
                }
            }
            case 2 -> {
                System.out.println("--- Materiales disponibles: MDF / Acrilico ---");
                System.out.print("Tipo Plancha (escriba MDF o Acrilico): ");
                String tipo = scanner.nextLine();
                
                System.out.print("Nombre del archivo: ");
                String arch = scanner.nextLine();
                System.out.print("Cantidad de planchas: ");
                int cant = scanner.nextInt(); scanner.nextLine();
                
                var res = solicitudController.crearSolicitudCorte(arch, tipo, cant, usuarioActual);
                if (res.exito()) {
                    SolicitudCorte s = res.getSolicitud();
                    System.out.println("\nSolicitud creada con éxito.");
                    System.out.println("ID: " + s.getIdSolicitud() + " | Archivo: " + s.getNombreArchivo() + " | Tiempo estimado: " + s.getTiempoTuboLaser() + "min");
                    System.out.println("Solicitud en espera de aprobación.");
                } else {
                    System.out.println("Error: " + res.getError() + ". Notificar al Administrador.");
                }
            }
            case 3 -> {
                System.out.println("--- Mis solicitudes ---");
                List<Solicitud> todas = solicitudController.listarTodasLasSolicitudesPendientes();
                List<Solicitud> misSolicitudes = new ArrayList<>();
                for(Solicitud s : todas) {
                    if (s.getDni() == usuarioActual.getDni()) misSolicitudes.add(s);
                }
                misSolicitudes.forEach(s -> System.out.println("ID: " + s.getIdSolicitud() + " | Tipo: " + s.getClass().getSimpleName() + " | Estado: " + s.getEstado()));
            }
            case 4 -> { return false; }
            case 5 -> System.exit(0);
        }
        return true;
    }
}
