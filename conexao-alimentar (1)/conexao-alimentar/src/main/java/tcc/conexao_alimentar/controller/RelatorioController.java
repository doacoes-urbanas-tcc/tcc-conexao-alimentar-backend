package tcc.conexao_alimentar.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.awt.Color;


import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import tcc.conexao_alimentar.DTO.RelatorioDoacaoDTO;
import tcc.conexao_alimentar.DTO.RelatorioDoacaoStatusDTO;
import tcc.conexao_alimentar.DTO.RelatorioDoacoesPorDoadorDTO;
import tcc.conexao_alimentar.DTO.RelatorioDoacoesPorReceptorDTO;
import tcc.conexao_alimentar.DTO.RelatorioEfetividadeDTO;
import tcc.conexao_alimentar.DTO.RelatorioQuantidadeTotalDTO;
import tcc.conexao_alimentar.service.RelatorioService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/relatorio")
@Tag(name = "Relatórios", description = "Endpoints exportação de relatórios de pdf e csv")
public class RelatorioController {

    private final RelatorioService relatorioService;

    @Operation(summary = "Exportação do relatório de doações mensais em csv",description = "Endpoint para exportação de relatórios referentes as doações efetuadas no mês em questão em formato csv")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Relatório exportado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "403", description = "Acesso não autorizado"),
    })
    @GetMapping("/csv/doacoes-mensais")
    public void exportarCSV(@RequestParam int ano, @RequestParam int mes, HttpServletResponse response) throws IOException {
    List<RelatorioDoacaoDTO> relatorio = relatorioService.gerarRelatorioMensal(ano, mes);

    response.setHeader("Content-Disposition", "attachment; filename=relatorio_doacoes_" + ano + "_" + mes + ".csv");
    response.setContentType("text/csv");

    PrintWriter writer = response.getWriter();
    writer.println("ID,Alimento,Quantidade,Conclusão,Categoria,Doador (CNPJ),ONG (CNPJ)");

    for (RelatorioDoacaoDTO dto : relatorio) {
        writer.printf("%d,%s,%s %s,%s,%s (%s),%s (%s)\n",
            dto.getIdDoacao(),
            dto.getNomeAlimento(),
            dto.getQuantidade(), dto.getUnidadeMedida(),
            dto.getDataConclusao(),
            dto.getNomeDoador(), dto.getCnpjDoador(),
            dto.getNomeOng(), dto.getCnpjOng()
        );
    }

    writer.flush();
   }

    @Operation(summary = "Exportação do relatório de doações mensais em pdf",description = "Endpoint para exportação de relatórios referentes as doações efetuadas no mês em questão em formato pdf")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Relatório exportado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "403", description = "Acesso não autorizado"),
    })

   @GetMapping("/pdf/doacoes-mensais")
    public void exportarPDF(@RequestParam int ano, @RequestParam int mes, HttpServletResponse response) throws Exception {
    List<RelatorioDoacaoDTO> relatorio = relatorioService.gerarRelatorioMensal(ano, mes);

    response.setHeader("Content-Disposition", "attachment; filename=relatorio_doacoes_" + ano + "_" + mes + ".pdf");
    response.setContentType("application/pdf");

    Document document = new Document();
    PdfWriter.getInstance(document, response.getOutputStream());
    document.open();
    Image logo = Image.getInstance(new ClassPathResource("static/logo.png").getURL());

    logo.scaleToFit(120, 120);
    logo.setAlignment(Image.ALIGN_CENTER);
    document.add(logo);

    Paragraph titulo = new Paragraph("Relatório de Doações Concluídas - " + mes + "/" + ano, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12));
    titulo.setAlignment(Element.ALIGN_CENTER);
    document.add(titulo);

    document.add(new Paragraph(" "));


    PdfPTable table = new PdfPTable(new float[]{0.7f, 2.2f, 1.8f, 1.6f, 1.8f, 3.2f, 3.2f});
    table.setWidthPercentage(100f);

    Color vermelho = new Color(199, 44, 65);
    Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, Color.WHITE);
    Font bodyFont = FontFactory.getFont(FontFactory.HELVETICA, 9);

    String[] headers = {"ID", "Alimento", "Quantidade", "Conclusão", "Categoria", "Doador (CNPJ)", "ONG (CNPJ)"};
    for (String col : headers) {
        PdfPCell cell = new PdfPCell(new Phrase(col, headerFont));
        cell.setBackgroundColor(vermelho);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(5f);
        table.addCell(cell);
    }

    for (RelatorioDoacaoDTO dto : relatorio) {
        table.addCell(new PdfPCell(new Phrase(dto.getIdDoacao().toString(), bodyFont)));
        table.addCell(new PdfPCell(new Phrase(dto.getNomeAlimento(), bodyFont)));
        table.addCell(new PdfPCell(new Phrase(dto.getQuantidade() + " " + dto.getUnidadeMedida(), bodyFont)));
        table.addCell(new PdfPCell(new Phrase(dto.getDataConclusao().toString(), bodyFont)));
        table.addCell(new PdfPCell(new Phrase(dto.getCategoria(), bodyFont)));
        table.addCell(new PdfPCell(new Phrase(dto.getNomeDoador() + "\n" +" ("+ dto.getCnpjDoador() + ")" , bodyFont)));
        table.addCell(new PdfPCell(new Phrase(dto.getNomeOng() + "\n"+" (" + dto.getCnpjOng() + ")", bodyFont)));
    }

    document.add(table);
    document.close();
    }

    @Operation(summary = "Exportação do relatório de doações que estão pendentes ou foram expiradas em csv",description = "Endpoint para exportação de relatórios referentes as doações  pendentes ou expiradas em formato csv")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Relatório exportado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "403", description = "Acesso não autorizado"),
       
    })

    @GetMapping("/csv/pendentes-ou-expiradas")
    public void exportarCSVStatus(HttpServletResponse response) throws IOException {
    List<RelatorioDoacaoStatusDTO> relatorio = relatorioService.gerarRelatorioPendentesOuExpiradas();
    response.setHeader("Content-Disposition", "attachment; filename=relatorio_doacoes_pendentes_ou_expiradas.csv");
    response.setContentType("text/csv");
    PrintWriter writer = response.getWriter();

    writer.println("ID,Alimento,Categoria,Status,Validade,Doador,CNPJ");
    for (RelatorioDoacaoStatusDTO dto : relatorio) {
        writer.printf("%d,%s,%s,%s,%s,%s,%s\n",
            dto.getIdDoacao(),
            dto.getNomeAlimento(),
            dto.getCategoria(),
            dto.getStatus(),
            dto.getDataExpiracao(),
            dto.getNomeDoador(),
            dto.getCnpjDoador()
        );
    }
    writer.flush();
    }

    @Operation(summary = "Exportação do relatório de doações que estão pendentes ou foram expiradas em pdf",description = "Endpoint para exportação de relatórios referentes as doações  pendentes ou expiradas em formato pdf")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Relatório exportado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "403", description = "Acesso não autorizado"),
    })


    @GetMapping("/pdf/pendentes-ou-expiradas")
    public void exportarPDFStatus(HttpServletResponse response) throws Exception {
    List<RelatorioDoacaoStatusDTO> relatorio = relatorioService.gerarRelatorioPendentesOuExpiradas();
    response.setHeader("Content-Disposition", "attachment; filename=relatorio_doacoes_pendentes_ou_expiradas.pdf");
    response.setContentType("application/pdf");

    Document document = new Document();
    PdfWriter.getInstance(document, response.getOutputStream());
    document.open();
    Image logo = Image.getInstance(new ClassPathResource("static/logo.png").getURL());
    logo.scaleToFit(120, 120);
    logo.setAlignment(Image.ALIGN_CENTER);
    document.add(logo);

    Paragraph titulo = new Paragraph("Relatório: Doações Pendentes ou Expiradas", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12));
    titulo.setAlignment(Element.ALIGN_CENTER);
    document.add(titulo);


    
    document.add(new Paragraph(" "));

    PdfPTable table = new PdfPTable(new float[]{0.7f, 2f, 1.6f, 1.3f, 1.6f, 2.5f, 2.5f});
    table.setWidthPercentage(100f);

    Color vermelho = new Color(199, 44, 65);
    Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, Color.WHITE);
    Font bodyFont = FontFactory.getFont(FontFactory.HELVETICA, 9);

    String[] headers = {"ID", "Alimento", "Categoria", "Status", "Validade", "Doador", "CNPJ"};
    for (String col : headers) {
        PdfPCell cell = new PdfPCell(new Phrase(col, headerFont));
        cell.setBackgroundColor(vermelho);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(5f);
        table.addCell(cell);
    }

    for (RelatorioDoacaoStatusDTO dto : relatorio) {
        table.addCell(new PdfPCell(new Phrase(dto.getIdDoacao().toString(), bodyFont)));
        table.addCell(new PdfPCell(new Phrase(dto.getNomeAlimento(), bodyFont)));
        table.addCell(new PdfPCell(new Phrase(dto.getCategoria(), bodyFont)));
        table.addCell(new PdfPCell(new Phrase(dto.getStatus().name(), bodyFont)));
        table.addCell(new PdfPCell(new Phrase(dto.getDataExpiracao().toString(), bodyFont)));
        table.addCell(new PdfPCell(new Phrase(dto.getNomeDoador(), bodyFont)));
        table.addCell(new PdfPCell(new Phrase(dto.getCnpjDoador(), bodyFont)));
    }

    document.add(table);
    document.close();
   }
    @Operation(summary = "Exportação do relatório da quantidade doações efetuadas no mês csv",description = "Endpoint para exportação de relatórios referentes a quantidade de doações edetuadas no mês csv")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Relatório exportado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "403", description = "Acesso não autorizado"),
    })

   @GetMapping("/csv/quantidade-total")
   public void exportarCSVQuantidadeTotal(HttpServletResponse response) throws IOException {
    List<RelatorioQuantidadeTotalDTO> relatorio = relatorioService.gerarRelatorioQuantidadeTotal();
    response.setHeader("Content-Disposition", "attachment; filename=relatorio_quantidade_total_por_categoria.csv");
    response.setContentType("text/csv");
    PrintWriter writer = response.getWriter();

    writer.println("Categoria,Quantidade Total");
    for (RelatorioQuantidadeTotalDTO dto : relatorio) {
        writer.printf("%s,%.2f\n", dto.getCategoria(), dto.getQuantidadeTotal());
    }
    writer.flush();
   }

   @Operation(summary = "Exportação do relatório a quantidade doações efetuadas no mês em pdf",description = "Endpoint para exportação de relatórios referentes a quantidade doações efetuadas no mês csv")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Relatório exportado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "403", description = "Acesso não autorizado"),
    })

   @GetMapping("/pdf/quantidade-total")
    public void exportarPDFQuantidadeTotal(HttpServletResponse response) throws Exception {
    List<RelatorioQuantidadeTotalDTO> relatorio = relatorioService.gerarRelatorioQuantidadeTotal();
    response.setHeader("Content-Disposition", "attachment; filename=relatorio_quantidade_total_por_categoria.pdf");
    response.setContentType("application/pdf");

    Document document = new Document();
    PdfWriter.getInstance(document, response.getOutputStream());
    document.open();

    Image logo = Image.getInstance(new ClassPathResource("static/logo.png").getURL());
    logo.scaleToFit(120, 120);
    logo.setAlignment(Image.ALIGN_CENTER);
    document.add(logo);

    Paragraph titulo = new Paragraph("Relatório: Quantidade Total Doada por Categoria", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12));
    titulo.setAlignment(Element.ALIGN_CENTER);
    document.add(titulo);

    document.add(new Paragraph(" "));

    PdfPTable table = new PdfPTable(new float[]{3f, 2f});
    table.setWidthPercentage(70f);

    Color vermelho = new Color(199, 44, 65);
    Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, Color.WHITE);
    Font bodyFont = FontFactory.getFont(FontFactory.HELVETICA, 9);

    String[] headers = {"Categoria", "Quantidade Total"};
    for (String col : headers) {
        PdfPCell cell = new PdfPCell(new Phrase(col, headerFont));
        cell.setBackgroundColor(vermelho);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(5f);
        table.addCell(cell);
    }

    for (RelatorioQuantidadeTotalDTO dto : relatorio) {
        String categoria = dto.getCategoria();
        String quantidade = String.format("%.2f %s", dto.getQuantidadeTotal(), dto.getUnidadeMedida());

        table.addCell(new PdfPCell(new Phrase(categoria, bodyFont)));
        table.addCell(new PdfPCell(new Phrase(quantidade, bodyFont)));
    }

    document.add(table);
    document.close();
   }

   
    @Operation(summary = "Exportação do relatório a quantidade doações por doador efetuadas dentro de um mês em pdf",description = "Endpoint para exportação de relatórios referentes a quantidade doações efetuadas por um doador dentro de um mês pdf")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Relatório exportado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "403", description = "Acesso não autorizado"),
    })


   @GetMapping("/pdf/doacoes-por-doador")
   public void exportarRelatorioPorDoadorPDF(HttpServletResponse response) throws Exception {
    List<RelatorioDoacoesPorDoadorDTO> relatorio = relatorioService.gerarRelatorioPorDoador();

    response.setHeader("Content-Disposition", "attachment; filename=relatorio_doacoes_por_doador.pdf");
    response.setContentType("application/pdf");

    Document document = new Document();
    PdfWriter.getInstance(document, response.getOutputStream());
    document.open();

    Image logo = Image.getInstance(new ClassPathResource("static/logo.png").getURL());
    logo.scaleToFit(100, 100);
    logo.setAlignment(Image.ALIGN_CENTER);
    document.add(logo);

    Paragraph titulo = new Paragraph("Relatório de Doações por Doador",
        FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, new Color(199, 44, 65)));
    titulo.setAlignment(Element.ALIGN_CENTER);
    titulo.setSpacingBefore(10);
    titulo.setSpacingAfter(20);
    document.add(titulo);

    PdfPTable table = new PdfPTable(new float[]{2.5f, 2.5f, 1.8f, 1.5f, 1.2f});
    table.setWidthPercentage(100f);

    Color vermelho = new Color(199, 44, 65);
    Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, Color.WHITE);
    Font bodyFont = FontFactory.getFont(FontFactory.HELVETICA, 9);

    String[] headers = {"Doador", "CNPJ", "Categoria", "Quantidade", "Unidade"};
    for (String col : headers) {
        PdfPCell cell = new PdfPCell(new Phrase(col, headerFont));
        cell.setBackgroundColor(vermelho);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(5f);
        table.addCell(cell);
    }

    for (RelatorioDoacoesPorDoadorDTO dto : relatorio) {
        table.addCell(new PdfPCell(new Phrase(dto.getNomeDoador(), bodyFont)));
        table.addCell(new PdfPCell(new Phrase(dto.getCnpjDoador(), bodyFont)));
        table.addCell(new PdfPCell(new Phrase(dto.getCategoria(), bodyFont)));
        table.addCell(new PdfPCell(new Phrase(String.format("%.2f", dto.getQuantidadeTotal()), bodyFont)));
        table.addCell(new PdfPCell(new Phrase(dto.getUnidade(), bodyFont)));
    }

    document.add(table);
    document.close();
    
    }
    @Operation(summary = "Exportação do relatório da quantidade doações efetuadas por um doador dentro de um mês csv",description = "Endpoint para exportação de relatórios referentes a quantidade de doações edetuadas por um doador dentro de um  mês csv")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Relatório exportado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "403", description = "Acesso não autorizado"),
    })
    @GetMapping("/csv/doacoes-por-doador")
    public void exportarCSVPorDoador(HttpServletResponse response) throws IOException {
    List<RelatorioDoacoesPorDoadorDTO> relatorio = relatorioService.gerarRelatorioPorDoador();

    response.setHeader("Content-Disposition", "attachment; filename=relatorio_doacoes_por_doador.csv");
    response.setContentType("text/csv");

    PrintWriter writer = response.getWriter();
    writer.println("Doador,CNPJ,Categoria,Quantidade Total,Unidade");

    for (RelatorioDoacoesPorDoadorDTO dto : relatorio) {
        writer.printf("%s,%s,%s,%.2f,%s\n",
            dto.getNomeDoador(),
            dto.getCnpjDoador(),
            dto.getCategoria(),
            dto.getQuantidadeTotal(),
            dto.getUnidade()
        );
    }

    writer.flush();
   }

    @Operation(summary = "Exportação do relatório da quantidade doações efetuadas por um receptor dentro de um mês csv",description = "Endpoint para exportação de relatórios referentes a quantidade de doações edetuadas por um receptor dentro de um  mês csv")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Relatório exportado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "403", description = "Acesso não autorizado"),
    })
    @GetMapping("/csv/doacoes-por-receptor")
    public void exportarCSVPorReceptor(HttpServletResponse response) throws IOException {
    List<RelatorioDoacoesPorReceptorDTO> relatorio = relatorioService.gerarRelatorioPorReceptor();

    response.setHeader("Content-Disposition", "attachment; filename=relatorio_doacoes_por_receptor.csv");
    response.setContentType("text/csv");

    PrintWriter writer = response.getWriter();
    writer.println("Receptor,CNPJ,Categoria,Quantidade Total,Unidade");

    for (RelatorioDoacoesPorReceptorDTO dto : relatorio) {
        writer.printf("%s,%s,%s,%.2f,%s\n",
            dto.getNomeReceptor(),
            dto.getCnpjReceptor(),
            dto.getCategoria(),
            dto.getQuantidadeTotal(),
            dto.getUnidade()
        );
    }

    writer.flush();
   }

    @Operation(summary = "Exportação do relatório da quantidade doações efetuadas por um receptor dentro de um mês csv",description = "Endpoint para exportação de relatórios referentes a quantidade de doações edetuadas por um receptor dentro de um  mês csv")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Relatório exportado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "403", description = "Acesso não autorizado"),
    })
    @GetMapping("/pdf/doacoes-por-receptor")
    public void exportarPDFPorReceptor(HttpServletResponse response) throws Exception {
    List<RelatorioDoacoesPorReceptorDTO> relatorio = relatorioService.gerarRelatorioPorReceptor();

    response.setHeader("Content-Disposition", "attachment; filename=relatorio_doacoes_por_receptor.pdf");
    response.setContentType("application/pdf");

    Document document = new Document();
    PdfWriter.getInstance(document, response.getOutputStream());
    document.open();

    Image logo = Image.getInstance(new ClassPathResource("static/logo.png").getURL());
    logo.scaleToFit(100, 100);
    logo.setAlignment(Element.ALIGN_CENTER);
    document.add(logo);

    Paragraph titulo = new Paragraph("Relatório: Doações por Receptor", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14));
    titulo.setAlignment(Element.ALIGN_CENTER);
    titulo.setSpacingAfter(10f);
    document.add(titulo);

    document.add(new Paragraph(" "));

    PdfPTable table = new PdfPTable(new float[]{2.5f, 2f, 1.6f, 1.4f, 1.4f});
    table.setWidthPercentage(100f);

    Color vermelho = new Color(199, 44, 65);
    Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, Color.WHITE);
    Font bodyFont = FontFactory.getFont(FontFactory.HELVETICA, 9);

    String[] headers = {"Receptor", "CNPJ", "Categoria", "Qtd Total", "Unidade"};
    for (String col : headers) {
        PdfPCell cell = new PdfPCell(new Phrase(col, headerFont));
        cell.setBackgroundColor(vermelho);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(5f);
        table.addCell(cell);
    }

    for (RelatorioDoacoesPorReceptorDTO dto : relatorio) {
        table.addCell(new PdfPCell(new Phrase(dto.getNomeReceptor(), bodyFont)));
        table.addCell(new PdfPCell(new Phrase(dto.getCnpjReceptor(), bodyFont)));
        table.addCell(new PdfPCell(new Phrase(dto.getCategoria(), bodyFont)));
        table.addCell(new PdfPCell(new Phrase(String.format("%.2f", dto.getQuantidadeTotal()), bodyFont)));
        table.addCell(new PdfPCell(new Phrase(dto.getUnidade(), bodyFont)));
    }

    document.add(table);
    document.close();
    }

    @Operation(summary = "Exportação do relatório da efetividade das doações em csv",description = "Endpoint para exportação de relatórios referentes a efetividade das doações em csv")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Relatório exportado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "403", description = "Acesso não autorizado"),
    })
    @GetMapping("/csv/efetividade")
    public void exportarCSVEfetividade(HttpServletResponse response) throws IOException {
    List<RelatorioEfetividadeDTO> relatorio = relatorioService.gerarRelatorioEfetividade();
    response.setHeader("Content-Disposition", "attachment; filename=relatorio_efetividade.csv");
    response.setContentType("text/csv");
    PrintWriter writer = response.getWriter();
    writer.println("Categoria,Total Doações,Concluídas,% Efetividade");
    for (RelatorioEfetividadeDTO dto : relatorio) {
        writer.printf("%s,%d,%d,%.2f%%\n",
            dto.getCategoria(),
            dto.getTotalDoacoes(),
            dto.getConcluidas(),
            dto.getEfetividadePercentual()
        );
    }
    writer.flush();
    }

    @Operation(summary = "Exportação do relatório da efetividade das doações em pdf",description = "Endpoint para exportação de relatórios referentes a efetividade das doações em pdf")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Relatório exportado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "403", description = "Acesso não autorizado"),
    })

    @GetMapping("/pdf/efetividade")
    public void exportarPDFEfetividade(HttpServletResponse response) throws Exception {
    List<RelatorioEfetividadeDTO> relatorio = relatorioService.gerarRelatorioEfetividade();
    response.setHeader("Content-Disposition", "attachment; filename=relatorio_efetividade.pdf");
    response.setContentType("application/pdf");

    Document document = new Document();
    PdfWriter.getInstance(document, response.getOutputStream());
    document.open();

    Image logo = Image.getInstance(new ClassPathResource("static/logo.png").getURL());
    logo.scaleToFit(100, 100);
    logo.setAlignment(Element.ALIGN_CENTER);
    document.add(logo);

    Paragraph titulo = new Paragraph("Relatório: Efetividade das Doações", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14));
    titulo.setAlignment(Element.ALIGN_CENTER);
    titulo.setSpacingAfter(10f);
    document.add(titulo);

    document.add(new Paragraph(" ")); 

    PdfPTable table = new PdfPTable(new float[]{2.5f, 2f, 2f, 2f});
    table.setWidthPercentage(100f);

    Color vermelho = new Color(199, 44, 65);
    Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, Color.WHITE);
    Font bodyFont = FontFactory.getFont(FontFactory.HELVETICA, 9);
    String[] headers = {"Categoria", "Total", "Concluídas", "Efetividade (%)"};

    for (String col : headers) {
        PdfPCell cell = new PdfPCell(new Phrase(col, headerFont));
        cell.setBackgroundColor(vermelho);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(5f);
        table.addCell(cell);
    }

    for (RelatorioEfetividadeDTO dto : relatorio) {
        table.addCell(new PdfPCell(new Phrase(dto.getCategoria(), bodyFont)));
        table.addCell(new PdfPCell(new Phrase(String.valueOf(dto.getTotalDoacoes()), bodyFont)));
        table.addCell(new PdfPCell(new Phrase(String.valueOf(dto.getConcluidas()), bodyFont)));
        table.addCell(new PdfPCell(new Phrase(String.format("%.2f", dto.getEfetividadePercentual()), bodyFont)));
    }

    document.add(table);
    document.close();
   }
}
