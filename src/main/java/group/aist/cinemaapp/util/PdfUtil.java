package group.aist.cinemaapp.util;

import group.aist.cinemaapp.annotation.Log;
import group.aist.cinemaapp.dto.UserTicketPdfDto;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@Component
@Log
public class PdfUtil {


    public String parseThymeleafTemplate(UserTicketPdfDto ticketPdfDto) {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        Context context = new Context();
        context.setVariable("pdfDto", ticketPdfDto);

        return templateEngine.process("pdftemplate", context);
    }

   public byte[] generatePdfFromHtml(String html) throws IOException {
       /* String outputPath = "src/main/resources/uploads/user_ticket_user_"+userId+"_ticket_"+ticketNumber+".pdf";
        OutputStream outputStream1 = new FileOutputStream(outputPath);*/

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(html);
        renderer.layout();
        renderer.createPDF(outputStream);

       // outputStream.close();

       return outputStream.toByteArray();
    }



}
