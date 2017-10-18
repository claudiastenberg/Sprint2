import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class BestGym {
    BestGym () {
    
        String filePath = "customers.txt";
        Path inFilePath;
        Scanner fil;
        String line1;
        String line2 = null;
        String[] uppgifter1;
        String[] datum;
        int år;
        int månad;
        int dag;
        boolean shouldAppend = true; 
        boolean personfinns = false; 

        try (PrintWriter w = new PrintWriter(new FileWriter("Todayscostumers.txt", shouldAppend))) {

            inFilePath = Paths.get(filePath); 
            fil = new Scanner(inFilePath);

            String s = JOptionPane.showInputDialog("Välkommen till gymmet, var god skriv ditt namn eller personnummer: ");

            if (s == null) {
            System.exit(0);
            }
            
            while (fil.hasNext()) { 
                line1 = fil.nextLine();
                uppgifter1 = line1.split(","); 

                if (fil.hasNext()) {
                    line2 = fil.nextLine();
                }
                
                datum = line2.split("-"); 
                år = Integer.parseInt(datum[0]); 
                månad = Integer.parseInt(datum[1]);
                dag = Integer.parseInt(datum[2]);

                LocalDate dagensdatum = LocalDate.now();
                LocalDate medlemsdatum = LocalDate.of(år, månad, dag);
                LocalDate minusettår = dagensdatum.minusYears(1); // Räknar från ett år minus alltså 2016-10 

                if (uppgifter1[0].trim().equalsIgnoreCase(s) || uppgifter1[1].trim().equalsIgnoreCase(s)) { 

                    personfinns = true; // Sant att personer finns. 

                    System.out.printf("%s\n %s\n", line1, line2); 

                    if (medlemsdatum.isAfter(minusettår)) { //Från 2016-10 och framåt till idag. 
                        System.out.println("Nuvarande medlem");
                        w.printf("%s\n Datum: %s\n", line1, dagensdatum);

                    } else if (medlemsdatum.isBefore(minusettår)) { // Innan 2016-10
                        System.out.println("Föredetta medlem!");
                    }
                }
            }
            if (!personfinns) {  // Inte sant om personen inte finns. 
                System.out.println(s + " du har aldrig varit medlem hos oss!");
            }

            fil.close();
           
        } catch (Exception e) { // superklass för alla exceptions. 
            System.out.println("Något har gått fel!!");
            System.out.flush();
            System.exit(0);
        }
   }
    public static void main(String[] args) throws IOException {
        
        BestGym ny = new BestGym(); 
       
    }
}
