INSERT INTO public."ENUMS"(
	"ENUM_ID", "NAME", "ENUM_TYPE_ID")
	VALUES (811, 'Pizza', 702),
    (812, 'Sushi', 702),
    (813, 'Burgers', 702),
    (814, 'Salads', 702),
    (815,'Snacks',702),
    (816,'Dessert',702),
    (817, 'Beverages', 702);

UPDATE public."ATTRIBUTES"
	SET "ATTR_TYPE_ID"=55
	WHERE "ATTR_ID"=408;
INSERT INTO public."OBJECTS"(
"OBJECT_ID", 	"NAME", "PARENT_ID", "OBJECT_TYPE_ID")
	VALUES (1590313064830067771,'Meat Pizza', 0, 306),
	(1590313064686412860, 'European Pizza', 0, 306),
	(1590313064687461437, 'Pizza Barbecue', 0, 306),
	(1590313064687461438, 'Pizza Diablo', 0, 306),
	(1590313064687461439, 'Peperoni', 0, 306),
	(1590313064687461440, 'Vegetarian Pizza', 0, 306),   
	(1590313064687461441, 'California', 0, 306),
	(1590313064687461442, 'Philadelphia', 0, 306),
(1590313064687461443, 'Chicken and cheese', 0, 306),
	(1590313064687461444, 'Creamy shrimp', 0, 306),
	(1590313064687461445, 'Chicken spice', 0, 306),      
	(1590313064687461446, 'Burger New York', 0, 306),
	(1590313064687461447, 'The Texas Burger', 0, 306),
	(1590313064687461448, 'Burger with Salmon', 0, 306),
		(1590313064687461449, 'Burger BBQ', 0, 306),
	(1590313064687461450, 'Cheeseburger', 0, 306),
	(1590313064687461451,'Mexican burger', 0, 306),
	(1590313064687461452,'Fish burger', 0, 306), 
	(1590313064687461453,'Black burger', 0, 306),
	(1590313064687461454,'Caesar salad', 0, 306),
	(1590313064687461455,'Salad with veal and greens', 0, 306),
	(1590313064687461456,'Vegetable salad with spinach', 0, 306),
	(1590313064687461457,'Greek salad', 0, 306),
	(1590313064687461458,'Tuscany', 0, 306),
	(1590313064687461459,'Nuggets with chicken', 0, 306),
	(1590313064687461460,'Toasted garlic', 0, 306),
	(1590313064687461461,'French fries', 0, 306),
	(1590313064687461462,'Rings of squids', 0, 306),
	(1590313064687461463,'Nuggets cheese', 0, 306),
	(1590313064687461464,'Cheesecake', 0, 306),
	(1590313064687461465,'Esterházy Cake', 0, 306),
	(1590313064688510042 ,'Tiramisu', 0, 306),
	(1590313064688510043,'Cheese pancake', 0, 306),
	(1590313064688510044 ,'Fanta', 0, 306),
	(1590313064688510045, 'Sprite', 0, 306),
	(1590313064688510046, 'Orange juice', 0, 306),
	(1590313064688510047, 'Schweppes', 0, 306),
	(1590313064688510048, 'Coca-Cola', 0, 306);
