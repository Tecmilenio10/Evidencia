public class Medico {
    public Medico(String n, String e){
        nombre =n;
        especialidad =e;
    }

    public String toString(){
        return "Medico: " + nombre + " Especialidad: " + especialidad;
    }

    private String nombre;
    private String especialidad;
}
