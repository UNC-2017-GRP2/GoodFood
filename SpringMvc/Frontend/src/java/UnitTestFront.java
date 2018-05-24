import com.netcracker.config.ApplicationContextConfig;
import com.netcracker.controller.BasketController;
import com.netcracker.controller.HomeController;
import com.netcracker.model.Address;
import com.netcracker.model.Item;
import com.netcracker.model.Order;
import com.netcracker.service.ItemService;
import com.netcracker.service.OrderService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import java.math.BigInteger;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

class ItemServiceMock implements ItemService{

    @Override
    public List<Item> getAllItems(Locale locale) {
        return null;
    }

    public Item resultFor_getItemById;

    @Override
    public Item getItemById(BigInteger itemId, Locale locale) {
        return resultFor_getItemById;
    }

    @Override
    public List<Item> getItemsByCategory(String category, Locale locale) {
        return null;
    }

    @Override
    public void removeItemById(BigInteger itemId) {

    }

    @Override
    public List<String> getAllCategories() {
        return null;
    }

    @Override
    public void saveItem(Item item, String nameRu, String nameUk, String descriptionRu, String descriptionUk) {

    }

    @Override
    public void updateItem(Item item, String nameRu, String nameUk, String descriptionRu, String descriptionUk) {

    }
}

class OrderServiceMock implements OrderService{

    @Override
    public BigInteger totalOrder(ArrayList<Item> items) {
        return new BigInteger("1400");
    }

    @Override
    public void checkout(BigInteger orderId, ArrayList<Item> items, String username, Address orderAddress, String inputPhone, long paymentType, Boolean isPaid, BigInteger changeFrom) throws SQLException {

    }

    @Override
    public List<Order> getAllFreeOrders(Locale locale) {
        return null;
    }

    @Override
    public Order getOrderById(BigInteger orderId) {
        return null;
    }

    @Override
    public List<Order> getAllOrdersByCourier(String username, Locale locale) {
        return null;
    }

    @Override
    public List<Order> getCompletedOrdersByCourier(String username, Locale locale) {
        return null;
    }

    @Override
    public List<Order> getNotCompletedOrdersByCourier(String username, Locale locale) {
        return null;
    }

    @Override
    public void changeOrderStatus(BigInteger orderId, long statusId) {

    }

    @Override
    public void setCourier(BigInteger orderId, String username) {

    }

    @Override
    public List<Order> getAllOrders(Locale locale) {
        return null;
    }

    @Override
    public List<Order> getOrdersByUsername(String username, Locale locale) {
        return null;
    }

    @Override
    public BigInteger getObjectId() {
        return null;
    }

    @Override
    public void removeOrderById(BigInteger orderId) {
    }

    @Override
    public void updateOrderPaid(BigInteger orderId, int isPaid){

    }
}

//@Component
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationContextConfig.class, UnitTestFront.Config.class})

public class UnitTestFront {

    @Configuration
    public static class Config {

        // this bean will be injected into the OrderServiceTest class
        @Bean
        public OrderService orderService() {
            OrderService orderService = new OrderServiceMock();
            // set properties, etc.
            return orderService;
        }

        @Bean
        public ItemService itemService() {
            return new ItemServiceMock();
        }
    }


    @Autowired
    private BasketController basketController;

    @Autowired
    private HomeController homeController;

    @Autowired
    private  ItemService itemService;

    private Item item1;
    private Item item2;
    private Item item3;
    private BigInteger productId1 = new BigInteger("111");
    private BigInteger productId2 = new BigInteger("222");
    private BigInteger productId3 = new BigInteger("333");
    private String productName1 = "ProductName1";
    private String productName2 = "ProductName2";
    private String productName3 = "ProductName3";
    private String productDescription1 = "ProductDescription1";
    private String productDescription2 = "ProductDescription2";
    private String productDescription3 = "ProductDescription3";
    private String productCategory1 = "ProductCategory1";
    private String productCategory2 = "ProductCategory2";
    private String productCategory3 = "ProductCategory3";
    private String productImage1 = "ProductImage1";
    private String productImage2 = "ProductImage2";
    private String productImage3 = "ProductImage3";
    private int productQuantity1 = 1;
    private int productQuantity2 = 2;
    private int productQuantity3 = 3;
    private BigInteger productCost1 = new BigInteger("100");
    private BigInteger productCost2 = new BigInteger("200");
    private BigInteger productCost3 = new BigInteger("300");

    private List<Item> myItems = new ArrayList<>();

    private Order myOrder;
    private BigInteger orderId = new BigInteger( "123");
    private BigInteger userId = new BigInteger("456");
    private BigInteger orderCost = new BigInteger("1400");
    private String status = "Created";
    private Address orderAddress = new Address (55.1, 66.2);
    private String orderPhone = "88005553535";
    private BigInteger courierId = new BigInteger ("789");
    private List<Item> orderItems = myItems;
    private LocalDateTime orderCreationDate = LocalDateTime.now();
    private String paymentType = "Payment by card";
    private Boolean isPaid = true;
    private BigInteger changeFrom = new BigInteger ("1500");

    @Before
    public void initData() {

        item1 = new Item(productId1, productName1, productDescription1, productCategory1, productCost1, productImage1, productQuantity1);
        item2 = new Item(productId2, productName2, productDescription2, productCategory2, productCost2, productImage2, productQuantity2);
        item3 = new Item(productId3, productName3, productDescription3, productCategory3, productCost3, productImage3, productQuantity3);
        myItems.clear();
        myItems.add(item1);
        myItems.add(item2);
        myItems.add(item3);
        myOrder = new Order(orderId, userId, orderCost, status, orderAddress, orderPhone, orderItems, orderCreationDate, courierId, paymentType, isPaid, changeFrom);
    }

    /*public Order(BigInteger orderId,
                 BigInteger userId,
                 BigInteger orderCost,
                 String status,
                 Address orderAddress,
                 String orderPhone,
                 List<Item> orderItems,
                 LocalDateTime orderCreationDate,
                 BigInteger courierId,
                 String paymentType,
                 Boolean isPaid) {
        this(orderId, userId, orderCost, status, orderAddress, orderPhone, orderItems, orderCreationDate, paymentType, isPaid );
        this.courierId = courierId;
    }*/

    @Test
    public void testRemoveItem(){
        MockHttpSession session = new MockHttpSession();

        session.setAttribute("basketItems", myItems);

        basketController.removeItem(productId1, session);

        Object list1 = session.getAttribute("basketItems");

        List<Item> list2 = new ArrayList<>();
        list2.add(item2);
        list2.add(item3);

        Assert.assertEquals(list1, list2);
    }

    @Test
    public void testAddBasket(){

        MockHttpSession session = new MockHttpSession();

        ItemServiceMock i = (ItemServiceMock) itemService;
        i.resultFor_getItemById = item2;

        homeController.addBasket(productId2, 123, Locale.getDefault(), session);

        Item x = ((List<Item>) session.getAttribute("basketItems")).get(0);
        Assert.assertEquals(x.getProductQuantity(), 123);
    }

}