INSERT INTO public."PARAMETERS"(
	"OBJECT_ID", "ATTR_ID", "TEXT_VALUE", "DATE_VALUE", "REFERENCE_VALUE", "ENUM_VALUE")
	VALUES (1590313064830067771, 413, 'Meat Pizza', null, 0, 0),   
	(1590313064830067771, 408, null, null, 0, 811),
	(1590313064830067771, 414, 'Chicken fillet, ham, sausages, tomatoes, gherkins, mozzarella cheese, red onions.', null, 0, 0),
	(1590313064830067771, 409, '450', null, 0, 0),

	(1590313064686412860, 413, 'European Pizza', null, 0, 0),   
	(1590313064686412860, 408, null, null, 0, 811),
	(1590313064686412860, 414, 'Ham, ham, chicken fillet, Servelat, Bulgarian pepper, Mozzarella cheese, Tomato sauce, "Maggiore" sauce.', null, 0, 0),
	(1590313064686412860, 409, '420', null, 0, 0),

	(1590313064687461437, 413, 'Pizza Barbecue', null, 0, 0),   
	(1590313064687461437, 408, null, null, 0, 811),
	(1590313064687461437, 414, 'City Pizza sauce, Mozzarella cheese, chicken, bacon, champignons, Bulgarian pepper, red onion, French onion, barbecue sauce.', null, 0, 0),
	(1590313064687461437, 409, '490', null, 0, 0),

	(1590313064687461438, 413, 'Pizza Diablo', null, 0, 0),   
	(1590313064687461438, 408, null, null, 0, 811),
	(1590313064687461438, 414, 'Spicy pizza with chicken fillet, pepperoni sausages, tomatoes, red onion, spicy herbs, mozzarella cheese and hot pepper.', null, 0, 0),
	(1590313064687461438, 409, '350', null, 0, 0),

	(1590313064687461439, 413, 'Peperoni', null, 0, 0),   
	(1590313064687461439, 408, null, null, 0, 811),
	(1590313064687461439, 414, 'Spicy sausages of pepperoni, mozzarella cheese.', null, 0, 0),
	(1590313064687461439, 409, '340', null, 0, 0),

	(1590313064687461440, 413, 'Vegetarian Pizza', null, 0, 0),   
	(1590313064687461440, 408, null, null, 0, 811),
	(1590313064687461440, 414, 'Cheese "Mozzarella", sweet peppers, onions, tomatoes, champignons, olives.', null, 0, 0),
	(1590313064687461440, 409, '300', null, 0, 0),

	(1590313064687461441, 413, 'California', null, 0, 0),   
	(1590313064687461441, 408, null, null, 0, 812),
	(1590313064687461441, 414, 'Snow crab, Masaga caviar, cream cheese, cucumber, sesame.', null, 0, 0),
	(1590313064687461441, 409, '120', null, 0, 0),

	(1590313064687461442, 413, 'Philadelphia', null, 0, 0),   
	(1590313064687461442, 408, null, null, 0, 812),
	(1590313064687461442, 414, 'Salmon, cream cheese, cucumber.', null, 0, 0),
	(1590313064687461442, 409, '190', null, 0, 0),
	
	(1590313064687461443, 413, 'Chicken and cheese', null, 0, 0),   
	(1590313064687461443, 408, null, null, 0, 812),
	(1590313064687461443, 414, 'Chicken fried, steamed vegetables, cream cheese, marbled cheese, sesame.', null, 0, 0),
	(1590313064687461443, 409, '145', null, 0, 0),
	
	(1590313064687461444, 413, 'Creamy shrimp', null, 0, 0),   
	(1590313064687461444, 408, null, null, 0, 812),
	(1590313064687461444, 414, 'Cream cheese, snow crab, Masaga caviar, cucumber, shrimp.', null, 0, 0),
	(1590313064687461444, 409, '125', null, 0, 0),
	
	(1590313064687461445, 413, 'Chicken spice', null, 0, 0),   
	(1590313064687461445, 408, null, null, 0, 812),
	(1590313064687461445, 414, 'Chicken Teriyaki, cream cheese, Spice sauce, Bulgarian pepper, Chinese cabbage, green onions.', null, 0, 0),
	(1590313064687461445, 409, '130', null, 0, 0),

	(1590313064687461446, 413, 'Burger New York', null, 0, 0),   
	(1590313064687461446, 408, null, null, 0, 813),
	(1590313064687461446, 414, '100% marble beef Black Angus, a special piquant Grill sauce, a slice of Maasdam cheese, an iceberg salad, fresh tomatoes, onions, pickled gherkins, a brioche bun.', null, 0, 0),
	(1590313064687461446, 409, '470', null, 0, 0),
	
	(1590313064687461447, 413, 'The Texas Burger', null, 0, 0),   
	(1590313064687461447, 408, null, null, 0, 813),
	(1590313064687461447, 414, 'Grilled meat grilled meat with Guacamole, piquant sauce Pico De Gallo with Chipotle pepper and Cheddar cheese on fresh Prentzel Bun.', null, 0, 0),
	(1590313064687461447, 409, '450', null, 0, 0),

	(1590313064687461448, 413, 'Burger with Salmon', null, 0, 0),   
	(1590313064687461448, 408, null, null, 0, 813),
	(1590313064687461448, 414, 'Chopped salmon, seasoned with soy sauce and mayonnaise, with the addition of sesame seeds, is served on a brioche brioche.', null, 0, 0),
	(1590313064687461448, 409, '500', null, 0, 0),

	(1590313064687461449, 413, 'Burger BBQ', null, 0, 0),   
	(1590313064687461449, 408, null, null, 0, 813),
	(1590313064687461449, 414, 'American burger with a marble beef cutlet, onion rings and bbq sauce.', null, 0, 0),
	(1590313064687461449, 409, '360', null, 0, 0),
	
	(1590313064687461450, 413, 'Cheeseburger', null, 0, 0),   
	(1590313064687461450, 408, null, null, 0, 813),
	(1590313064687461450, 414, 'Cutlet from marble beef, cheddar cheese, romano lettuce, tomatoes, red onions, pickles, mayonnaise-mustard sauce.', null, 0, 0),
	(1590313064687461450, 409, '300', null, 0, 0),

	(1590313064687461451, 413, 'Mexican burger', null, 0, 0),   
	(1590313064687461451, 408, null, null, 0, 813),
	(1590313064687461451, 414, 'Spicy juicy burger with jalapeno pepper and chopped beef patty with roasted bacon, fresh tomatoes, iceberg salad, crispy pickled cucumbers and barbecue sauce in a brioche bun.', null, 0, 0),
	(1590313064687461451, 409, '520', null, 0, 0),

	(1590313064687461452, 413, 'Fish burger', null, 0, 0),   
	(1590313064687461452, 408, null, null, 0, 813),
	(1590313064687461452, 414, 'Fillet of salmon, fresh spinach, tomatoes, red onion, blue-cheese sauce and tar-tar.', null, 0, 0),
	(1590313064687461452, 409, '430', null, 0, 0),

	(1590313064687461453, 413, 'Black burger', null, 0, 0),   
	(1590313064687461453, 408, null, null, 0, 813),
	(1590313064687461453, 414, 'An example of an incredibly juicy filling thanks to a cutlet from marble beef bacon poached egg and double cheddar cheese and also the addition of home-made mayonnaise and ketchup red wine. Vegetable base consists of lightly salted cucumbers spicy ruccola leaves romaine lettuce and crunchy red onion rings. Served with French fries and cole slou.', null, 0, 0),
	(1590313064687461453, 409, '750', null, 0, 0),

	(1590313064687461454, 413, 'Caesar salad', null, 0, 0),   
	(1590313064687461454, 408, null, null, 0, 814),
	(1590313064687461454, 414, 'Lettuce leaves in the original dressing with roasted chicken fillet, parmesan cheese, fresh tomatoes and crispy croutons.', null, 0, 0),
	(1590313064687461454, 409, '195', null, 0, 0),

	(1590313064687461455, 413, 'Salad with veal and greens', null, 0, 0),   
	(1590313064687461455, 408, null, null, 0, 814),
	(1590313064687461455, 414, 'Fried calf meat, cucumbers, tomatoes, eggplant, arugula, cilantro, red onion, honey-balsamic sauce.', null, 0, 0),
	(1590313064687461455, 409, '490', null, 0, 0),

	(1590313064687461456, 413, 'Vegetable salad with spinach', null, 0, 0),   
	(1590313064687461456, 408, null, null, 0, 814),
	(1590313064687461456, 414, 'Spinach, arugula, Baku tomatoes, cucumbers, bell peppers, dill, Narsharab sauce, olive oil.', null, 0, 0),
	(1590313064687461456, 409, '590', null, 0, 0),

	(1590313064687461457, 413, 'Greek salad', null, 0, 0),   
	(1590313064687461457, 408, null, null, 0, 814),
	(1590313064687461457, 414, 'Mix of fresh vegetables, Iceberg lettuce, olives and soft cheese with spicy notes of basil and sesame.', null, 0, 0),
	(1590313064687461457, 409, '175', null, 0, 0),

	(1590313064687461458, 413, 'Tuscany', null, 0, 0),   
	(1590313064687461458, 408, null, null, 0, 814),
	(1590313064687461458, 414, 'Warm salad with pork and fresh vegetables in the original dressing.', null, 0, 0),
	(1590313064687461458, 409, '245', null, 0, 0),

	(1590313064687461459, 413, 'Nuggets with chicken', null, 0, 0),   
	(1590313064687461459, 408, null, null, 0, 815),
	(1590313064687461459, 414, 'Deep-fried chicken fillet.', null, 0, 0),
	(1590313064687461459, 409, '175', null, 0, 0),

	(1590313064687461460, 413, 'Toasted garlic', null, 0, 0),   
	(1590313064687461460, 408, null, null, 0, 815),
	(1590313064687461460, 414, 'Aromatic toast with garlic and cream sauce.', null, 0, 0),
	(1590313064687461460, 409, '95', null, 0, 0),

	(1590313064687461461, 413, 'French fries', null, 0, 0),   
	(1590313064687461461, 408, null, null, 0, 815),
	(1590313064687461461, 414, 'Crispy potato straw.', null, 0, 0),
	(1590313064687461461, 409, '95', null, 0, 0),

	(1590313064687461462, 413, 'Rings of squids', null, 0, 0),   
	(1590313064687461462, 408, null, null, 0, 815),
	(1590313064687461462, 414, 'Appetizing crispy squid rings. Served on Iceberg salad with Ischia sauce.', null, 0, 0),
	(1590313064687461462, 409, '155', null, 0, 0),

	(1590313064687461463, 413, 'Nuggets cheese', null, 0, 0),   
	(1590313064687461463, 408, null, null, 0, 815),
	(1590313064687461463, 414, 'Crispy cheese sticks with Ischia sauce.', null, 0, 0),
	(1590313064687461463, 409, '160', null, 0, 0),

	(1590313064687461464, 413, 'Cheesecake', null, 0, 0),   
	(1590313064687461464, 408, null, null, 0, 816),
	(1590313064687461464, 414, 'Cake of cream cheese Philadelphia.', null, 0, 0),
	(1590313064687461464, 409, '175', null, 0, 0),

	(1590313064687461465, 413, 'Esterh?zy Cake', null, 0, 0),   
	(1590313064687461465, 408, null, null, 0, 816),
	(1590313064687461465, 414, 'Dessert of thin air cakes, hazelnuts and walnut praline.', null, 0, 0),
	(1590313064687461465, 409, '195', null, 0, 0),

	(1590313064688510042, 413, 'Tiramisu', null, 0, 0),   
	(1590313064688510042, 408, null, null, 0, 816),
	(1590313064688510042, 414, 'A classic dessert made of coffee biscuit impregnated with liqueur and espresso, under a layer of airy cheese cream.', null, 0, 0),
	(1590313064688510042, 409, '185', null, 0, 0),

	(1590313064688510043, 413, 'Cheese pancake', null, 0, 0),   
	(1590313064688510043, 408, null, null, 0, 816),
	(1590313064688510043, 414, 'Cheesecakes with fruit jam and strawberry sauce.', null, 0, 0),
	(1590313064688510043, 409, '135', null, 0, 0),

	(1590313064688510044, 413, 'Fanta', null, 0, 0),   
	(1590313064688510044, 408, null, null, 0, 817),
	(1590313064688510044, 414, 'Fanta 250 ml', null, 0, 0),
	(1590313064688510044, 409, '137', null, 0, 0),

	(1590313064688510045, 413, 'Sprite', null, 0, 0),   
	(1590313064688510045, 408, null, null, 0, 817),
	(1590313064688510045, 414, 'Sprite 250 ml', null, 0, 0),
	(1590313064688510045, 409, '137', null, 0, 0),

	(1590313064688510046, 413, 'Orange juice', null, 0, 0),   
	(1590313064688510046, 408, null, null, 0, 817),
	(1590313064688510046, 414, 'Orange juice freshly squeezed 500 ml', null, 0, 0),
	(1590313064688510046, 409, '490', null, 0, 0),

	(1590313064688510047, 413, 'Schweppes', null, 0, 0),   
	(1590313064688510047, 408, null, null, 0, 817),
	(1590313064688510047, 414, 'Schweppes 250 ml', null, 0, 0),
	(1590313064688510047, 409, '140', null, 0, 0),

	(1590313064688510048, 413, 'Coca-Cola', null, 0, 0),   
	(1590313064688510048, 408, null, null, 0, 817),
	(1590313064688510048, 414, 'Coca-Cola 1000 ml', null, 0, 0),
	(1590313064688510048, 409, '170', null, 0, 0);

	INSERT INTO public."OBJECTS"(
"OBJECT_ID",	"NAME",  "PARENT_ID", "OBJECT_TYPE_ID")
	VALUES (1590323698616493153,'/resources/img/pizza/pizza_meat.jpg', 0, 301),    
