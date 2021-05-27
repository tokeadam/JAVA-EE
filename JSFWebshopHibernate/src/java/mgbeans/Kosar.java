package mgbeans;

import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import pojos.Orderitem;
import pojos.Product;

@ManagedBean
@SessionScoped
public class Kosar {

    private List<Orderitem> kosarElemek;

    public Kosar() {
        kosarElemek = new ArrayList<>();

    }

    public void add(Product prod) {
        boolean ujTermek = true;
        for (Orderitem oi : kosarElemek) {
            if (oi.getProduct().equals(prod)) {  //ha ez a termék már szerepelt a kosárban
                incQty(oi);
                ujTermek = false;
                break;
            }
        }
        if (ujTermek) { //ha még nem volt ilyen a kosárban akkor hozzáadom
            Orderitem oi = new Orderitem(null, prod, 1, prod.getPrice());
            kosarElemek.add(oi);
        }
    }

    public void remove(Orderitem oi) {
        kosarElemek.remove(oi);
    }

    public void incQty(Orderitem oi) {
        oi.setQty(oi.getQty() + 1);
        oi.setPrice(oi.getQty() * oi.getProduct().getPrice());
    }

    public void decQty(Orderitem oi) {
        if (oi.getQty() > 1) { //ha 1-nél több van a kosárban akkor 1-el csökkentem a mennyiséget.
            oi.setQty(oi.getQty() - 1);
            oi.setPrice(oi.getQty() * oi.getProduct().getPrice());
        } else { //ha 1 db volt a kosárban akkor törlöm ezt az elemet a kosárból
            remove(oi);
        }
    }
    
    public int getSumQuantity(){
        int sum = 0;
        for (Orderitem oi : kosarElemek) {
            sum+=oi.getQty();
        }
        return sum;
    }
    
    public double getSumPrice(){
        double sum = 0;
        for (Orderitem oi : kosarElemek) {
            sum+=oi.getPrice();
        }
        return sum;
    }

    public List<Orderitem> getKosarElemek() {
        return kosarElemek;
    }

    public void setKosarElemek(List<Orderitem> kosarElemek) {
        this.kosarElemek = kosarElemek;
    }

}
