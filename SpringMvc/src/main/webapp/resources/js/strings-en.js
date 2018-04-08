function getNotificationString(stringId) {
    switch (stringId) {
        case 'role_changed':
            return 'Role changed.';
        case 'item_added':
            return 'Item added.';
        case 'payment_error':
            return 'There was an error during payment.';
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
    }
}
