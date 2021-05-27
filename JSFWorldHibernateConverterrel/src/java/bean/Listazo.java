/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.hibernate.Session;
import pojos.City;
import pojos.Country;

@ManagedBean
@SessionScoped
public class Listazo {

    private List<Country> orszagok;
    private List<City> varosok;
    private String selectedCountryID;
    private Country selectedCountry;
    private City selectedCity;
    private Map<String, Country> orszagMap;
    
    public Listazo() {
        orszagMap = new HashMap<String, Country>();
        Session session = hibernate.HibernateUtil.getSessionFactory().openSession();
        orszagok = session.createQuery("FROM Country").list();
        session.close();
        for (Country c : orszagok) {
            orszagMap.put(c.getCode(), c);
        }
    }

    public void kivalaszt(){
       // selectedCountry = orszagMap.get(selectedCountryID);
        varosok = new ArrayList<City>(selectedCountry.getCities());
    }
    
    public String szerkeszt(City c){
        selectedCity = c;
        return "edit";
    }
    
    public List<Country> getOrszagok() {
        return orszagok;
    }

    public void setOrszagok(List<Country> orszagok) {
        this.orszagok = orszagok;
    }

    public List<City> getVarosok() {
        return varosok;
    }

    public void setVarosok(List<City> varosok) {
        this.varosok = varosok;
    }

    public String getSelectedCountryID() {
        return selectedCountryID;
    }

    public void setSelectedCountryID(String selectedCountryID) {
        this.selectedCountryID = selectedCountryID;
    }

    public Country getSelectedCountry() {
        return selectedCountry;
    }

    public void setSelectedCountry(Country selectedCountry) {
        this.selectedCountry = selectedCountry;
    }

    public City getSelectedCity() {
        return selectedCity;
    }

    public void setSelectedCity(City selectedCity) {
        this.selectedCity = selectedCity;
    }
    
    
    
}
