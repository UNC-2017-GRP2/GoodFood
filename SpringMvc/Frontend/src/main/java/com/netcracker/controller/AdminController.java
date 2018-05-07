package com.netcracker.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.netcracker.config.AuthManager;
import com.netcracker.config.BigIntegerAdapter;
import com.netcracker.config.Constant;
import com.netcracker.model.Entity;
import com.netcracker.model.Item;
import com.netcracker.model.Order;
import com.netcracker.model.User;
import com.netcracker.service.ItemService;
import com.netcracker.service.OrderService;
import com.netcracker.service.UserService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletContext;
import java.io.*;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormatSymbols;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Controller
public class AdminController{
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;
    @Autowired
    private ItemService itemService;

    @RequestMapping(value = { "/admin"}, method = RequestMethod.GET)
    public ModelAndView adminPanel(Locale locale) throws IOException {

        /*File myFile = new File("/resources/Items.xlsx");
        FileInputStream fis = new FileInputStream(myFile);
        XSSFWorkbook myWorkBook = new XSSFWorkbook (fis);

        // Return first sheet from the XLSX workbook
        XSSFSheet mySheet = myWorkBook.getSheetAt(0);

        // Get iterator to all the rows in current sheet
        Iterator<Row> rowIterator = mySheet.iterator();

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            // For each row, iterate through each columns
            Iterator<Cell> cellIterator = row.cellIterator();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();

                switch (cell.getCellType()) {
                    case Cell.CELL_TYPE_STRING:
                        System.out.print(cell.getStringCellValue() + "\t");
                        break;
                    case Cell.CELL_TYPE_NUMERIC:
                        System.out.print(cell.getNumericCellValue() + "\t");
                        break;
                    case Cell.CELL_TYPE_BOOLEAN:
                        System.out.print(cell.getBooleanCellValue() + "\t");
                        break;
                    default:
                }
            }
        }



*/
        ModelAndView model = new ModelAndView();
        List<Order> allOrders = orderService.getAllOrders(locale);
        List<User> allUsers = userService.getAllUsers();
        List<Item> items =  itemService.getAllItems(locale);
        model.addObject("now", LocalDateTime.now());
        model.addObject("chr", ChronoUnit.HOURS);


        Map<String, Integer> pieDataMap = new HashMap<>();

        //pieChartData-----------------------------------------------
        List<Item> allItems = new ArrayList<>();
        for (Order o : allOrders)
            allItems.addAll(o.getOrderItems());

        for (Item i : allItems) {
            if (pieDataMap.containsKey(i.getProductName())) {
                pieDataMap.replace(i.getProductName(), pieDataMap.get(i.getProductName()) + i.getProductQuantity());
                continue;
            }
            pieDataMap.put(i.getProductName(), i.getProductQuantity());
        }
        //pieChartData END-------------------------------------------------

        //CoreChart--------------------------------------------
        Map<String, Integer> coreChartDataMap = new HashMap<>();
        DateFormatSymbols symbols = new DateFormatSymbols(locale);
        String[] weekDays = symbols.getShortWeekdays();
        for (String day : weekDays) {
            coreChartDataMap.put(day, 0);
        }

        DateTimeFormatter weekFormatter = DateTimeFormatter.ofPattern("EEE").withLocale(locale);
        Integer newRevenue;
        for (Order o : allOrders) {
            newRevenue = coreChartDataMap.get(o.getOrderCreationDate().format(weekFormatter)) + o.getOrderCost().intValue();
            coreChartDataMap.replace(o.getOrderCreationDate().format(weekFormatter), newRevenue);
        }
        //CoreChart    END--------------------------------------------


        //Linechart----------------------------------------------
        Map<String, Integer> revenuePerDayMap = new TreeMap<>();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM EEE").withLocale(locale);
        LocalDateTime dateTime = LocalDateTime.now();

        int i = 0;
        while (++i < 10) {
            revenuePerDayMap.put(dateTime.format(dateTimeFormatter), 0);
            dateTime = dateTime.minusDays(1);
        }

        for (Order o : allOrders)
            if(o.getOrderCreationDate().isAfter(LocalDateTime.now().minusDays(10))) {
                String s = o.getOrderCreationDate().format(dateTimeFormatter);
                newRevenue = revenuePerDayMap.get(o.getOrderCreationDate().format(dateTimeFormatter)) + o.getOrderCost().intValue();
                revenuePerDayMap.replace(o.getOrderCreationDate().format(dateTimeFormatter), newRevenue);
            }
        //  revenuePerDayMap.

        //LineChartEnd---------------------------------------------------
        model.setViewName("admin");
        if (allOrders != null){
            model.addObject("orders", allOrders);
        }
        if (allUsers != null){
            model.addObject("users", allUsers);
        }
        if (items != null){
            model.addObject("items", items);
        }
        if (pieDataMap != null) {
            model.addObject("pieDataMap", pieDataMap);
        }
        if (coreChartDataMap != null) {
            model.addObject("coreChartDataMap", coreChartDataMap);
        }
        if ( revenuePerDayMap != null) {
            model.addObject("revenuePerDayMap", revenuePerDayMap);
        }
        model.addObject("weekDays", weekDays);
        model.addObject("userForm", new User());
        model.addObject("itemForm", new Item());
        List<String> categories = itemService.getAllCategories();
        model.addObject("categories", categories);
        return model;
    }

