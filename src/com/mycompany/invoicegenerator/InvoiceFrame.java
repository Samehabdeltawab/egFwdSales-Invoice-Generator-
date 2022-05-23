/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.invoicegenerator;

import com.mycompany.invoicegenerator.InvoiceData.InvoiceFile;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author BumbleBee
 */
public class InvoiceFrame extends javax.swing.JFrame implements ActionListener, ListSelectionListener  {

   public SimpleDateFormat df= new SimpleDateFormat("dd-mm-yyyy");
    private  ArrayList<InvoiceFile> Invoices= new ArrayList<>();

    private final ArrayList<String[]> Rs = new ArrayList<String[]>();
    private final ArrayList<String[]> ItemRs = new ArrayList<String[]>();
    private final String[] columnNames = { "Invoice ID", "Date","CustomerName" };
   private  String [][] rows = {{"" , "", " ","",""},{"" , "", " ","",""},{"" , " ", " ","",""},{"" , "", " ","",""}};
    private final String[] columnItemNames = { "Invoice ID", "Item Name", "Item Price", "Count" , " Total"};
    private  String [][] itemRows = {{"" , "", " ","",""},{"" , "", " ","",""},{"" , " ", " ","",""},{"" , "", " ","",""}};


    Boolean itemFileisFull= false;
    private JMenuBar menubar;
    private JMenu FileMenu;
    private JMenuItem saveItem;
    private JMenuItem OpenItem;
    private JMenuItem exitItem;

    Container contentPane;
    private JPanel Panel1;
    private JPanel Panel2;
    private JPanel Panel3;
    private JPanel Panel4;
    private JPanel buttonPanel;
    private JPanel buttonPanel1;
    private JPanel buttonPanel2;

    private JButton newBtn, deleteBtn, saveBtn, cancelBn;

    private JTable invoiceTable;
    private JTable itemTable;

    private JLabel label, label2, label3, numlabel;
    private JTextField dateField, nameField;
    private String oldNameText;
    private String oldDateText;

    // components of popup panel
    private JLabel idLbl;
    private JLabel nameLbl;
    private JLabel dateLbl;
    private JTextField idField1;
    private JTextField nameField1;
    private JTextField dateField1;
    private JPanel newPanel;
    
    
    // components of popup panel items
    
    private JLabel nameItemLbl;
    private JLabel priceLbl;
    private JLabel countLbl;
    private JTextField nameItemField1;
    private JTextField priceField1;
    private JTextField countField1;
    private JPanel newPanelItem;

    
     @SuppressWarnings("unchecked")
    public InvoiceFrame() {
        super(" Sales Invoice System");
        setSize(950, 600);
        setResizable(false);
        //setLocationRelativeTo(null);
        setLocation(50, 50);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        contentPane = getContentPane();

        menubar = new JMenuBar();
        FileMenu = new JMenu("File");
        OpenItem = new JMenuItem("load", 'l');
        OpenItem.setAccelerator(KeyStroke.getKeyStroke('l', KeyEvent.CTRL_DOWN_MASK));
        OpenItem.setActionCommand("L");
        saveItem = new JMenuItem("Save", 's');
        saveItem.setAccelerator(KeyStroke.getKeyStroke('s', KeyEvent.CTRL_DOWN_MASK));
        saveItem.setActionCommand("S");

        exitItem = new JMenuItem("EXit", 'E');
        exitItem.setAccelerator(KeyStroke.getKeyStroke('E', KeyEvent.CTRL_DOWN_MASK));
        exitItem.setActionCommand("E");

        FileMenu.add(OpenItem);
        FileMenu.add(saveItem);
        FileMenu.add(exitItem);
        menubar.add(FileMenu);
        setJMenuBar(menubar);

        this.invoiceTable = new JTable(new TableModel());
        this.itemTable = new JTable(new ItemsTableModel());
        invoiceTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        invoiceTable.getSelectionModel().addListSelectionListener(this);


        //this.invoiceTable = new JTable(rows, columnNames);
        //this.itemTable = new JTable(itemRows, columnItemNames);

        GroupLayout layout = new GroupLayout(contentPane);
        Panel1 = new JPanel();
        Panel2 = new JPanel();
        Panel2.setLayout(new BoxLayout(Panel2, BoxLayout.Y_AXIS));
        Panel3 = new JPanel(new GridLayout());
        Panel4 = new JPanel();
       Panel4.setLayout(new BoxLayout(Panel4, BoxLayout.Y_AXIS));
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout());
        buttonPanel1 = new JPanel();
        buttonPanel2 = new JPanel();


