import com.netcracker.config.ApplicationContextConfig;
import com.netcracker.config.MvcWebApplicationInitializer;
import com.netcracker.model.Address;
import com.netcracker.model.Item;
import com.netcracker.model.Order;
import com.netcracker.repository.ItemRepository;
import com.netcracker.repository.OrderRepository;
import com.netcracker.repository.impl.ItemRepositoryImpl;
import com.netcracker.repository.impl.OrderRepositoryImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigInteger;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.oracle.jrockit.jfr.ContentType.Address;

//@Component

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationContextConfig.class})

public class Unit_tests {

    @Autowired
    private OrderRepository orderRepository;

    private Item item1;
    private Item item2;
    private Item item3;
    private BigInteger productId1 = new BigInteger("111");
    private BigInteger productId2 = new BigInteger("222");
    private BigInteger productId3 = new BigInteger("333");
    private BigInteger productCost1 = new BigInteger("100");
    private BigInteger productCost2 = new BigInteger("200");
    private BigInteger productCost3 = new BigInteger("300");
    private List<Item> myItems = new ArrayList<>();

    @Before
    public void initData() {

        item1 = new Item(productId1, "productName1", "productDescription1", "productCategory1", productCost1, "productImage1", 1);
        item2 = new Item(productId2, "productName2", "productDescription2", "productCategory2", productCost2, "productImage2", 2);
        item3 = new Item(productId3, "productName3", "productDescription3", "productCategory3", productCost3, "productImage3", 3);
        myItems.add(item1);
        myItems.add(item2);
        myItems.add(item3);
        Order order1 = new Order(
                new BigInteger ("11111"),
                new BigInteger ("10"),
                new BigInteger ("1000"),
                "Created",
                new Address (55.1, 66.2),
                "88005553535",
                myItems,
                LocalDateTime.now(),
                new BigInteger ("22222"),
                "Payment by card",
                true
        );
    }

    @Autowired
    private ItemRepository itemRepository;

    @Test
    public void testGetItemsByCategory() {
        List<Item> list = itemRepository.getItemsByCategory("Pizza");
        Assert.assertNotNull(list);
    }

    @Test public void testGetOrderId() {

        Order order1 = new Order(
                new BigInteger ("11111"),
                new BigInteger ("10"),
                new BigInteger ("1000"),
                "Created",
                new Address (55.1, 66.2),
                "88005553535",
                myItems,
                LocalDateTime.now(),
                new BigInteger ("22222"),
                "Payment by card",
                true
        );

        BigInteger actual = order1.getOrderId();

        BigInteger expected = new BigInteger("11111");

        Assert.assertEquals(actual,expected);
    }

    @Test
    public void testCheckout() {

        Order order10 = new Order(
                new BigInteger ("11111"),
                new BigInteger ("10"),
                new BigInteger ("1000"),
                "Created",
                new Address (55.1, 66.2),
                "88005553535",
                myItems,
                LocalDateTime.now(),
                new BigInteger ("22222"),
                "Payment by card",
                true
        );

        try {
            orderRepository.checkout(order10, 819);
            } catch (SQLException e) {
            e.printStackTrace();
        }

        Order order20 = orderRepository.getOrderById(order10.getOrderId());

        Assert.assertEquals(order10,order20);
    }

}
