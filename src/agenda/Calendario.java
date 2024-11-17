package agenda;

import java.util.ArrayList;
import java.time.LocalDate;
import java.util.List;

public class Calendario {

    private final List<Evento> eventos;

    public Calendario() {
        this.eventos = new ArrayList<>();
    }

    public void adicionarEvento(Evento evento) {
        if(this.horarioDisponivel(evento)) {
            this.eventos.add(evento);
        } else {
            System.err.println("Conflito de horario, evento não adicionado");
        }
    }

    public void removerEvento(Evento evento) {
        this.eventos.remove(evento);
    }

    public List<Evento> listarEventos() {
        return this.eventos;
    }

    public List<Evento> listarEventosPorData(LocalDate data) {
        List<Evento> eventosData = new ArrayList<>();
        for (Evento evento : this.eventos) {
            if (evento.getData().equals(data)) {
                eventosData.add(evento);
            }
        }
        return eventosData;
    }

    private boolean horarioDisponivel(Evento novoEvento) {
        for (Evento evento : this.eventos) {
            if (evento.conflitaHorario(novoEvento)) {
                return false;
            }
        }
        return true;
    }

}
