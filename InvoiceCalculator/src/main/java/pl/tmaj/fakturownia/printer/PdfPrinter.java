package pl.tmaj.fakturownia.printer;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import lombok.extern.log4j.Log4j2;
import pl.tmaj.fakturownia.Calculations;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.ZonedDateTime;

@Log4j2
public class PdfPrinter extends Printer {

    private final long TIMESTAMP = ZonedDateTime.now().toEpochSecond();
    private final String PDF_NAME = "invoiceMachine_" + TIMESTAMP + ".pdf";

    public PdfPrinter(Calculations calculations) {
        super(calculations);
    }

    @Override
    public void print() {
        Document document = new Document();
        try {
            FileOutputStream fileName = new FileOutputStream(PDF_NAME);
            PdfWriter.getInstance(document, fileName);
            document.open();
            fillPdf(document);
        } catch (DocumentException | IOException e) {
            log.error(e);
        }
        document.close();
    }

    private void fillPdf(Document document) {
        document.add(new Paragraph(String.format("regular salary = %f (ex. tax %f)%n", calculations.getRegularPay(), calculations.getRegularPayAfterWorkTax())));
        document.add(new Paragraph(String.format("over hours salary = %f (ex. tax %f)%n", calculations.getOverHoursPay(), calculations.getOverHoursPayAfterWorkTax())));
        document.add(new Paragraph(String.format("nett salary = %f%n", calculations.getNettSalary())));
        document.add(new Paragraph(String.format("VAT = %f%n", calculations.getVatTax())));
        document.add(new Paragraph(String.format("summary = %f%n", calculations.getWithWorkTax())));
    }
}
