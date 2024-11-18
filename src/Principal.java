import agenda.Calendario;
import agenda.Evento;
import agenda.Reuniao;
import agenda.Usuario;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Principal {

    private static final Scanner scanner = new Scanner(System.in);
    private static final Usuario usuario = new Usuario();
    private static final Calendario calendario = new Calendario();

    public static void main(String[] args) {

        boolean encerrar = false;
        boolean menuValidacao = true;
        int menu;

        System.out.println();

        do {
            if(menuValidacao) {
                exibirMenu();
            }
            menuValidacao = true;
            menu = scanner.nextInt();
            scanner.nextLine();
            switch (menu) {
                case 0:
                    System.out.println("\nEncerrando sistema...");
                    encerrar = true;
                    scanner.close();
                    break;
                case 1:
                    cadastrarUsuario();
                    break;
                case 2:
                    if(usuario.getNome() == null) {
                        System.out.println("\nNenhum usuário cadastrado");
                    } else {
                        System.out.println("\n"+ usuario);
                    }
                    break;
                case 3:
                    if(usuario.getNome() == null) {
                        System.out.println("\nO cadastre de um usuário é necessário para registar um evento");
                    } else {
                        boolean eventoCadastrado = false;
                        do {
                            System.out.println("\n\t1. Evento Recorrente");
                            System.out.println("\t2. Reunião");
                            System.out.println("\t0. Voltar");
                            System.out.print("\tInforme o tipo do evento: ");
                            int tipoEvento = scanner.nextInt();
                            scanner.nextLine();
                            if (tipoEvento == 1) {
                                Evento evento = new Evento();
                                cadastrarEvento(evento);
                                calendario.adicionarEvento(evento);
                                System.out.println("\nEvento cadastrado com sucesso!");
                                eventoCadastrado = true;
                            } else if (tipoEvento == 2) {
                                Reuniao reuniao = new Reuniao();
                                cadastrarReuniao(reuniao);
                                calendario.adicionarEvento(reuniao);
                                System.out.println("\nReunião cadastrada com sucesso!");
                                eventoCadastrado = true;
                            } else if (tipoEvento == 0) {
                                System.out.println();
                                eventoCadastrado = true;
                            } else {
                                System.err.println("\nOpção inválida");
                            }
                        } while(!eventoCadastrado);
                    }
                    break;
                case 4:
                    if(calendario.listarEventos().isEmpty()){
                        System.out.println("\nNenhum evento encontrado no calendário");
                    } else {
                        exibirCalendario();
                        encerrar = voltarMenu();
                    }
                    break;
                case 5:
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    boolean dataValidacao = false;
                    List<Evento> eventosConsulta;
                    System.out.print("\n\tInforme a data que deseja consultar (Exemplo: 30/11/2024): ");
                    do {
                        String dataConsulta = scanner.nextLine();
                        try {
                            eventosConsulta = calendario.listarEventosPorData(LocalDate.parse(dataConsulta, formatter));
                            if(eventosConsulta.isEmpty()) {
                                System.out.println("\t\nNenhum evento encontrado no calendário com a data informada");
                            } else {
                                for (Evento evento : eventosConsulta) {
                                    evento.lembrete();
                                    System.out.println("--------------------------------");
                                }
                            }
                            dataValidacao = true;
                        } catch (DateTimeParseException e) {
                            System.err.print("\nData inválida, digite da forma correta: ");
                        }
                    } while(!dataValidacao);
                    encerrar = voltarMenu();
                    break;
                default:
                    System.err.print("Escolha uma opção válida: ");
                    menuValidacao = false;
            }
        } while (!encerrar);

    }

    private static void exibirMenu() {
        System.out.println("\n════════════════════════════════════════════════");
        System.out.println("\t\tCALENDÁRIO PILARES");
        System.out.println("════════════════════════════════════════════════");
        System.out.println("1. Cadastrar Usuário");
        System.out.println("2. Exibir Usuários");
        System.out.println("3. Cadastrar Evento");
        System.out.println("4. Exibir Calendário");
        System.out.println("5. Consultar Calendário");
        System.out.println("0. Encerrar");
        System.out.print("Escolha uma opção: ");
    }

    private static void cadastrarUsuario() {
        System.out.print("\nDigite o nome do usuário: ");
        usuario.setNome(scanner.nextLine());
        System.out.print("Digite o email do usuário: ");
        usuario.setEmail(scanner.nextLine());
        System.out.print("Digite o telefone do usuário: ");
        usuario.setTelefone(scanner.nextLine());
        System.out.println("\nUsuário <"+usuario.getNome()+"> cadastrado com sucesso");
    }

    private static void cadastrarEvento(Evento evento) {
        System.out.print("\nInforme o título do evento: ");
        evento.setTitulo(scanner.nextLine());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        boolean dataValidacao = false;
        System.out.print("Informe a data do evento (Exemplo: 30/11/2024): ");
        do {
            String data = scanner.nextLine();
            try {
                evento.setData(LocalDate.parse(data, formatter));
                dataValidacao = true;
            } catch (DateTimeParseException e) {
                System.err.print("\nData inválida, digite da forma correta: ");
            }
        } while(!dataValidacao);
        formatter = DateTimeFormatter.ofPattern("HH:mm");
        boolean horaValidacao = false;
        System.out.print("Informe a hora do evento (Exemplo: 13:30): ");
        do {
            String hora = scanner.nextLine();
            try {
                evento.setHora(LocalTime.parse(hora, formatter));
                horaValidacao = true;
            } catch (DateTimeParseException e) {
                System.err.print("\nHora inválida, digite da forma correta: ");
            }
        } while (!horaValidacao);
    }

    private static void cadastrarReuniao(Reuniao evento) {
        cadastrarEvento(evento);
        System.out.print("Descreva a modalidade da reuniao: ");
        evento.setModalidade(scanner.nextLine());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        boolean horaValidacao = false;
        System.out.print("Informe a duração da reunião em horas:minutos (Exemplo 01:30): ");
        do {
            String horaString = scanner.nextLine();
            try {
                LocalTime hora = LocalTime.parse(horaString, formatter);
                evento.setHoraDuracao(hora.getHour());
                evento.setMinutoDuracao(hora.getMinute());
                horaValidacao = true;
            } catch (DateTimeParseException e) {
                System.err.print("\nHorário inválida, digite da forma correta: ");
            }
        } while (!horaValidacao);
    }

    private static void exibirCalendario() {
        for (Evento evento : calendario.listarEventos()) {
            evento.lembrete();
            System.out.println("--------------------------------");
        }
    }

    private static boolean voltarMenu() {
        boolean voltar;
        System.out.println();
        do {
            System.out.print("Voltar ao menu principal 1-sim | 0-não: ");
            voltar = scanner.nextInt() == 1;
            return !voltar;
        } while(!voltar);
    }

}