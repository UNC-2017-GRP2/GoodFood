function getNotificationString(stringId) {
    switch (stringId) {
        case 'role_changed':
            return 'Role changed.';
        case 'item_added':
            return 'Item added.';
        case 'user_created':
            return 'User created.';
        case 'user_deleted':
            return 'User deleted.';
    }
}

function getErrorString(stringId) {
    switch (stringId) {
        case 'clarify_address':
            return 'Inaccurate address, clarification required.';
        case 'address_is_not_found':
            return 'The address is not found.';
        case 'phone_is_not_valid':
            return 'Phone is not valid.';
        case 'address_is_already_exists':
            return 'The address is already exists.';
        case 'full_name_must_not_be_empty':
            return 'Full name must not be empty.';
        case 'login_must_not_be_empty':
            return 'Username must not be empty.';
        case 'login_is_already_in_use':
            return 'The username is already in use';
        case 'email_must_not_be_empty':
            return "E-mail must not be empty.";
        case 'email_is_not_valid':
            return "The e-mail is not valid";
        case 'email_is_already_in_use':
            return 'The e-mail is already in use.';
        case 'birthday_is_not_valid':
            return 'The birthday is not valid.';
        case 'phone_is_not_valid':
            return 'The phone is not valid.';
        case 'password_must_not_be_empty':
            return 'Password must not be empty.';
        case 'old_password_must_not_be_empty':
            return 'Old password must not be empty.';
        case 'old_password_is_not_correct':
            return 'Old password is not correct.';
        case 'password_must_contain':
            return 'Password must contain from 6 characters, one capital letter,one lower case letter and one number';
        case 'passwords_do_not_match':
            return 'Passwords donʼt match.';
        case 'password_confirmation_must_not_be_empty':
            return 'Confirm password must not be empty.';
        case 'payment_error':
            return 'There was an error during payment.';
        case 'user_not_created':
            return 'User was not created.';
        case 'user_not_deleted':
            return 'User was not deleted.';
        case 'role_not_changed':
            return 'Role was not changed.';
        case 'no_results':
            return 'No results found.';
        case 'data_error':
            return 'Data error.'
    }
}

function getLocStrings(stringId) {
    switch (stringId) {
        case 'addresses':
            return 'Addresses';
        case 'ROLE_COURIER':
            return 'Courier';
        case 'ROLE_ADMIN':
            return 'Administrator';
        case 'ROLE_USER':
            return 'User';
        case 'change_role':
            return 'Change role';
        case 'del_user':
            return 'Del';
        case 'product_order_statistics':
            return 'Product order statistics';
        case 'revenue_day':
            return 'Revenue by day of the week';
        case 'revenue_last_days':
            return 'Revenue for the last 10 days';
        case 'revenue':
            return 'Revenue';
        case 'day_of_the_week':
            return 'Day of the week';
        case 'day':
            return 'Day';
        case 'order_paid':
            return 'Order has been paid';
        case 'order_not_paid':
            return 'Order not paid';
        case 'items_count':
            return 'pcs';
        case 'rub':
            return '₽';
        case 'multiplication_sign':
            return '×';
    }
}
