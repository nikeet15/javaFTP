
package Client;
import java.io.*; 
import java.util.*; 
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;

public class Client extends javax.swing.JFrame {

    // getting localhost ip 
    InetAddress ip;
    Scanner scn; 
    Socket s;  
    DataInputStream dis;
    DataOutputStream dos;
    FileInputStream fis;
    FileOutputStream fos;
    File f;
    String cl;    
    
    /**
     * Creates new form Client
     */
    public Client() {
        initComponents();
        
        try
        {
            // establish the connection 
            ip = InetAddress.getByName("localhost");
            scn = new Scanner(System.in);
            s = new Socket(ip, 3333);
            dis =  new DataInputStream(s.getInputStream()); 
            dos = new DataOutputStream(s.getOutputStream());
        
            // readMessage thread 
            Thread readMessage = new Thread(new Runnable()  
            { 
                @Override
                public void run() { 
                    
                    while (true) { 
                    try { 
                        // read the message sent to this client 
                        int type = Integer.parseInt(dis.readUTF());
                        
                        if(type==21)
                        {
                            String msg = dis.readUTF(); 
                            System.out.println(msg);
                            clientIDLabel.setText(msg);
                        }
                        
                        else if(type==22)
                        {   String msg = dis.readUTF(); 
                            System.out.println(msg);
                            displayLabel.setText(msg);
                        }
                        
                        else if(type==23)
                        {
                            String sizext= dis.readUTF();               
                            StringTokenizer st = new StringTokenizer(sizext, "#");              //read siz#ext 
                            long totsiz = Long.parseLong(st.nextToken()); 
                            String ext = st.nextToken();
                            fos= new FileOutputStream("C:\\Users\\acer\\Desktop\\file2."+ext);
                            
                            long buffer= 65536;
                            long cur= 0;
                            byte [] b= new byte[(int)buffer];
                    
                            while(cur<totsiz)                                           //recieving file in chunks
                            {
                                long siztorec= totsiz-cur;
                                siztorec = siztorec < buffer ? siztorec : buffer;
      
                                int numRead= dis.read(b, 0, (int)siztorec);
                        
                                if(numRead ==-1 ) break;
 
                                fos.write(b,0,numRead);
                                cur += numRead;
                            }
                            
                            System.out.println("file recieved "+totsiz+" "+ext);
                            displayLabel.setText("file recieved");
                        }
                        
                        else if(type == 24)
                        {
                            clientList.removeAll();
                            int n= Integer.parseInt(dis.readUTF());
                            System.out.println("size= "+n);
                
                            for (int i = 0; i < n; i++) {
                                String x= dis.readUTF();    
                                System.out.println(x);
                                clientList.add(x);
                            }
                        System.out.println("Printed all available clients");
                        }            
                        
                    } catch (Exception e) { 
                        System.out.println("problem in sending text or logged out");
                        System.exit(0);
                    } 
                } 
            } 
        }); 
   
        readMessage.start();
    
        }catch(Exception e){
            System.out.println("problem in client connection");
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        selectButton = new javax.swing.JButton();
        sendMsgButton = new javax.swing.JButton();
        textBox = new javax.swing.JTextField();
        displayLabel = new javax.swing.JLabel();
        clientIDLabel = new javax.swing.JLabel();
        sendFileButton = new javax.swing.JButton();
        clientList = new java.awt.List();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        selectButton.setText("Select File");
        selectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectButtonActionPerformed(evt);
            }
        });

        sendMsgButton.setText("Send Msg");
        sendMsgButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendMsgButtonActionPerformed(evt);
            }
        });

        sendFileButton.setText("Send File");
        sendFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendFileButtonActionPerformed(evt);
            }
        });

        clientList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                clientListMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                clientListMouseEntered(evt);
            }
        });
        clientList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clientListActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("ONLINE");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(337, 337, 337)
                        .addComponent(clientIDLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(162, 162, 162)
                        .addComponent(selectButton)
                        .addGap(42, 42, 42)
                        .addComponent(sendFileButton)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(textBox, javax.swing.GroupLayout.PREFERRED_SIZE, 415, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(60, 60, 60)
                        .addComponent(sendMsgButton))
                    .addComponent(displayLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 611, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(clientList, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(26, 26, 26))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(clientIDLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(textBox, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(sendMsgButton)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(24, 24, 24)
                        .addComponent(displayLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addComponent(clientList, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(31, 31, 31)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(selectButton)
                    .addComponent(sendFileButton))
                .addContainerGap(80, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void sendMsgButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendMsgButtonActionPerformed
        
        try{
            String msg= textBox.getText();
            if(msg.contentEquals("logout"))
            { 
                dos.writeUTF(Integer.toString(11));
                dos.writeUTF(msg);
            }
            
            else
            {
                dos.writeUTF(Integer.toString(12));
                dos.writeUTF(msg+"#"+cl);        
            }
            System.out.println("sent: "+msg);
        
        }catch(Exception e){
            System.out.println("Problem in sending text");
        }
    }//GEN-LAST:event_sendMsgButtonActionPerformed

    private void selectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectButtonActionPerformed
       JFileChooser fileChooser = new JFileChooser();
       fileChooser.showOpenDialog(null);
       f=fileChooser.getSelectedFile();
       
    }//GEN-LAST:event_selectButtonActionPerformed

    private void sendFileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendFileButtonActionPerformed
        try {
            
            System.out.println("file path: "+f.getAbsolutePath());
            StringTokenizer st = new StringTokenizer(f.getAbsolutePath(), "."); 
            String s = st.nextToken(); 
            String ext = st.nextToken();
            
            dos.writeUTF(Integer.toString(13));
            dos.writeUTF(cl+"#"+ext);                   //send recipient and file extension
            dos.writeUTF(Long.toString((long)f.length()));           //send size of file
            
            fis= new FileInputStream(f);
            long totsiz= (int)f.length();
            long buffer= 65536;
            long cur= 0;
            byte[] b= new byte[(int)buffer];
            
            while(cur<totsiz)                                           //sending file in chunks
            {
                long siztosend= totsiz-cur;
                siztosend = siztosend < buffer ? siztosend : buffer;
                int numRead = fis.read(b, 0, (int)siztosend);
                
                if(numRead ==-1 ) break;
 
                dos.write(b,0,numRead);
                cur += numRead;
            }
            
            System.out.println("sent file to "+cl);
        
        } catch (Exception ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_sendFileButtonActionPerformed

    private void clientListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clientListActionPerformed

    }//GEN-LAST:event_clientListActionPerformed

    private void clientListMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clientListMouseEntered
        try{
                
            dos.writeUTF(Integer.toString(14));
        
        }catch(Exception e)
        {
            System.out.println("problem in allclients");
        }
    }//GEN-LAST:event_clientListMouseEntered

    private void clientListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clientListMouseClicked
        
        cl= clientList.getSelectedItem();
        System.out.println(cl+" selected client");
        //textBox.setText(cl);
    }//GEN-LAST:event_clientListMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Client().setVisible(true);
            }
        });
           
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel clientIDLabel;
    private java.awt.List clientList;
    private javax.swing.JLabel displayLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton selectButton;
    private javax.swing.JButton sendFileButton;
    private javax.swing.JButton sendMsgButton;
    private javax.swing.JTextField textBox;
    // End of variables declaration//GEN-END:variables
}
