function getNotificationString(stringId) {
    switch (stringId) {
        case 'role_changed':
            return 'Роль изменена.';
        case 'item_added':
            return 'Товар добавлен.';
        case 'user_created':
            return 'Пользователь создан.';
    }
}
function getErrorString(stringId) {
    switch (stringId) {
        case 'clarify_address':
            return 'Адрес требует уточнения.';
        case 'address_is_not_found':
            return 'Адрес не найден.';
        case 'phone_is_not_valid':
            return 'Неверный номер телефона.';
        case 'address_is_already_exists':
            return 'Адрес уже привязан к профилю.';
        case 'full_name_must_not_be_empty':
            return 'Полное имя не может быть пустым.';
        case 'login_must_not_be_empty':
            return 'Имя пользователя не может быть пустым.';
        case 'login_is_already_in_use':
            return 'Это имя пользователя уже используется.';
        case 'email_must_not_be_empty':
            return "E-mail не может быть пустым.";
        case 'email_is_not_valid':
            return "Неправильный E-mail.";
        case 'email_is_already_in_use':
            return 'Данный E-mail уже используется.';
        case 'birthday_is_not_valid':
            return 'Неверная дата рождения.';
        case 'password_must_not_be_empty':
            return 'Пароль не может быть пустым.';
        case 'old_password_must_not_be_empty':
            return 'Поле не может быть пустым.';
        case 'old_password_is_not_correct':
            return 'Неверный пароль.';
        case 'password_must_contain':
            return 'Пароль должен содержать более 6 символов, как минимум одну заглавную и одну строчную букву, а также одну цифру';
        case 'passwords_do_not_match':
            return 'Пароли не совпадают.';
        case 'password_confirmation_must_not_be_empty':
            return 'Поле не может быть пустым.';
        case 'payment_error':
            return 'Во время оплаты произошла ошибка.';
        case 'user_not_created':
            return 'Пользователь не был создан.';
    }
}
