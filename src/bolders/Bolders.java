package bolders;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;


public class Bolders {

	
	public static void CommandLine(String[] args) {
		
		// parameters
		System.out.println("Bolders - naranjas para markdown.");
		System.out.println("---------------------------------");
		if(args.length>1){
			//for(int i=0;i<args.length;i++){
			//	System.out.println("Param ["+i+"] : "+args[i]);
			//}
			//System.out.println("---------------------------------");
		}else{
			System.out.println("Missing parameters.");
			System.out.println("0: File <myfile.txt>");
			System.out.println("1: File <myWords.txt>");
			System.exit(404);
		}

		
		File inFile = new File(args[0]); // files-in
		String inFileDir="";
		try {
			inFileDir = inFile.toPath().getParent().toString()+"/";
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		String inFileName = inFile.toPath().getFileName().toString();
		String outFileName = "bold-"+inFileName; // files-out
		File narFile = new File(args[1]); // files-naranjas

		// naranjas
		List<String> naranjas = new ArrayList<String>(); // algo por defecto
		try {
			naranjas = FileUtils.readLines(narFile);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(404);
		}
		
		// inform
		System.out.println("Entrada:  ["+inFileDir+inFileName+"]");
		System.out.println("Salida:   ["+inFileDir+outFileName+"]");
		System.out.println("Naranjas: "+naranjas.toString());
		System.out.println("---------------------------------");
		
		// salida
		File outFile = new File(inFileDir+outFileName);

		boldersProcess(inFile, outFile, naranjas);
	}
	public static void process(String filename, List<String> palabras){
		
		File inFile, outFile;
		String	inFileDir="",
				inFileName="",
				outFileName="";
		
		inFile = new File(filename);
		try {
			inFileDir = inFile.toPath().getParent().toString()+"/";
			inFileName = inFile.toPath().getFileName().toString();
			outFileName = "bold-"+inFileName;		
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Imposible procesar archivo ["+filename+"]");
			System.exit(500);
		}
		outFile = new File(inFileDir+outFileName);
		
		boldersProcess(inFile,outFile,palabras);
	}
	private static void boldersProcess(File in, File out, List<String> naranjas){
		
		// entrada
		String data="no data";
		try {
			data = FileUtils.readFileToString(in);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(404);
		}		
		
		// naranjas
		for(String naranja : naranjas){
			data= StringUtils.replace(data, naranja, "**"+naranja+"**");
		}
		
		// salida
		try {
			out.createNewFile();
			FileUtils.writeStringToFile(out, data);
			System.out.println("Bolders processed >> "+out.toString());
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Imposible guardar archivo ["+out.toString()+"]");
			System.exit(500);
		}
	}
	
}
