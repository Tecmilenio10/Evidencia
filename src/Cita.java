import java.time.LocalDateTime;

public class Cita {
    public Cita(String n, String p, String f, String m){
        medico =n;
        paciente =p;
        fecha =f;
        motivo = m;
    }

    public String toString(){
        return "Medico: " + medico + " Paciente: " + paciente + " Fecha Hora: " + fecha
                + " Motivo Consulta: " + motivo;
    }

    private String medico;
    private String paciente;
    private String fecha;
    private String motivo;
}
