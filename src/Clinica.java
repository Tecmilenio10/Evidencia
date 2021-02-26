import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Clinica {
    private static ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
    public static HashMap<Integer, Medico> listaMedicos = new HashMap<Integer, Medico>();
    public static HashMap<Integer, Paciente> listaPacientes = new HashMap<Integer, Paciente>();
    public static HashMap<Integer, Cita> listaCitas = new HashMap<Integer, Cita>();
    private static Scanner teclado = new Scanner(System.in);

    public static void main(String[] args) {
        crearCatalogos(listaMedicos, listaPacientes);

        if (validarAcceso()) {
            System.out.println("Usuario autorizado");
            menu();
        }
        else
            System.out.println("\nUsuario no autorizado.");

        System.out.println("FIN DE PROGRAMA");

    }

    public static void crearCatalogos(HashMap listaMedicos, HashMap listaPacientes) {

        String inputFilenameMedicos = "src/CitaMedica/medicos.txt"; //archivo que se leera para cargar  medicos
        String inputFilenamePacientes = "src/CitaMedica/pacientes.txt"; //archivo que se leera para cargar  pacientes
        BufferedReader bufferedReader = null;
        String Nombre = "";
        String Especialidad = "";
        try {
            bufferedReader = new BufferedReader(new FileReader(inputFilenameMedicos));

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                int coma = line.indexOf(',');
                Nombre = line.substring(0, coma);
                Especialidad = line.substring(coma+1, line.length());
                int id = listaMedicos.size();
                listaMedicos.put(id+1, new Medico(Nombre,Especialidad));
            }
        } catch(IOException e) {
            System.out.println("IOException catched while reading: " + e.getMessage());
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                System.out.println("IOException catched while closing: " + e.getMessage());
            }
        }

        try {
            bufferedReader = new BufferedReader(new FileReader(inputFilenamePacientes));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                Nombre = line;
                int id = listaPacientes.size();
                listaPacientes.put(id+1, new Paciente(line));
            }
        } catch(IOException e) {
            System.out.println("IOException catched while reading: " + e.getMessage());
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                System.out.println("IOException catched while closing: " + e.getMessage());
            }
        }

    }

    private static boolean validarAcceso( ) {

        usuarios.add(new Usuario("admin", "admin"));
        usuarios.add(new Usuario("admin2", "admin2"));
        usuarios.add(new Usuario("admin3", "admin3"));

        System.out.println("CLINICA AGENDA CITA");
        System.out.print("Nombre: ");
        String nombre = teclado.nextLine();
        System.out.print("Password: ");
        String password = teclado.nextLine();

        Usuario admin = new Usuario(nombre, password);

        return usuarios.contains(admin);

    }

    private static void menu(){
        Scanner sn = new Scanner(System.in);
        boolean salir = false;
        int opcion;

        while (!salir) {
            System.out.println("\n Seleccione la Opcion deseada");
            System.out.println("1. Alta Doctor");
            System.out.println("2. Alta Paciente");
            System.out.println("3. Generar Cita");
            System.out.println("4. Salir");
            try {
                System.out.println("Escribe una de las opciones:");
                opcion = sn.nextInt();
                int id = 0;
                String nombre = "";
                switch (opcion) {
                    case 1:
                        String especialidad = "";
                        System.out.print("Nombre Doctor: ");
                        nombre = teclado.nextLine();
                        System.out.print("Especialidad: ");
                        especialidad = teclado.nextLine();
                        id = listaMedicos.size();
                        listaMedicos.put(id+1, new Medico(nombre,especialidad));
                        break;
                    case 2:
                        System.out.print("Nombre Paciente: ");
                        nombre = teclado.nextLine();
                        id = listaPacientes.size();
                        listaPacientes.put(id+1, new Paciente(nombre));
                        break;
                    case 3:
                        int medico;
                        int paciente;
                        String fecha;
                        String motivo;
                        boolean valid = false;
                        do{
                            System.out.println("AGENDA DE DOCTORES");
                            for (Iterator<Map.Entry<Integer, Medico>> entries = listaMedicos.entrySet().iterator(); entries.hasNext(); ) {
                                Map.Entry<Integer, Medico> entry = entries.next();
                                String output = String.format("%s. %s", entry.getKey(), entry.getValue());
                                System.out.println(output);
                            }
                            System.out.print("Seleccione Doctor: ");
                            medico = Integer.parseInt(teclado.nextLine());
                            valid = listaMedicos.containsKey(medico);
                        }while(valid != true);
                        valid = false;
                        do{
                            System.out.println("AGENDA DE PACIENTES");
                            for (Iterator<Map.Entry<Integer, Paciente>> entries = listaPacientes.entrySet().iterator(); entries.hasNext(); ) {
                                Map.Entry<Integer, Paciente> entry = entries.next();
                                String output = String.format("%s. %s", entry.getKey(), entry.getValue());
                                System.out.println(output);
                            }
                            System.out.print("Seleccione Paciente: ");
                            paciente = Integer.parseInt(teclado.nextLine());
                            valid = listaPacientes.containsKey(paciente);
                        }while(valid != true);
                        valid = false;
                        Date testDate = null;
                        do{
                            System.out.print("Seleccione Fecha: ");
                            System.out.println("Introduzca la fecha con formato yyyy-MM-ddHH:mm:ss");
                            Scanner sc = new Scanner(System.in);

                            fecha = sc.nextLine();
                            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss");
                            String date = fecha;
                            try{
                                testDate = df.parse(date);
                                valid = true;
                                //System.out.println("Ahora hemos creado un objeto date con la fecha indicada, "+testDate);
                            } catch (Exception e){ System.out.println("Formato invalido para Fecha intente de nuevo!!");}

                            if (df != null) {
                                if(!df.format(testDate).equals(date)) {
                                    System.out.println("Formato invalido para Fecha intente de nuevo!!");
                                } else{
                                    //System.out.println("valid date");
                                    valid = true;
                                }
                            }
                        }while(valid != true);
                        System.out.print("Motivo de consulta: ");
                        motivo = teclado.nextLine();
                        id = listaCitas.size();
                        String Medico = listaMedicos.get(medico).toString();
                        int coma = Medico.indexOf(':');
                        Medico = Medico.substring(coma+2, Medico.length()).toString();
                        coma = Medico.indexOf(':');
                        Medico = Medico.substring(0, coma).toString();
                        Medico = Medico.substring(0, Medico.length()-13);
                        String Paciente = listaPacientes.get(paciente).toString();
                        coma = Paciente.indexOf(':');
                        Paciente = Paciente.substring(coma+2, Paciente.length()).toString();
                        listaCitas.put(id+1, new Cita(Medico, Paciente, testDate.toString(), motivo));
                        System.out.print(listaCitas.get(id+1));
                        break;
                    case 4:
                        salir = true;
                        break;
                    default:
                        System.out.println("Solo números entre 1 y 4");
                }
            } catch (InputMismatchException e) {
                System.out.println("Debes insertar un número");
                sn.next();
            }
        }
    }

}