(1590348921198863458,'/resources/img/pizza/pizza_europe.jpg', 0, 301),   
(1590348921347761251,'/resources/img/pizza/pizza_bbq.jpg', 0, 301),  
(1590348921347761252,'/resources/img/pizza/pizza_diablo.jpg', 0, 301),
(1590348921347761253,'/resources/img/pizza/pizza_peperoni.jpg', 0, 301),
(1590348921347761254,'/resources/img/pizza/pizza_vegan.jpg', 0, 301);  

	INSERT INTO public."PARAMETERS"(
	"OBJECT_ID", "ATTR_ID", "TEXT_VALUE", "DATE_VALUE", "REFERENCE_VALUE", "ENUM_VALUE")
	VALUES (1590313064830067771, 418, null, null, 1590323698616493153, 0),
(1590313064686412860, 418, null, null, 1590348921198863458, 0),
(1590313064687461437, 418, null, null, 1590348921347761251, 0),
(1590313064687461438, 418, null, null, 1590348921347761252, 0),
(1590313064687461439, 418, null, null, 1590348921347761253, 0),
(1590313064687461440, 418, null, null, 1590348921347761254, 0);

	INSERT INTO public."OBJECTS"(
"OBJECT_ID",		"NAME",  "PARENT_ID", "OBJECT_TYPE_ID")
	VALUES (1590349951498183783,'/resources/img/sushi/sushi_california.png', 0, 301), 
