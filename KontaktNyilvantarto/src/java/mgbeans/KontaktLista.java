package mgbeans;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.hibernate.Session;
import pojos.Contact;
import pojos.Phone;

/**
 *
 * @author ADAM-PC
 */
@ManagedBean
@SessionScoped
public class KontaktLista {
    
    private List<Contact> contacts;
    private List<Phone> phones;    
    private Contact newContact;
    private Phone newPhone;
    private String hiba = "";
    private String search = "";
    private Contact selectedContact;
    private Phone selectedPhone;
    
    public KontaktLista() {
      Session session = hibernate.HibernateUtil.getSessionFactory().openSession();      
      contacts = session.createQuery("FROM Contact").list();                   
      session.close();
    }
    
    public void updatePhones(Contact kont){ 
        phones = new ArrayList<>();
        for (Phone phn : kont.getPhones()){
            phones.add(phn);
        }        
    }
    
    public String ContactSzerkeszt(int id){
         for (Contact c : contacts) {
            if (c.getId().equals(id)) {
                selectedContact = c;
                break;
            }
        }
         return "editContact";
    }
    
     public String PhoneSzerkeszt(int id){
         for (Phone p : phones) {
            if (p.getId().equals(id)) {
                selectedPhone = p;
                break;
            }
        }
         return "editPhoneNumber";
    }

    public void removeContact(Contact c){
      Session session = hibernate.HibernateUtil.getSessionFactory().openSession();
      session.beginTransaction();
     
      session.delete(c);
      
      session.getTransaction().commit();
      session.close();
      contacts.remove(c);    
    }
    
    public String ujKontaktFelvetel(){
        newContact = new Contact();
        return "ujKontakt";
    }
    public String ujPhoneFelvetel(){
        newPhone = new Phone();
        return "ujPhone";
    }
    
     public void removePhone(Phone p){
        Session session = hibernate.HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
     
        session.delete(p);

        session.getTransaction().commit();
        session.close();
        phones.remove(p);  
    }
    
    public String saveContact() {      
       if (newContact.getName() != null && newContact.getEmail() != null && newContact.getBirthdate() != null) {          
            Session session = hibernate.HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.saveOrUpdate(newContact);
            session.getTransaction().commit();
            session.close();            
            contacts.add(newContact);
            return "index";
        }else{
           hiba = "Minden mezőt ki kell tölteni!";
       }        
       return "ures";
    }
    public String savePhone() {      
       if (newPhone.getNumber()!= null && newPhone.getType()!= null) {          
            Session session = hibernate.HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.saveOrUpdate(newPhone);
            session.getTransaction().commit();
            session.close();            
            phones.add(newPhone);
            return "index";
        }else{
           hiba = "Minden mezőt ki kell tölteni!";
       }        
       return "ures";
    }
    
    public String saveEditedContact() {   
          boolean isNewContact = selectedContact.getId() == null;
       if (selectedContact.getName() != null && selectedContact.getEmail() != null && selectedContact.getBirthdate() != null) {          
            Session session = hibernate.HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.saveOrUpdate(selectedContact);
            session.getTransaction().commit();
            session.close();            
           
            if (isNewContact) {
            contacts.add(selectedContact);  
            }
            
            return "index";
        }else{
           hiba = "Minden mezőt ki kell tölteni!";
       }        
       return "ures";
    }
    
    public String saveEditedPhones() {   
          boolean isNewPhone = selectedPhone.getId() == null;
       if (selectedPhone.getNumber() != null && selectedPhone.getType()!= null) {          
            Session session = hibernate.HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.saveOrUpdate(selectedPhone);
            session.getTransaction().commit();
            session.close();            
           
            if (isNewPhone) {
            phones.add(selectedPhone);  
            }
            
            return "index";
        }else{
           hiba = "A telefonszámot és a típúst ki kell tölteni!";
       }        
       return "ures";
    }
    
    public void szures(){
        Session session = hibernate.HibernateUtil.getSessionFactory().openSession();      
        contacts = session.createQuery("FROM Contact").list(); 
        Iterator<Contact> i = contacts.iterator();
        
        if (search == ""){            
            contacts = session.createQuery("FROM Contact").list();                  
            
        }else{           
            while (i.hasNext()) {
                Contact c = i.next();
                if (!c.getName().toLowerCase().contains(search.toLowerCase())) {
                   i.remove();                        
                }
            }
        }
        session.close();        
    }
    
    public String getHiba() {
        return hiba;
    }    

    public List<Contact> getContacts() {    
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public Contact getNewContact() {
        return newContact;
    }

    public void setNewContact(Contact newContact) {
        this.newContact = newContact;
    }    
    
    public List<Phone> getPhones() { 
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }
    
     public Contact getSelectedContact() {
        return selectedContact;
    }

    public void setSelectedContact(Contact selectedContact) {
        this.selectedContact = selectedContact;
    }

    public String getSearch() {
        return search;
    }

    public Phone getSelectedPhone() {
        return selectedPhone;
    }

    public void setSelectedPhone(Phone selectedPhone) {
        this.selectedPhone = selectedPhone;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public Phone getNewPhone() {
        return newPhone;
    }

    public void setNewPhone(Phone newPhone) {
        this.newPhone = newPhone;
    }
    
}
