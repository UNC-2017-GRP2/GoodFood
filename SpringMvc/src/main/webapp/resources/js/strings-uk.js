function getNotificationString(stringId) {
    switch (stringId) {
        case 'role_changed':
            return 'Роль змінено.';
        case 'item_added':
            return 'Товар додано.';
        case 'user_created':
            return 'Аккаунт було створено.';

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
    }
}