(1590349951498183784,'/resources/img/sushi/sushi_creamy_shrimp.png', 0, 301), 
(1590349951498183785,'/resources/img/sushi/sushi_chicken_and_cheese.png', 0, 301),  
(1590349951498183786,'/resources/img/sushi/sushi_chicken_spice.png', 0, 301),  
(1590349951498183787,'/resources/img/sushi/sushi_filadelfia.png', 0, 301);  

INSERT INTO public."PARAMETERS"(
	"OBJECT_ID", "ATTR_ID", "TEXT_VALUE", "DATE_VALUE", "REFERENCE_VALUE", "ENUM_VALUE")
	VALUES (1590313064687461441, 418, null, null, 1590349951498183783, 0),
(1590313064687461444, 418, null, null, 1590349951498183784, 0),
(1590313064687461443, 418, null, null, 1590349951498183785, 0),
(1590313064687461445, 418, null, null, 1590349951498183786, 0),
(1590313064687461442, 418, null, null, 1590349951498183787, 0);

	INSERT INTO public."OBJECTS"(
"OBJECT_ID",		"NAME",  "PARENT_ID", "OBJECT_TYPE_ID")
	VALUES 
(1590350533457863788,'/resources/img/burgers/burger_new_york.jpg', 0, 301), 
(1590350533458912365,'/resources/img/burgers/burger_texas.jpg', 0, 301), 
(1590350533458912366,'/resources/img/burgers/burger_with_salmon.jpg', 0, 301), 
(1590350533458912367,'/resources/img/burgers/burger_bbq.jpg', 0, 301), 
(1590350533458912368,'/resources/img/burgers/cheeseburger.jpg', 0, 301), 
(1590350533458912369,'/resources/img/burgers/burger_mexica.jpg', 0, 301),
(1590350533458912370,'/resources/img/burgers/fish_burger.jpeg', 0, 301), 
(1590350533458912371,'/resources/img/burgers/black_burger.jpg', 0, 301); 