        label = new JLabel("Name: ");
        nameField = new JTextField(10);
        label2 = new JLabel("Date: ");
        dateField = new JTextField(10);
        label3 = new JLabel("Invoice ID: ");
        numlabel = new JLabel("   ");

        //Buttons on Frame
        newBtn = new JButton(" Create New Invoice");
        newBtn.setActionCommand("new");
        deleteBtn = new JButton(" Delete Invoice");
        deleteBtn.setActionCommand("delete");
        saveBtn = new JButton(" Add Item");
        saveBtn.setActionCommand("save");
        cancelBn = new JButton(" cancel");
        cancelBn.setActionCommand("cancel");

        Panel1.setBorder(BorderFactory.createTitledBorder(" Invoice Header"));
        Panel3.setBorder(BorderFactory.createTitledBorder(" Invoice Lines"));

        //table2= new JTable(values2, col2);
       Panel1.add(new JScrollPane(invoiceTable));
        Panel4.add(label3);
        Panel4.add(numlabel);
        Panel4.add(label);
        Panel4.add(nameField);
        Panel4.add(label2);
        Panel4.add(dateField);
        Panel3.add(new JScrollPane(itemTable));
        Panel2.add(Panel4);
        Panel2.add(Panel3);

        buttonPanel1.add(newBtn);
        buttonPanel1.add(cancelBn);
        buttonPanel2.add(saveBtn);
        buttonPanel2.add(deleteBtn);
        buttonPanel.add(buttonPanel1);
        buttonPanel.add(buttonPanel2);
        contentPane.add(new JScrollPane(Panel1));
        contentPane.add(Panel2, BorderLayout.EAST);
        contentPane.add(buttonPanel, BorderLayout.PAGE_END);


        // action listener of all buttons
        OpenItem.addActionListener(this);
        saveItem.addActionListener(this);
        newBtn.addActionListener(this);
        deleteBtn.addActionListener(this);
        cancelBn.addActionListener(this);
        exitItem.addActionListener(this);
        saveBtn.addActionListener(this);



        // panel of ( add new popup)
        newPanel= new JPanel(new GridLayout(0,1));
        newPanel.setBorder(BorderFactory.createTitledBorder(" Add new Invoice Header"));

        idLbl = new JLabel("ID: ");
        idField1 = new JTextField(25);
        nameLbl = new JLabel("Name:  ");
        nameField1 = new JTextField(25);
        dateLbl = new JLabel("Date:  ");
        dateField1 = new JTextField(25);
        newPanel.add(idLbl);
        newPanel.add(idField1);
        newPanel.add(nameLbl);
        newPanel.add(nameField1);
        newPanel.add(dateLbl);
        newPanel.add(dateField1);
        
        
        
      // panel of ( add new popup item)
        newPanelItem= new JPanel(new GridLayout(0,1));
        newPanelItem.setBorder(BorderFactory.createTitledBorder(" Add new Item"));

        nameItemLbl = new JLabel("Name: ");
        nameItemField1 = new JTextField(25);
        priceLbl = new JLabel("Price:  ");
        priceField1 = new JTextField(25);
        countLbl = new JLabel("Count:  ");
        countField1 = new JTextField(25);
        newPanelItem.add(nameItemLbl);
        newPanelItem.add(nameItemField1);
        newPanelItem.add(priceLbl);
        newPanelItem.add(priceField1);
        newPanelItem.add(countLbl);
        newPanelItem.add(countField1);
      
        
        
        
        
    
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
   

