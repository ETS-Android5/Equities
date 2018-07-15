package airhawk.com.myapplication;


public class Constructor_Stock_Twits {
   String user_name;
   String message_time;
   String message;
   String user_image_url;
   public String getUser_name() {
     return user_name;
   }  
   public void setUser_name(String user_name) {
     this.user_name = user_name;
   }  
   public String getMessage_time() {
     return message_time;
   }  
   public void setMessage_time(String message_time) {
     this.message_time = message_time;
   }  
   public String getMessage() { return message; }
   public void setMessage(String message){this.message =message;}

   public String getUser_image_url(){return user_image_url;}
   public void setUser_image_url(String user_image_url){this.user_image_url =user_image_url;}


  /*
   public void setDescription(String description) {
       this.description = description;
       if (description.contains("<img ")){
           String img  = description.substring(description.indexOf("<img "));

           String cleanUp = img.substring(0, img.indexOf(">")+1);
           img = img.substring(img.indexOf("src=") + 5);
           int indexOf = img.indexOf("'");
           if (indexOf==-1){
               indexOf = img.indexOf("\"");
           }
           img = img.substring(0, indexOf);
           setUser_image_url(img);
           this.description = this.description.replace(cleanUp, "");
           //System.out.println("FINAL IMAGE URL IS "+this.description);
       }
   }
  */
 }