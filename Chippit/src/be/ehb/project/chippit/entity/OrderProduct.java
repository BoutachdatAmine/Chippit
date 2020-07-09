package be.ehb.project.chippit.entity;

public class OrderProduct {
    private int order_product_id;
    private int order_id;
    private int quantity;
    private int product_id;

    public OrderProduct(int order_product_id, int order_id, int quantity, int product_id) {
        this.order_product_id = order_product_id;
        this.order_id = order_id;
        this.quantity = quantity;
        this.product_id = product_id;
    }

    public OrderProduct(int order_id, int quantity, int product_id) {
        this.order_id = order_id;
        this.quantity = quantity;
        this.product_id = product_id;
    }

    public int getOrder_product_id() {
        return order_product_id;
    }

    public void setOrder_product_id(int order_product_id) {
        this.order_product_id = order_product_id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    @Override
    public String toString() {
        return "OrderProduct{" +
                "order_product_id=" + order_product_id +
                ", order_id=" + order_id +
                ", quantity=" + quantity +
                ", product_id=" + product_id +
                '}'+ "\n";
    }
}