    @Override
    public void actionPerformed(ActionEvent e) {
      
        // load Files in tables
        if ( e.getActionCommand().equals("L")) {
            loadHeaderFile();}

        // SAVE
        if ( e.getActionCommand().equals("S")) {
            JOptionPane.showMessageDialog(this, " Save Customer In Header File",
                        " Success ", JOptionPane.INFORMATION_MESSAGE);
                ExportTableToCSV(invoiceTable);
                JOptionPane.showMessageDialog(this, " Save Items Into InvoiceLine",
                        " Success ", JOptionPane.INFORMATION_MESSAGE);
                 ExportListToCSV(ItemRs);


        }
        //Create new Row
        if ( e.getActionCommand().equals("new")) {
            createNewInvoice();
        }

        //Delete row from Lines table
        if ( e.getActionCommand().equals("delete"))
        {DeleteSelectedRow(itemTable);}

        //save changes of invoice row
        if ( e.getActionCommand().equals("save"))
          { saveChnagesOfInvoice(); }

        // CANCEL Changes
        if ( e.getActionCommand().equals("cancel"))
        { caneclChnages();}

        // exit
        if(e.getActionCommand().equals("E"))
        { System.exit(0); }

    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
    try{
        
        System.out.println("Selected Action");
        getSelectedRowValues();
        /*
               List filterItems = new ArrayList<>();
               ItemRs.stream().filter( a -> a.toString().contains(""))
        Object[][] content1 = new Object[ItemRs.size()][columnItemNames.length];
                for(int i=0; i<ItemRs.size(); i++) {
                    content1[i][0] = ItemRs.get(i)[0];
                    content1[i][1] = ItemRs.get(i)[1];
                    content1[i][2] = ItemRs.get(i)[2];
                    Double x= Double.valueOf(ItemRs.get(i)[2]);
                    content1[i][3] = ItemRs.get(i)[3];
                    int y= Integer.parseInt(ItemRs.get(i)[3]);
                    content1[i][4]=x*y; // calculation of Total

                }
                itemTable.setModel(new DefaultTableModel(content1,columnItemNames));
        */
        System.out.println("In ValueCHange");
    }catch(Exception ex){
        System.out.println("Error as:"+ex.getMessage());
    }    
    }

// save changes in haeder table
    private void caneclChnages()
    {
        nameField.setText(oldNameText);
        dateField.setText(oldDateText);
    }

    // show Popup of add new invoice and then add new row in header table
    private void createNewInvoice()
    {
      // if( itemFileisFull==true)
       //{
           int optionType = JOptionPane.DEFAULT_OPTION;
           int messageType = JOptionPane.PLAIN_MESSAGE;
           String[] options = { "Save", "Cancel" };
           Object initialValue = options[0];
           int res= JOptionPane.showOptionDialog(null, newPanel,
                   "Add new invoice", optionType, messageType, null, options,
                   initialValue);

           if ( res==JOptionPane.YES_OPTION)
           {
               String id=idField1.getText();
               String newDATE= dateField1.getText();
               String name1= nameField1.getText();
               DefaultTableModel t= (DefaultTableModel)invoiceTable.getModel();
               
               t.addRow(new Object[]{id, newDATE,name1});
               String[] arr = new String[3];
               arr[0] = id;
               arr[1] = newDATE;
                arr[2]=  name1;     
               Rs.add(arr);
                Object[][] content = new Object[Rs.size()][columnNames.length];
                for(int i=0; i<Rs.size(); i++) {
                    content[i][0] = Rs.get(i)[0];
                    content[i][1] = Rs.get(i)[1];
                    content[i][2] = Rs.get(i)[2];
                }
               
                invoiceTable.setModel(new DefaultTableModel(content,columnNames));
                this.pack();
               
                System.out.println("after add to table1"); 
               JOptionPane.showMessageDialog(this, "New row is added Successfully to Header Table ",
                       " New Row.. ", JOptionPane.INFORMATION_MESSAGE);
                  
               idField1.setText("");
               nameField1.setText("");
                System.out.println("after add to table0");    
               dateField1.setText("");
                             
                  System.out.println("after add to table2");             


           }
       //}
     //  else
     //  {
      //     JOptionPane.showMessageDialog(this, "Please load Header File first  ",
      //             " Warning ", JOptionPane.WARNING_MESSAGE);
      // }
    }


