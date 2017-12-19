/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JavaMail;

/**
 *
 * @author MilanT
 */
public class Tips {
    public Long idTip;
    public String tipContent;
    public String image;
    
    public Tips(Long idTip, String tipContent){
        this.idTip = idTip;
        this.tipContent = tipContent;
    }
    
    public Tips(Long idTip, String tipContent, String image){
        this.idTip = idTip;
        this.tipContent = tipContent;
        this.image = image;
    }
}
