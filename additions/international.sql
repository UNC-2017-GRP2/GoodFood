CREATE TABLE "LOC_STRINGS" (
  "OBJECT_ID" bigint NOT NULL,
  "ATTR_ID" bigint NOT NULL,
  "LANG_ID" bigint NOT NULL,
  "LOC_TEXT_VALUE" text
);

CREATE TABLE "SOB_DRIVER" (
  "USER_ID" bigint NOT NULL,
  "SOB_ORD_ID" bigint NOT NULL
);

INSERT INTO public."LOC_STRINGS"(
	"OBJECT_ID", "ATTR_ID", "LANG_ID", "LOC_TEXT_VALUE")
	VALUES (1590313064830067771, 401, 81, 'Мясная пицца'),
	(1590313064830067771, 414, 81, 'Куриное филе, ветчина, колбаски, томаты, корнишоны, сыр моцарелла, красный лук'),

	(1590313064686412860, 401, 81, 'Пицца Европейская'),
	(1590313064686412860, 414, 81, 'Ветчина, куриное филе, сервелат, болгарский перец, сыр моцарелла, томатный соус, соус "Маггиори"'),

	(1590313064687461437, 401, 81, 'Пицца Барбекю'),
	(1590313064687461437, 414, 81, 'Соус Сити-Пицца, сыр моцарелла, мясо цыплёнка, бекон, шампиньоны, болгарский перец, красный лук, французский лук, союс барбекю'),

	(1590313064687461438, 401, 81, 'Пицца Дьябло'),
	(1590313064687461438, 414, 81, 'Куриное филе, пепперони, помидоры, красный лук, пряности, сыр моцарелла, острый перец'),

	(1590313064687461439, 401, 81, 'Пицца Пепперони'),
	(1590313064687461439, 414, 81, 'Острое пепперони, сыр моцарелла'),

	(1590313064687461440, 401, 81, 'Пицца Вегетарианская'),
	(1590313064687461440, 414, 81, 'Сыр моцарелла, сладкий перец, лук, помидоры, шампиньоны, оливки'),

	(1590313064687461441, 401, 81, 'Роллы Калифорния'),
	(1590313064687461441, 414, 81, 'Снежный краб, икра масаго, сливочный сыр, огурец, кунжут'),

	(1590313064687461442, 401, 81, 'Роллы Филадельфия'),
	(1590313064687461442, 414, 81, 'Лосось, сливочный сыр, огурец'),
	
	(1590313064687461443, 401, 81, 'Курица с сыром'),
	(1590313064687461443, 414, 81, 'Жареная курица, овощи на пару, сливочный сыр, мраморный сыр, кунжут'),
	
	(1590313064687461444, 401, 81, 'Сливочные креветки'),
	(1590313064687461444, 414, 81, 'Сливочный сыр, снежный краб, икра масаго, огурец, креветки'),

	(1590313064687461445, 401, 81, 'Острая курица'),
	(1590313064687461445, 414, 81, 'Курица Терияки, сливочный сыр, острый соус, болгарский перец, китайская капуста, зеленый лук'),
	
	(1590313064687461446, 401, 81, 'Бургер Нью-Йорк'),
	(1590313064687461446, 414, 81, '100% мраморная говядина черный Ангус, специальный пикантный Гриль-соус, ломтик сыра Маасдам, салат айсберг, свежие помидоры, лук, маринованные корнишоны, булочка бриош'),
	
	(1590313064687461447, 401, 81, 'Бургер Техасский'),
	(1590313064687461447, 414, 81, 'Мясо на гриле с Гуакамоле, пикантный соус Пико де Галло с перцем Чипотле и сыром Чеддер на свежей Булочке Prentzel'),

	(1590313064687461448, 401, 81, 'Бургер с лососем'),
	(1590313064687461448, 414, 81, 'Нарезанный лосось, приправленный соевым соусом и майонезом, с добавлением кунжута, подается на булочке бриош'),
	
	(1590313064687461449, 401, 81, 'Бургер Барбекю'),
	(1590313064687461449, 414, 81, 'Американский бургер с котлетой из мраморной говядины, луковыми кольцами и соусом барбекю'),

	(1590313064687461450, 401, 81, 'Чизбургер'),
	(1590313064687461450, 414, 81, 'Котлета из мраморной говядины, сыр чеддер, салат Романо, помидоры, красный лук, соленые огурцы, майонезно-горчичный соус'),

	(1590313064687461451, 401, 81, 'Бургер Мексиканский'),
	(1590313064687461451, 414, 81, 'Пикантный сочный бургер с перцем халапеньо и рубленой говяжьей котлетой с жареным беконом, свежими помидорами, салатом айсберг, хрустящими маринованными огурцами и соусом барбекю в булочке бриош'),

	(1590313064687461452, 401, 81, 'Фишбургер'),
	(1590313064687461452, 414, 81, 'Филе лосося, свежий шпинат, помидоры, красный лук, соус блю-чиз и тар-тар'),

	(1590313064687461453, 401, 81, 'Блэкбургер'),
	(1590313064687461453, 414, 81, 'Котлета из мраморного говяжьего бекона, яйцо-пашот, двойной сыр чеддер, домашний майонез и красного кетчупа. Овощная основа состоит из слабосоленых огурцов, пикантной рукколы, листьев салата романи и хрустящих красных луковых колец. Подается с картофелем фри и салатом Коул слоу'),

	(1590313064687461454, 401, 81, 'Салат Цезарь'),
	(1590313064687461454, 414, 81, 'Листья салата в оригинальной заправке с жареным куриным филе, сыром пармезан, свежими помидорами и хрустящими гренками'),

	(1590313064687461455, 401, 81, 'Салат с телятиной и зеленью'),
	(1590313064687461455, 414, 81, 'Жареное телячье мясо, огурцы, помидоры, баклажаны, руккола, кинза, красный лук, медово-бальзамический соус'),

	(1590313064687461456, 401, 81, 'Овощной салат со шпинатом'),
	(1590313064687461456, 414, 81, 'Шпинат, руккола, бакинские помидоры, огурцы, болгарский перец, укроп, соус Наршараб, оливковое масло'),

	(1590313064687461457, 401, 81, 'Салат Греческий'),
	(1590313064687461457, 414, 81, 'Смесь свежих овощей, салата Айсберг, оливок и мягкого сыра с пряными нотками базилика и кунжута'),

	(1590313064687461458, 401, 81, 'Тоскана'),
	(1590313064687461458, 414, 81, 'Теплый салат со свининой и свежими овощами в оригинальной заправке'),
	
	(1590313064687461459, 401, 81, 'Куриные наггетсы'),
	(1590313064687461459, 414, 81, 'Жареное куриное филе'),

	(1590313064687461460, 401, 81, 'Чесночные гренки'),
	(1590313064687461460, 414, 81, 'Ароматные гренки с чесноком и сливочным соусом'),

	(1590313064687461461, 401, 81, 'Картофель фри'),
	(1590313064687461461, 414, 81, 'Хрустящая картофельные палочки'),

	(1590313064687461462, 401, 81, 'Кольца кальмаров'),
	(1590313064687461462, 414, 81, 'Аппетитно хрустящие кольца кальмаров. Подается на салате Айсберг с соусом Искья'),

	(1590313064687461463, 401, 81, 'Сырные наггетсы'),
	(1590313064687461463, 414, 81, 'Хрустящие сырные палочки с соусом Искья'),
	
	(1590313064687461464, 401, 81, 'Чизкейк'),
	(1590313064687461464, 414, 81, 'Торт из сливочного сыра Филадельфия'),

	(1590313064687461465, 401, 81, 'Торт Эстерхази'),
	(1590313064687461465, 414, 81, 'Десерт из тонких воздушных пирожных, фундука и пралине грецкого ореха'),

	(1590313064688510042, 401, 81, 'Тирамису'),
	(1590313064688510042, 414, 81, 'Классический десерт из кофейного печенья, пропитанного ликером и эспрессо, под слоем воздушного сырного крема'),

	(1590313064688510043, 401, 81, 'Сырники'),
	(1590313064688510043, 414, 81, 'Подаются с фруктовым джемом и клубничным соусом'),

	(1590313064688510044, 401, 81, 'Фанта'),
	(1590313064688510044, 414, 81, '250 мл'),

	(1590313064688510045, 401, 81, 'Спрайт'),
	(1590313064688510045, 414, 81, '250 мл'),

	(1590313064688510046, 401, 81, 'Апельсиновый сок'),
	(1590313064688510046, 414, 81, 'Свежевыжатый апельсиновый сок, 500 мл'),

	(1590313064688510047, 401, 81, 'Швепс'),
	(1590313064688510047, 414, 81, '250, мл'),

	(1590313064688510048, 401, 81, 'Кока-Кола'),
	(1590313064688510048, 414, 81, '1000 мл');