    // fill fields  with data of selected row
    private void getSelectedRowValues()
    {
        int selectedRowIndex = invoiceTable.getSelectedRow();
        
        String headerNumber=invoiceTable.getValueAt(selectedRowIndex, 0).toString();
        numlabel.setText(headerNumber);
        dateField.setText(invoiceTable.getValueAt(selectedRowIndex, 1).toString());
        nameField.setText(invoiceTable.getValueAt(selectedRowIndex, 2).toString());
        oldNameText = nameField.getText();
        oldDateText = dateField.getText();
        
        
        System.out.println("InvoiceID="+headerNumber);
        
        List<String[]> filterItems = new ArrayList<>();
              for(String[] str : ItemRs){
                  if(str[0].equalsIgnoreCase(headerNumber)){
                    filterItems.add(str);
                  }
              }
        
              System.out.println("Hereeeeeeeeee");
              
        Object[][] content1 = new Object[filterItems.size()][columnItemNames.length];
                for(int i=0; i<filterItems.size(); i++) {
                    content1[i][0] = filterItems.get(i)[0];
                    content1[i][1] = filterItems.get(i)[1];
                    content1[i][2] = filterItems.get(i)[2];
                    Double x= Double.valueOf(filterItems.get(i)[2]);
                    System.out.println("33");
                    content1[i][3] = filterItems.get(i)[3];
                    System.out.println("44");
                    int y= Integer.parseInt(filterItems.get(i)[3].replaceAll(" ", ""));
                    content1[i][4]=x*y; // calculation of Total

                }
                itemTable.setModel(new DefaultTableModel(content1,columnItemNames));

    }// end of function


    // Save changes in Invoice
    private void saveChnagesOfInvoice()
    {
        
        int optionType = JOptionPane.DEFAULT_OPTION;
           int messageType = JOptionPane.PLAIN_MESSAGE;
           String[] options = { "Add", "Cancel" };
           Object initialValue = options[0];
           int res= JOptionPane.showOptionDialog(null, newPanelItem,
                   "Add new invoice", optionType, messageType, null, options,
                   initialValue);
           
           
           
            if ( res==JOptionPane.YES_OPTION)
           {
               String name=nameItemField1.getText();
               String price= priceField1.getText();
               String count= countField1.getText();
               double Total = Integer.parseInt(price) * Integer.parseInt(count);
               int x= invoiceTable.getSelectedRow();
               String invoiceID = "";
               
               if(invoiceTable.getSelectedRow() != -1)
        {
           invoiceID = invoiceTable.getValueAt(x, 0).toString();
            
        }
               
                String[] headerSegments1 = new String[4];

                     headerSegments1[0] = invoiceID;
                     headerSegments1[1] = name;
                     headerSegments1[2] = price;
                    headerSegments1[3] = count;

                    System.out.println(Arrays.toString(headerSegments1));
                    ItemRs.add(headerSegments1);
               
                    
                List<String[]> filterItems = new ArrayList<>();
              for(String[] str : ItemRs){
                  if(str[0].equalsIgnoreCase(invoiceID)){
                    filterItems.add(str);
                  }
              }
        
        Object[][] content1 = new Object[filterItems.size()][columnItemNames.length];
                for(int i=0; i<filterItems.size(); i++) {
                    content1[i][0] = filterItems.get(i)[0];
                    content1[i][1] = filterItems.get(i)[1];
                    content1[i][2] = filterItems.get(i)[2];
                    Double pri= Double.valueOf(filterItems.get(i)[2]);
                    content1[i][3] = filterItems.get(i)[3];
                    int y= Integer.parseInt(filterItems.get(i)[3]);
                    content1[i][4]=pri*y; // calculation of Total

                }
                itemTable.setModel(new DefaultTableModel(content1,columnItemNames));
              /////
               
               
               nameItemField1.setText("");
               priceField1.setText("");
                System.out.println("after add to table0");    
               countField1.setText("");
                             
                  System.out.println("after add to table2"); 
                  
                  // save to InvoiceItems To file


           }
           
        
        
        /////////////////////////////////////////
        int i= invoiceTable.getSelectedRow();
        if(invoiceTable.getSelectedRow() != -1)
        {
            invoiceTable.setValueAt(dateField.getText(),i,1);
            invoiceTable.setValueAt(nameField.getText(),i,2);
            JOptionPane.showMessageDialog(this, "Invoice Number: "+numlabel.getText()+ "  is Updated successfully ",
                    " Save Changes ", JOptionPane.INFORMATION_MESSAGE);
        }
        else {JOptionPane.showMessageDialog(this, "No row selected ",
                " Save Changes ", JOptionPane.ERROR_MESSAGE);}
    }


