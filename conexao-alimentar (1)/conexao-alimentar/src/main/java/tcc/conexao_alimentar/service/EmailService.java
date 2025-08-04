package tcc.conexao_alimentar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void enviarEmailHtml(String para, String assunto, String conteudoHtml) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(para);
            helper.setSubject(assunto);
            helper.setText(conteudoHtml, true); 

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Erro ao enviar e-mail HTML: " + e.getMessage());
        }
    }

    public void enviarEmailAprovacaoCadastro(String nome, String email) {
    String conteudoHtml = """
    <!DOCTYPE html>
    <html lang="pt-BR">
    <head>
      <meta charset="UTF-8">
    </head>
    <body style="margin: 0; padding: 0; background-color: #f0fdf4; font-family: Arial, sans-serif;">
      <div style="max-width: 600px; margin: 40px auto; background-color: #ffffff; padding: 30px; border-radius: 12px; box-shadow: 0 0 10px rgba(0,0,0,0.1); text-align: center;">
          <img src="https://res.cloudinary.com/du9zmknbe/image/upload/logo.png" alt="Conexão Alimentar" style="max-width: 150px; margin-bottom: 25px;" />
          <h2 style="font-size: 24px; color: #15803d; margin-bottom: 10px;">Cadastro Aprovado!</h2>
          <p style="font-size: 16px; color: #333;">Olá, <strong>%s</strong>! Seu cadastro foi aprovado com sucesso.</p>
          <p style="font-size: 16px; color: #333;">Agora você já pode acessar a plataforma e começar a utilizar nossos serviços.</p>
          <a href="http://localhost:5501/cadastrologin/login"
             style="display: inline-block; padding: 14px 28px; background-color: #15803d; color: #ffffff !important; text-decoration: none; border-radius: 8px; font-weight: bold; font-size: 16px; margin-top: 20px;">
             Fazer Login
          </a>
          <p style="font-size: 16px; color: #333; margin-top: 20px;">Se você não realizou esse cadastro, apenas ignore este e-mail.</p>
          <div style="font-size: 12px; color: #888; margin-top: 30px;">
              © 2025 Conexão Alimentar
          </div>
      </div>
    </body>
    </html>
    """.formatted(nome);

    enviarEmailHtml(email, "Cadastro aprovado!", conteudoHtml);
   }

   public void enviarEmailReprovacaoCadastro(String nome, String email) {
    String conteudoHtml = """
    <!DOCTYPE html>
    <html lang="pt-BR">
    <head>
      <meta charset="UTF-8">
    </head>
    <body style="margin: 0; padding: 0; background-color: #fef2f2; font-family: Arial, sans-serif;">
      <div style="max-width: 600px; margin: 40px auto; background-color: #ffffff; padding: 30px; border-radius: 12px; box-shadow: 0 0 10px rgba(0,0,0,0.1); text-align: center;">
          <img src="https://res.cloudinary.com/du9zmknbe/image/upload/logo.png" style="max-width: 150px; margin-bottom: 25px;" />
          <h2 style="font-size: 24px; color: #b91c1c; margin-bottom: 10px;">Cadastro Reprovado</h2>
          <p style="font-size: 16px; color: #333;">Olá, <strong>%s</strong>!</p>
          <p style="font-size: 16px; color: #333;">Após análise dos dados fornecidos, infelizmente seu cadastro na plataforma <strong>Conexão Alimentar</strong> não foi aprovado neste momento.</p>
          <p style="font-size: 16px; color: #333;">Você pode revisar seus dados e realizar um novo cadastro, caso desejar.</p>
          <a href="http://localhost:5501/pages/cadastrologin/cadastro.html"
             style="display: inline-block; padding: 14px 28px; background-color: #b91c1c; color: #ffffff !important; text-decoration: none; border-radius: 8px; font-weight: bold; font-size: 16px; margin-top: 20px;">
             Refazer Cadastro
          </a>
          <div style="font-size: 12px; color: #888; margin-top: 30px;">
              © 2025 Conexão Alimentar
          </div>
      </div>
    </body>
    </html>
    """.formatted(nome);

    enviarEmailHtml(email, "Cadastro não aprovado", conteudoHtml);
   }

   public void enviarEmailContaDesativada(String nome, String email, String justificativa) {
        String conteudoHtml = """
        <!DOCTYPE html>
        <html lang=\"pt-BR\">
        <head><meta charset=\"UTF-8\"></head>
        <body style='background-color:#fef2f2; font-family:Arial;'>
            <div style='max-width:600px; margin:40px auto; background:#fff; padding:30px; border-radius:12px; text-align:center;'>
                <img src='https://res.cloudinary.com/du9zmknbe/image/upload/logo.png' alt='Conexão Alimentar' style='max-width:150px; margin-bottom:25px;'>
                <h2 style='color:#b91c1c;'>Conta Desativada</h2>
                <p style='color:#333;'>Olá, <strong>%s</strong>. Sua conta foi desativada.</p>
                <p style='color:#333;'><strong>Motivo:</strong> %s</p>
                <p style='color:#333;'>Se acredita que foi um erro, entre em contato conosco.</p>
                <p style='margin-top:30px; color:#888; font-size:12px;'>©2025 Conexão Alimentar</p>
            </div>
        </body>
        </html>
        """.formatted(nome, justificativa);

        enviarEmailHtml(email, "Conta desativada", conteudoHtml);
    }



}
