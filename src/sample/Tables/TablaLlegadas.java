package sample.Tables;

public class TablaLlegadas {

    String pid, estado, ubicacion;
    int llegada, duracion;

    public TablaLlegadas(String pid, int llegada, int duracion, String estado, String ubicacion){
        this.pid       = pid;
        this.llegada   = llegada;
        this.duracion  = duracion;
        this.estado    = estado;
        this.ubicacion = ubicacion;
    }

    public String getPid() {return pid;}

    public void setPid(String pid) {this.pid = pid;}

    public int getLlegada() {return llegada;}

    public void setLlegada(int llegada) {this.llegada = llegada;}

    public String getEstado() {return estado;}

    public void setEstado(String estado) {this.estado = estado;}

    public String getUbicacion() {return ubicacion;}

    public void setUbicacion(String ubicacion) {this.ubicacion = ubicacion;}

    public int getDuracion() {return duracion;}

    public void setDuracion(int duracion) {this.duracion = duracion;}
}