INSERT INTO public."PARAMETERS"(
	"OBJECT_ID", "ATTR_ID", "TEXT_VALUE", "DATE_VALUE", "REFERENCE_VALUE", "ENUM_VALUE")
	VALUES (1590313064687461446, 418, null, null, 1590350533457863788, 0),
(1590313064687461447, 418, null, null, 1590350533458912365, 0),
(1590313064687461448, 418, null, null, 1590350533458912366, 0),
(1590313064687461449, 418, null, null, 1590350533458912367, 0),
(1590313064687461450, 418, null, null, 1590350533458912368, 0),
(1590313064687461451, 418, null, null, 1590350533458912369, 0),
(1590313064687461452, 418, null, null, 1590350533458912370, 0),
(1590313064687461453, 418, null, null, 1590350533458912371, 0);
	

	INSERT INTO public."OBJECTS"(
"OBJECT_ID",		"NAME",  "PARENT_ID", "OBJECT_TYPE_ID")
	VALUES (1590351152602145908,'/resources/img/salads/salad_cesar.jpg', 0, 301), 
(1590351152602145909,'/resources/img/salads/salad_with_veal_greens.jpg', 0, 301), 
(1590351152602145910,'/resources/img/salads/salad_with_spinach.jpg', 0, 301), 
(1590351152602145911,'/resources/img/salads/salad_greek.jpg', 0, 301), 
(1590351152602145912,'/resources/img/salads/salad_toscana.jpg', 0, 301); 