    @RequestMapping(value = { "/admin/actualize"}, method = RequestMethod.POST)
    public ModelAndView markOrderAsExpired(Locale locale) throws IOException {
        List<Order> allOrders = orderService.getAllOrders(locale);
        for (Order order : allOrders) {
            if ((order.getStatus().equals(Constant.STATUSES.get(Constant.STATUS_CREATED_ENUM_ID)) ||
                    order.getStatus().equals(Constant.STATUSES.get(Constant.STATUS_LINKED_WITH_COURIER_ENUM_ID))) &&
                    order.getOrderCreationDate().until(LocalDateTime.now(), ChronoUnit.HOURS) > Constant.END_EXPIRATION_TIME) {
                orderService.changeOrderStatus(order.getOrderId(), Constant.STATUS_EXPIRED_ENUM_ID);
            }
        }
        ModelAndView model = new ModelAndView();
        /*model.addObject("actualize", true);*/
        model.setViewName("redirect:/admin");
        return model;
    }

    @RequestMapping(value = "/changeRole", method = RequestMethod.GET)
    public @ResponseBody
    String changeRole(@RequestParam BigInteger userId, @RequestParam String role){
        userService.changeRole(userId, role);
        return "redirect:/admin";
    }

    @RequestMapping(value = {"/getUserInfo"}, method = RequestMethod.GET)
    public @ResponseBody String getUserInfo(@RequestParam BigInteger userId) throws IOException {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(BigInteger.class, new BigIntegerAdapter());
        Gson gson = builder.create();
        return gson.toJson(userService.getUserById(userId));
    }

    @RequestMapping(value = "/removeUser", method = RequestMethod.GET)
    public @ResponseBody
    void removeUser(@RequestParam BigInteger userId){
        userService.removeUserById(userId);
    }

    @RequestMapping(value = {"/createUser"}, method = RequestMethod.GET)
    public @ResponseBody String sendEntity(@RequestParam String jsonUser) throws Exception {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(BigInteger.class, new BigIntegerAdapter());
        Gson gson = builder.create();
        User user = gson.fromJson(jsonUser, User.class);
        ShaPasswordEncoder encoder = new ShaPasswordEncoder();
        user.setPasswordHash(encoder.encodePassword(user.getPasswordHash(), null));
        user.setUserId(userService.getObjectId());
        userService.saveUser(user);
        return gson.toJson(user);
    }

    @RequestMapping(value = {"/getOrderInfo"}, method = RequestMethod.GET)
    public @ResponseBody String getOrderInfo(@RequestParam BigInteger orderId) throws IOException {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(BigInteger.class, new BigIntegerAdapter());
        Gson gson = builder.create();
        return gson.toJson(orderService.getOrderById(orderId));
    }

    @RequestMapping(value = {"/getItemInfo"}, method = RequestMethod.GET)
    public @ResponseBody String getItemInfo(@RequestParam BigInteger itemId, Locale locale) throws IOException {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(BigInteger.class, new BigIntegerAdapter());
        Gson gson = builder.create();
        return gson.toJson(itemService.getItemById(itemId, locale));
    }

