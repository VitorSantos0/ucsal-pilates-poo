package agenda;

public class Reuniao extends Evento {

    private String modalidade;
    private int horaDuracao;
    private int minutoDuracao;

    public String getModalidade() {
        return modalidade;
    }

    public void setModalidade(String local) {
        this.modalidade = local;
    }

    public int getHoraDuracao() {
        return horaDuracao;
    }

    public void setHoraDuracao(int horaDuracao) {
        this.horaDuracao = horaDuracao;
    }

    public int getMinutoDuracao() {
        return minutoDuracao;
    }

    public void setMinutoDuracao(int minutoDuracao) {
        this.minutoDuracao = minutoDuracao;
    }

    @Override
    public void lembrete() {
        super.lembrete();
        System.out.println("REUNIÃO "+this.getModalidade().toUpperCase());
        String duracao;
        if(this.horaDuracao != 0) {
            duracao = "Duração: "+this.getHoraDuracao()+" horas";
            if(this.minutoDuracao != 0){
                duracao += " e "+this.getMinutoDuracao()+" minutos";
            }
        } else {
            duracao = String.format("Duração: %02d minutos", this.getMinutoDuracao());
        }
        System.out.println(duracao);
    }

}
