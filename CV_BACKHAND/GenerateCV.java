import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.layout.*;
import com.itextpdf.layout.element.*;
import javax.swing.JOptionPane; // Correct import for JOptionPane
import java.io.File;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import java.util.HashMap;
import java.io.IOException;

public class GenerateCV {

    public static void generateCV(HashMap<String, String> userData) {
        // Output PDF path
        String pdfFilePath = System.getProperty("user.home") + File.separator + "Generated_CV.pdf";

        try (PdfWriter writer = new PdfWriter(pdfFilePath);
             PdfDocument pdf = new PdfDocument(writer);
             Document document = new Document(pdf)) {

            // Set Document Title
            pdf.getDocumentInfo().setTitle("Generated CV");

            // Header Section
            addHeader(document, userData.get("First Name") + " " + userData.get("Last Name"));

            // Personal Information Section
            addSectionHeader(document, "Personal Information");
            addKeyValuePair(document, "Phone", userData.get("Phone"));
            addKeyValuePair(document, "Email", userData.get("E-mail"));
            addKeyValuePair(document, "Date of Birth", userData.get("Date of Birth (dd/MM/yyyy)"));
            addKeyValuePair(document, "LinkedIn", userData.get("LinkedIn"));

            // Summary Section
            addSectionHeader(document, "Summary");
            addParagraph(document, userData.get("Summary"));

            // Experience Section
            addSectionHeader(document, "Experience");
            addKeyValuePair(document, "Company Name", userData.get("Company Name"));
            addKeyValuePair(document, "Job Title", userData.get("Job Title"));
            addParagraph(document, userData.get("Description of Responsibilities"));

            // Education Section
            addSectionHeader(document, "Education");
            addKeyValuePair(document, "10th School Name", userData.get("10th School Name"));
            addKeyValuePair(document, "12th School Name", userData.get("12th School Name"));
            addKeyValuePair(document, "Graduation College", userData.get("Graduation College"));

            // Skills Section
            addSectionHeader(document, "Skills");
            addParagraph(document, String.join(", ", userData.get("Skill 1"),
                    userData.get("Skill 2"),
                    userData.get("Skill 3"),
                    userData.get("Skill 4"),
                    userData.get("Skill 5")));

            document.close();

            JOptionPane.showMessageDialog(null, "CV successfully generated!\nSaved at: " + pdfFilePath, "Success", JOptionPane.INFORMATION_MESSAGE);

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error while generating CV: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private static void addHeader(Document document, String name) {
        Paragraph header = new Paragraph(name)
                .setFontSize(24)
                .setBold()
                .setFontColor(new DeviceRgb(0, 102, 204));
        document.add(header);
    }

    private static void addSectionHeader(Document document, String sectionTitle) {
        Paragraph sectionHeader = new Paragraph(sectionTitle)
                .setFontSize(18)
                .setBold()
                .setUnderline()
                .setFontColor(new DeviceRgb(0, 51, 153));
        document.add(sectionHeader);
    }

    private static void addKeyValuePair(Document document, String key, String value) {
        Paragraph keyValue = new Paragraph()
                .add(new Text(key + ": ").setBold())
                .add(new Text(value));
        document.add(keyValue);
    }

    private static void addParagraph(Document document, String text) {
        document.add(new Paragraph(text));
    }
}