    @RequestMapping(value = "/delItem", method = RequestMethod.GET)
    public void delItem(@RequestParam BigInteger itemId){
        itemService.removeItemById(itemId);
    }

    @RequestMapping(value = {"/getLocItemsInfo"}, method = RequestMethod.GET, produces = { "application/json; charset=utf-8" })
    public @ResponseBody String getLocItemsInfo(@RequestParam BigInteger itemId, Locale locale) throws IOException {
        Gson gson = new Gson();
        Item itemEn = itemService.getItemById(itemId, new Locale("en"));
        Item itemRu = itemService.getItemById(itemId, new Locale("ru"));
        Item itemUk = itemService.getItemById(itemId, new Locale("uk"));
        Map<String, String> itemLocalize = new HashMap<>();
        itemLocalize.put("nameEn", itemEn.getProductName());
        itemLocalize.put("nameRu", itemRu.getProductName());
        itemLocalize.put("nameUk", itemUk.getProductName());
        itemLocalize.put("descriptionEn", itemEn.getProductDescription());
        itemLocalize.put("descriptionRu", itemRu.getProductDescription());
        itemLocalize.put("descriptionUk", itemUk.getProductDescription());
        return gson.toJson(itemLocalize);
    }

    @RequestMapping(value = "/delOrder", method = RequestMethod.GET)
    public @ResponseBody void delOrder(@RequestParam BigInteger orderId){
        orderService.removeOrderById(orderId);
    }



    @RequestMapping(value = "/admin/createItems", method = RequestMethod.POST)
    public @ResponseBody void createItems(@RequestParam MultipartFile file) throws IOException {
        //String fileLocation;
/*        InputStream in = file.getInputStream();
        Workbook workbook = new XSSFWorkbook(in);
        Sheet sheet = workbook.getSheetAt(0);
        for (Row row : sheet) {
            for (Cell cell : row) {
                switch (cell.getCellTypeEnum()) {
                    case STRING: System.out.println(cell.getStringCellValue() + " "); break;
                    case NUMERIC: System.out.println(cell.getNumericCellValue() + " "); break;
                    case BOOLEAN: System.out.println(cell.getBooleanCellValue() + " "); break;
                    default: System.out.println("default");
                }
            }
        }*/

        InputStream ExcelFileToRead = file.getInputStream();
        XSSFWorkbook  wb = new XSSFWorkbook(ExcelFileToRead);

        XSSFWorkbook test = new XSSFWorkbook();

        XSSFSheet sheet = wb.getSheetAt(0);
        XSSFRow row;
        XSSFCell cell;

        Iterator rows = sheet.rowIterator();

        while (rows.hasNext())
        {
            row=(XSSFRow) rows.next();
            Iterator cells = row.cellIterator();
            while (cells.hasNext())
            {
                cell=(XSSFCell) cells.next();

                if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING)
                {
                    System.out.print(cell.getStringCellValue()+" ");
                }
                else if(cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC)
                {
                    System.out.print(cell.getNumericCellValue()+" ");
                }
                else
                {
                    //U Can Handel Boolean, Formula, Errors
                }
            }
            System.out.println();
        }

        /*File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        fileLocation = path.substring(0, path.length() - 1) + file.getOriginalFilename();
        FileOutputStream f = new FileOutputStream(fileLocation);
        int ch = 0;
        while ((ch = in.read()) != -1) {
            f.write(ch);
        }
        f.flush();
        f.close();*/
    }


    @Autowired
    ServletContext servletContext;

    @PostMapping("/upload")
    public void singleFileUpload(@RequestParam("file") MultipartFile file) {
        String webappRoot = servletContext.getRealPath("/");
//        String relativeFolder = File.separator + "resources" + File.separator
//                + "img" + File.separator;
        String relativeFolder = File.separator + ".." + File.separator + ".." + File.separator
                + "src" + File.separator + "main" + File.separator + "webapp" + File.separator + "resources" + File.separator
                + "img" + File.separator;
        String filename = webappRoot + relativeFolder
                + file.getOriginalFilename();
        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(filename);
            Files.write(path, bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
