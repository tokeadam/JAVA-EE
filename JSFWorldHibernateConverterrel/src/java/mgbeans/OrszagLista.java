package mgbeans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.hibernate.Session;
import org.hibernate.internal.util.SerializationHelper;
import pojos.City;
import pojos.Country;

@ManagedBean
@SessionScoped
public class OrszagLista {

    private List<Country> countries;
    private List<City> cities;
    private Country selectedCountry;
    private City selectedCity;
    private String selectedCountryID;
    private Map<String, Country> countryMap;

    public OrszagLista() {
        Session session = hibernate.HibernateUtil.getSessionFactory().openSession();
        countries = session.createQuery("FROM Country").list();
        session.close();

        countryMap = new HashMap<>();
        for (Country c : countries) {
            countryMap.put(c.getCode(), c);
        }
    }

    public void orszagValaszt() {
//        Session session = hibernate.HibernateUtil.getSessionFactory().openSession();
//        selectedCountry = (Country) session.createQuery("FROM Country WHERE code=:ccode").
//                setString("ccode", selectedCountryID).uniqueResult();
//        session.close();
        //-----VAGY-----
//        for (Country c : countries) {
//            if (c.getCode().equals(selectedCountryID)) {
//                selectedCountry = c;
//                break;
//            }
//        }
        //-----VAGY-----a leggyorsabb és legjobb
        selectedCountry = countryMap.get(selectedCountryID);
        cities = new ArrayList<>(selectedCountry.getCities());

    }

    public void removeCity(City c) {
        Session session = hibernate.HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        session.delete(c);

        session.getTransaction().commit();
        session.close();

        cities.remove(c);
        selectedCountry.getCities().remove(c);
    }

    public String editCity(City c) {
        selectedCity = c;

        return "varos_szerkeszt";
    }

    public String newCity() {
        selectedCity = new City();
        selectedCity.setCountry(selectedCountry);

        return "varos_szerkeszt";
    }

    public String saveCity() {
        boolean isNewCity = selectedCity.getId() == null;
                
        Session session = hibernate.HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        session.saveOrUpdate(selectedCity);

        session.getTransaction().commit();
        session.close();
        
        if (isNewCity) {
            cities.add(selectedCity);
            selectedCountry.getCities().add(selectedCity);
        }
        
        return "index";
    }

    public List<Country> getCountries() {
        return countries;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }

    public Country getSelectedCountry() {
        return selectedCountry;
    }

    public void setSelectedCountry(Country selectedCountry) {
        this.selectedCountry = selectedCountry;
    }

    public String getSelectedCountryID() {
        return selectedCountryID;
    }

    public void setSelectedCountryID(String selectedCountryID) {
        this.selectedCountryID = selectedCountryID;
    }

    public City getSelectedCity() {
        return selectedCity;
    }

    public void setSelectedCity(City selectedCity) {
        this.selectedCity = selectedCity;
    }

}
