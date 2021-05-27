/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package converter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.hibernate.Session;
import pojos.Country;

@FacesConverter("converter.CountryConverter")
public class CountryConverter implements Converter{

    private Map<String, Country> orszagMap;
    
    public CountryConverter() {
        orszagMap = new HashMap<String, Country>();
        Session session = hibernate.HibernateUtil.getSessionFactory().openSession();
        List<Country> orszagok = session.createQuery("FROM Country").list();
        session.close();
        for (Country c : orszagok) {
            orszagMap.put(c.getCode(), c);
        }
    }
 
    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        return orszagMap.get(string);
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        Country c = (Country) o;
        return c.getCode();
    }
    
}
