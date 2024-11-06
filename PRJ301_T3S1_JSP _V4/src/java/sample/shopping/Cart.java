package sample.shopping;

import java.util.HashMap;
import java.util.Map;

public class Cart {

    private Map<String, ClothesDTO> cart;

    public Cart() {
    }

    public Cart(Map<String, ClothesDTO> cart) {
        this.cart = cart;
    }

    public Map<String, ClothesDTO> getCart() {
        return cart;
    }

    public void setCart(Map<String, ClothesDTO> cart) {
        this.cart = cart;
    }

    public boolean add(ClothesDTO clothes) {

        boolean check = false;
        try {
            if (this.cart == null) {
                this.cart = new HashMap<>();
            }
            if (this.cart.containsKey(clothes.getId())) {
                int currentQuantity = this.cart.get(clothes.getId()).getQuantity();
                this.cart.get(clothes.getId()).setQuantity(currentQuantity + clothes.getQuantity());
            } else {
                cart.put(clothes.getId(), clothes);
            }
            check = true;
        } catch (Exception e) {
        }
        return check;
    }

    public boolean remove(String id) {
        boolean check = false;
        try {
            if (this.cart != null) {
                if (this.cart.containsKey(id)) {
                    this.cart.remove(id);
                    check = true;
                }
            }
        } catch (Exception e) {
        }
        return check;
    }

    public boolean editQuantity(String id, int quantity) {
        boolean check = false;
        try {
            if (this.cart != null) {
                if (this.cart.containsKey(id)) {
                    this.cart.get(id).setQuantity(quantity);
                    check = true;
                }
            }
        } catch (Exception e) {           
        }
        return check;
    }
}
