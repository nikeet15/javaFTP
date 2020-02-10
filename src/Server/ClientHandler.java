
package Server;
import java.io.*; 
import java.util.*; 
import java.net.*; 

class ClientHandler implements Runnable  
{ 
    Scanner scn = new Scanner(System.in); 
    private String name; 
    DataInputStream dis; 
    DataOutputStream dos; 
    FileInputStream fis;
    FileOutputStream fos;
    Socket s; 
    boolean isloggedin; 
    String received;
    
    // constructor 
    public ClientHandler(Socket s, String name, DataInputStream dis, DataOutputStream dos) { 
        this.dis = dis; 
        this.dos = dos; 
        this.name = name; 
        this.s = s; 
        this.isloggedin=true; 
    } 
  
    @Override
    public void run() { 
  
        try{
            String m= '#'+this.name;
            dos.writeUTF(Integer.toString(21));
            dos.writeUTF(m);
            System.out.println("5.ClientID sent");
        
        }catch(Exception e){
            System.out.println("5.problem in sending clientID");
        }
        
        while (true)  
        { 
            try
            { 
                // receive the string 
                int type = Integer.parseInt(dis.readUTF());
                System.out.println("6.type recieved: "+type);
                   
                if(type==11)
                { 
                    received = dis.readUTF(); 
                    this.isloggedin=false; 
                    this.s.close(); 
                    break; 
                } 
                  
                if(type==12)
                {   
                    received = dis.readUTF();
                    // break the string into message and recipient part 
                    StringTokenizer st = new StringTokenizer(received, "#"); 
                    String MsgToSend = this.name + ":" + st.nextToken(); 
                    String recipient = st.nextToken(); 
  
                    // search for the recipient in the connected devices list. 
                    // ar is the vector storing client of active users 
                    for (ClientHandler mc : Server.ar)  
                    { 
                        // if the recipient is found, write on its 
                        // output stream 
                        if (mc.name.equals(recipient) && mc.isloggedin==true)  
                        { 
                            mc.dos.writeUTF(Integer.toString(22));
                            mc.dos.writeUTF(MsgToSend);
                            System.out.println("6.message sent");
                            break;
                        } 
                    }
                }
                
                else if(type==13)
                {
                    String recipient= dis.readUTF();
                    System.out.println("6.file recieved recipient: "+recipient);
                    
                    fos= new FileOutputStream("C:\\Users\\acer\\Desktop\\file1.txt");
                    byte [] b= new byte[20003];
                    dis.read(b,0,b.length);
                    fos.write(b,0,b.length);
                    
                    for (ClientHandler mc : Server.ar)  
                    { 
                        // if the recipient is found, write on its 
                        // output stream 
                        if (mc.name.equals(recipient) && mc.isloggedin==true)  
                        {   
                            mc.dos.writeUTF(Integer.toString(23));
                            fis= new FileInputStream("C:\\Users\\acer\\Desktop\\file1.txt");
                            byte[] b1= new byte[20003];
                            fis.read(b1,0,b1.length);
                            mc.dos.write(b1, 0, b1.length);
                            System.out.println("6.file sent");
                            break;
                        } 
                    }
                }
                
            } catch (Exception e) {   
                System.out.println("7.Problem in recieving or sending text further"); 
            } 
              
        } 
        try
        { 
            // closing resources 
            this.dis.close(); 
            this.dos.close(); 
              
        }catch(IOException e){ 
            System.out.println(e);
        } 
    } 
} 