INSERT INTO public."LOC_STRINGS"(
	"OBJECT_ID", "ATTR_ID", "LANG_ID", "LOC_TEXT_VALUE")
	VALUES (1590313064830067771, 401, 82, 'Мʼясна Піца'),
	(1590313064830067771, 414, 82, 'Куряче філе, шинка, сосиски, помідори, корнішони, сир моцарелла, червоний лук'),

	(1590313064686412860, 401, 82, 'Піца Європейська'),
	(1590313064686412860, 414, 82, 'Шинка, філе куряче, сервелат, болгарський перець, сир моцарелла, томатний соус, соус "Маггиоре"'),

	(1590313064687461437, 401, 82, 'Піца Барбекю'),
	(1590313064687461437, 414, 82, 'Соус Сіті-Піца, сир моцарелла, мʼясо курчати, бекон, печериці, болгарський перець, червоний лук, французький цибуля, соус барбекю'),

	(1590313064687461438, 401, 82, 'Піца Дьябло'),
	(1590313064687461438, 414, 82, 'Куряче філе, пепероні, помідори, червоний цибулю, прянощі, сир моцарелла, гострий перець'),

	(1590313064687461439, 401, 82, 'Піца Пепероні'),
	(1590313064687461439, 414, 82, 'Гостре пепероні, сир моцарелла'),

	(1590313064687461440, 401, 82, 'Вегетаріанська Піца'),
	(1590313064687461440, 414, 82, 'Сир моцарела, солодкий перець, цибуля, помідори, гриби, оливки'),

	(1590313064687461441, 401, 82, 'Роли Каліфорнія'),
	(1590313064687461441, 414, 82, 'Сніжний краб, ікра масаго, вершковий сир, огірок, кунжут'),

	(1590313064687461442, 401, 82, 'Роли Філадельфія'),
	(1590313064687461442, 414, 82, 'Лосось, вершковий сир, огірок'),

	(1590313064687461443, 401, 82, 'Курка з сиром'),
	(1590313064687461443, 414, 82, 'Смажена курка, овочі на пару, вершковий сир, сир мармуровий, кунжут'),

	(1590313064687461444, 401, 82, 'Вершкові креветки'),
	(1590313064687461444, 414, 82, 'Вершковий сир, сніжний краб, ікра масаго, огірок, креветки'),

	(1590313064687461445, 401, 82, 'Гостра курка'),
	(1590313064687461445, 414, 82, 'Курка Теріякі, вершковий сир, гострий соус, болгарський перець, китайська капуста, зелений лук'),

	(1590313064687461446, 401, 82, 'Бургер Нью-Йорк'),
	(1590313064687461446, 414, 82, '100% мармурова яловичина чорний Ангус, спеціальний пікантний Гриль-соус, скибочка сиру Маасдам, салат айсберг, свіжі помідори, цибуля, мариновані корнішони, булочка бріош'),

	(1590313064687461447, 401, 82, 'Бургер Техаський'),
	(1590313064687461447, 414, 82, 'Мʼясо на грилі з Гуакамоле, пікантний соус Піко де Галло з перцем Чіпотле і сиром Чеддер зі свіжою булочкою Prentzel'),
	
	(1590313064687461448, 401, 82, 'Бургер з лососем'),
	(1590313064687461448, 414, 82, 'Нарізаний лосось, приправлений соєвим соусом і майонезом, з додаванням кунжуту, подається на булочці бріош'),
	
	(1590313064687461449, 401, 82, 'Бургер Барбекю'),
	(1590313064687461449, 414, 82, 'Американський бургер з котлетою з мармурової яловичини, цибульними кільцями і соусом барбекю'),

	(1590313064687461450, 401, 82, 'Чізбургер'),
	(1590313064687461450, 414, 82, 'Котлета з мармурової яловичини, сир чеддер, салат Романо, помідори, червоний цибулю, солоні огірки, майонезно-гірчичний соус'),

	(1590313064687461451, 401, 82, 'Бургер Мексиканський'),
	(1590313064687461451, 414, 82, 'Пікантний соковитий бургер з перцем халапеньо і рубаною яловичої котлетою зі смаженим беконом, свіжими помідорами, салатом айсберг, хрусткими маринованими огірками і соусом барбекю в булочці бріош'),

	(1590313064687461452, 401, 82, 'Фишбургер'),
	(1590313064687461452, 414, 82, 'Філе лосося, свіжий шпинат, помідори, червоний цибуля, соус блю-чіз і тар-тар'),

	(1590313064687461453, 401, 82, 'Блэкбургер'),
	(1590313064687461453, 414, 82, 'Котлета з мармурового яловичого бекону, яйце-пашот, подвійний сир чеддер, домашній майонез і червоного кетчупу. Овочева основа складається з слабосоленых огірків, пікантною рукколи, листя салату романі і хрустких червоних цибулевих кілець. Подається з картоплею фрі і салатом Коул слоу'),

	(1590313064687461454, 401, 82, 'Салат Цезар'),
	(1590313064687461454, 414, 82, 'Листя салату в оригінальній заправці з смаженою курячим філе, сиром пармезан, свіжими помідорами і хрусткими грінками'),

	(1590313064687461455, 401, 82, 'Салат з телятиною і зеленню'),
	(1590313064687461455, 414, 82, 'Смажене теляче мʼясо, огірки, помідори, баклажани, рукола, кінза, червоний лук, медово-бальзамічний соус'),
	
	(1590313064687461456, 401, 82, 'Овочевий салат зі шпинатом'),
	(1590313064687461456, 414, 82, 'Шпинат, рукола, бакинські помідори, огірки, болгарський перець, кріп, соус Наршараб, оливкова олія'),
	
	(1590313064687461457, 401, 82, 'Салат Грецький'),
	(1590313064687461457, 414, 82, 'Суміш свіжих овочів, салату Айсберг, оливок і мʼякого сиру з пряними нотками базиліка і кунжуту'),
	
	(1590313064687461458, 401, 82, 'Тоскана'),
	(1590313064687461458, 414, 82, 'Теплий салат зі свининою і свіжими овочами в оригінальній заправці'),

	(1590313064687461459, 401, 82, 'Курячі нагетси'),
	(1590313064687461459, 414, 82, 'Смажене куряче філе'),
	
	(1590313064687461460, 401, 82, 'Часникові грінки'),
	(1590313064687461460, 414, 82, 'Ароматні грінки з часником і вершковим соусом'),

	(1590313064687461461, 401, 82, 'Картопля фрі'),
	(1590313064687461461, 414, 82, 'Хрустка картопляні палички'),

	(1590313064687461462, 401, 82, 'Кільця кальмарів'),
	(1590313064687461462, 414, 82, 'Апетитно хрусткі кільця кальмарів. Подається салат Айсберг з соусом Іскья'),

	(1590313064687461463, 401, 82, 'Сирні нагетси'),
	(1590313064687461463, 414, 82, 'Хрусткі сирні палички з соусом Іскья'),

	(1590313064687461464, 401, 82, 'Чізкейк'),
	(1590313064687461464, 414, 82, 'Торт з вершкового сиру Філадельфія'),

	(1590313064687461465, 401, 82, 'Торт Естерхазі'),
	(1590313064687461465, 414, 82, 'Десерт з тонких повітряних тістечок, фундука і праліне волоського горіха'),

	(1590313064688510042, 401, 82, 'Тірамісу'),
	(1590313064688510042, 414, 82, 'Класичний десерт з кавового печива, просоченого лікером і еспрессо, під шаром повітряного сирного крему'),

	(1590313064688510043, 401, 82, 'Сирники'),
	(1590313064688510043, 414, 82, 'Подаються з фруктовим джемом та полуничним соусом'),
	
	(1590313064688510044, 401, 82, 'Фанта'),
	(1590313064688510044, 414, 82, '250 мл'),

	(1590313064688510045, 401, 82, 'Спрайт'),
	(1590313064688510045, 414, 82, '250 мл'),

	(1590313064688510046, 401, 82, 'Апельсиновий сік'),
	(1590313064688510046, 414, 82, 'Свіжовичавлений апельсиновий сік, 500 мл'),

	(1590313064688510047, 401, 82, 'Швепс'),
	(1590313064688510047, 414, 82, '250, мл'),

	(1590313064688510048, 401, 82, 'Кока-Кола'),
	(1590313064688510048, 414, 82, '1000 мл'),

	(3590313064688510048, 401, 81, 'ABSOLUT KURANT'),
	(3590313064688510048, 414, 81, 'ABSOLUT KURANT, 40%, 0.7Л '),
	(3590313064688510048, 401, 82, 'ABSOLUT KURANT'),
	(3590313064688510048, 414, 82, 'ABSOLUT KURANT, 40%, 0.7Л '),

	(4590313064688510048, 401, 81, 'KENTUCKY GENTLEMAN'),
	(4590313064688510048, 414, 81, 'KENTUCKY GENTLEMAN, 40%, 0.75Л'),
	(4590313064688510048, 401, 82, 'KENTUCKY GENTLEMAN'),
	(4590313064688510048, 414, 82, 'KENTUCKY GENTLEMAN, 40%, 0.75Л'),

	(2590313064688510048, 401, 81, 'PILS, KROMBACHER'),
	(2590313064688510048, 414, 81, 'PILS, KROMBACHER, 4.8%, 500 ml'),
	(2590313064688510048, 401, 82, 'PILS, KROMBACHER'),
	(2590313064688510048, 414, 82, 'PILS, KROMBACHER, 4.8%, 500 ml'),

	(811, 702, 81, 'Пицца'),
	(811, 702, 82, 'Піца'),

	(812, 702, 81, 'Суши'),
	(812, 702, 82, 'Суші'),

	(813, 702, 81, 'Бургеры'),
	(813, 702, 82, 'Бургери'),

	(814, 702, 81, 'Салаты'),
	(814, 702, 82, 'Салати'),

	(815, 702, 81, 'Закуски'),
	(815, 702, 82, 'Закуски'),

	(816, 702, 81, 'Десерты'),
	(816, 702, 82, 'Десерти'),

	(817, 702, 81, 'Напитки'),
	(817, 702, 82, 'Напої'),

	(820, 702, 81, 'Алкоголь'),
	(820, 702, 82, 'Алкоголь'),
	
	(818, 703, 81, 'Оплата наличными'),
	(818, 703, 82, 'Оплата готівкою'),

	(819, 703, 81, 'Оплата картой онлайн'),
	(819, 703, 82, 'Оплата картою онлайн'),

	(803, 701, 81, 'В обработке'),
	(803, 701, 82, 'В обробці'),

	(805, 701, 81, 'Принят курьером'),
	(805, 701, 82, 'Прийнято кур`єром'),

	(806, 701, 81, 'Доставлен'),
	(806, 701, 82, 'Доставлений'),

	(808, 701, 81, 'Просрочен'),
	(808, 701, 82, 'Прострочений'),

	(809, 701, 81, 'Отменен'),
	(809, 701, 82, 'Скасований');