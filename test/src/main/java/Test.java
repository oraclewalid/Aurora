import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

import java.io.IOException;

/**
 * Created by walid on 15/04/17.
 */
public class Test {

  public static void main(String[] args) throws IOException {
    PDDocument doc = PDDocument.load("/home/walid/LawInsiderContract7pYKoWqzzJkbry5nrcoPAD.pdf");
    PDFTextStripper stripper = new PDFTextStripper();
    String text = stripper.getText(doc);
    System.out.println(text);
    doc.close();
  }
}
