package mgbeans;

import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.hibernate.Session;
import pojos.Product;

@ManagedBean
@SessionScoped
public class Listazo {

    private List<Product> products;
    
    public Listazo() {
        Session session = hibernate.HibernateUtil.getSessionFactory().openSession();
        
        products = session.createQuery("FROM Product").list();
        
        session.close();
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
    
    
    
}
