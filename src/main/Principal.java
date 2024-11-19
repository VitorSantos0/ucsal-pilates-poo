package main;
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
                	System.out.println("\nDigite 0 a qualquer momento para cancelar a operação");
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
                        if(encerrar) {
                        	System.out.println("\nEncerrando sistema...");
                        }
                    }
                    break;
                case 5:
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    boolean dataValidacao = false;
                    List<Evento> eventosConsulta;
                    System.out.print("\n\tInforme a data que deseja consultar (Exemplo: 30/11/2024 ou 30/11): ");
                    do {
                        String dataConsulta = scanner.nextLine();
                        if(dataConsulta.length() == 5) {
                        	String anoAtual = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy"));
                        	dataConsulta += "/"+anoAtual;
                        }
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
                    if(encerrar) {
                    	System.out.println("\nEncerrando sistema...");
                    }
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

    private static int cadastrarUsuario() {
    	String valor;
        System.out.print("Digite o nome do usuário: ");
        valor = scanner.nextLine();
        if(valor.equals("0")) {
        	return 0;
        }
        usuario.setNome(valor);
        System.out.print("Digite o email do usuário: ");
        valor = scanner.nextLine();
        if(valor.equals("0")) {
        	usuario.setNome(null);
        	return 0;
        	
        }
        usuario.setEmail(valor);
        System.out.print("Digite o telefone do usuário: ");
        valor = scanner.nextLine();
        if(valor.equals("0")) {
        	usuario.setNome(null);
        	usuario.setEmail(null);
        	return 0;
        }
        usuario.setTelefone(valor);
        System.out.println("\nUsuário <"+usuario.getNome()+"> cadastrado com sucesso");
		return 0;
    }

    private static int cadastrarEvento(Evento evento) {
    	String valor;
        System.out.print("\nInforme o título do evento: ");
        valor = scanner.nextLine();
        if(valor.equals("0")) {
        	return 0;
        }
        evento.setTitulo(valor);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        boolean dataValidacao = false;
        System.out.print("Informe a data do evento (Exemplo: 30/11/2024): ");
        do {
            String data = scanner.nextLine();
            if(data.equals("0")) {
            	return 0;
            }
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
            if(hora.equals("0")) {
            	return 0;
            }
            try {
                evento.setHora(LocalTime.parse(hora, formatter));
                horaValidacao = true;
            } catch (DateTimeParseException e) {
                System.err.print("\nHora inválida, digite da forma correta: ");
            }
        } while (!horaValidacao);
        return 0;
    }

    private static int cadastrarReuniao(Reuniao evento) {
        cadastrarEvento(evento);
        String valor;
        System.out.print("Descreva a modalidade da reuniao: ");
        valor = scanner.nextLine();
        if(valor.equals("0")) {
        	return 0;
        }
        evento.setModalidade(valor);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        boolean horaValidacao = false;
        System.out.print("Informe a duração da reunião em horas:minutos (Exemplo 01:30): ");
        do {
            String horaString = scanner.nextLine();
            if(horaString.equals("0")) {
            	return 0;
            }
            try {
                LocalTime hora = LocalTime.parse(horaString, formatter);
                evento.setHoraDuracao(hora.getHour());
                evento.setMinutoDuracao(hora.getMinute());
                horaValidacao = true;
            } catch (DateTimeParseException e) {
                System.err.print("\nHorário inválida, digite da forma correta: ");
            }
        } while (!horaValidacao);
        return 0;
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