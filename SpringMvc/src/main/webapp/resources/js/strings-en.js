function getString(stringId) {
    switch (stringId) {
        case 'error_clarify_address':
            return 'Inaccurate address, clarification required.';
        case 'error_address_not_found':
            return 'The address is not found.';
        case 'error_phone_is_not_valid':
            return 'Phone is not valid.';
        case 'error_address_already_exists':
            return 'The address is already exists.';
        case 'error_full_name_must_not_be_empty':
            return 'Full name must not be empty.';
        case 'error_login_must_not_be_empty':
            return 'Username must not be empty.';
        case 'error_login_already_in_use':
            return 'The username is already in use';
        case 'email_must_not_be_empty':
            return "E-mail must not be empty.";
        case 'email_is_not_valid':
            return "The e-mail is not valid";
        case 'email_already_in_use':
            return 'The e-mail is already in use.';
        case 'birthday_is_not_valid':
            return 'The birthday is not valid.';
    }
}
