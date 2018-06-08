package airhawk.com.myapplication;


public class News_FeedItem {
   String title;  
   String link;  
   String description;  
   String pubDate;
   String thumbnailUrl;
   public String getTitle() {
     return title;  
   }  
   public void setTitle(String title) {  
     this.title = title;  
   }  
   public String getLink() {  
     return link;  
   }  
   public void setLink(String link) {  
     this.link = link;  
   }  
   public String getDescription() {  
     return description;  
   }  
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
           setThumbnailUrl(img);
           this.description = this.description.replace(cleanUp, "");
           System.out.println("FINAL IMAGE URL IS "+this.description);
       }
   }  
   public String getPubDate() {  
     return pubDate;  
   }  
   public void setPubDate(String pubDate) {  
     this.pubDate = pubDate;  
   }  
   public String getThumbnailUrl() {  
     return thumbnailUrl;  
   }  
   public void setThumbnailUrl(String thumbnailUrl) {
       this.thumbnailUrl = thumbnailUrl;
       //Log.d("*******image*********", thumbnailUrl.toString());
   }  
 }