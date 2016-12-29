package finalproject;

/**
 * Final Project
 * Phone Book application
 * Add, modify, delete, and browse contacts
 * 
 * @author Richard Cummings
 */
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

/**
 * Defines Phone Book app
 * Add, modify, delete, and browse through contacts
 * Contacts can be saved to file
 * Final Project - Java 2
 * @author Richard Cummings
 * student # - 991 226 908
 */
@SuppressWarnings("serial")
public class PhoneBook extends JFrame {
	Container c;
	JMenuBar bar;
	JMenu menuFile, menuEdit;
	JMenuItem menuItemAdd, menuItemSave, menuItemMod, menuItemDel, menuItemBrowse;
	JToolBar tb;
	JTextField txtFName, txtLName, txtNumber, txtEmail;
	JButton cmdAdd, cmdMod, cmdDel, cmdBrowse, cmdSave, cmdIconSave, cmdSaveAs;
	JLabel lblFName, lblLName, lblNumber, lblEmail;
	JTextArea txtDisplay;
	JPanel pnlButtons, pnlText;
	// File to store contacts (default)
	String file = "c:\\My Documents\\Java\\contacts.txt";
	// ArrayList to store contacts
	ArrayList<Contact> contacts;
	
	/**
	 *  Defines phone book app
	 */
	public PhoneBook() {
		super("Phone Book"); // set title
		
		c = getContentPane();
		
		bar = new JMenuBar(); 
		this.setJMenuBar(bar);  // set menu bar
		
		// Add 'File' to menu bar
		menuFile = new JMenu("File");
		menuFile.setMnemonic(KeyEvent.VK_F);
		
		// Add menu items and mnemonics
		menuItemAdd = new JMenuItem("Add");
		menuItemAdd.setMnemonic(KeyEvent.VK_A);
		menuItemAdd.addActionListener(new AddButtonHandler());
		menuItemSave = new JMenuItem("Save");
		menuItemSave.setMnemonic(KeyEvent.VK_S);
		menuItemSave.addActionListener(new SaveButtonHandler());
		
		menuFile.add(menuItemAdd); menuFile.add(menuItemSave);
		bar.add(menuFile);
		
		// Add 'Edit' to menu bar
		menuEdit = new JMenu("Edit");
		menuEdit.setMnemonic(KeyEvent.VK_E);
		
		menuItemMod = new JMenuItem("Modify");
		menuItemMod.setMnemonic(KeyEvent.VK_M);
		menuItemMod.addActionListener(new ModButtonHandler());
		menuItemDel = new JMenuItem("Delete");
		menuItemDel.setMnemonic(KeyEvent.VK_D);
		//menuItemDel.addActionListener(new DelButtonHandler());
		menuItemBrowse = new JMenuItem("Browse");
		menuItemBrowse.setMnemonic(KeyEvent.VK_B);
		menuItemBrowse.addActionListener(new BrowseButtonHandler());
		
		menuEdit.add(menuItemMod); menuEdit.add(menuItemDel); menuEdit.add(menuItemBrowse);
		bar.add(menuEdit);
		
		tb = new JToolBar("Toolbar");
		
		ImageIcon iconSave = new ImageIcon("images/save.png");
		ImageIcon iconSaveAs = new ImageIcon("images/save_as.png");
		
		cmdIconSave = new JButton(iconSave);
		cmdSaveAs = new JButton(iconSaveAs);
		
		cmdIconSave.addActionListener(new SaveButtonHandler());
		cmdSaveAs.addActionListener(new SaveButtonHandler());
		
		tb.add(cmdIconSave); tb.add(cmdSaveAs);
		
		c.add(tb, BorderLayout.NORTH);
		
		// add first panel containing text fields for user input
		pnlText = new JPanel();
		pnlText.setLayout(new GridLayout(2,2));
		
		// text fields for manipulating data
		lblFName = new JLabel("First Name: ");
		lblLName = new JLabel("Last Name: ");
		lblNumber = new JLabel("Phone No: ");
		lblEmail = new JLabel("e-mail: ");
		txtFName = new JTextField(10);
		txtLName = new JTextField(15);
		txtNumber = new JTextField(10);
		txtEmail = new JTextField(15);
		
		// add labels and text fields to panel
		pnlText.add(lblFName); pnlText.add(txtFName);
		pnlText.add(lblLName); pnlText.add(txtLName);
		pnlText.add(lblNumber); pnlText.add(txtNumber);
		pnlText.add(lblEmail); pnlText.add(txtEmail);
		
		// add panel to container
		c.add(pnlText, BorderLayout.NORTH);
		
		// add display field, contact information can be displayed here
		txtDisplay = new JTextArea(100, 100);
		c.add(txtDisplay, BorderLayout.CENTER);
		
		// add panel for buttons
		pnlButtons = new JPanel();
		pnlButtons.setLayout(new FlowLayout());

		cmdAdd = new JButton("Add");
		cmdAdd.addActionListener(new AddButtonHandler());
		cmdMod = new JButton("Modify");
		cmdMod.addActionListener(new ModButtonHandler());
		cmdDel = new JButton("Delete");
		//cmdDel.addActionListener(new DelButtonHandler());
		cmdBrowse = new JButton("Browse");
		cmdBrowse.addActionListener(new BrowseButtonHandler());
		cmdSave = new JButton("Save");
		cmdSave.addActionListener(new SaveButtonHandler());
		
		pnlButtons.add(cmdAdd); pnlButtons.add(cmdMod);
		pnlButtons.add(cmdDel); pnlButtons.add(cmdBrowse);
		pnlButtons.add(cmdSave);
		
		// add panel to container, south position
		c.add(pnlButtons, BorderLayout.SOUTH);
		
		// ArrayList to hold contact objects
		contacts = new ArrayList<Contact>();
		try {
			// if file does not exist, create it
			File f = new File(file);
			if (!f.exists())
				f.createNewFile();
			else {
				Scanner s = new Scanner(f);
				while(s.hasNextLine()) {
					String line = s.nextLine();
					String[] fields = line.split(";"); // ; delimiter
					// fields[0] = first name, fields[1] = last name
					// fields[2] = phone number, fields[3] = email
					contacts.add(new Contact(fields[0], fields[1], fields[2], fields[3]));
				}
				s.close(); // close scanner
			}
		} catch(IOException ioe) {}
	}
	/**
	 * Set Display field(txtDisplay) to display the information of 
	 * Contact c and clear input boxes
	 * @param c - contact whose information is to be displayed
	 */
	public void setDisplay(Contact c) {
		// clear text boxes
		txtFName.setText(""); txtLName.setText(""); txtNumber.setText(""); txtEmail.setText("");
		// output contact information to display area
		String output = "";
		output += "First Name: \t" + c.getFName() + "\tLast Name: \t" + c.getLName() + 
					"\nPhone No.: \t" + c.getNumber() + "\temail: \t" + c.getEmail() + "\n";
		txtDisplay.setText(output);
	}
	/**
	 * Search for contact
	 * Criteria depends on int passed
	 * @param search - search term to use
	 * @param n - determines which field to search by
	 * @return index of Contact in arraylist contacts, -1 if not found
	 */
	public int search(String search, int n) {
		switch(n) {
		case 1: 
			for(int i = 0; i < contacts.size(); i++) {
			if (contacts.get(i).getFName().equalsIgnoreCase(search))
				return i;
			}
			break;
		case 2:
			for(int i = 0; i < contacts.size(); i++) {
				if (contacts.get(i).getLName().equalsIgnoreCase(search))
					return i;
			}
			break;
		case 3:
			for(int i = 0; i < contacts.size(); i++) {
				if (contacts.get(i).getNumber().equalsIgnoreCase(search))
					return i;
			}
			break;
		case 4:
			for(int i = 0; i < contacts.size(); i++) {
				if (contacts.get(i).getEmail().equalsIgnoreCase(search))
					return i;
			}
			break;
		}
		return -1; // contact not found
	}
	/**
	 * User choose which field to search by
	 * @return int to be passed to search()
	 */
	public int searchBy() {
		/* Panel with radio buttons for user to choose field to search by */
		JPanel pnlSearch = new JPanel(new GridLayout(0, 1));
		ButtonGroup bg = new ButtonGroup();
		JRadioButton optFName = new JRadioButton("First Name");
		JRadioButton optLName = new JRadioButton("Last Name");
		JRadioButton optNumber = new JRadioButton("Phone Number");
		JRadioButton optEmail = new JRadioButton("email");
		bg.add(optFName); bg.add(optLName);
		bg.add(optNumber); bg.add(optEmail);
		pnlSearch.add(optFName); pnlSearch.add(optLName);
		pnlSearch.add(optNumber); pnlSearch.add(optEmail);
		
		int by = -1; // will return -1 on error
		
		int choice = JOptionPane.showConfirmDialog(null, pnlSearch, 
							"Search by: ", JOptionPane.OK_CANCEL_OPTION);
		
		if (choice == JOptionPane.OK_OPTION) {
			if (optFName.isSelected())
				by = 1;
			else if (optLName.isSelected())
				by = 2;
			else if (optNumber.isSelected())
				by = 3;
			else
				by = 4;
		}
		return by;
	}
	/**
	 * Add Contact to <contacts> when 'Add' button is hit
	 */
	class AddButtonHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// add text from fields to contacts
			contacts.add(new Contact(txtFName.getText(), txtLName.getText(),
									txtNumber.getText(), txtEmail.getText()));
			// set the display area to show most recent contact added
			setDisplay(contacts.get(contacts.size() - 1));
		}
	}
	/**
	 * Modify contacts that have already been added
	 */
	class ModButtonHandler implements ActionListener {
		// New panel for modifications
		JPanel pnlMod = new JPanel();
		JLabel lblModFName = new JLabel("First Name: ");
		JLabel lblModLName = new JLabel("Last Name: ");
		JLabel lblModNumber = new JLabel("Phone No: ");
		JLabel lblModEmail = new JLabel("e-mail: ");
		JTextField txtModFName = new JTextField(10);
		JTextField txtModLName = new JTextField(15);
		JTextField txtModNumber = new JTextField(10);
		JTextField txtModEmail = new JTextField(15);
		
		public void actionPerformed(ActionEvent e) {
			/* Panel with text fields to get user's modification input */
			pnlMod.setLayout(new GridLayout(2, 2));
			pnlMod.add(lblModFName); pnlMod.add(txtModFName);
			pnlMod.add(lblModLName); pnlMod.add(txtModLName);
			pnlMod.add(lblModNumber); pnlMod.add(txtModNumber);
			pnlMod.add(lblModEmail); pnlMod.add(txtModEmail);
			
			/* searchBy() returns value of field to be searched for */
			int by = searchBy();
			/* search criteria */
			String mod = JOptionPane.showInputDialog("Enter value of contact to modify: ");
			// search(mod, by) will return index of contact, -1 if not found
			
			int index = search(mod, by);
			if (index != -1) {
				int result = JOptionPane.showConfirmDialog(null, pnlMod, 
									"Enter Modifications", JOptionPane.OK_CANCEL_OPTION);
				if (result == JOptionPane.OK_OPTION) {
					// set new modifications to contact
					contacts.get(index).setFName(txtModFName.getText());
					contacts.get(index).setLName(txtModLName.getText());
					contacts.get(index).setNumber(txtModNumber.getText());
					contacts.get(index).setEmail(txtModEmail.getText());
				}
			}
			// if not found
			else {
				JOptionPane.showMessageDialog(c, "Contact not found.", "Not Found",
												JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	/**
	 * Delete contacts that have already been added 
	 */
	class DelButtonHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			/* searchBy() returns value of field to be searched for */
			int by = searchBy();
			/* search criteria */
			String del = JOptionPane.showInputDialog("Enter value of contact to delete: ");
			// search(del, by) will return index of contact, -1 if not found
			int index = search(del, by);
			
			if (index != -1) {
				// confirm deletion
				int result = JOptionPane.showConfirmDialog(null, "Delete this contact? " + 
								contacts.get(index).toString(),
								"Confirm Deletion", JOptionPane.OK_CANCEL_OPTION);
				if (result == JOptionPane.OK_OPTION) {
					// remove contact
					contacts.remove(index);
					JOptionPane.showMessageDialog(null, "Contact removed", "Contact Removed",
													JOptionPane.INFORMATION_MESSAGE);
				}
			}
			// not found
			else {
				JOptionPane.showMessageDialog(null, "Contact not found.", "Not Found",
													JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	/**
	 * Browse through contacts
	 * Lists contacts in new window
	 */
	class BrowseButtonHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// create new window to show contacts
			BrowseWindow browser = new BrowseWindow();
			browser.setSize(400, 300);
			browser.setVisible(true);
			browser.setAlwaysOnTop(true);
		}
	}
	/**
	 * New Window for browsing contacts 
	 */
	class BrowseWindow extends JFrame {
		Container c2;
		JTextArea txtBrowse;
		String output = ""; // empty string to hold contacts' information
		
		public BrowseWindow() {
			super("Browse"); // set title
			c2 = getContentPane();
			c2.setLayout(new FlowLayout());
			txtBrowse = new JTextArea();
			c2.add(txtBrowse);
			
			// add contacts' info to 'output'
			for(Contact con : contacts) {
				output += con.toString();
			}
			// display output
			txtBrowse.setText(output);
		}
	}
	/**
	 * Save <contacts> to file 
	 *
	 */
	class SaveButtonHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				// create file if it doesn't exist already
				File f = new File(file);
				if(!f.exists())
					f.createNewFile();
				PrintWriter pw = new PrintWriter(f);
				// write each contact to file using ';' as delimiter
				for(Contact c : contacts) {
					pw.println(c.getFName() + ";" + c.getLName() + ";" +
								c.getNumber() + ";" + c.getEmail());
				}
				pw.close();
			} catch(IOException ioe) {}
		}
	}
	
	public static void main(String[] args) {
		PhoneBook app = new PhoneBook();
		app.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		app.setSize(400, 400);
		app.setVisible(true);
		
		JFileChooser fc = new JFileChooser(new File("."));
		fc.setDialogTitle("Select Data File");
		fc.setDialogType(JFileChooser.OPEN_DIALOG);
		
		int result = fc.showOpenDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
			app.file = fc.getSelectedFile().getPath();
		}
		
	}
}
