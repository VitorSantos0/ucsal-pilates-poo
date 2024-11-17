package agenda;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Evento {

    private String titulo;
    private LocalDate data;
    private LocalTime hora;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public boolean conflitaHorario(Evento evento) {
        return this.data.equals(evento.getData()) && this.hora.equals(evento.getHora());
    }

    public void lembrete() {
        System.out.println("\n"+this.getTitulo().toUpperCase());
        System.out.println(this.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))+
                " - "+this.getHora().format(DateTimeFormatter.ofPattern("HH:mm")));
    }

}
