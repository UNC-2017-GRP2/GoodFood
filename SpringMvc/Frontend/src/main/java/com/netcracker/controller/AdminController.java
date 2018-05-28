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
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
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
import javax.servlet.http.HttpServletResponse;
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
public class AdminController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;
    @Autowired
    private ItemService itemService;
    @Autowired
    ServletContext servletContext;

    @RequestMapping(value = {"/admin"}, method = RequestMethod.GET)
    public ModelAndView adminPanel(
            Locale locale,
            @RequestParam(value = "invalidDoc", required = false) String invalidDoc,
            @RequestParam(value = "itemsSave", required = false) String itemsSave) throws IOException {

        ModelAndView model = new ModelAndView();
        List<Order> allOrders = orderService.getAllOrders(locale);
        List<User> allUsers = userService.getAllUsers();
        List<User> allCouriers = userService.getAllCouriers();
        List<Item> items = itemService.getAllItems(locale);
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
            if (o.getOrderCreationDate().isAfter(LocalDateTime.now().minusDays(10))) {
                String s = o.getOrderCreationDate().format(dateTimeFormatter);
                newRevenue = revenuePerDayMap.get(o.getOrderCreationDate().format(dateTimeFormatter)) + o.getOrderCost().intValue();
                revenuePerDayMap.replace(o.getOrderCreationDate().format(dateTimeFormatter), newRevenue);
            }
        //  revenuePerDayMap.

        //LineChartEnd---------------------------------------------------
        model.setViewName("admin");
        if (allOrders != null) {
            model.addObject("orders", allOrders);
        }
        if (allUsers != null) {
            model.addObject("users", allUsers);
        }
        if (allCouriers != null){
            model.addObject("couriers", allCouriers);
        }
        if (items != null) {
            model.addObject("items", items);
        }
        if (pieDataMap != null) {
            model.addObject("pieDataMap", pieDataMap);
        }
        if (coreChartDataMap != null) {
            model.addObject("coreChartDataMap", coreChartDataMap);
        }
        if (revenuePerDayMap != null) {
            model.addObject("revenuePerDayMap", revenuePerDayMap);
        }
        model.addObject("weekDays", weekDays);
        model.addObject("userForm", new User());
        model.addObject("itemForm", new Item());
        List<String> categories = itemService.getAllCategories();
        model.addObject("categories", categories);
        if (invalidDoc != null){
            model.addObject("invalidDoc", true);
        }
        if (itemsSave != null){
            model.addObject("itemsSave", true);
        }
        return model;
    }

    @RequestMapping(value = {"/admin/actualize"}, method = RequestMethod.POST)
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
    String changeRole(@RequestParam BigInteger userId, @RequestParam String role) {
        userService.changeRole(userId, role);
        return "redirect:/admin";
    }

    @RequestMapping(value = {"/getUserInfo"}, method = RequestMethod.GET)
    public @ResponseBody
    String getUserInfo(@RequestParam BigInteger userId) throws IOException {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(BigInteger.class, new BigIntegerAdapter());
        Gson gson = builder.create();
        return gson.toJson(userService.getUserById(userId));
    }

    @RequestMapping(value = "/removeUser", method = RequestMethod.GET)
    public @ResponseBody
    void removeUser(@RequestParam BigInteger userId) {
        userService.removeUserById(userId);
    }

    @RequestMapping(value = {"/createUser"}, method = RequestMethod.GET)
    public @ResponseBody
    String sendEntity(@RequestParam String jsonUser) throws Exception {
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
    public @ResponseBody
    String getOrderInfo(@RequestParam BigInteger orderId) throws IOException {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(BigInteger.class, new BigIntegerAdapter());
        Gson gson = builder.create();
        return gson.toJson(orderService.getOrderById(orderId));
    }

    @RequestMapping(value = {"/getItemInfo"}, method = RequestMethod.GET)
    public @ResponseBody
    String getItemInfo(@RequestParam BigInteger itemId, Locale locale) throws IOException {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(BigInteger.class, new BigIntegerAdapter());
        Gson gson = builder.create();
        return gson.toJson(itemService.getItemById(itemId, locale));
    }

    @RequestMapping(value = "/delItem", method = RequestMethod.GET)
    public @ResponseBody void delItem(@RequestParam BigInteger itemId) {
        itemService.removeItemById(itemId);
    }

    @RequestMapping(value = {"/getLocItemsInfo"}, method = RequestMethod.GET, produces = {"application/json; charset=utf-8"})
    public @ResponseBody
    String getLocItemsInfo(@RequestParam BigInteger itemId, Locale locale) throws IOException {
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
    public @ResponseBody
    void delOrder(@RequestParam BigInteger orderId) {
        orderService.removeOrderById(orderId);
    }

    @RequestMapping(value = "/admin/createItems", method = RequestMethod.POST)
    public @ResponseBody
    ModelAndView createItems(@RequestParam MultipartFile file) throws IOException {
        ModelAndView model = new ModelAndView();
        try{
            switch (file.getContentType()) {
                case Constant.XLSX_TYPE:
                    parseXLSX(file);
                    break;
                case Constant.XLS_TYPE:
                    parseXLS(file);
                    break;
            }
            model.setViewName("redirect:/admin?itemsSave=true");
        }catch (Exception e){
            e.printStackTrace();
            model.setViewName("redirect:/admin?invalidDoc=true");
        }
        return model;
    }

    private void parseXLSX(MultipartFile file) throws Exception{
        InputStream inputStream = file.getInputStream();
        XSSFWorkbook workBook = new XSSFWorkbook(inputStream);
        XSSFSheet sheet = workBook.getSheetAt(0);
        for (Row row : sheet) {
            if (row.getRowNum() == 0) {
                continue;
            }
            if (isCorrectColumnCount(row.getLastCellNum()) && isCorrectCategory(row.getCell(3).getStringCellValue()) && isCorrectCostCellType(row.getCell(7).getCellType())) {
                int cost = (int) row.getCell(7).getNumericCellValue();
                Item item = new Item(
                        row.getCell(0).getStringCellValue(),
                        row.getCell(4).getStringCellValue(),
                        row.getCell(3).getStringCellValue(),
                        new BigInteger(String.valueOf(cost)),
                        1);
                itemService.saveItem(
                        item,
                        row.getCell(1).getStringCellValue(),
                        row.getCell(2).getStringCellValue(),
                        row.getCell(5).getStringCellValue(),
                        row.getCell(6).getStringCellValue());
            } else {
                throw new Exception();
            }
        }
    }

    private void parseXLS(MultipartFile file) throws Exception {
        InputStream inputStream = file.getInputStream();
        HSSFWorkbook workBook = new HSSFWorkbook(inputStream);
        HSSFSheet sheet = workBook.getSheetAt(0);
        for (Row row : sheet) {
            if (row.getRowNum() == 0) {
                continue;
            }
            if (isCorrectColumnCount(row.getLastCellNum()) && isCorrectCategory(row.getCell(3).getStringCellValue()) && isCorrectCostCellType(row.getCell(7).getCellType())) {
                int cost = (int) row.getCell(7).getNumericCellValue();
                Item item = new Item(
                        row.getCell(0).getStringCellValue(),
                        row.getCell(4).getStringCellValue(),
                        row.getCell(3).getStringCellValue(),
                        new BigInteger(String.valueOf(cost)),
                        1);
                itemService.saveItem(
                        item,
                        row.getCell(1).getStringCellValue(),
                        row.getCell(2).getStringCellValue(),
                        row.getCell(5).getStringCellValue(),
                        row.getCell(6).getStringCellValue());
            } else {
                throw new Exception();
            }
        }
    }

    private boolean isCorrectColumnCount(int count) {
        return count == Constant.COLUMN_NUMBER_IN_EXCEL_DOC;
    }

    private boolean isCorrectCategory(String category) {
        return Constant.CATEGORIES.containsKey(category);
    }

    private boolean isCorrectCostCellType(int cellType) {
        return cellType == XSSFCell.CELL_TYPE_NUMERIC;
    }

    @RequestMapping(value = "/admin/itemsToDoc", method = RequestMethod.POST)
    public void itemsToDoc(HttpServletResponse response){
        try{
            Workbook workbook = new XSSFWorkbook(); // new HSSFWorkbook() for generating `.xls` file
        /* CreationHelper helps us create instances for various things like DataFormat,
           Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way */
            CreationHelper createHelper = workbook.getCreationHelper();

            // Create a Sheet
            Sheet sheet = workbook.createSheet("Items");

            // Create a Row
            Row headerRow = sheet.createRow(0);
            // Creating cells
            for(int i = 0; i < Constant.COLUMNS_FOR_EXCEL.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(Constant.COLUMNS_FOR_EXCEL[i]);
            }
            // Create Other rows and cells with employees data
            int rowNum = 1;
            List<Item>itemsEn = itemService.getAllItems(new Locale("en"));
            List<Item>itemsRu = itemService.getAllItems(new Locale("ru"));
            List<Item>itemsUk = itemService.getAllItems(new Locale("uk"));

            for(Item item: itemsEn) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0)
                        .setCellValue(item.getProductName());
                row.createCell(1)
                        .setCellValue(getLocItemName(itemsRu, item.getProductId()));
                row.createCell(2)
                        .setCellValue(getLocItemName(itemsUk, item.getProductId()));
                row.createCell(3)
                        .setCellValue(item.getProductCategory());
                row.createCell(4)
                        .setCellValue(item.getProductDescription());
                row.createCell(5)
                        .setCellValue(getLocItemDescription(itemsRu, item.getProductId()));
                row.createCell(6)
                        .setCellValue(getLocItemDescription(itemsUk, item.getProductId()));
                row.createCell(7)
                        .setCellValue(Double.valueOf(item.getProductCost().toString()));
            }

            // Resize all columns to fit the content size
            for(int i = 0; i < Constant.COLUMNS_FOR_EXCEL.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // Write the output to a file
            String webappRoot = servletContext.getRealPath("/");
            try (FileOutputStream outputStream = new FileOutputStream(webappRoot + File.separator + "items_good_food.xlsx")) {
                workbook.write(outputStream);
            }catch (Exception e){
                e.printStackTrace();
            }

            /*FileOutputStream fileOut = new FileOutputStream("items-good-food.xlsx");
            workbook.write(fileOut);*/
            //fileOut.close();
            // Closing the workbook
            workbook.close();
        }catch (Exception e){
        }

        try {
            String webappRoot = servletContext.getRealPath("/");
            String file = webappRoot + File.separator + "items_good_food.xlsx";
            InputStream is = new BufferedInputStream(new FileInputStream(file));
            response.setContentType("application/xlsx");
            response.setHeader("Content-Disposition", "attachment; filename=\"items_good_food.xlsx\"");
            org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());
            response.flushBuffer();
        } catch (IOException ex) {
//            log.info("Error writing file to output stream. Filename was '{}'", fileName, ex);
            throw new RuntimeException("IOError writing file to output stream");
        }
    }

    private String getLocItemName(List<Item> locItems, BigInteger itemId){
        for (Item item : locItems){
            if (item.getProductId().equals(itemId)){
                return item.getProductName();
            }
        }
        return null;
    }

    private String getLocItemDescription(List<Item> locItems, BigInteger itemId){
        for (Item item : locItems){
            if (item.getProductId().equals(itemId)){
                return item.getProductDescription();
            }
        }
        return null;
    }

    @RequestMapping(value = "/updateItem", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
    public ModelAndView updateItem(
            @RequestParam("itemId") BigInteger itemId,
            @RequestParam("category") String category,
            @RequestParam("nameEn") String nameEn,
            @RequestParam("nameRu") String nameRu,
            @RequestParam("nameUk") String nameUk,
            @RequestParam("descriptionEn") String descriptionEn,
            @RequestParam("descriptionRu") String descriptionRu,
            @RequestParam("descriptionUk") String descriptionUk,
            @RequestParam("cost") BigInteger cost,
            @RequestParam("image") MultipartFile image
    ) {
        ModelAndView model = new ModelAndView();
        model.setViewName("redirect:/admin");
        String imageNameForSave = "";
        if (!image.getOriginalFilename().equals("")){
            String webappRoot = servletContext.getRealPath("/");
            String relativeFolder = File.separator + ".." + File.separator + ".." + File.separator
                    + "src" + File.separator + "main" + File.separator + "webapp" + File.separator + Constant.ITEM_IMAGE_PATH + category.toLowerCase() + File.separator;
            String itemImageName = itemId + image.getOriginalFilename();
            String filename = webappRoot + relativeFolder + itemImageName;
            try {
                byte[] bytes = image.getBytes();
                Path path = Paths.get(filename);
                Files.write(path, bytes);
                Path targetPath = Paths.get(webappRoot + Constant.ITEM_IMAGE_PATH + category.toLowerCase() + File.separator + itemImageName);
                Files.write(targetPath, bytes);
                imageNameForSave = (File.separator + Constant.ITEM_IMAGE_PATH + category.toLowerCase() + File.separator + itemImageName).replace('\\', '/');
            } catch (IOException e) {
                e.printStackTrace();
                return model;
            }
        }else{
            imageNameForSave = itemService.getItemById(itemId, new Locale("en")).getProductImage();
        }
        Item item = new Item(itemId, nameEn, descriptionEn, category, cost, imageNameForSave, 1);
        itemService.updateItem(item, nameRu, nameUk, descriptionRu, descriptionUk);

        return model;
    }

    @RequestMapping(value = "/getNumOfOrders", method = RequestMethod.GET)
    public @ResponseBody
    String getNumOfOrders(@RequestParam BigInteger courierId, Locale locale)
    {
        List<Order> orders = orderService.getNotCompletedOrdersByCourierId(courierId, locale);
        return String.valueOf(orders.size());
    }
    @RequestMapping(value = "/setCourier", method = RequestMethod.GET)
    public @ResponseBody
    void setCourier(@RequestParam BigInteger courierId, @RequestParam BigInteger orderId)
    {
        Order order = orderService.getOrderById(orderId);
        if (order != null){
            orderService.setCourier(orderId, courierId);
        }
    }

}