    // Delete Row from table
    public  void DeleteSelectedRow(JTable table)
    {
        if(itemFileisFull==true)
        {
            if(table.getSelectedRow() != -1)
            {
                DefaultTableModel df1 = (DefaultTableModel) table.getModel();
                int rs[] = table.getSelectedRows();
                for (int i = rs.length - 1; i >= 0; i--)
                {int k = rs[i];
                    df1.removeRow(k);
                    JOptionPane.showMessageDialog(this, "Selected row is deleted Successfully",
                            " Delete Row .. ", JOptionPane.INFORMATION_MESSAGE);
                }
            }
            else {
                JOptionPane.showMessageDialog(this, "Please Select Row of Invoice Lines ",
                        " Warning ", JOptionPane.WARNING_MESSAGE);
            }
        } else if(itemFileisFull== false) {
            JOptionPane.showMessageDialog(this, " Table is Empty, Please load Invoice lines ",
                    " Delete Row ", JOptionPane.ERROR_MESSAGE);
        }
    }



// save table as CSV file
    public  void ExportTableToCSV (JTable tb) {

        JFileChooser ch= new JFileChooser();
        ch.setDialogTitle(" Save as");
        int userSelection= ch.showSaveDialog(this);
        if( userSelection==JFileChooser.APPROVE_OPTION)
        {
           File savedFile= ch.getSelectedFile();

            DefaultTableModel defaultTableModel = (DefaultTableModel) tb.getModel();
            int Row = defaultTableModel.getRowCount();
            int Col = defaultTableModel.getColumnCount();
            Writer writer = null;

            try {
                writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(savedFile), "utf-8"));

                StringBuffer bufferHeader = new StringBuffer();
                for (int j = 0; j < Col; j++) {
                    bufferHeader.append(defaultTableModel.getColumnName(j));
                    if (j!=Col) bufferHeader.append(", ");
                }
              //  writer.write(bufferHeader.toString() + "\r\n");

                for (int i = 0 ; i < Row ; i++){
                    StringBuffer buffer = new StringBuffer();
                    for (int j = 0 ; j < Col ; j++){
                        buffer.append(defaultTableModel.getValueAt(i,j));
                        if (j!=Col) buffer.append(", ");
                    }
                    writer.write(buffer.toString() + "\r\n");
                }
                JOptionPane.showMessageDialog(this, " Saved Files Sucessfully",
                        " Success ", JOptionPane.INFORMATION_MESSAGE);
            }
            catch (IOException e){e.printStackTrace();}
            finally
            {
                try {writer.close(); } catch (IOException e) {e.printStackTrace();}
            }
        }
    }
    
    
    
    public  void ExportListToCSV ( ArrayList<String[]> Items) {

        JFileChooser ch= new JFileChooser();
        ch.setDialogTitle(" Save as");
        int userSelection= ch.showSaveDialog(this);
        if( userSelection==JFileChooser.APPROVE_OPTION)
        {
           File savedFile= ch.getSelectedFile();

            
            Writer writer = null;

            try {
                writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(savedFile), "utf-8"));

              //  writer.write(bufferHeader.toString() + "\r\n");

                for (int i = 0 ; i < Items.size() ; i++){
                    StringBuffer buffer = new StringBuffer();
                    for (int j = 0 ; j < 4 ; j++){
                        
                         buffer.append(Items.get(i)[j]);
                         buffer.append(", ");
                    }
                    writer.write(buffer.toString() + "\r\n");
                }
                JOptionPane.showMessageDialog(this, " Saved Files Sucessfully",
                        " Success ", JOptionPane.INFORMATION_MESSAGE);
            }
            catch (IOException e){e.printStackTrace();}
            finally
            {
                try {writer.close(); } catch (IOException e) {e.printStackTrace();}
            }
        }
    }


    // load csv files and display in Jtable
    public void loadHeaderFile()
    {
        JOptionPane.showMessageDialog(this, " please select Invoice header",
                " Invoice Header", JOptionPane.WARNING_MESSAGE);

        ArrayList<String> headerline = new ArrayList<>();

        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle(" Load File");

        String line = null;
        FileReader fr = null;
        int result = fc.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fc.getSelectedFile();
            try {
                fr = new FileReader(selectedFile);
            }
            catch (FileNotFoundException e)
            {JOptionPane.showMessageDialog(this, " File Not Found",
                        " Invoice Header File ", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
            BufferedReader br = new BufferedReader(fr);
            InvoiceFile row = null;

            try {
                while ((line = br.readLine()) != null) {
                    headerline.add(line);
                    String[] headerSegments = line.split(",");

                    String invNumStr = headerSegments[0];
                    String invDateStr = headerSegments[1];
                    String invCustStr = headerSegments[2];
                    int invNum = Integer.parseInt(invNumStr);

                    row = new InvoiceFile(invNum, invDateStr, invCustStr);
                    Invoices.add(row);

                    System.out.println(Arrays.toString(headerSegments));
                    Rs.add(headerSegments);
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, " Wrong File Format",
                        " Invoice Header  ", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();}
            finally {
                try {br.close();
                    fr.close();
                } catch (Exception e) {e.printStackTrace();}

                Object[][] content = new Object[Rs.size()][columnNames.length];
                for(int i=0; i<Rs.size(); i++) {
                    content[i][0] = Rs.get(i)[0];
                    content[i][1] = Rs.get(i)[1];
                    content[i][2] = Rs.get(i)[2];
                }
                loadItemFile();
                invoiceTable.setModel(new DefaultTableModel(content,columnNames));
                this.pack();
            }
        }
    }

    // load Invoice lines files and import in Jtable
    public void loadItemFile()
    {
      
        /*
        JOptionPane.showMessageDialog(this, "Please select Invoice Line ",
                " Invoice Line", JOptionPane.WARNING_MESSAGE);
        */
        ArrayList<String> Itemline = new ArrayList<>();
        JFileChooser fc1 = new JFileChooser();
        String line1 = null;
        FileReader fr1 = null;
       // int result = fc1.showOpenDialog(this);
       // if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = new File("InvoiceLine.csv");
            try {
                fr1 = new FileReader(selectedFile);
            }
            catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(this, " File Not Found",
                        " Invoice line  ", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
            BufferedReader br1 = new BufferedReader(fr1);
            InvoiceFile row = null;

            try {
                while ((line1 = br1.readLine()) != null) {
                    Itemline.add(line1);
                    String[] headerSegments1 = line1.split(",");

                    String itemNum = headerSegments1[0];
                    String itemName=headerSegments1[1];
                    System.out.println("name="+itemName);
                    double itemPrice = Double.valueOf(headerSegments1[2]);
                    System.out.println("Price="+itemPrice);
                    System.out.println("Cou="+headerSegments1[3]);
                     System.out.println("Cou2="+headerSegments1[3].replaceAll(" ", ""));
                    int itemCount = Integer.parseInt(headerSegments1[3].replaceAll(" ", ""));
                       System.out.println("count="+itemCount);
                    System.out.println(Arrays.toString(headerSegments1));
                    ItemRs.add(headerSegments1);
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, " Wrong File Format",
                        " Invoice line  ", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            } finally {
                try {br1.close();
                    fr1.close();
                } catch (Exception e) {e.printStackTrace();}
                     
                
                 /*  
                Object[][] content1 = new Object[ItemRs.size()][columnItemNames.length];
                for(int i=0; i<ItemRs.size(); i++) {
                    content1[i][0] = ItemRs.get(i)[0];
                    content1[i][1] = ItemRs.get(i)[1];
                    content1[i][2] = ItemRs.get(i)[2];
                    Double x= Double.valueOf(ItemRs.get(i)[2]);
                    content1[i][3] = ItemRs.get(i)[3];
                    int y= Integer.parseInt(ItemRs.get(i)[3]);
                    content1[i][4]=x*y; // calculation of Total

                }
                itemTable.setModel(new DefaultTableModel(content1,columnItemNames));
                */
                this.pack();
                itemFileisFull=true;
            }
     //   }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
