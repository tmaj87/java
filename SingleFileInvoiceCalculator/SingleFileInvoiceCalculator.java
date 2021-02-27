package pl.tmaj.fakturownia;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import lombok.extern.log4j.Log4j2;

import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.ZonedDateTime;

public class SingleFileInvoiceCalculator {

    public static void main(String[] args) {
        double hours = 160;
        double wage = 25;
        double overHours = 4;
        test(hours, wage, overHours);
        System.out.println();
        testV2(hours, wage, overHours);
    }

    private static void test(double hours, double wage, double overHours) {
        InvoiceMachine machine = new InvoiceMachine(hours, wage, overHours);
        machine.toConsole();
    }

    private static void testV2(double hours, double wage, double overHours) {
        InvoiceMachineV2 machine = new InvoiceMachineV2(hours, wage, overHours);
        machine.toConsole();
        machine.toPdf();
    }
}

class InvoiceMachine {

    protected Calculations calculations;

    protected InvoiceMachine() {}

    public InvoiceMachine(double hours, double wage, double overHours) {
        calculations = new DoubleCalculations(hours, wage, overHours);
    }

    public void toConsole() {
        System.out.printf("regular salary = %f (ex. tax %f)%n",  calculations.getRegularPay(), calculations.getRegularPayAfterWorkTax());
        System.out.printf("over hours salary = %f (ex. tax %f)%n", calculations.getOverHoursPay(), calculations.getOverHoursPayAfterWorkTax());
        System.out.printf("nett salary = %f%n", calculations.getNettSalary());
        System.out.printf("VAT = %f%n", calculations.getVatTax());
        System.out.printf("summary = %f%n", calculations.getAfterWorkTax());
    }
}

@Log4j2
class InvoiceMachineV2 extends InvoiceMachine {

    private final long TIMESTAMP = ZonedDateTime.now().toEpochSecond();
    private final String PDF_NAME = "invoiceMachine_" + TIMESTAMP + ".pdf";

    public InvoiceMachineV2(double hours, double wage, double overHours) {
        calculations = new BigDecimalCalculations(hours, wage, overHours);
    }

    public void toPdf() {
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
        document.add(new Paragraph(String.format("summary = %f%n", calculations.getAfterWorkTax())));
    }
}

interface Calculations {

    double getRegularPay();
    double getOverHoursPay();
    double getVatTax();
    double getRegularPayAfterWorkTax();
    double getOverHoursPayAfterWorkTax();
    double getNettSalary();
    double getAfterWorkTax();
}

class DoubleCalculations implements Calculations {

    private static final double OVER_HOUR_FACTOR = 1.5;
    private static final double VAT_23 = .23;
    private static final double LINEAR_WORK_TAX = .19;

    private final double regularPay;
    private final double regularPayTax;
    private final double overHoursPay;
    private final double overHoursPayTax;
    private final double taxation;

    public DoubleCalculations(double hours, double wage, double overHours) {
        regularPay = hours * wage;
        regularPayTax = regularPay * LINEAR_WORK_TAX;
        overHoursPay = overHours * OVER_HOUR_FACTOR * wage;
        overHoursPayTax = overHoursPay * LINEAR_WORK_TAX;
        taxation = (regularPay + overHoursPay) * VAT_23;
    }

    @Override
    public double getRegularPay() {
        return regularPay;
    }

    @Override
    public double getOverHoursPay() {
        return overHoursPay;
    }

    @Override
    public double getVatTax() {
        return taxation;
    }

    @Override
    public double getRegularPayAfterWorkTax() {
        return regularPay - regularPayTax;
    }

    @Override
    public double getOverHoursPayAfterWorkTax() {
        return overHoursPay - overHoursPayTax;
    }

    @Override
    public double getNettSalary() {
        return regularPay + overHoursPay;
    }

    @Override
    public double getAfterWorkTax() {
        return getNettSalary() + taxation;
    }
}

class BigDecimalCalculations implements Calculations {

    private static final BigDecimal OVER_HOUR_FACTOR = BigDecimal.valueOf(1.5);
    private static final BigDecimal VAT_23 = BigDecimal.valueOf(.23);
    private static final BigDecimal LINEAR_WORK_TAX = BigDecimal.valueOf(.19);

    private final BigDecimal regularPay;
    private final BigDecimal regularPayTax;
    private final BigDecimal overHoursPay;
    private final BigDecimal overHoursPayTax;
    private final BigDecimal taxation;

    public BigDecimalCalculations(double hours, double wage) {
        this(BigDecimal.valueOf(hours), BigDecimal.valueOf(wage), BigDecimal.ZERO);
    }

    public BigDecimalCalculations(double hours, double wage, double overHours) {
        this(BigDecimal.valueOf(hours), BigDecimal.valueOf(wage), BigDecimal.valueOf(overHours));
    }

    public BigDecimalCalculations(BigDecimal hours, BigDecimal wage, BigDecimal overHours) {
        regularPay = hours.multiply(wage);
        regularPayTax = regularPay.multiply(LINEAR_WORK_TAX);
        overHoursPay = overHours.multiply(OVER_HOUR_FACTOR).multiply(wage);
        overHoursPayTax = overHoursPay.multiply(LINEAR_WORK_TAX);
        taxation = regularPay.add(overHoursPay).multiply(VAT_23);
    }

    @Override
    public double getRegularPay() {
        return roundAndConvert(regularPay);
    }

    @Override
    public double getRegularPayAfterWorkTax() {
        return roundAndConvert(regularPay.subtract(regularPayTax));
    }

    @Override
    public double getOverHoursPay() {
        return roundAndConvert(overHoursPay);
    }

    @Override
    public double getOverHoursPayAfterWorkTax() {
        return roundAndConvert(overHoursPay.subtract(overHoursPayTax));
    }

    @Override
    public double getVatTax() {
        return roundAndConvert(taxation);
    }

    @Override
    public double getNettSalary() {
        return roundAndConvert(regularPay.add(overHoursPay));
    }

    @Override
    public double getAfterWorkTax() {
        return roundAndConvert(taxation.add(regularPay).add(overHoursPay));
    }

    private double roundAndConvert(BigDecimal value) {
        return value.setScale(2, RoundingMode.CEILING).doubleValue();
    }
}
