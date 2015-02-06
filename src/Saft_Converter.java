import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Scanner;


public class Saft_Converter {

	public static void main(String[] args) throws IOException {

		Scanner sc = new Scanner(System.in);

		System.out.println("Please insert the file name to be converted: ");
		String filename = sc.nextLine();
		
		try {
			String line = "";
			String line2 = "";
			BufferedReader fromFile0 = new BufferedReader(new FileReader(filename));
			BufferedReader fromFile = new BufferedReader(new FileReader(filename));
			
			double sum = 0.0;
			while((line = fromFile0.readLine()) != null)
			{
				if(line.contains("<GrossTotal>"))
				{
					line2 = line.substring(line.indexOf("al>")+3, line.indexOf("</Gr"));
					
					sum += Double.parseDouble(line2);
				}
			}
			
			fromFile0.close();
			
			
			FileWriter xmlWriter = new FileWriter("NEW_"+filename, false);
			
			double price = 0.0;
			while((line = fromFile.readLine()) != null)
			{
				if(line.contains("<TaxPayable>"))
				{
					xmlWriter.write("                    <TaxPayable>0.0000</TaxPayable>\n");
				}
				else if(line.contains("<TotalCredit>0</TotalCredit>"))
				{
					xmlWriter.write(line+"\n");
				}
				else if(line.contains("<TotalCredit>"))
				{
					xmlWriter.write("            <TotalCredit>"+new DecimalFormat("#0.0000").format(sum)+"</TotalCredit>\n");
				}
				else if(line.contains("<UnitPrice>"))
				{
					line2 = line.substring(line.indexOf("ce>")+3, line.indexOf("</Uni"));
					price = Double.parseDouble(line2) * 1.23;
					line2 = new DecimalFormat("#0.00").format(price);
					price = Double.parseDouble(line2);
					xmlWriter.write("                    <UnitPrice>"+new DecimalFormat("#0.0000").format(price)+"</UnitPrice>\n");
				}
				else if(line.contains("<CreditAmount>"))
				{
					line2 = line.substring(line.indexOf("nt>")+3, line.indexOf("</Cre"));
					price = Double.parseDouble(line2) * 1.23;
					line2 = new DecimalFormat("#0.00").format(price);
					price = Double.parseDouble(line2);
					xmlWriter.write("                    <CreditAmount>"+new DecimalFormat("#0.0000").format(price)+"</CreditAmount>\n");
				}
				else if(line.contains("<NetTotal>"))
				{
					//line = fromFile.readLine();
				}
				else if(line.contains("<GrossTotal>"))
				{
					line2 = line.substring(line.indexOf("al>")+3, line.indexOf("</Gr"));
					xmlWriter.write("                    <NetTotal>"+line2+"</NetTotal>\n");
					xmlWriter.write(line+"\n");
				}
				else
					xmlWriter.write(line+"\n");
			}
			
			fromFile.close();
			System.out.println("New_"+filename + " generated successfully!");
			xmlWriter.close();

		
		} catch (FileNotFoundException e) {
			System.out.println("No such file or directory! Try again!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
}
