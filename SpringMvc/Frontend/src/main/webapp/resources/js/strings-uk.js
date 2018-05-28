function getNotificationString(stringId) {
    switch (stringId) {
        case 'role_changed':
            return 'Роль змінено.';
        case 'item_added':
            return 'Товар додано.';
        case 'user_created':
            return 'Аккаунт було створено.';
        case 'user_deleted':
            return 'Користувача видалено.';
        case 'item_deleted':
            return 'Продукт видалений.';
        case 'order_deleted':
            return 'Замовлення видалений.';
        case 'items_was_saved':
            return 'Елементи успішно збережені.';
        case 'courier_appointed':
            return 'Кур`єр був призначений.';
    }
}

function getErrorString(stringId) {
    switch (stringId) {
        case 'clarify_address':
            return 'Адреса потребує уточнення.';
        case 'address_is_not_found':
            return 'Адресу не знайдено.';
        case 'phone_is_not_valid':
            return 'Неправильиий номер телефону.';
        case 'address_is_already_exists':
            return 'Адресу вже привʼязано до профілю.';
        case 'full_name_must_not_be_empty':
            return 'Повне імʼя не має бути порожнім.';
        case 'login_must_not_be_empty':
            return 'Імʼя користувача не має бути порожнім.';
        case 'login_is_already_in_use':
            return 'Це імʼя користувача вже використовується';
        case 'email_must_not_be_empty':
            return "Адреса е-пошти не має бути порожньою";
        case 'email_is_not_valid':
            return "Неправильиа адреса е-пошти";
        case 'email_is_already_in_use':
            return 'Ця адреса е-пошти вже використовується.';
        case 'birthday_is_not_valid':
            return 'Неправильиа дата народження.';
        case 'password_must_not_be_empty':
            return 'Пароль не має бути порожнім.';
        case 'old_password_must_not_be_empty':
            return 'Це поле не має бути порожнім.';
        case 'old_password_is_not_correct':
            return 'Неправильиий старий пароль';
        case 'password_must_contain':
            return 'Пароль має містити більше за 6 символів, щонайменш одну велику та одну малу літеру, а також одну цифру.';
        case 'passwords_do_not_match':
            return 'Паролі не збігаються.';
        case 'password_confirmation_must_not_be_empty':
            return 'Це поле не має бути порожнім.';
        case 'payment_error':
            return 'Під час оплати сталася помилка';
        case 'user_not_created':
            return 'Нема був створений.';
        case 'user_not_deleted':
            return 'Нема був видалений.';
        case 'role_not_changed':
            return 'Роль не була змінена';
        case 'no_results':
            return 'Нічого не знайдено.';
        case 'data_error':
            return 'Помилка даних.';
        case 'item_not_deleted':
            return 'Елемент не був видалений.';
        case 'order_not_deleted':
            return 'Замовлення не був видалений.';
        case 'invalid_file':
            return 'Це не файл .xls або .xlsx.';
        case 'invalid_format_file':
            return 'Невірний формат документа або його даних.';
        case 'change_must_be_greater':
            return 'Значення поля повинно перевищувати загальну вартість замовлення';
    }
}

function getLocStrings(stringId) {
    switch (stringId) {
        case 'addresses':
            return 'Адреси';
        case 'ROLE_COURIER':
            return 'Курʼєр';
        case 'ROLE_ADMIN':
            return 'Адміністратор';
        case 'ROLE_USER':
            return 'Користувач';
        case 'change_role':
            return 'Змінити роль';
        case 'del_user':
            return 'Видалити';
        case 'product_order_statistics':
            return 'Статистика замовлення товару';
        case 'revenue_day':
            return 'Дохід по днях тижня';
        case 'revenue_last_days':
            return 'Дохід за останні 10 днів';
        case 'revenue':
            return 'Дохід';
        case 'day_of_the_week':
            return 'День тижня';
        case 'day':
            return 'День';
        case 'order_paid':
            return 'Замовлення оплачено';
        case 'order_not_paid':
            return 'Замовлення не сплачено';
        case 'items_count':
            return 'шт';
        case 'rub':
            return '₽';
        case 'multiplication_sign':
            return '×';
        case 'search':
            return 'Пошук:';
        case 'show':
            return "Показати";
        case 'entries':
            return 'записів';
        case 'no_records found':
            return 'Збігів, не знайдено';
        case 'showing_page':
            return 'Показана сторінка';
        case 'page_of':
            return 'з';
        case 'no_records_available':
            return 'Немає доступних записів';
        case 'filtered_from':
            return 'відфільтровано з';
        case 'total_records':
            return 'записів';
        case 'processing':
            return 'Виконується обробка';
        case 'loading_records':
            return 'Завантаження';
        case 'empty_table':
            return 'Порожня таблиця';
        case 'previous':
            return 'Попередня';
        case 'next':
            return 'Наступна';
        case 'first':
            return 'Перша';
        case 'last':
            return 'Остання';
        case 'sort_ascending':
            return 'Сортувати по зростанню';
        case 'sort_descending':
            return 'Сортувати по спадаючій';
        case 'change_from':
            return 'Потрібна здача з';
        case 'status_with_courier':
            return 'Прийнято кур`єром';
    }
}