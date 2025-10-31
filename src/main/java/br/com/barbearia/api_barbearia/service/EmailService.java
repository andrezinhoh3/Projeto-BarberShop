package br.com.barbearia.api_barbearia.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.email.username")
    private String origem;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void enviarEmail(String destino, String novaSenha) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(origem);
        message.setTo(destino);
        message.setSubject("Sua Nova Senha - Barbershop Juan");
        message.setText("Olá,\n\nSua senha foi redefinida com sucesso.\n\n" +
                "Sua nova senha temporária é: " + novaSenha +
                "\n\nRecomendamos que você altere esta senha após o primeiro login.");

        mailSender.send(message);
    }

    public void enviarEmailConfirmacao(String destino, String nome) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(origem);
        message.setTo(destino);
        message.setSubject("Confirmação de agendamento - Barbershop Juan");
        message.setText("Olá, " + nome + "\n\nSeu agendamento foi confirmado com sucesso.\n\n" +
                "Estamos aguardando você no dia e horário marcado para o atendimento. \n\n" +
                "Muito obrigado por escolher nosso serviço!");
        mailSender.send(message);
    }

}