INSERT INTO public."PARAMETERS"(
	"OBJECT_ID", "ATTR_ID", "TEXT_VALUE", "DATE_VALUE", "REFERENCE_VALUE", "ENUM_VALUE")
	VALUES (1590313064687461454, 418, null, null, 1590351152602145908, 0),
(1590313064687461455, 418, null, null, 1590351152602145909, 0),
(1590313064687461456, 418, null, null, 1590351152602145910, 0),
(1590313064687461457, 418, null, null, 1590351152602145911, 0),
(1590313064687461458, 418, null, null, 1590351152602145912, 0);


	INSERT INTO public."OBJECTS"(
"OBJECT_ID",		"NAME",  "PARENT_ID", "OBJECT_TYPE_ID")
	VALUES (1590351706102424697,'/resources/img/snacks/schack_chicken_naggets.jpg', 0, 301), 
(1590351706102424698,'/resources/img/snacks/snack_toasted_garlic.jpg', 0, 301), 
(1590351706102424699,'/resources/img/snacks/snack_french_fries.jpg', 0, 301),  
(1590351706102424700,'/resources/img/snacks/snack_rings_squids.jpg', 0, 301), 
(1590351706102424701,'/resources/img/snacks/snack_cheese_nag.jpg', 0, 301); 


INSERT INTO public."PARAMETERS"(
	"OBJECT_ID", "ATTR_ID", "TEXT_VALUE", "DATE_VALUE", "REFERENCE_VALUE", "ENUM_VALUE")
	VALUES (1590313064687461459, 418, null, null, 1590351706102424697, 0),
(1590313064687461460, 418, null, null, 1590351706102424698, 0),
(1590313064687461461, 418, null, null, 1590351706102424699, 0),
(1590313064687461462, 418, null, null, 1590351706102424700, 0),
(1590313064687461463, 418, null, null, 1590351706102424701, 0);

	INSERT INTO public."OBJECTS"(
"OBJECT_ID",		"NAME",  "PARENT_ID", "OBJECT_TYPE_ID")
	VALUES (1590352234943341702,'/resources/img/dessert/desert_cheesecake.jpg', 0, 301), 
(1590352234943341703,'/resources/img/dessert/desert_esterházy.jpg', 0, 301), 
(1590352234943341704,'/resources/img/dessert/desert_tiramisu.jpg', 0, 301), 
(1590352234943341705,'/resources/img/dessert/desert_cheese_pancake.jpg', 0, 301); 


INSERT INTO public."PARAMETERS"(
	"OBJECT_ID", "ATTR_ID", "TEXT_VALUE", "DATE_VALUE", "REFERENCE_VALUE", "ENUM_VALUE")
	VALUES (1590313064687461464, 418, null, null, 1590352234943341702, 0),
(1590313064687461465, 418, null, null, 1590352234943341703, 0),
(1590313064688510042, 418, null, null, 1590352234943341704, 0),
(1590313064688510043, 418, null, null, 1590352234943341705, 0);


	INSERT INTO public."OBJECTS"(
"OBJECT_ID",		"NAME",  "PARENT_ID", "OBJECT_TYPE_ID")
	VALUES (1590352560633144458,'/resources/img/beverages/beverages_fanta.png', 0, 301), 
(1590352560633144459,'/resources/img/beverages/beverages_sprite.png', 0, 301),  
(1590352560633144460,'/resources/img/beverages/beverages_orange_juce.png', 0, 301), 
(1590352560633144461,'/resources/img/beverages/beverages_schweps.png', 0, 301), 
(1590352560633144462,'/resources/img/beverages/beverages_cola.png', 0, 301); 


INSERT INTO public."PARAMETERS"(
	"OBJECT_ID", "ATTR_ID", "TEXT_VALUE", "DATE_VALUE", "REFERENCE_VALUE", "ENUM_VALUE")
	VALUES (1590313064688510044, 418, null, null, 1590352560633144458, 0),
(1590313064688510045, 418, null, null, 1590352560633144459, 0),
(1590313064688510046, 418, null, null, 1590352560633144460, 0),
(1590313064688510047, 418, null, null, 1590352560633144461, 0),
(1590313064688510048, 418, null, null, 1590352560633144462, 0);

UPDATE public."ATTRIBUTES"
	SET "ATTR_TYPE_ID"=56
	WHERE "ATTR_ID"=410;
