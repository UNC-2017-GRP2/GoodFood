import com.netcracker.config.ApplicationContextConfig;
import com.netcracker.model.Address;
import com.netcracker.model.Item;
import com.netcracker.model.Order;
import com.netcracker.repository.ItemRepository;
import com.netcracker.repository.OrderRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import java.math.BigInteger;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

//@Component
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationContextConfig.class})

public class UnitTestBack {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private OrderRepository orderRepository;

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
    //private BigInteger orderId = new BigInteger( "123");
    private BigInteger userId = new BigInteger("456");
    private BigInteger orderCost = new BigInteger("1400");
    private String status = "Created";
    private Address orderAddress = new Address (55.1, 66.2);
    private String orderPhone = "88005553535";
    private List<Item> orderItems = myItems;
    private LocalDateTime orderCreationDate = LocalDateTime.now();
    private String paymentType = "Payment by card";
    private Boolean isPaid = true;
    private BigInteger changeFrom = new BigInteger("1500");

    @Before
    public void initData() {

        item1 = new Item(productId1, productName1, productDescription1, productCategory1, productCost1, productImage1, productQuantity1);
        item2 = new Item(productId2, productName2, productDescription2, productCategory2, productCost2, productImage2, productQuantity2);
        item3 = new Item(productId3, productName3, productDescription3, productCategory3, productCost3, productImage3, productQuantity3);
        myItems.clear();
        myItems.add(item1);
        myItems.add(item2);
        myItems.add(item3);
        myOrder = new Order(orderRepository.getObjectId(), userId, orderCost, status, orderAddress, orderPhone, orderItems, orderCreationDate, paymentType, isPaid, changeFrom);
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
    public void testGetItemsByCategory() {
        List<Item> listItem = itemRepository.getItemsByCategory("811"); //Pizza, getItemsByCategoryID
        Assert.assertTrue(listItem.size() > 0);
    }

    @Test
    public void testCheckout() {

        Order order1 = myOrder;

        try {
            orderRepository.checkout(order1);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Order order2 = orderRepository.getOrderById(order1.getOrderId());

        Assert.assertEquals(order1.getOrderId(),order2.getOrderId());
    }

}
