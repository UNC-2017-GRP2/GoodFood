function dropdownButton(userId, buttonId) {
    $.ajax({
        url : 'changeRole',
        type: 'GET',
        /*contentType: 'application/json',
        mimeType: 'application/json',*/
        data : ({
            userId: userId,
            role: $('#' + buttonId).val()
        }),
        success: function (data) {
            $.notify(getNotificationString('role_changed'), "success");
        }
    });
}

function selectOption(elementId, value) {
    document.getElementById(elementId).value = value;
}
