
package Server;
import java.io.*; 
import java.util.*; 
import java.net.*; 

class ClientHandler implements Runnable  
{ 
    Scanner scn = new Scanner(System.in); 
    private String name; 
    final DataInputStream dis; 
    final DataOutputStream dos; 
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
                received = dis.readUTF(); 
                System.out.println("6.msg recieved: "+received); 
                  
                if(received.equals("logout")){ 
                    this.isloggedin=false; 
                    this.s.close(); 
                    break; 
                } 
                  
                // break the string into message and recipient part 
                StringTokenizer st = new StringTokenizer(received, "#"); 
                String MsgToSend = this.name + ":" + st.nextToken(); 
                String recipient = st.nextToken(); 
                String type = st.nextToken();
  
                // search for the recipient in the connected devices list. 
                // ar is the vector storing client of active users 
                for (ClientHandler mc : Server.ar)  
                { 
                    // if the recipient is found, write on its 
                    // output stream 
                    if (mc.name.equals(recipient) && mc.isloggedin==true)  
                    { 
                        switch(type)
                        {
                            case "msg": mc.dos.writeUTF(MsgToSend+"#"+type); 
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