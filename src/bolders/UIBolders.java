package bolders;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.border.BevelBorder;

import org.apache.commons.io.FileUtils;

public class UIBolders {

	private JFrame frame;
	private JTextField txtPalabras;
	private JList<String> lista;
	private DefaultListModel<String> listModel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		if(args.length<2){
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						UIBolders window = new UIBolders();
						window.frame.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}else{ // soy command line
			Bolders.CommandLine(args);
		}
		
	}

	/**
	 * Create the application.
	 */
	public UIBolders() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		frame.getContentPane().add(toolBar, BorderLayout.NORTH);
		
		JButton btnarchivo = new JButton("+Archivo");
		btnarchivo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				fileOpen();
			}
		});
		toolBar.add(btnarchivo);
		
		JButton btnseleccionado = new JButton("-Seleccionado");
		btnseleccionado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeSelectedFile();
			}
		});
		toolBar.add(btnseleccionado);
		
		JButton btnLimpiar = new JButton("Limpiar");
		btnLimpiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearAll();
			}
		});
		toolBar.add(btnLimpiar);
		
		JSeparator separator = new JSeparator();
		toolBar.add(separator);
		
		JButton btnprocesar = new JButton("[Procesar]");
		btnprocesar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				processBolders();
			}
		});
		toolBar.add(btnprocesar);
		
		lista = new JList<String>(new DefaultListModel<String>());
		listModel = (DefaultListModel<String>) lista.getModel();
		lista.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		frame.getContentPane().add(lista, BorderLayout.CENTER);
		
		JToolBar toolBar_1 = new JToolBar();
		toolBar_1.setFloatable(false);
		frame.getContentPane().add(toolBar_1, BorderLayout.SOUTH);
		
		JButton btnPalabras = new JButton("Palabras");
		btnPalabras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getPalabras();
			}
		});
		toolBar_1.add(btnPalabras);
		
		txtPalabras = new JTextField();
		toolBar_1.add(txtPalabras);
		txtPalabras.setColumns(10);
	}

	protected void fileOpen() {
		JFileChooser dlgOpen = new JFileChooser();
		dlgOpen.setMultiSelectionEnabled(true);
		int res = dlgOpen.showDialog(this.frame, "");
		if (res == JFileChooser.APPROVE_OPTION) {
			
			File[] files = dlgOpen.getSelectedFiles();
			for(int i=0; i<files.length; i++) addSelectedFile(files[i].toString());
		}			
	}
	
	protected void addSelectedFile(String fil){
		//DefaultListModel<String> listModel = (DefaultListModel<String>) lista.getModel();
		listModel.addElement(fil);
	}

	protected void removeSelectedFile() {
		int[] l=lista.getSelectedIndices();
		//DefaultListModel<String> listModel = (DefaultListModel<String>) lista.getModel();
		for(int i=0; i<l.length; i++) listModel.remove(l[i]);
	}

	protected void clearAll() {
		listModel.clear();
	}

	protected void getPalabras() {
		JFileChooser dlgOpen = new JFileChooser();
		dlgOpen.setMultiSelectionEnabled(false);
		dlgOpen.setApproveButtonText("Seleccionar archivos");
		int res = dlgOpen.showDialog(this.frame, "");
		if (res == JFileChooser.APPROVE_OPTION) {
			
			File fPalabras = dlgOpen.getSelectedFile();
			System.out.println(fPalabras.toString());
			try {
				txtPalabras.setText(FileUtils.readFileToString(fPalabras,"UTF-8"));
			} catch (IOException e) {
				e.printStackTrace();
			}

		}	
	}

	protected void processBolders() {
		List<String> palabras = Arrays.asList(txtPalabras.getText().split(" "));
		System.out.println(palabras);
		for(Object filename : listModel.toArray() ){
			Bolders.process((String)filename, palabras);
			System.out.println((String)filename);
		}
	}
	
}
